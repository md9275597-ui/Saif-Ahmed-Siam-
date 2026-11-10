package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.VoiceSlider
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.NeonCyan
import java.io.File

@Composable
fun VoiceCloningScreen(
    isBangla: Boolean,
    cloneName: String,
    cloneGender: String,
    isRecording: Boolean,
    recordingDurationSec: Float,
    audioAmplitudes: List<Float>,
    clonedSampleFile: File?,
    isExtracting: Boolean,
    extractionProgress: Float,
    userConsentAccepted: Boolean,
    onNameChange: (String) -> Unit,
    onGenderChange: (String) -> Unit,
    onStartRecording: () -> Unit,
    onStopRecording: () -> Unit,
    onConsentChange: (Boolean) -> Unit,
    onExtractAndSaveProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    var similarityValue by remember { mutableFloatStateOf(0.92f) }
    var stabilityValue by remember { mutableFloatStateOf(0.85f) }
    var cloneMode by remember { mutableStateOf("10SEC") }

    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 16.dp).testTag("voice_cloning_screen"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                FilterChip(
                    selected = cloneMode == "10SEC",
                    onClick = { cloneMode = "10SEC" },
                    label = { Text(if (isBangla) "১০ সেকেন্ড কুইক ক্লোন" else "10s Quick Clone") },
                    leadingIcon = { Icon(Icons.Default.Mic, contentDescription = null, modifier = Modifier.size(16.dp)) },
                    modifier = Modifier.weight(1f)
                )
                FilterChip(
                    selected = cloneMode == "UPLOAD",
                    onClick = { cloneMode = "UPLOAD" },
                    label = { Text(if (isBangla) "অডিও আপলোড" else "Audio Upload") },
                    leadingIcon = { Icon(Icons.Default.CloudUpload, contentDescription = null, modifier = Modifier.size(16.dp)) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth().testTag("recording_studio_card"),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(if (isBangla) "কণ্ঠের নমুনা সংগ্রহ" else "Voice Identity Capture", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(if (isBangla) "নিচের বাক্যটি স্পষ্ট স্বরে পাঠ করুন (১০ সেকেন্ড)" else "Read clearly into microphone (10s)", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                    Spacer(modifier = Modifier.height(14.dp))

                    Surface(shape = RoundedCornerShape(14.dp), color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = if (isBangla)
                                "“বাংলা আমাদের মাতৃভাষা। প্রযুক্তির যুগে কৃত্রিম বুদ্ধিমত্তার মাধ্যমে বাঙালির নিজের কণ্ঠ বিশ্বমঞ্চে ছড়িয়ে পড়ুক।”"
                            else
                                "“Bangla is our mother tongue. Let the authentic voices of Bengal echo across the digital universe.”",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = NeonCyan,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(14.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(modifier = Modifier.fillMaxWidth().height(48.dp).padding(horizontal = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        audioAmplitudes.forEach { amp ->
                            val barHeight = if (isRecording) (40.dp * amp).coerceAtLeast(4.dp) else 4.dp
                            Box(modifier = Modifier.width(4.dp).height(barHeight).clip(RoundedCornerShape(2.dp)).background(if (isRecording) NeonCyan else MaterialTheme.colorScheme.outline))
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("${String.format("%.1f", recordingDurationSec)} / 10.0 সেকেন্ড", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = if (isRecording) EmeraldGreen else MaterialTheme.colorScheme.onSurfaceVariant)

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = { if (isRecording) onStopRecording() else onStartRecording() },
                        modifier = Modifier.fillMaxWidth(0.85f).testTag("record_sample_button"),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(if (isRecording) Icons.Default.Stop else Icons.Default.Mic, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (isRecording) (if (isBangla) "রেকর্ডিং সমাপ্ত করুন" else "Stop") else (if (isBangla) "১০ সেকেন্ড রেকর্ড শুরু করুন" else "Start 10s Record"), fontWeight = FontWeight.Bold)
                    }
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
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(if (isBangla) "ভয়েস প্রোফাইল বিবরণ" else "Profile Details", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = cloneName,
                        onValueChange = onNameChange,
                        label = { Text(if (isBangla) "ভয়েসের নাম" else "Voice Name") },
                        modifier = Modifier.fillMaxWidth().testTag("clone_name_input"),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        FilterChip(selected = cloneGender == "Male", onClick = { onGenderChange("Male") }, label = { Text("পুরুষ কণ্ঠ (Male)") }, modifier = Modifier.weight(1f))
                        FilterChip(selected = cloneGender == "Female", onClick = { onGenderChange("Female") }, label = { Text("নারী কণ্ঠ (Female)") }, modifier = Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    VoiceSlider(label = if (isBangla) "সাদৃশ্য নিয়ন্ত্রণ" else "Similarity", value = similarityValue, range = 0.5f..1.0f, valueDisplay = "${(similarityValue * 100).toInt()}%", onValueChange = { similarityValue = it })
                    Spacer(modifier = Modifier.height(8.dp))
                    VoiceSlider(label = if (isBangla) "স্থিতিশীলতা" else "Stability", value = stabilityValue, range = 0.5f..1.0f, valueDisplay = "${(stabilityValue * 100).toInt()}%", onValueChange = { stabilityValue = it })
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = userConsentAccepted, onCheckedChange = onConsentChange, modifier = Modifier.testTag("consent_checkbox"))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isBangla) "আমি নিশ্চিত করছি যে এই কণ্ঠটি আমার নিজের অথবা মূল কণ্ঠাধিকারীর স্পষ্ট অনুমতি রয়েছে।" else "I certify this voice is my own or I have explicit permission.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        if (isExtracting) {
            item {
                Surface(shape = RoundedCornerShape(16.dp), color = MaterialTheme.colorScheme.primaryContainer, modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Fingerprint, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(if (isBangla) "স্পিকার এমবেডিং তৈরি হচ্ছে..." else "Extracting Speaker Embedding...", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(progress = { extractionProgress }, modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)), color = NeonCyan)
                    }
                }
            }
        }

        item {
            Button(
                onClick = onExtractAndSaveProfile,
                enabled = !isExtracting && userConsentAccepted,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth().testTag("extract_and_save_button")
            ) {
                Icon(Icons.Default.Fingerprint, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (isBangla) "ভয়েস প্রোফাইল তৈরি ও সংরক্ষণ করুন" else "Save Voice Profile", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}
