package com.example.model

enum class ModelStatus {
    READY,
    COMING_SOON
}

data class DistrictDialect(
    val id: String,
    val districtBn: String,
    val districtEn: String,
    val divisionBn: String,
    val divisionEn: String,
    val dialectNameBn: String,
    val sampleTextBn: String,
    val standardTranslationBn: String,
    val phoneticCharacteristics: String,
    val status: ModelStatus,
    val maleVoiceId: String,
    val femaleVoiceId: String,
    val vocabularyNotes: String = ""
)
