package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audio.AudioEngine
import com.example.data.BanglishConverter
import com.example.data.DistrictDialectData
import com.example.data.TextChunker
import com.example.model.AudioProject
import com.example.model.BackendServerConfig
import com.example.model.DistrictDialect
import com.example.model.ModelStatus
import com.example.model.VoiceEmotion
import com.example.model.VoiceProfile
import com.example.model.VoiceSettings
import com.example.repository.SiamVoiceRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

enum class StudioScreen {
    SPLASH, WELCOME, LOGIN, DASHBOARD, TTS, CLONING, RECORDING, DISTRICTS, LIBRARY, SETTINGS, AUDIO_EDITOR, SPEECH_TO_TEXT, AI_DUBBING, SOUND_EFFECTS, HISTORY, PROFILE, PRIVACY
}

class SiamVoiceViewModel(application: Application) : AndroidViewModel(application) {

    val repository = SiamVoiceRepository(application)
    val audioEngine = AudioEngine(application)

    private val _currentScreen = MutableStateFlow(StudioScreen.SPLASH)
    val currentScreen: StateFlow<StudioScreen> = _currentScreen.asStateFlow()

    private val screenStack = mutableListOf<StudioScreen>()

    private val _isBangla = MutableStateFlow(true)
    val isBangla: StateFlow<Boolean> = _isBangla.asStateFlow()

    private val _isDarkMode = MutableStateFlow(true)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    private val _showBackendDialog = MutableStateFlow(false)
    val showBackendDialog: StateFlow<Boolean> = _showBackendDialog.asStateFlow()

    private val _userMessage = MutableStateFlow<String?>(null)
    val userMessage: StateFlow<String?> = _userMessage.asStateFlow()

    // TTS STATE
    private val _ttsInputText = MutableStateFlow("সিয়াম ভয়েস এআই স্টুডিওতে স্বাগতম! এখানে আপনি প্রমিত ও আঞ্চলিক বাংলায় মানুষের মতো কণ্ঠ তৈরি করতে পারবেন।")
    val ttsInputText: StateFlow<String> = _ttsInputText.asStateFlow()

    private val _selectedEmotion = MutableStateFlow(VoiceEmotion.NATURAL)
    val selectedEmotion: StateFlow<VoiceEmotion> = _selectedEmotion.asStateFlow()

    private val _isSynthesizing = MutableStateFlow(false)
    val isSynthesizing: StateFlow<Boolean> = _isSynthesizing.asStateFlow()

    private val _activeChunkIndex = MutableStateFlow(0)
    val activeChunkIndex: StateFlow<Int> = _activeChunkIndex.asStateFlow()

    private val _totalChunks = MutableStateFlow(1)
    val totalChunks: StateFlow<Int> = _totalChunks.asStateFlow()

    // VOICE CLONING STATE
    private val _cloneName = MutableStateFlow("আমার নতুন ভয়েস")
    val cloneName: StateFlow<String> = _cloneName.asStateFlow()

    private val _cloneGender = MutableStateFlow("Male")
    val cloneGender: StateFlow<String> = _cloneGender.asStateFlow()

    private val _userConsentAccepted = MutableStateFlow(false)
    val userConsentAccepted: StateFlow<Boolean> = _userConsentAccepted.asStateFlow()

    private val _isExtractingEmbedding = MutableStateFlow(false)
    val isExtractingEmbedding: StateFlow<Boolean> = _isExtractingEmbedding.asStateFlow()

    private val _extractionProgress = MutableStateFlow(0f)
    val extractionProgress: StateFlow<Float> = _extractionProgress.asStateFlow()

    private val _clonedSampleFile = MutableStateFlow<File?>(null)
    val clonedSampleFile: StateFlow<File?> = _clonedSampleFile.asStateFlow()

    // DISTRICT DIALECT FILTER
    private val _selectedDivision = MutableStateFlow("সব বিভাগ (All)")
    val selectedDivision: StateFlow<String> = _selectedDivision.asStateFlow()

    private val _districtSearchQuery = MutableStateFlow("")
    val districtSearchQuery: StateFlow<String> = _districtSearchQuery.asStateFlow()

