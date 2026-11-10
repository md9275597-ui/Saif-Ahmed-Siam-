package com.example.repository

import android.content.Context
import com.example.data.DistrictDialectData
import com.example.data.PretrainedVoices
import com.example.model.AudioProject
import com.example.model.BackendServerConfig
import com.example.model.DistrictDialect
import com.example.model.DubbingSegment
import com.example.model.PronunciationRule
import com.example.model.SoundEffectItem
import com.example.model.VoiceEmotion
import com.example.model.VoiceProfile
import com.example.model.VoiceSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class SiamVoiceRepository(private val context: Context) {

    private val _voiceProfiles = MutableStateFlow<List<VoiceProfile>>(
        PretrainedVoices.masterVoices + listOf(
            VoiceProfile(
                id = "cloned_user_01",
                name = "আমার কণ্ঠ - নমুনা ১ (My Voice)",
                description = "১০ সেকেন্ডের রেকর্ডকৃত অডিও থেকে নির্গত নিজস্ব স্পিকার এমবেডিং।",
                gender = "Male",
                isCloned = true,
                isFavorite = true,
                dialectTag = "নিজস্ব ক্লোন",
                similarityScore = 0.94f,
                stabilityScore = 0.88f
            )
        )
    )
    val voiceProfiles: StateFlow<List<VoiceProfile>> = _voiceProfiles.asStateFlow()

    private val _selectedVoice = MutableStateFlow<VoiceProfile>(PretrainedVoices.masterVoices.first())
    val selectedVoice: StateFlow<VoiceProfile> = _selectedVoice.asStateFlow()

    private val _districts = MutableStateFlow<List<DistrictDialect>>(DistrictDialectData.allDistricts)
    val districts: StateFlow<List<DistrictDialect>> = _districts.asStateFlow()

    private val _voiceSettings = MutableStateFlow(VoiceSettings())
    val voiceSettings: StateFlow<VoiceSettings> = _voiceSettings.asStateFlow()

    private val _audioHistory = MutableStateFlow<List<AudioProject>>(
        listOf(
            AudioProject("proj_01", "স্বাগতম বার্তা - সিয়াম ভয়েস এআই", "সিয়াম ভয়েস এআই স্টুডিওতে স্বাগতম।", "শফিক আহমেদ (সংবাদ পাঠক)", VoiceEmotion.PROFESSIONAL, 12, "MP3", 1024 * 380, System.currentTimeMillis(), true, "Standard"),
            AudioProject("proj_02", "চাটগাঁইয়া আঞ্চলিক স্বর পরীক্ষা", "অনা কেন আছন? আঁই গম আছি, আঁরার দেশত আইয়্যুন!", "চাটগাঁইয়া পুরুষ কণ্ঠ", VoiceEmotion.HAPPY, 8, "WAV", 1024 * 720, System.currentTimeMillis(), false, "চট্টগ্রাম (চাটগাঁইয়া)"),
            AudioProject("proj_03", "১০ সেকেন্ডের ভয়েস ক্লোন টেস্ট", "আমার কণ্ঠ এখন যেকোনো বাংলা লেখা নিখুঁত স্পষ্টতায় পড়তে সক্ষম।", "আমার কণ্ঠ - নমুনা ১", VoiceEmotion.NATURAL, 6, "MP3", 1024 * 190, System.currentTimeMillis(), true, "Personal Clone")
        )
    )
    val audioHistory: StateFlow<List<AudioProject>> = _audioHistory.asStateFlow()

    private val _pronunciationRules = MutableStateFlow<List<PronunciationRule>>(
        listOf(
            PronunciationRule("pr_1", "এআই", "এ-আই"),
            PronunciationRule("pr_2", "সিয়াম", "সি-য়াম"),
            PronunciationRule("pr_3", "ElevenLabs", "ইলেভেন-ল্যাবস"),
            PronunciationRule("pr_4", "ChatGPT", "চ্যাট-জি-পি-টি"),
            PronunciationRule("pr_5", "FastAPI", "ফাস্ট-এপিআই")
        )
    )
    val pronunciationRules: StateFlow<List<PronunciationRule>> = _pronunciationRules.asStateFlow()

    private val _dubbingSegments = MutableStateFlow<List<DubbingSegment>>(
        listOf(
            DubbingSegment("dub_1", "Welcome to modern AI voice studio.", "আধুনিক এআই ভয়েস স্টুডিওতে স্বাগতম।", "English", "Bangla", 0f, 3.2f),
            DubbingSegment("dub_2", "High accuracy voice synthesis for all 64 districts.", "৬৪টি জেলার জন্য উচ্চ নির্ভুলতার আঞ্চলিক কণ্ঠ সংশ্লেষণ।", "English", "Bangla", 3.5f, 7.8f),
            DubbingSegment("dub_3", "Create your personal clone in just 10 seconds.", "মাত্র ১০ সেকেন্ডে আপনার নিজস্ব কণ্ঠের ক্লোন তৈরি করুন।", "English", "Bangla", 8.0f, 11.5f)
        )
    )
    val dubbingSegments: StateFlow<List<DubbingSegment>> = _dubbingSegments.asStateFlow()

    private val _soundEffects = MutableStateFlow<List<SoundEffectItem>>(
        listOf(
            SoundEffectItem("sfx_1", "ঢাকার বৃষ্টি ও দূরবর্তী মেঘের গর্জন", "ঢাকার বৃষ্টি ও বজ্রপাত", 6.5f, "প্রকৃতি (Nature)"),
            SoundEffectItem("sfx_2", "বাউল একতারা ও শান্ত বাঁশির সুর", "একতারা ও বাঁশির সুর", 10.0f, "সংগীত (Acoustic)"),
            SoundEffectItem("sfx_3", "নিউমার্কেট ফুটপাতের কোলাহল ও চা দোকানের শব্দ", "ঢাকার চা দোকান ও কোলাহল", 8.0f, "নগর পরিবেশ (Ambient)"),
            SoundEffectItem("sfx_4", "রিকশার টুংটাং বেল ও সন্ধ্যার বাতাস", "রিকশার টুংটাং ঘণ্টা", 5.2f, "ঐতিহ্যবাহী (City Life)"),
            SoundEffectItem("sfx_5", "পদ্মা নদীর ঢেউ ও নৌকার বৈঠার শব্দ", "নদীর ঢেউ ও বৈঠার শব্দ", 12.0f, "নদীমাতৃক (River)")
        )
    )
    val soundEffects: StateFlow<List<SoundEffectItem>> = _soundEffects.asStateFlow()

    private val _backendConfig = MutableStateFlow(BackendServerConfig())
    val backendConfig: StateFlow<BackendServerConfig> = _backendConfig.asStateFlow()

    private val _charactersUsedToday = MutableStateFlow(4280)
    val charactersUsedToday: StateFlow<Int> = _charactersUsedToday.asStateFlow()

    private val _generationsCount = MutableStateFlow(18)
    val generationsCount: StateFlow<Int> = _generationsCount.asStateFlow()

    fun selectVoice(voice: VoiceProfile) { _selectedVoice.value = voice }

    fun addClonedVoice(name: String, description: String, gender: String, samplePath: String, similarity: Float = 0.95f, stability: Float = 0.85f): VoiceProfile {
        val newVoice = VoiceProfile(
            id = "clone_${UUID.randomUUID().toString().take(8)}",
            name = name,
            description = description,
            gender = gender,
            samplePath = samplePath,
            isCloned = true,
            isFavorite = true,
            dialectTag = "ব্যক্তিগত ক্লোন",
            similarityScore = similarity,
            stabilityScore = stability
        )
        _voiceProfiles.value = _voiceProfiles.value + newVoice
        _selectedVoice.value = newVoice
        return newVoice
    }

    fun deleteVoice(voiceId: String) {
        _voiceProfiles.value = _voiceProfiles.value.filterNot { it.id == voiceId }
        if (_selectedVoice.value.id == voiceId) {
            _selectedVoice.value = _voiceProfiles.value.firstOrNull() ?: PretrainedVoices.masterVoices.first()
        }
    }

    fun toggleFavoriteVoice(voiceId: String) {
        _voiceProfiles.value = _voiceProfiles.value.map {
            if (it.id == voiceId) it.copy(isFavorite = !it.isFavorite) else it
        }
    }

    fun renameVoice(voiceId: String, newName: String) {
        _voiceProfiles.value = _voiceProfiles.value.map {
            if (it.id == voiceId) it.copy(name = newName) else it
        }
    }

    fun updateSettings(settings: VoiceSettings) { _voiceSettings.value = settings }

    fun saveAudioProject(title: String, textContent: String, voiceName: String, emotion: VoiceEmotion, durationSeconds: Int, format: String = "MP3", dialectName: String = "Standard"): AudioProject {
        val project = AudioProject(
            id = "proj_${UUID.randomUUID().toString().take(8)}",
            title = title.ifBlank { "ভয়েস প্রজেক্ট #${_audioHistory.value.size + 1}" },
            textContent = textContent,
            voiceName = voiceName,
            emotion = emotion,
            durationSeconds = durationSeconds,
            format = format,
            fileSizeBytes = (durationSeconds * 32 * 1024L).coerceAtLeast(64000L),
            dialectName = dialectName
        )
        _audioHistory.value = listOf(project) + _audioHistory.value
        _charactersUsedToday.value += textContent.length
        _generationsCount.value += 1
        return project
    }

    fun deleteAudioProject(projectId: String) {
        _audioHistory.value = _audioHistory.value.filterNot { it.id == projectId }
    }

    fun toggleFavoriteProject(projectId: String) {
        _audioHistory.value = _audioHistory.value.map {
            if (it.id == projectId) it.copy(isFavorite = !it.isFavorite) else it
        }
    }

    fun addPronunciationRule(original: String, phonetic: String) {
        if (original.isBlank() || phonetic.isBlank()) return
        val rule = PronunciationRule("pr_${UUID.randomUUID().toString().take(6)}", original.trim(), phonetic.trim())
        _pronunciationRules.value = _pronunciationRules.value + rule
    }

    fun applyPronunciationRules(text: String): String {
        var processed = text
        for (rule in _pronunciationRules.value) {
            if (rule.isEnabled) {
                processed = processed.replace(rule.originalWord, rule.phoneticReplacement)
            }
        }
        return processed
    }

    fun updateBackendConfig(config: BackendServerConfig) { _backendConfig.value = config }
}
