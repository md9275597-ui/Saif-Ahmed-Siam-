package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.DistrictDialectData
import com.example.ui.components.BackendConfigDialog
import com.example.ui.components.StudioBottomBar
import com.example.ui.components.StudioTopBar
import com.example.ui.screens.AiDubbingScreen
import com.example.ui.screens.AudioEditorScreen
import com.example.ui.screens.AudioHistoryScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.DistrictVoiceScreen
import com.example.ui.screens.PrivacySettingsScreen
import com.example.ui.screens.SoundEffectsScreen
import com.example.ui.screens.SpeechToTextScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.screens.TextToSpeechScreen
import com.example.ui.screens.UserProfileScreen
import com.example.ui.screens.VoiceCloningScreen
import com.example.ui.screens.VoiceLibraryScreen
import com.example.ui.screens.VoiceSettingsScreen
import com.example.ui.theme.SiamVoiceTheme
import com.example.viewmodel.SiamVoiceViewModel
import com.example.viewmodel.StudioScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: SiamVoiceViewModel = viewModel()
            val isDarkMode by viewModel.isDarkMode.collectAsStateWithLifecycle()

            SiamVoiceTheme(darkTheme = isDarkMode) {
                SiamVoiceApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun SiamVoiceApp(viewModel: SiamVoiceViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val isBangla by viewModel.isBangla.collectAsStateWithLifecycle()
    val isDarkMode by viewModel.isDarkMode.collectAsStateWithLifecycle()
    val showBackendDialog by viewModel.showBackendDialog.collectAsStateWithLifecycle()
    val userMessage by viewModel.userMessage.collectAsStateWithLifecycle()

    // Data from repository
    val selectedVoice by viewModel.repository.selectedVoice.collectAsStateWithLifecycle()
    val voiceProfiles by viewModel.repository.voiceProfiles.collectAsStateWithLifecycle()
    val districts by viewModel.repository.districts.collectAsStateWithLifecycle()
    val voiceSettings by viewModel.repository.voiceSettings.collectAsStateWithLifecycle()
    val audioHistory by viewModel.repository.audioHistory.collectAsStateWithLifecycle()
    val pronunciationRules by viewModel.repository.pronunciationRules.collectAsStateWithLifecycle()
    val dubbingSegments by viewModel.repository.dubbingSegments.collectAsStateWithLifecycle()
    val soundEffects by viewModel.repository.soundEffects.collectAsStateWithLifecycle()
    val backendConfig by viewModel.repository.backendConfig.collectAsStateWithLifecycle()
    val charactersUsedToday by viewModel.repository.charactersUsedToday.collectAsStateWithLifecycle()
    val generationsCount by viewModel.repository.generationsCount.collectAsStateWithLifecycle()

    // Audio Engine State
    val isPlaying by viewModel.audioEngine.isPlaying.collectAsStateWithLifecycle()
    val playbackProgress by viewModel.audioEngine.playbackProgress.collectAsStateWithLifecycle()
    val isRecording by viewModel.audioEngine.isRecording.collectAsStateWithLifecycle()
    val recordingDurationSec by viewModel.audioEngine.recordingDurationSec.collectAsStateWithLifecycle()
    val audioAmplitudes by viewModel.audioEngine.audioAmplitudes.collectAsStateWithLifecycle()

    // TTS & Cloning State
    val ttsInputText by viewModel.ttsInputText.collectAsStateWithLifecycle()
    val selectedEmotion by viewModel.selectedEmotion.collectAsStateWithLifecycle()
    val isSynthesizing by viewModel.isSynthesizing.collectAsStateWithLifecycle()
    val activeChunkIndex by viewModel.activeChunkIndex.collectAsStateWithLifecycle()
    val totalChunks by viewModel.totalChunks.collectAsStateWithLifecycle()
    val cloneName by viewModel.cloneName.collectAsStateWithLifecycle()
    val cloneGender by viewModel.cloneGender.collectAsStateWithLifecycle()
    val userConsentAccepted by viewModel.userConsentAccepted.collectAsStateWithLifecycle()
    val isExtractingEmbedding by viewModel.isExtractingEmbedding.collectAsStateWithLifecycle()
    val extractionProgress by viewModel.extractionProgress.collectAsStateWithLifecycle()
    val clonedSampleFile by viewModel.clonedSampleFile.collectAsStateWithLifecycle()

    // Filter & Editor State
    val selectedDivision by viewModel.selectedDivision.collectAsStateWithLifecycle()
    val districtSearchQuery by viewModel.districtSearchQuery.collectAsStateWithLifecycle()
    val editorAudioTitle by viewModel.editorAudioTitle.collectAsStateWithLifecycle()
    val trimStart by viewModel.trimStart.collectAsStateWithLifecycle()
    val trimEnd by viewModel.trimEnd.collectAsStateWithLifecycle()
    val editorVolume by viewModel.editorVolume.collectAsStateWithLifecycle()
    val editorSpeed by viewModel.editorSpeed.collectAsStateWithLifecycle()
    val noiseReduction by viewModel.noiseReduction.collectAsStateWithLifecycle()
    val sttTranscript by viewModel.sttTranscript.collectAsStateWithLifecycle()
    val isTranscribing by viewModel.isTranscribing.collectAsStateWithLifecycle()
    val dubbingSourceText by viewModel.dubbingSourceText.collectAsStateWithLifecycle()
    val dubbingTargetText by viewModel.dubbingTargetText.collectAsStateWithLifecycle()
    val sfxPrompt by viewModel.sfxPrompt.collectAsStateWithLifecycle()
    val isGeneratingSfx by viewModel.isGeneratingSfx.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(userMessage) {
        userMessage?.let {
            snackbarHostState.showSnackbar(
                message = it,
                duration = SnackbarDuration.Short
            )
        }
    }

    // Android Hardware Back Handler
    BackHandler(enabled = currentScreen != StudioScreen.DASHBOARD && currentScreen != StudioScreen.SPLASH) {
        viewModel.navigateBack()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (currentScreen != StudioScreen.SPLASH) {
                val screenTitle = when (currentScreen) {
                    StudioScreen.DASHBOARD -> if (isBangla) "সিয়াম ভয়েস এআই" else "Siam Voice AI"
                    StudioScreen.TTS -> if (isBangla) "বাংলা টেক্সট টু স্পিচ" else "Bangla Text to Speech"
                    StudioScreen.CLONING -> if (isBangla) "ভয়েস ক্লোনিং স্টুডিও" else "Voice Cloning Studio"
                    StudioScreen.RECORDING -> if (isBangla) "কণ্ঠ রেকর্ডিং" else "Voice Recording"
                    StudioScreen.DISTRICTS -> if (isBangla) "৬৪ জেলার আঞ্চলিক ভাষা" else "64 District Dialects"
                    StudioScreen.LIBRARY -> if (isBangla) "ভয়েস লাইব্রেরি" else "Voice Library"
                    StudioScreen.SETTINGS -> if (isBangla) "ভয়েস টিউনিং সেটিংস" else "Voice Tuning Settings"
                    StudioScreen.AUDIO_EDITOR -> if (isBangla) "অডিও এডিটর ও ট্রিমার" else "Audio Editor & Trimmer"
                    StudioScreen.SPEECH_TO_TEXT -> if (isBangla) "স্পিচ টু টেক্সট" else "Speech to Text"
                    StudioScreen.AI_DUBBING -> if (isBangla) "এআই ডাবিং স্টুডিও" else "AI Dubbing Studio"
                    StudioScreen.SOUND_EFFECTS -> if (isBangla) "সাউন্ড ইফেক্টস" else "Sound Effects"
                    StudioScreen.HISTORY -> if (isBangla) "অডিও হিস্ট্রি ও প্রজেক্ট" else "Audio History"
                    StudioScreen.PROFILE -> if (isBangla) "স্টুডিও প্রোফাইল ও কোটা" else "Studio Profile"
                    StudioScreen.PRIVACY -> if (isBangla) "ভয়েস গোপনীয়তা ও সুরক্ষা" else "Privacy & Safety"
                    else -> "Siam Voice AI"
                }

                StudioTopBar(
                    title = screenTitle,
                    isBangla = isBangla,
                    isDarkMode = isDarkMode,
                    canGoBack = currentScreen != StudioScreen.DASHBOARD,
                    onBackClick = { viewModel.navigateBack() },
                    onToggleLanguage = { viewModel.toggleLanguage() },
                    onToggleTheme = { viewModel.toggleDarkMode() },
                    onOpenServerConfig = { viewModel.setShowBackendDialog(true) }
                )
            }
        },
        bottomBar = {
            if (currentScreen != StudioScreen.SPLASH) {
                StudioBottomBar(
                    currentScreen = currentScreen,
                    isBangla = isBangla,
                    onNavigate = { viewModel.navigateTo(it) }
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        val contentModifier = Modifier.padding(innerPadding)

        when (currentScreen) {
            StudioScreen.SPLASH -> {
                SplashScreen(
                    isBangla = isBangla,
                    onEnterStudio = { viewModel.navigateTo(StudioScreen.DASHBOARD) }
                )
            }
            StudioScreen.DASHBOARD -> {
                DashboardScreen(
                    isBangla = isBangla,
                    selectedVoice = selectedVoice,
                    charactersUsedToday = charactersUsedToday,
                    generationsCount = generationsCount,
                    onNavigate = { viewModel.navigateTo(it) },
                    onQuickListenVoice = { viewModel.generateAndPlayTTS(it) },
                    modifier = contentModifier
                )
            }
            StudioScreen.TTS -> {
                TextToSpeechScreen(
                    isBangla = isBangla,
                    textInput = ttsInputText,
                    selectedVoice = selectedVoice,
                    allVoices = voiceProfiles,
                    selectedEmotion = selectedEmotion,
                    isSynthesizing = isSynthesizing,
                    isPlaying = isPlaying,
                    playbackProgress = playbackProgress,
                    audioAmplitudes = audioAmplitudes,
                    activeChunkIndex = activeChunkIndex,
                    totalChunks = totalChunks,
                    pronunciationRules = pronunciationRules,
                    onTextChanged = { viewModel.updateTtsInputText(it) },
                    onConvertBanglish = { viewModel.convertBanglishIfApplicable() },
                    onVoiceSelected = { viewModel.repository.selectVoice(it) },
                    onEmotionSelected = { viewModel.selectEmotion(it) },
                    onGenerateAndPlay = { viewModel.generateAndPlayTTS() },
                    onStopPlayback = { viewModel.stopPlayback() },
                    onExportAudio = {
                        val file = viewModel.audioEngine.exportSynthesizedAudioFile("TTS_Output", ttsInputText, 8.0f, it)
                        viewModel.showMessage(if (isBangla) "${it} ফাইল সংরক্ষিত হয়েছে: ${file.name}" else "${it} saved: ${file.name}")
                    },
                    onAddPronunciationRule = { w, p -> viewModel.repository.addPronunciationRule(w, p) },
                    modifier = contentModifier
                )
            }
            StudioScreen.CLONING, StudioScreen.RECORDING -> {
                VoiceCloningScreen(
                    isBangla = isBangla,
                    cloneName = cloneName,
                    cloneGender = cloneGender,
                    isRecording = isRecording,
                    recordingDurationSec = recordingDurationSec,
                    audioAmplitudes = audioAmplitudes,
                    clonedSampleFile = clonedSampleFile,
                    isExtracting = isExtractingEmbedding,
                    extractionProgress = extractionProgress,
                    userConsentAccepted = userConsentAccepted,
                    onNameChange = { viewModel.updateCloneName(it) },
                    onGenderChange = { viewModel.updateCloneGender(it) },
                    onStartRecording = { viewModel.start10SecRecording() },
                    onStopRecording = { viewModel.stopRecordingEarly() },
                    onConsentChange = { viewModel.setUserConsent(it) },
                    onExtractAndSaveProfile = { viewModel.processVoiceExtraction() },
                    modifier = contentModifier
                )
            }
            StudioScreen.DISTRICTS -> {
                DistrictVoiceScreen(
                    isBangla = isBangla,
                    districts = districts,
                    divisions = DistrictDialectData.divisions,
                    selectedDivision = selectedDivision,
                    searchQuery = districtSearchQuery,
                    onDivisionSelect = { viewModel.updateSelectedDivision(it) },
                    onSearchChange = { viewModel.updateDistrictSearch(it) },
                    onPlaySample = { viewModel.playDistrictDialectSample(it) },
                    modifier = contentModifier
                )
            }
            StudioScreen.LIBRARY -> {
                VoiceLibraryScreen(
                    isBangla = isBangla,
                    voiceProfiles = voiceProfiles,
                    selectedVoice = selectedVoice,
                    onSelectVoice = {
                        viewModel.repository.selectVoice(it)
                        viewModel.showMessage(if (isBangla) "সক্রিয় কণ্ঠ নির্বাচন: ${it.name}" else "Selected voice: ${it.name}")
                    },
                    onPreviewVoice = { viewModel.generateAndPlayTTS(it) },
                    onToggleFavorite = { viewModel.repository.toggleFavoriteVoice(it) },
                    onRenameVoice = { id, name -> viewModel.repository.renameVoice(id, name) },
                    onDeleteVoice = { viewModel.repository.deleteVoice(it) },
                    modifier = contentModifier
                )
            }
            StudioScreen.SETTINGS -> {
                VoiceSettingsScreen(
                    isBangla = isBangla,
                    currentSettings = voiceSettings,
                    onSaveSettings = {
                        viewModel.repository.updateSettings(it)
                        viewModel.showMessage(if (isBangla) "টিউনিং সেটিংস সংরক্ষিত হয়েছে!" else "Settings applied!")
                    },
                    modifier = contentModifier
                )
            }
            StudioScreen.AUDIO_EDITOR -> {
                AudioEditorScreen(
                    isBangla = isBangla,
                    audioTitle = editorAudioTitle,
                    isPlaying = isPlaying,
                    playbackProgress = playbackProgress,
                    audioAmplitudes = audioAmplitudes,
                    trimStart = trimStart,
                    trimEnd = trimEnd,
                    volumeMultiplier = editorVolume,
                    speedMultiplier = editorSpeed,
                    noiseReduction = noiseReduction,
                    onTrimChange = { s, e ->
                        viewModel.setTrimStart(s)
                        viewModel.setTrimEnd(e)
                    },
                    onVolumeChange = { viewModel.setEditorVolume(it) },
                    onSpeedChange = { viewModel.setEditorSpeed(it) },
                    onToggleNoiseReduction = { viewModel.toggleNoiseReduction() },
                    onPlayPreview = { viewModel.playEditorPreview() },
                    onStopPreview = { viewModel.stopPlayback() },
                    onExportAudio = { viewModel.exportEditedAudio(it) },
                    modifier = contentModifier
                )
            }
            StudioScreen.SPEECH_TO_TEXT -> {
                SpeechToTextScreen(
                    isBangla = isBangla,
                    transcript = sttTranscript,
                    isTranscribing = isTranscribing,
                    onStartListening = { viewModel.startLiveSTT() },
                    onUseInTTS = {
                        viewModel.updateTtsInputText(it)
                        viewModel.navigateTo(StudioScreen.TTS)
                    },
                    onShowMessage = { viewModel.showMessage(it) },
                    modifier = contentModifier
                )
            }
            StudioScreen.AI_DUBBING -> {
                AiDubbingScreen(
                    isBangla = isBangla,
                    sourceText = dubbingSourceText,
                    targetText = dubbingTargetText,
                    sourceLang = "English",
                    targetLang = "Bangla",
                    dubbingSegments = dubbingSegments,
                    onProcessDubbing = { viewModel.processDubbing() },
                    modifier = contentModifier
                )
            }
            StudioScreen.SOUND_EFFECTS -> {
                SoundEffectsScreen(
                    isBangla = isBangla,
                    sfxPrompt = sfxPrompt,
                    isGenerating = isGeneratingSfx,
                    soundEffects = soundEffects,
                    onPromptChange = { viewModel.updateSfxPrompt(it) },
                    onGenerateSfx = { viewModel.generateSoundEffect() },
                    onPlaySfx = {
                        viewModel.audioEngine.playHarmonicTone(baseFreqHz = 180f, durationMs = (it.durationSec * 1000).toInt())
                    },
                    modifier = contentModifier
                )
            }
            StudioScreen.HISTORY -> {
                AudioHistoryScreen(
                    isBangla = isBangla,
                    historyProjects = audioHistory,
                    onPlayProject = {
                        viewModel.audioEngine.speak(it.textContent, rate = 1.0f)
                    },
                    onToggleFavorite = { viewModel.repository.toggleFavoriteProject(it.id) },
                    onDeleteProject = { viewModel.repository.deleteAudioProject(it.id) },
                    modifier = contentModifier
                )
            }
            StudioScreen.PROFILE -> {
                UserProfileScreen(
                    isBangla = isBangla,
                    charactersUsedToday = charactersUsedToday,
                    generationsCount = generationsCount,
                    onOpenServerConfig = { viewModel.setShowBackendDialog(true) },
                    modifier = contentModifier
                )
            }
            StudioScreen.PRIVACY -> {
                PrivacySettingsScreen(
                    isBangla = isBangla,
                    onShowMessage = { viewModel.showMessage(it) },
                    modifier = contentModifier
                )
            }
            StudioScreen.WELCOME, StudioScreen.LOGIN -> {
                DashboardScreen(
                    isBangla = isBangla,
                    selectedVoice = selectedVoice,
                    charactersUsedToday = charactersUsedToday,
                    generationsCount = generationsCount,
                    onNavigate = { viewModel.navigateTo(it) },
                    onQuickListenVoice = { viewModel.generateAndPlayTTS(it) },
                    modifier = contentModifier
                )
            }
        }
    }

    // Backend Config Dialog
    if (showBackendDialog) {
        BackendConfigDialog(
            config = backendConfig,
            isBangla = isBangla,
            onDismiss = { viewModel.setShowBackendDialog(false) },
            onSaveConfig = {
                viewModel.repository.updateBackendConfig(it)
                viewModel.showMessage(if (isBangla) "সার্ভার কনফিগারেশন আপডেট হয়েছে!" else "Server configuration updated!")
            }
        )
    }
}