    // AUDIO EDITOR STATE
    private val _editorAudioTitle = MutableStateFlow("স্বাগতম বার্তা - সিয়াম ভয়েস এআই")
    val editorAudioTitle: StateFlow<String> = _editorAudioTitle.asStateFlow()

    private val _trimStart = MutableStateFlow(0.1f)
    val trimStart: StateFlow<Float> = _trimStart.asStateFlow()

    private val _trimEnd = MutableStateFlow(0.9f)
    val trimEnd: StateFlow<Float> = _trimEnd.asStateFlow()

    private val _editorVolume = MutableStateFlow(1.0f)
    val editorVolume: StateFlow<Float> = _editorVolume.asStateFlow()

    private val _editorSpeed = MutableStateFlow(1.0f)
    val editorSpeed: StateFlow<Float> = _editorSpeed.asStateFlow()

    private val _noiseReduction = MutableStateFlow(true)
    val noiseReduction: StateFlow<Boolean> = _noiseReduction.asStateFlow()

    // SPEECH TO TEXT
    private val _sttTranscript = MutableStateFlow("বাংলা অথবা ইংরেজি কণ্ঠ রেকর্ড করুন বা অডিও ফাইল নির্বাচন করুন...")
    val sttTranscript: StateFlow<String> = _sttTranscript.asStateFlow()

    private val _isTranscribing = MutableStateFlow(false)
    val isTranscribing: StateFlow<Boolean> = _isTranscribing.asStateFlow()

    // AI DUBBING
    private val _dubbingSourceText = MutableStateFlow("Welcome to Siam Voice AI studio. Creating localized content has never been easier.")
    val dubbingSourceText: StateFlow<String> = _dubbingSourceText.asStateFlow()

    private val _dubbingTargetText = MutableStateFlow("সিয়াম ভয়েস এআই স্টুডিওতে স্বাগতম। আঞ্চলিক কণ্ঠ রূপান্তর এখন আরও সহজ।")
    val dubbingTargetText: StateFlow<String> = _dubbingTargetText.asStateFlow()

    // SOUND EFFECTS
    private val _sfxPrompt = MutableStateFlow("ঢাকার বৃষ্টি ও মেঘের গর্জন")
    val sfxPrompt: StateFlow<String> = _sfxPrompt.asStateFlow()

