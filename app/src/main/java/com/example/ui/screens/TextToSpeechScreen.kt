package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Spellcheck
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.PronunciationRule
import com.example.model.VoiceEmotion
import com.example.model.VoiceProfile
import com.example.ui.components.AudioWaveformVisualizer
import com.example.ui.components.EmotionPicker
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.NeonCyan

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextToSpeechScreen(
    isBangla: Boolean,
    textInput: String,
    selectedVoice: VoiceProfile,
    allVoices: List<VoiceProfile>,
    selectedEmotion: VoiceEmotion,
    isSynthesizing: Boolean,
    isPlaying: Boolean,
    playbackProgress: Float,
    audioAmplitudes: List<Float>,
    activeChunkIndex: Int,
    totalChunks: Int,
    pronunciationRules: List<PronunciationRule>,
    onTextChanged: (String) -> Unit,
    onConvertBanglish: () -> Unit,
    onVoiceSelected: (VoiceProfile) -> Unit,
    onEmotionSelected: (VoiceEmotion) -> Unit,
    onGenerateAndPlay: () -> Unit,
    onStopPlayback: () -> Unit,
    onExportAudio: (String) -> Unit,
    onAddPronunciationRule: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showVoiceSheet by remember { mutableStateOf(false) }
    var showPronounceDialog by remember { mutableStateOf(false) }
    var newRuleWord by remember { mutableStateOf("") }
    var newRulePhonetic by remember { mutableStateOf("") }

    val charCount = textInput.length
    val wordCount = if (textInput.isBlank()) 0 else textInput.trim().split(Regex("\\s+")).size
    val estDurationSec = (wordCount / 135f * 60f).toInt().coerceAtLeast(1)

    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 16.dp).testTag("text_to_speech_screen"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth().clickable { showVoiceSheet = true }.testTag("tts_voice_selector_card"),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Row(modifier = Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(42.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primaryContainer), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.RecordVoiceOver, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(if (isBangla) "নির্বাচিত কণ্ঠ:" else "Selected Voice:", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(selectedVoice.name, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        Text("${selectedVoice.dialectTag} • ${selectedVoice.gender}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                    }
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text(if (isBangla) "টেক্সট স্ক্রিপ্ট (সর্বোচ্চ ৫০,০০০ অক্ষর)" else "Text Script (Up to 50k chars)", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)

                        Surface(modifier = Modifier.clip(RoundedCornerShape(8.dp)).clickable { onConvertBanglish() }.testTag("banglish_convert_button"), color = MaterialTheme.colorScheme.primaryContainer) {
                            Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Translate, contentDescription = null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.onPrimaryContainer)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(if (isBangla) "বাংলিশ -> বাংলা" else "Banglish to Bangla", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = textInput,
                        onValueChange = onTextChanged,
                        modifier = Modifier.fillMaxWidth().height(160.dp).testTag("tts_text_input_field"),
                        placeholder = { Text(if (isBangla) "এখানে যে কোনো বাংলা, ইংরেজি অথবা বাংলিশ টেক্সট লিখুন..." else "Enter text here...") },
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("$charCount অক্ষর • $wordCount শব্দ • ~$estDurationSec সেকেন্ড", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                        IconButton(onClick = { showPronounceDialog = true }) {
                            Icon(Icons.Default.Spellcheck, contentDescription = "Pronunciation", tint = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        }

        item {
            EmotionPicker(selectedEmotion = selectedEmotion, isBangla = isBangla, onEmotionSelected = onEmotionSelected)
        }

        if (totalChunks > 1) {
            item {
                Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), color = MaterialTheme.colorScheme.surfaceVariant) {
                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        CircularProgressIndicator(progress = { activeChunkIndex.toFloat() / totalChunks }, modifier = Modifier.size(24.dp), color = NeonCyan, strokeWidth = 2.dp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(if (isBangla) "অটো চাঙ্কিং চলমান: অংশ $activeChunkIndex / $totalChunks" else "Auto Chunking: $activeChunkIndex / $totalChunks", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
                    }
                }
            }
        }

        item {
            AudioWaveformVisualizer(
                amplitudes = audioAmplitudes,
                isPlaying = isPlaying || isSynthesizing,
                progress = playbackProgress,
                onPlayPauseClick = { if (isPlaying || isSynthesizing) onStopPlayback() else onGenerateAndPlay() }
            )
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(onClick = onGenerateAndPlay, modifier = Modifier.weight(1.2f).testTag("tts_generate_button"), shape = RoundedCornerShape(14.dp)) {
                    Icon(if (isPlaying) Icons.Default.Stop else Icons.Default.PlayArrow, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (isPlaying) (if (isBangla) "থামান" else "Stop") else (if (isBangla) "ভয়েস বানান ও শুনুন" else "Generate & Play"), fontWeight = FontWeight.Bold)
                }

                OutlinedButton(onClick = { onExportAudio("MP3") }, modifier = Modifier.weight(0.9f).testTag("download_mp3_button"), shape = RoundedCornerShape(14.dp)) {
                    Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("MP3")
                }

                OutlinedButton(onClick = { onExportAudio("WAV") }, modifier = Modifier.weight(0.9f).testTag("download_wav_button"), shape = RoundedCornerShape(14.dp)) {
                    Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("WAV")
                }
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }
    }

    if (showVoiceSheet) {
        ModalBottomSheet(onDismissRequest = { showVoiceSheet = false }) {
            Column(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
                Text(if (isBangla) "ভয়েস নির্বাচন করুন" else "Select AI Voice", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(14.dp))
                LazyColumn(modifier = Modifier.height(380.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(allVoices) { voice ->
                        val isSelected = voice.id == selectedVoice.id
                        Card(
                            modifier = Modifier.fillMaxWidth().clickable {
                                onVoiceSelected(voice)
                                showVoiceSheet = false
                            },
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                Box(modifier = Modifier.size(36.dp).clip(CircleShape).background(if (voice.isCloned) NeonCyan else ElectricViolet), contentAlignment = Alignment.Center) {
                                    Text(if (voice.gender == "Male") "M" else "F", fontWeight = FontWeight.Bold, color = Color.Black)
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(voice.name, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                                    Text("${voice.dialectTag} • ${(voice.similarityScore * 100).toInt()}% মিল", style = MaterialTheme.typography.bodySmall)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showPronounceDialog) {
        AlertDialog(
            onDismissRequest = { showPronounceDialog = false },
            title = { Text(if (isBangla) "উচ্চারণ সংশোধন" else "Pronunciation") },
            text = {
                Column {
                    OutlinedTextField(value = newRuleWord, onValueChange = { newRuleWord = it }, label = { Text("মূল শব্দ") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = newRulePhonetic, onValueChange = { newRulePhonetic = it }, label = { Text("উচ্চারণ রূপ") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (newRuleWord.isNotBlank() && newRulePhonetic.isNotBlank()) {
                        onAddPronunciationRule(newRuleWord, newRulePhonetic)
                        newRuleWord = ""
                        newRulePhonetic = ""
                    }
                    showPronounceDialog = false
                }) {
                    Text(if (isBangla) "যোগ করুন" else "Add")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPronounceDialog = false }) { Text(if (isBangla) "বন্ধ" else "Close") }
            }
        )
    }
}
