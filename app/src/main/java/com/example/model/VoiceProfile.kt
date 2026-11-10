package com.example.model

data class VoiceProfile(
    val id: String,
    val name: String,
    val description: String,
    val gender: String,
    val samplePath: String = "",
    val isCloned: Boolean = false,
    val isFavorite: Boolean = false,
    val dialectTag: String = "প্রমিত বাংলা (Standard)",
    val similarityScore: Float = 0.94f,
    val stabilityScore: Float = 0.85f,
    val pitchAdjustment: Float = 0.0f,
    val speedMultiplier: Float = 1.0f,
    val createdAt: Long = System.currentTimeMillis(),
    val embeddingPreview: List<Float> = listOf(0.12f, 0.45f, 0.78f, 0.32f, 0.91f, 0.55f, 0.23f, 0.87f)
)

enum class VoiceEmotion(
    val id: String,
    val banglaName: String,
    val englishName: String,
    val pitchShift: Float,
    val speedFactor: Float,
    val iconName: String
) {
    NATURAL("natural", "স্বাভাবিক", "Natural", 0.0f, 1.0f, "chat"),
    HAPPY("happy", "আনন্দিত", "Happy", 1.8f, 1.12f, "sentiment_very_satisfied"),
    SAD("sad", "দুঃখিত", "Sad", -1.5f, 0.88f, "sentiment_dissatisfied"),
    ROMANTIC("romantic", "রোমান্টিক", "Romantic", -0.5f, 0.92f, "favorite"),
    ANGRY("angry", "ক্রোধ / রাগ", "Angry", 2.0f, 1.25f, "flash_on"),
    EMOTIONAL("emotional", "আবেগাপ্লুত", "Emotional", -0.8f, 0.90f, "auto_awesome"),
    CALM("calm", "শান্ত", "Calm", -0.4f, 0.95f, "spa"),
    STORYTELLING("storytelling", "গল্প বলা", "Storytelling", 0.5f, 0.98f, "menu_book"),
    NEWS_PRESENTER("news", "সংবাদ পাঠক", "News Presenter", 0.2f, 1.05f, "record_voice_over"),
    PROFESSIONAL("professional", "পেশাদার", "Professional", 0.0f, 1.02f, "work")
}

data class VoiceSettings(
    val speed: Float = 1.0f,
    val pitch: Float = 0.0f,
    val stability: Float = 0.75f,
    val similarity: Float = 0.85f,
    val styleExaggeration: Float = 0.20f,
    val sentencePauseSec: Float = 0.35f,
    val paragraphPauseSec: Float = 0.75f,
    val volume: Float = 1.0f,
    val clarity: Float = 0.90f,
    val clarityFilter: Boolean = true,
    val automaticChunking: Boolean = true
)

data class AudioProject(
    val id: String,
    val title: String,
    val textContent: String,
    val voiceName: String,
    val emotion: VoiceEmotion,
    val durationSeconds: Int,
    val format: String = "MP3",
    val fileSizeBytes: Long = 1024 * 320,
    val createdAt: Long = System.currentTimeMillis(),
    val isFavorite: Boolean = false,
    val dialectName: String = "Standard"
)

data class PronunciationRule(
    val id: String,
    val originalWord: String,
    val phoneticReplacement: String,
    val isEnabled: Boolean = true
)

data class DubbingSegment(
    val id: String,
    val originalText: String,
    val translatedText: String,
    val sourceLanguage: String,
    val targetLanguage: String,
    val startTimeSec: Float,
    val endTimeSec: Float
)

data class SoundEffectItem(
    val id: String,
    val prompt: String,
    val banglaTitle: String,
    val durationSec: Float,
    val category: String,
    val audioUrl: String = ""
)

data class BackendServerConfig(
    val serverUrl: String = "http://10.0.2.2:8000",
    val isConnected: Boolean = true,
    val activeModel: String = "IndicF5 + VITS Bangla v2.4",
    val gpuType: String = "NVIDIA RTX 4090 (24GB VRAM)",
    val queueCount: Int = 0,
    val latencyMs: Int = 142,
    val useLocalFallback: Boolean = true
)