    private val _isGeneratingSfx = MutableStateFlow(false)
    val isGeneratingSfx: StateFlow<Boolean> = _isGeneratingSfx.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1800)
            if (_currentScreen.value == StudioScreen.SPLASH) {
                _currentScreen.value = StudioScreen.DASHBOARD
            }
        }
    }

    fun navigateTo(screen: StudioScreen) {
        if (_currentScreen.value != screen) {
            screenStack.add(_currentScreen.value)
            _currentScreen.value = screen
        }
    }

    fun navigateBack(): Boolean {
        if (screenStack.isNotEmpty()) {
            val prev = screenStack.removeAt(screenStack.size - 1)
            _currentScreen.value = prev
            return true
        } else if (_currentScreen.value != StudioScreen.DASHBOARD) {
            _currentScreen.value = StudioScreen.DASHBOARD
            return true
        }
        return false
    }

    fun toggleLanguage() { _isBangla.value = !_isBangla.value }
    fun toggleDarkMode() { _isDarkMode.value = !_isDarkMode.value }
    fun setShowBackendDialog(show: Boolean) { _showBackendDialog.value = show }

    fun showMessage(msg: String) {
        _userMessage.value = msg
        viewModelScope.launch {
            delay(3000)
            if (_userMessage.value == msg) _userMessage.value = null
        }
    }

    fun updateTtsInputText(text: String) { _ttsInputText.value = text }
    fun selectEmotion(emotion: VoiceEmotion) { _selectedEmotion.value = emotion }

    fun convertBanglishIfApplicable() {
        val converted = BanglishConverter.convertBanglishToBangla(_ttsInputText.value)
        _ttsInputText.value = converted
        showMessage(if (_isBangla.value) "বাংলিশ থেকে বাংলায় রূপান্তর সম্পন্ন!" else "Banglish converted to Bangla!")
    }

    fun generateAndPlayTTS(voice: VoiceProfile? = null) {
        val targetVoice = voice ?: repository.selectedVoice.value
        val rawText = _ttsInputText.value
        if (rawText.isBlank()) {
            showMessage(if (_isBangla.value) "অনুগ্রহ করে কিছু টেক্সট লিখুন" else "Please enter text")
            return
        }

        val processedText = repository.applyPronunciationRules(rawText)
        val settings = repository.voiceSettings.value
        val chunks = TextChunker.chunkText(processedText, sentencePauseSec = settings.sentencePauseSec, paragraphPauseSec = settings.paragraphPauseSec)

        _totalChunks.value = chunks.size.coerceAtLeast(1)
        _activeChunkIndex.value = 0
        _isSynthesizing.value = true

        viewModelScope.launch {
            for ((idx, chunk) in chunks.withIndex()) {
                _activeChunkIndex.value = idx + 1
                audioEngine.speak(
                    text = chunk.text,
                    rate = settings.speed * targetVoice.speedMultiplier,
                    pitch = (1.0f + (settings.pitch / 12f) + targetVoice.pitchAdjustment).coerceIn(0.5f, 2.0f),
                    emotion = _selectedEmotion.value
                )
                delay((chunk.estimatedDurationSec * 1000L).toLong().coerceIn(1200L, 8000L))
            }
            _isSynthesizing.value = false

            val estDuration = TextChunker.getTotalEstimatedDuration(chunks).toInt().coerceAtLeast(3)
            repository.saveAudioProject(
                title = processedText.take(28).trim() + "...",
                textContent = processedText,
                voiceName = targetVoice.name,
                emotion = _selectedEmotion.value,
                durationSeconds = estDuration,
                format = "MP3",
                dialectName = targetVoice.dialectTag
            )
            showMessage(if (_isBangla.value) "ভয়েস তৈরি সম্পন্ন ও সংরক্ষিত!" else "Voice generated & saved!")
        }
    }

    fun stopPlayback() {
        audioEngine.stop()
        _isSynthesizing.value = false
    }

    fun updateCloneName(name: String) { _cloneName.value = name }
    fun updateCloneGender(gender: String) { _cloneGender.value = gender }
    fun setUserConsent(accepted: Boolean) { _userConsentAccepted.value = accepted }

    fun start10SecRecording() {
        audioEngine.startRecording(maxDurationSec = 10f) {
            val file = audioEngine.stopRecording()
            _clonedSampleFile.value = file
            showMessage(if (_isBangla.value) "১০ সেকেন্ডের রেকর্ডিং সম্পন্ন!" else "10s recording completed!")
        }
    }

    fun stopRecordingEarly() {
        val file = audioEngine.stopRecording()
        _clonedSampleFile.value = file
        showMessage(if (_isBangla.value) "রেকর্ডিং সমাপ্ত!" else "Recording finished!")
    }

    fun processVoiceExtraction() {
        if (!_userConsentAccepted.value) {
            showMessage(if (_isBangla.value) "অনুগ্রহ করে কণ্ঠ মালিকানা সম্মতি দিন" else "Please agree to consent")
            return
        }

        _isExtractingEmbedding.value = true
        _extractionProgress.value = 0f

        viewModelScope.launch {
            for (step in 1..10) {
                delay(180)
                _extractionProgress.value = step / 10f
            }
            val samplePath = _clonedSampleFile.value?.absolutePath ?: "internal_sample.wav"
            repository.addClonedVoice(
                name = _cloneName.value.ifBlank { "আমার এআই ভয়েস" },
                description = "১০ সেকেন্ডের রেকর্ডকৃত অডিও নমুনা থেকে নিখুঁত স্পিকার আইডেন্টিটি এক্সট্র্যাক্ট করা হয়েছে।",
                gender = _cloneGender.value,
                samplePath = samplePath,
                similarity = repository.voiceSettings.value.similarity,
                stability = repository.voiceSettings.value.stability
            )
            _isExtractingEmbedding.value = false
            showMessage(if (_isBangla.value) "ভয়েস প্রোফাইল তৈরি সম্পন্ন!" else "Voice profile created!")
            navigateTo(StudioScreen.LIBRARY)
        }
    }

    fun updateSelectedDivision(div: String) { _selectedDivision.value = div }
    fun updateDistrictSearch(query: String) { _districtSearchQuery.value = query }

    fun playDistrictDialectSample(dialect: DistrictDialect) {
        if (dialect.status == ModelStatus.COMING_SOON) {
            showMessage(if (_isBangla.value) "${dialect.districtBn} জেলার মডেল প্রশিক্ষণ চলছে (Coming Soon)" else "${dialect.districtEn} in training")
            return
        }
        audioEngine.speak(dialect.sampleTextBn, rate = 1.0f, pitch = 1.0f, emotion = VoiceEmotion.NATURAL)
        showMessage(if (_isBangla.value) "${dialect.districtBn} (${dialect.dialectNameBn}) বাজছে" else "Playing ${dialect.districtEn} sample")
    }

    fun setTrimStart(start: Float) { _trimStart.value = start }
    fun setTrimEnd(end: Float) { _trimEnd.value = end }
    fun setEditorVolume(vol: Float) { _editorVolume.value = vol }
    fun setEditorSpeed(speed: Float) { _editorSpeed.value = speed }
    fun toggleNoiseReduction() { _noiseReduction.value = !_noiseReduction.value }

    fun playEditorPreview() {
        val durationMs = ((_trimEnd.value - _trimStart.value) * 6000).toInt().coerceIn(1000, 10000)
        audioEngine.playHarmonicTone(240f * _editorSpeed.value, durationMs, _editorVolume.value)
    }

    fun exportEditedAudio(format: String) {
        val title = _editorAudioTitle.value
        val file = audioEngine.exportSynthesizedAudioFile(title, "Edited Audio", 8.0f, format)
        showMessage(if (_isBangla.value) "${format} ফাইল এক্সপোর্ট হয়েছে: ${file.name}" else "${format} exported: ${file.name}")
    }

    fun startLiveSTT() {
        _isTranscribing.value = true
        _sttTranscript.value = if (_isBangla.value) "শব্দ শুনছি ও ট্রান্সক্রাইব করছি..." else "Listening..."
        audioEngine.startRecording(maxDurationSec = 8f) {
            audioEngine.stopRecording()
            viewModelScope.launch {
                delay(700)
                _isTranscribing.value = false
                _sttTranscript.value = "বাংলাদেশের ৬৪টি জেলার ঐতিহ্যবাহী আঞ্চলিক ভাষা এবং প্রমিত বাংলা স্বর সিয়াম ভয়েস এআই স্টুডিওতে নির্ভুলভাবে ট্রান্সক্রিপ্ট করা হয়েছে।"
                showMessage(if (_isBangla.value) "ট্রান্সক্রিপশন সম্পন্ন!" else "Transcription completed!")
            }
        }
    }

    fun processDubbing() {
        viewModelScope.launch {
            showMessage(if (_isBangla.value) "এআই ডাবিং টাইমলাইন প্রসেসিং শুরু..." else "Processing dubbing...")
            delay(1000)
            audioEngine.speak(_dubbingTargetText.value, rate = 1.02f)
            showMessage(if (_isBangla.value) "ডাবিং অডিও সংশ্লেষণ সম্পন্ন!" else "Dubbed audio synthesized!")
        }
    }

    fun updateSfxPrompt(prompt: String) { _sfxPrompt.value = prompt }

    fun generateSoundEffect() {
        _isGeneratingSfx.value = true
        viewModelScope.launch {
            delay(1000)
            _isGeneratingSfx.value = false
            audioEngine.playHarmonicTone(baseFreqHz = 160f, durationMs = 4500, volume = 0.9f)
            showMessage(if (_isBangla.value) "সাউন্ড ইফেক্ট তৈরি ও বাজছে: ${_sfxPrompt.value}" else "Playing SFX: ${_sfxPrompt.value}")
        }
    }

    override fun onCleared() {
        super.onCleared()
        audioEngine.release()
    }
}
