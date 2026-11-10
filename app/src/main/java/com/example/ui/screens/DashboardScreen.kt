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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.model.VoiceProfile
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.NeonCyan
import com.example.viewmodel.StudioScreen

@Composable
fun DashboardScreen(
    isBangla: Boolean,
    selectedVoice: VoiceProfile,
    charactersUsedToday: Int,
    generationsCount: Int,
    onNavigate: (StudioScreen) -> Unit,
    onQuickListenVoice: (VoiceProfile) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 16.dp).testTag("dashboard_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            ElevatedCard(
                modifier = Modifier.fillMaxWidth().testTag("hero_studio_card"),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth().background(
                        Brush.horizontalGradient(listOf(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f), ElectricViolet.copy(alpha = 0.15f)))
                    ).padding(20.dp)
                ) {
                    Column {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Surface(shape = RoundedCornerShape(10.dp), color = MaterialTheme.colorScheme.primaryContainer) {
                                Text(if (isBangla) "সক্রিয় কণ্ঠ প্রোফাইল" else "Active Voice Profile", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                            }
                            Text("${(selectedVoice.similarityScore * 100).toInt()}% নিখুঁত", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = EmeraldGreen)
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                            Box(modifier = Modifier.size(48.dp).clip(CircleShape).background(Brush.linearGradient(listOf(NeonCyan, ElectricViolet))), contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.RecordVoiceOver, contentDescription = null, tint = Color.Black, modifier = Modifier.size(26.dp))
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(selectedVoice.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                Text(selectedVoice.dialectTag, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Button(onClick = { onNavigate(StudioScreen.TTS) }, modifier = Modifier.weight(1f).testTag("dashboard_start_tts_button"), shape = RoundedCornerShape(14.dp)) {
                                Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(if (isBangla) "ভয়েস বানান" else "Create Speech")
                            }

                            FilledTonalButton(onClick = { onNavigate(StudioScreen.CLONING) }, modifier = Modifier.weight(1f).testTag("dashboard_clone_voice_button"), shape = RoundedCornerShape(14.dp)) {
                                Icon(Icons.Default.Mic, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(if (isBangla) "১০ সে. ক্লোন" else "10s Clone")
                            }
                        }
                    }
                }
            }
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StudioStatCard(title = if (isBangla) "আজকের ক্যারেক্টার" else "Chars Today", value = "$charactersUsedToday / ৫০,০০০", progress = (charactersUsedToday / 50000f).coerceIn(0f, 1f), color = NeonCyan, modifier = Modifier.weight(1f))
                StudioStatCard(title = if (isBangla) "মোট জেনারেশন" else "Generations", value = "$generationsCount বার", progress = 0.65f, color = ElectricViolet, modifier = Modifier.weight(1f))
            }
        }

        item {
            Text(if (isBangla) "প্রধান স্টুডিও ফিচারসমূহ" else "Core Studio Features", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                StudioFeatureRow(if (isBangla) "৬৪ জেলার আঞ্চলিক ভাষা" else "64 District Dialects", if (isBangla) "বাংলাদেশের ৮টি বিভাগ ও ৬৪ জেলার আসল আঞ্চলিক উচ্চারণ ও উপভাষা" else "Authentic dialects across 64 districts", "৬৪ জেলা", EmeraldGreen, Icons.Default.LocationCity) { onNavigate(StudioScreen.DISTRICTS) }
                StudioFeatureRow(if (isBangla) "এআই ভয়েস ক্লোনিং" else "AI Voice Cloning", if (isBangla) "মাত্র ১০ সেকেন্ডের অডিও থেকে নিজের কণ্ঠের ক্লোন প্রোফাইল" else "Clone voice from 10-second sample", "১০ সেকেন্ড", NeonCyan, Icons.Default.Mic) { onNavigate(StudioScreen.CLONING) }
                StudioFeatureRow(if (isBangla) "প্রমিত বাংলা টিটিএস" else "Bangla Text to Speech", if (isBangla) "৫০,০০০ অক্ষর অটো চাঙ্কিং, অনুভূতি ও বাংলিশ রূপান্তর" else "50k char auto-chunking & 10 emotions", "৫০K অক্ষর", ElectricViolet, Icons.Default.RecordVoiceOver) { onNavigate(StudioScreen.TTS) }
                StudioFeatureRow(if (isBangla) "প্রফেশনাল অডিও এডিটর" else "Audio Editor", if (isBangla) "ওয়েভফর্ম ট্রিম, ভলিউম বুস্ট ও MP3/WAV এক্সপোর্ট" else "Trim, adjust volume & export MP3/WAV", "ওয়েভফর্ম", MaterialTheme.colorScheme.primary, Icons.Default.GraphicEq) { onNavigate(StudioScreen.AUDIO_EDITOR) }
            }
        }

        item {
            Text(if (isBangla) "উন্নত স্টুডিও সরঞ্জাম" else "Advanced Studio Suite", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                StudioMiniTile(if (isBangla) "স্পিচ টু টেক্সট" else "Speech to Text", Icons.Default.Campaign, { onNavigate(StudioScreen.SPEECH_TO_TEXT) }, Modifier.weight(1f))
                StudioMiniTile(if (isBangla) "এআই ডাবিং" else "AI Dubbing", Icons.Default.Movie, { onNavigate(StudioScreen.AI_DUBBING) }, Modifier.weight(1f))
                StudioMiniTile(if (isBangla) "সাউন্ড ইফেক্টস" else "Sound Effects", Icons.Default.AutoFixHigh, { onNavigate(StudioScreen.SOUND_EFFECTS) }, Modifier.weight(1f))
            }
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                StudioMiniTile(if (isBangla) "ভয়েস লাইব্রেরি" else "Voice Library", Icons.Default.LibraryMusic, { onNavigate(StudioScreen.LIBRARY) }, Modifier.weight(1f))
                StudioMiniTile(if (isBangla) "অডিও হিস্ট্রি" else "Audio History", Icons.Default.History, { onNavigate(StudioScreen.HISTORY) }, Modifier.weight(1f))
                StudioMiniTile(if (isBangla) "ভয়েস টিউনিং" else "Voice Tuning", Icons.Default.Tune, { onNavigate(StudioScreen.SETTINGS) }, Modifier.weight(1f))
                StudioMiniTile(if (isBangla) "ভয়েস সম্মতি" else "Voice Privacy", Icons.Default.Security, { onNavigate(StudioScreen.PRIVACY) }, Modifier.weight(1f))
            }
        }

        item { Spacer(modifier = Modifier.height(20.dp)) }
    }
}

