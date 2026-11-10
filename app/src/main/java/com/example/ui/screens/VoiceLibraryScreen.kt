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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.example.model.VoiceProfile
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.NeonCyan

@Composable
fun VoiceLibraryScreen(
    isBangla: Boolean,
    voiceProfiles: List<VoiceProfile>,
    selectedVoice: VoiceProfile,
    onSelectVoice: (VoiceProfile) -> Unit,
    onPreviewVoice: (VoiceProfile) -> Unit,
    onToggleFavorite: (String) -> Unit,
    onRenameVoice: (String, String) -> Unit,
    onDeleteVoice: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf("ALL") }
    var renameTargetVoice by remember { mutableStateOf<VoiceProfile?>(null) }
    var renameText by remember { mutableStateOf("") }

    val filteredProfiles = voiceProfiles.filter { voice ->
        when (selectedTab) {
            "CLONED" -> voice.isCloned
            "MASTER" -> !voice.isCloned
            "FAVORITE" -> voice.isFavorite
            else -> true
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 16.dp).testTag("voice_library_screen"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(if (isBangla) "ভয়েস লাইব্রেরি ও কালেকশন" else "Voice Library", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(if (isBangla) "ব্যক্তিগত ক্লোন করা কণ্ঠ এবং প্রিমিয়াম মাস্টার ভয়েস কালেকশন।" else "Manage cloned voices and studio master voices.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(selected = selectedTab == "ALL", onClick = { selectedTab = "ALL" }, label = { Text("সকল") })
                FilterChip(selected = selectedTab == "CLONED", onClick = { selectedTab = "CLONED" }, label = { Text("ক্লোন") })
                FilterChip(selected = selectedTab == "MASTER", onClick = { selectedTab = "MASTER" }, label = { Text("মাস্টার") })
                FilterChip(selected = selectedTab == "FAVORITE", onClick = { selectedTab = "FAVORITE" }, label = { Text("পছন্দের") })
            }
        }

        items(filteredProfiles, key = { it.id }) { voice ->
            val isCurrentSelected = voice.id == selectedVoice.id
            Card(
                modifier = Modifier.fillMaxWidth().clickable { onSelectVoice(voice) }.testTag("voice_item_${voice.id}"),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isCurrentSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surfaceVariant
                ),
                border = if (isCurrentSelected) androidx.compose.foundation.BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary) else null
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(44.dp).clip(CircleShape).background(if (voice.isCloned) NeonCyan else ElectricViolet), contentAlignment = Alignment.Center) {
                            Icon(if (voice.isCloned) Icons.Default.Mic else Icons.Default.RecordVoiceOver, contentDescription = null, tint = Color.Black)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(voice.name, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            Text("${voice.dialectTag} • ${(voice.similarityScore * 100).toInt()}% মিল", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                        }

                        IconButton(onClick = { onToggleFavorite(voice.id) }) {
                            Icon(if (voice.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder, contentDescription = null, tint = if (voice.isFavorite) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Button(onClick = { onPreviewVoice(voice) }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(12.dp)) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(if (isBangla) "নমুনা শুনুন" else "Preview")
                        }

                        if (voice.isCloned) {
                            IconButton(onClick = {
                                renameTargetVoice = voice
                                renameText = voice.name
                            }) { Icon(Icons.Default.Edit, contentDescription = null) }

                            IconButton(onClick = { onDeleteVoice(voice.id) }) {
                                Icon(Icons.Default.Delete, contentDescription = null, tint = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }
    }

    renameTargetVoice?.let { target ->
        AlertDialog(
            onDismissRequest = { renameTargetVoice = null },
            title = { Text("ভয়েস নাম পরিবর্তন") },
            text = { OutlinedTextField(value = renameText, onValueChange = { renameText = it }, singleLine = true, modifier = Modifier.fillMaxWidth()) },
            confirmButton = {
                Button(onClick = {
                    if (renameText.isNotBlank()) onRenameVoice(target.id, renameText.trim())
                    renameTargetVoice = null
                }) { Text("সংরক্ষণ") }
            },
            dismissButton = {
                TextButton(onClick = { renameTargetVoice = null }) { Text("বাতিল") }
            }
        )
    }
}
