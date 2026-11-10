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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SurroundSound
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.model.SoundEffectItem
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.NeonCyan

@Composable
fun SoundEffectsScreen(
    isBangla: Boolean,
    sfxPrompt: String,
    isGenerating: Boolean,
    soundEffects: List<SoundEffectItem>,
    onPromptChange: (String) -> Unit,
    onGenerateSfx: () -> Unit,
    onPlaySfx: (SoundEffectItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Banner
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
                        imageVector = Icons.Default.SurroundSound,
                        contentDescription = null,
                        tint = NeonCyan
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = if (isBangla) "এআই সাউন্ড ইফেক্টস জেনারেটর" else "AI Sound Effects Generator",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (isBangla) "টেক্সট প্রম্পট লিখে যেকোনো বাস্তবসম্মত সাউন্ড ইফেক্ট তৈরি করুন"
                        else "Describe any sound effect in text to generate high-fidelity audio",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Quick Category Suggestions
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = false,
                onClick = {
                    onPromptChange(if (isBangla) "কক্সবাজারের সমুদ্রের ঢেউ ও বাতাসের শোঁ শোঁ শব্দ" else "Ocean waves crashing on Cox's Bazar beach")
                },
                label = { Text(if (isBangla) "সমুদ্র ঢেউ" else "Ocean") }
            )
            FilterChip(
                selected = false,
                onClick = {
                    onPromptChange(if (isBangla) "শ্রীমঙ্গলের চা বাগানে পাখির মিষ্টি ডাক" else "Birds chirping in Sreemangal tea garden")
                },
                label = { Text(if (isBangla) "পাখির ডাক" else "Birds") }
            )
            FilterChip(
                selected = false,
                onClick = {
                    onPromptChange(if (isBangla) "ঢাকার বর্ষার মেঘের গর্জন ও মুষলধারে বৃষ্টি" else "Dhaka monsoon thunderstorm with heavy rain")
                },
                label = { Text(if (isBangla) "বজ্রপাত ও বৃষ্টি" else "Thunder") }
            )
        }

        // Text Prompt Input
        OutlinedTextField(
            value = sfxPrompt,
            onValueChange = onPromptChange,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("sfx_prompt_input"),
            label = { Text(if (isBangla) "সাউন্ড ইফেক্টের বিবরণ লিখুন" else "Describe sound effect") },
            placeholder = { Text(if (isBangla) "যেমন: পদ্মা নদীর শান্ত পানির কলতান..." else "e.g., Gentle water ripples...") },
            shape = RoundedCornerShape(12.dp),
            maxLines = 3
        )

        // Generate SFX Button
        Button(
            onClick = onGenerateSfx,
            enabled = !isGenerating && sfxPrompt.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("generate_sfx_button"),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = NeonCyan)
        ) {
            if (isGenerating) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isBangla) "সাউন্ড তৈরি হচ্ছে..." else "Generating Audio...",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Icon(
                    Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isBangla) "সাউন্ড ইফেক্ট তৈরি করুন" else "Generate Sound Effect",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Pre-generated / Preset Sound Effects List
        Text(
            text = if (isBangla) "স্টুডিও সাউন্ড লাইব্রেরি" else "Sound Effects Library",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = NeonCyan
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(soundEffects, key = { it.id }) { sfx ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(ElectricViolet.copy(alpha = 0.15f), RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.GraphicEq,
                                contentDescription = null,
                                tint = ElectricViolet
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = sfx.banglaTitle,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "${sfx.category} • ${sfx.durationSec}s",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        IconButton(
                            onClick = { onPlaySfx(sfx) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Play SFX",
                                tint = NeonCyan
                            )
                        }
                    }
                }
            }
        }
    }
}
