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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.VoiceSettings
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.NeonCyan

@Composable
fun VoiceSettingsScreen(
    isBangla: Boolean,
    currentSettings: VoiceSettings,
    onSaveSettings: (VoiceSettings) -> Unit,
    modifier: Modifier = Modifier
) {
    var speed by remember(currentSettings) { mutableFloatStateOf(currentSettings.speed) }
    var pitch by remember(currentSettings) { mutableFloatStateOf(currentSettings.pitch) }
    var stability by remember(currentSettings) { mutableFloatStateOf(currentSettings.stability) }
    var similarity by remember(currentSettings) { mutableFloatStateOf(currentSettings.similarity) }
    var styleExaggeration by remember(currentSettings) { mutableFloatStateOf(currentSettings.styleExaggeration) }
    var sentencePause by remember(currentSettings) { mutableFloatStateOf(currentSettings.sentencePauseSec) }
    var paragraphPause by remember(currentSettings) { mutableFloatStateOf(currentSettings.paragraphPauseSec) }
    var volume by remember(currentSettings) { mutableFloatStateOf(currentSettings.volume) }
    var clarity by remember(currentSettings) { mutableFloatStateOf(currentSettings.clarity) }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header Banner
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(NeonCyan.copy(alpha = 0.15f), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Tune,
                        contentDescription = "Tuning",
                        tint = NeonCyan
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = if (isBangla) "ভয়েস টিউনিং কনসোল" else "Voice Tuning Console",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (isBangla) "গতি, পিচ, স্থায়িত্ব ও সাদৃশ্যের সুনির্দিষ্ট নিয়ন্ত্রণ" else "Fine-tune speed, pitch, stability & similarity",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Quick Presets
        Text(
            text = if (isBangla) "প্রিসেট প্রোফাইলসমূহ" else "Quick Presets",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = NeonCyan
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = false,
                onClick = {
                    speed = 1.0f; pitch = 0f; stability = 0.75f; similarity = 0.85f; sentencePause = 0.4f
                },
                label = { Text(if (isBangla) "প্রমিত কথন" else "Natural") },
                colors = FilterChipDefaults.filterChipColors()
            )
            FilterChip(
                selected = false,
                onClick = {
                    speed = 0.92f; pitch = -1.5f; stability = 0.88f; similarity = 0.90f; sentencePause = 0.6f; paragraphPause = 1.2f
                },
                label = { Text(if (isBangla) "গল্প ও আবৃত্তি" else "Storyteller") }
            )
            FilterChip(
                selected = false,
                onClick = {
                    speed = 1.15f; pitch = 1.0f; stability = 0.92f; similarity = 0.88f; sentencePause = 0.3f
                },
                label = { Text(if (isBangla) "সংবাদ পাঠ" else "News Broadcast") }
            )
        }

        // Section: Speed & Pitch
        SettingSliderCard(
            title = if (isBangla) "কণ্ঠের গতি ও তীক্ষ্ণতা (Speed & Pitch)" else "Pacing & Pitch",
            icon = Icons.Default.Speed
        ) {
            SliderRow(
                label = if (isBangla) "কথা বলার গতি" else "Speaking Speed",
                valueDisplay = "%.2fx".format(speed),
                value = speed,
                range = 0.5f..2.0f,
                onValueChange = { speed = it }
            )
            Spacer(modifier = Modifier.height(8.dp))
            SliderRow(
                label = if (isBangla) "পিচ অ্যাডজাস্টমেন্ট" else "Pitch Adjustment",
                valueDisplay = "%+.1f st".format(pitch),
                value = pitch,
                range = -12f..12f,
                onValueChange = { pitch = it }
            )
        }

        // Section: ElevenLabs-grade Identity Controls (Stability, Similarity, Style)
        SettingSliderCard(
            title = if (isBangla) "এআই ভয়েস স্থায়িত্ব ও সাদৃশ্য (Stability & Similarity)" else "AI Stability & Similarity",
            icon = Icons.Default.GraphicEq
        ) {
            SliderRow(
                label = if (isBangla) "ভয়েস স্ট্যাবিলিটি (Stability)" else "Voice Stability",
                valueDisplay = "${(stability * 100).toInt()}%",
                value = stability,
                range = 0.0f..1.0f,
                onValueChange = { stability = it }
            )
            Text(
                text = if (isBangla) "উচ্চ মান কণ্ঠকে শান্ত ও স্থিতিশীল রাখে; নিম্ন মান আবেগময় পরিবর্তন আনে।" else "Higher value yields consistent tone; lower value is more expressive.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                fontSize = 11.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            SliderRow(
                label = if (isBangla) "সাদৃশ্য অনুপাত (Similarity)" else "Similarity Boost",
                valueDisplay = "${(similarity * 100).toInt()}%",
                value = similarity,
                range = 0.0f..1.0f,
                onValueChange = { similarity = it }
            )
            Text(
                text = if (isBangla) "ক্লোন করা কণ্ঠের মূল নমুনার সঙ্গে মিল নিশ্চিত করে।" else "Enhances closeness to the original speaker's timbre.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                fontSize = 11.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            SliderRow(
                label = if (isBangla) "স্টাইল অতিরঞ্জন (Style Exaggeration)" else "Style Exaggeration",
                valueDisplay = "${(styleExaggeration * 100).toInt()}%",
                value = styleExaggeration,
                range = 0.0f..1.0f,
                onValueChange = { styleExaggeration = it }
            )
        }

        // Section: Pauses & Volume
        SettingSliderCard(
            title = if (isBangla) "বিরাম ও প্রমিত নিয়ন্ত্রণ (Pauses & Clarity)" else "Pauses & Audio Dynamics",
            icon = Icons.Default.VolumeUp
        ) {
            SliderRow(
                label = if (isBangla) "বাক্য বিরতি (Sentence Pause)" else "Sentence Pause",
                valueDisplay = "%.2fs".format(sentencePause),
                value = sentencePause,
                range = 0.1f..2.0f,
                onValueChange = { sentencePause = it }
            )
            Spacer(modifier = Modifier.height(8.dp))
            SliderRow(
                label = if (isBangla) "অনুচ্ছেদ বিরতি (Paragraph Pause)" else "Paragraph Pause",
                valueDisplay = "%.2fs".format(paragraphPause),
                value = paragraphPause,
                range = 0.2f..4.0f,
                onValueChange = { paragraphPause = it }
            )
            Spacer(modifier = Modifier.height(8.dp))
            SliderRow(
                label = if (isBangla) "ভলিউম বুস্ট" else "Master Volume",
                valueDisplay = "${(volume * 100).toInt()}%",
                value = volume,
                range = 0.2f..1.5f,
                onValueChange = { volume = it }
            )
            Spacer(modifier = Modifier.height(8.dp))
            SliderRow(
                label = if (isBangla) "নয়েজ হ্রাস ও স্পষ্টতা (Clarity)" else "Clarity & Denoise",
                valueDisplay = "${(clarity * 100).toInt()}%",
                value = clarity,
                range = 0.0f..1.0f,
                onValueChange = { clarity = it }
            )
        }

        // Action Buttons: Reset & Save
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = {
                    val def = VoiceSettings()
                    speed = def.speed
                    pitch = def.pitch
                    stability = def.stability
                    similarity = def.similarity
                    styleExaggeration = def.styleExaggeration
                    sentencePause = def.sentencePauseSec
                    paragraphPause = def.paragraphPauseSec
                    volume = def.volume
                    clarity = def.clarity
                },
                modifier = Modifier
                    .weight(1f)
                    .testTag("reset_settings_button"),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(if (isBangla) "রিসেট" else "Reset")
            }

            Button(
                onClick = {
                    val updated = VoiceSettings(
                        speed = speed,
                        pitch = pitch,
                        stability = stability,
                        similarity = similarity,
                        styleExaggeration = styleExaggeration,
                        sentencePauseSec = sentencePause,
                        paragraphPauseSec = paragraphPause,
                        volume = volume,
                        clarity = clarity
                    )
                    onSaveSettings(updated)
                },
                modifier = Modifier
                    .weight(1f)
                    .testTag("save_settings_button"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = NeonCyan)
            ) {
                Icon(Icons.Default.Check, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (isBangla) "সংরক্ষণ" else "Save Settings",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun SettingSliderCard(
    title: String,
    icon: ImageVector,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = ElectricViolet,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(14.dp))
            content()
        }
    }
}

@Composable
private fun SliderRow(
    label: String,
    valueDisplay: String,
    value: Float,
    range: ClosedFloatingPointRange<Float>,
    onValueChange: (Float) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = valueDisplay,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = NeonCyan
            )
        }
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = range,
            colors = SliderDefaults.colors(
                thumbColor = NeonCyan,
                activeTrackColor = NeonCyan,
                inactiveTrackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        )
    }
}
