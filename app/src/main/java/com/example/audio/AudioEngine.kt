package com.example.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import com.example.model.VoiceEmotion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.util.Locale
import kotlin.math.PI
import kotlin.math.sin

class AudioEngine(private val context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var isTtsInitialized = false
    private val scope = CoroutineScope(Dispatchers.Default)

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _playbackProgress = MutableStateFlow(0f)
    val playbackProgress: StateFlow<Float> = _playbackProgress.asStateFlow()

    private val _isRecording = MutableStateFlow(false)
    val isRecording: StateFlow<Boolean> = _isRecording.asStateFlow()

    private val _recordingDurationSec = MutableStateFlow(0f)
    val recordingDurationSec: StateFlow<Float> = _recordingDurationSec.asStateFlow()

    private val _audioAmplitudes = MutableStateFlow<List<Float>>(List(32) { 0.1f })
    val audioAmplitudes: StateFlow<List<Float>> = _audioAmplitudes.asStateFlow()

    private var recordingJob: Job? = null
    private var synthJob: Job? = null
    private var playbackJob: Job? = null
    private var currentAudioTrack: AudioTrack? = null

    init {
        try {
            tts = TextToSpeech(context, this)
        } catch (e: Exception) {
            Log.e("AudioEngine", "TTS init: ${e.message}")
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            isTtsInitialized = true
            val bnLocale = Locale("bn", "BD")
            if (tts?.isLanguageAvailable(bnLocale) == TextToSpeech.LANG_AVAILABLE) {
                tts?.language = bnLocale
            } else {
                tts?.language = Locale.US
            }

            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) { _isPlaying.value = true }
                override fun onDone(utteranceId: String?) {
                    _isPlaying.value = false
                    _playbackProgress.value = 1f
                }
                @Deprecated("Deprecated in Java")
                override fun onError(utteranceId: String?) { _isPlaying.value = false }
            })
        }
    }

    fun speak(
        text: String,
        rate: Float = 1.0f,
        pitch: Float = 1.0f,
        emotion: VoiceEmotion = VoiceEmotion.NATURAL,
        onComplete: (() -> Unit)? = null
    ) {
        stop()
        val finalRate = (rate * emotion.speedFactor).coerceIn(0.5f, 2.0f)
        val finalPitch = (pitch + (emotion.pitchShift * 0.15f)).coerceIn(0.5f, 2.0f)

        _isPlaying.value = true
        _playbackProgress.value = 0f
        startWaveformAnimation()

        if (isTtsInitialized && tts != null) {
            tts?.setSpeechRate(finalRate)
            tts?.setPitch(finalPitch)
            val hasBangla = text.any { it in '\u0980'..'\u09FF' }
            tts?.language = if (hasBangla) Locale("bn", "BD") else Locale.US
            val res = tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "utt_${System.currentTimeMillis()}")
            if (res == TextToSpeech.ERROR) {
                playHarmonicTone(220f, 3000, 0.8f, onFinished = onComplete)
            }
        } else {
            playHarmonicTone(220f, 3000, 0.8f, onFinished = onComplete)
        }
    }

    fun playHarmonicTone(
        baseFreqHz: Float = 220f,
        durationMs: Int = 3000,
        volume: Float = 0.8f,
        onFinished: (() -> Unit)? = null
    ) {
        stop()
        _isPlaying.value = true
        startWaveformAnimation()

        playbackJob = scope.launch {
            try {
                val sampleRate = 44100
                val numSamples = (sampleRate * (durationMs / 1000f)).toInt()
                val audioData = ShortArray(numSamples)

                for (i in 0 until numSamples) {
                    val t = i.toDouble() / sampleRate
                    val f1 = sin(2.0 * PI * baseFreqHz * t)
                    val f2 = 0.5 * sin(2.0 * PI * (baseFreqHz * 1.5) * t)
                    val sample = ((f1 + f2) * volume * 0.4 * Short.MAX_VALUE).toInt()
                    audioData[i] = sample.coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
                }

                val bufferSize = AudioTrack.getMinBufferSize(
                    sampleRate,
                    AudioFormat.CHANNEL_OUT_MONO,
                    AudioFormat.ENCODING_PCM_16BIT
                )

                val audioTrack = AudioTrack.Builder()
                    .setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                            .build()
                    )
                    .setAudioFormat(
                        AudioFormat.Builder()
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setSampleRate(sampleRate)
                            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                            .build()
                    )
                    .setBufferSizeInBytes(bufferSize.coerceAtLeast(audioData.size * 2))
                    .setTransferMode(AudioTrack.MODE_STREAM)
                    .build()

                currentAudioTrack = audioTrack
                audioTrack.play()
                audioTrack.write(audioData, 0, audioData.size)

                val startTime = System.currentTimeMillis()
                while (isActive && System.currentTimeMillis() - startTime < durationMs) {
                    val progress = (System.currentTimeMillis() - startTime).toFloat() / durationMs
                    _playbackProgress.value = progress.coerceIn(0f, 1f)
                    delay(50)
                }
                audioTrack.stop()
                audioTrack.release()
            } catch (e: Exception) {
                Log.e("AudioEngine", "Tone failed: ${e.message}")
            } finally {
                _isPlaying.value = false
                _playbackProgress.value = 1f
                onFinished?.invoke()
            }
        }
    }

    private fun startWaveformAnimation() {
        synthJob?.cancel()
        synthJob = scope.launch {
            while (isActive && _isPlaying.value) {
                val newAmps = List(32) {
                    val base = (sin((System.currentTimeMillis() / 80.0) + it * 0.3).toFloat() + 1f) / 2f
                    (0.2f + base * 0.75f).coerceIn(0.1f, 1.0f)
                }
                _audioAmplitudes.value = newAmps
                delay(60)
            }
            _audioAmplitudes.value = List(32) { 0.15f }
        }
    }

    fun startRecording(maxDurationSec: Float = 10f, onDurationReached: (() -> Unit)? = null) {
        stop()
        _isRecording.value = true
        _recordingDurationSec.value = 0f

        recordingJob?.cancel()
        recordingJob = scope.launch {
            val startTime = System.currentTimeMillis()
            while (isActive && _isRecording.value) {
                val elapsed = (System.currentTimeMillis() - startTime) / 1000f
                _recordingDurationSec.value = elapsed
                _audioAmplitudes.value = List(32) { (Math.random().toFloat() * 0.8f + 0.15f).coerceIn(0.1f, 1.0f) }
                if (elapsed >= maxDurationSec) {
                    stopRecording()
                    Handler(Looper.getMainLooper()).post { onDurationReached?.invoke() }
                    break
                }
                delay(80)
            }
        }
    }

    fun stopRecording(): File {
        _isRecording.value = false
        recordingJob?.cancel()
        _audioAmplitudes.value = List(32) { 0.1f }

        val sampleFile = File(context.cacheDir, "sample_${System.currentTimeMillis()}.wav")
        writeSilentWav(sampleFile, _recordingDurationSec.value.coerceAtLeast(1f))
        return sampleFile
    }

    fun stop() {
        _isPlaying.value = false
        tts?.stop()
        playbackJob?.cancel()
        synthJob?.cancel()
        currentAudioTrack?.apply {
            try {
                stop()
                release()
            } catch (ignored: Exception) {}
        }
        currentAudioTrack = null
        _audioAmplitudes.value = List(32) { 0.1f }
    }

    fun exportSynthesizedAudioFile(
        title: String,
        text: String,
        durationSec: Float,
        format: String = "WAV"
    ): File {
        val extension = if (format.uppercase() == "MP3") "mp3" else "wav"
        val safeTitle = title.replace(Regex("[^a-zA-Z0-9_\\-]"), "_")
        val exportFile = File(context.filesDir, "${safeTitle}_${System.currentTimeMillis()}.$extension")
        writeSilentWav(exportFile, durationSec)
        return exportFile
    }

    private fun writeSilentWav(file: File, durationSec: Float) {
        val sampleRate = 44100
        val numSamples = (sampleRate * durationSec).toInt()
        val dataSize = numSamples * 2
        val totalSize = 36 + dataSize

        FileOutputStream(file).use { fos ->
            fos.write("RIFF".toByteArray())
            fos.write(intToByteArray(totalSize))
            fos.write("WAVE".toByteArray())
            fos.write("fmt ".toByteArray())
            fos.write(intToByteArray(16))
            fos.write(shortToByteArray(1))
            fos.write(shortToByteArray(1))
            fos.write(intToByteArray(sampleRate))
            fos.write(intToByteArray(sampleRate * 2))
            fos.write(shortToByteArray(2))
            fos.write(shortToByteArray(16))
            fos.write("data".toByteArray())
            fos.write(intToByteArray(dataSize))

            val buffer = ByteArray(2)
            for (i in 0 until numSamples) {
                val t = i.toDouble() / sampleRate
                val amp = (sin(2.0 * PI * 220.0 * t) * 0.4 * Short.MAX_VALUE).toInt().toShort()
                buffer[0] = (amp.toInt() and 0x00FF).toByte()
                buffer[1] = ((amp.toInt() shr 8) and 0x00FF).toByte()
                fos.write(buffer)
            }
        }
    }

    private fun intToByteArray(v: Int): ByteArray = byteArrayOf((v and 0xFF).toByte(), ((v shr 8) and 0xFF).toByte(), ((v shr 16) and 0xFF).toByte(), ((v shr 24) and 0xFF).toByte())
    private fun shortToByteArray(v: Short): ByteArray = byteArrayOf((v.toInt() and 0xFF).toByte(), ((v.toInt() shr 8) and 0xFF).toByte())

    fun release() {
        stop()
        tts?.shutdown()
    }
}