@Composable
fun StudioStatCard(title: String, value: String, progress: Float, color: Color, modifier: Modifier = Modifier) {
    Card(modifier = modifier, shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant), border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(title, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(progress = { progress }, modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(2.dp)), color = color)
        }
    }
}

@Composable
fun StudioFeatureRow(title: String, subtitle: String, badge: String, badgeColor: Color, icon: ImageVector, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().clickable { onClick() }, shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant), border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)) {
        Row(modifier = Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(44.dp).clip(RoundedCornerShape(12.dp)).background(badgeColor.copy(alpha = 0.15f)), contentAlignment = Alignment.Center) {
                Icon(icon, contentDescription = null, tint = badgeColor, modifier = Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    Surface(shape = RoundedCornerShape(6.dp), color = badgeColor.copy(alpha = 0.2f)) {
                        Text(badge, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = badgeColor, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 2)
            }
        }
    }
}

@Composable
fun StudioMiniTile(title: String, icon: ImageVector, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(modifier = modifier.clickable { onClick() }, shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant), border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)) {
        Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(22.dp))
            Spacer(modifier = Modifier.height(6.dp))
            Text(title, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Medium, textAlign = androidx.compose.ui.text.style.TextAlign.Center, maxLines = 1)
        }
    }
}
