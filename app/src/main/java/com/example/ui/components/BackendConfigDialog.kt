package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dns
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.model.BackendServerConfig
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.NeonCyan

@Composable
fun BackendConfigDialog(
    config: BackendServerConfig,
    isBangla: Boolean,
    onDismiss: () -> Unit,
    onSaveConfig: (BackendServerConfig) -> Unit,
    modifier: Modifier = Modifier
) {
    var urlText by remember { mutableStateOf(config.serverUrl) }
    var useFallback by remember { mutableStateOf(config.useLocalFallback) }

    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier.testTag("backend_config_dialog"),
        shape = RoundedCornerShape(24.dp),
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Dns, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (isBangla) "এআই ব্যাকএন্ড সার্ভার" else "AI Backend Server", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = if (isBangla) "সেলফ-হোস্টেড ওপেন সোর্স ইঞ্জিন (FastAPI + IndicF5 + Whisper)।" else "Self-hosted open source engine (FastAPI + IndicF5 + Whisper).",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(14.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(EmeraldGreen))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(if (isBangla) "স্ট্যাটাস: অনলাইন" else "Status: Online", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = EmeraldGreen)
                            }
                            Text("${config.latencyMs}ms", style = MaterialTheme.typography.labelSmall)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Memory, contentDescription = null, modifier = Modifier.size(16.dp), tint = NeonCyan)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(config.activeModel, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = urlText,
                    onValueChange = { urlText = it },
                    label = { Text(if (isBangla) "FastAPI সার্ভার URL" else "FastAPI Server URL") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("backend_url_field")
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text(if (isBangla) "অফলাইন অন-ডিভাইস ইঞ্জিন" else "On-Device Engine Fallback", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                    Switch(checked = useFallback, onCheckedChange = { useFallback = it }, modifier = Modifier.testTag("fallback_switch"))
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                onSaveConfig(config.copy(serverUrl = urlText, useLocalFallback = useFallback))
                onDismiss()
            }) {
                Text(if (isBangla) "সংরক্ষণ" else "Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(if (isBangla) "বাতিল" else "Cancel")
            }
        }
    )
}
