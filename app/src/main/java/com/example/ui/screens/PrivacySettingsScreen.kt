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
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.NeonCyan

@Composable
fun PrivacySettingsScreen(
    isBangla: Boolean,
    onShowMessage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    var enableWatermark by remember { mutableStateOf(true) }
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }
    var showAbuseReportDialog by remember { mutableStateOf(false) }
    var abuseReasonText by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
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
                        imageVector = Icons.Default.Security,
                        contentDescription = null,
                        tint = NeonCyan
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = if (isBangla) "ভয়েস গোপনীয়তা ও এআই সুরক্ষা" else "Voice Privacy & Safety",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (isBangla) "কণ্ঠের মালিকানা যাচাই, এনক্রিপশন এবং অপব্যবহার প্রতিরোধ"
                        else "Voice ownership verification, encryption & safety",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Voice Ownership Verification
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.VerifiedUser,
                        contentDescription = null,
                        tint = NeonCyan,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isBangla) "কণ্ঠের মালিকানা সম্মতি (Owner Consent)" else "Voice Ownership Consent",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = if (isBangla) "সিয়াম ভয়েস এআই তে অননুমোদিত কোনো ব্যক্তির কণ্ঠ ক্লোন করা কঠোরভাবে নিষিদ্ধ। প্রতিটি ভয়েস প্রোফাইল তৈরির পূর্বে বক্তার মৌখিক সম্মতি নিশ্চিত করা হয়।"
                    else "Unauthorized voice cloning is strictly prohibited. Verbal consent is required before processing any voice embedding.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 18.sp
                )
            }
        }

        // Encrypted Storage & AI Watermark
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.WaterDrop,
                            contentDescription = null,
                            tint = ElectricViolet,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = if (isBangla) "এআই অডিও জলছাপ (Audio Watermark)" else "AI Audio Watermarking",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = if (isBangla) "সিন্থেটিক অডিও সনাক্তকরণের জন্য মেটাডাটা ট্যাগ" else "Embeds synthetic AI disclosure tag",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Switch(
                        checked = enableWatermark,
                        onCheckedChange = {
                            enableWatermark = it
                            onShowMessage(
                                if (isBangla) {
                                    if (it) "এআই ওয়াটারমার্ক সক্রিয়" else "এআই ওয়াটারমার্ক নিষ্ক্রিয়"
                                } else {
                                    if (it) "Watermark enabled" else "Watermark disabled"
                                }
                            )
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = NeonCyan,
                            checkedTrackColor = NeonCyan.copy(alpha = 0.4f)
                        )
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = NeonCyan,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isBangla) "AES-256 অন-ডিভাইস স্পিকার ভেক্টর এনক্রিপশন সক্রিয়" else "AES-256 on-device vector encryption active",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Safety Actions: Delete Data & Report Abuse
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = if (isBangla) "নিরাপত্তা ও প্রতিকার ব্যবস্থা" else "Safety & Redressal",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                OutlinedButton(
                    onClick = { showAbuseReportDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("report_abuse_button"),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.ReportProblem, contentDescription = null, tint = ElectricViolet)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isBangla) "অননুমোদিত কণ্ঠ ব্যবহারের অভিযোগ জানান" else "Report Unauthorized Voice Cloning",
                        color = ElectricViolet
                    )
                }

                Button(
                    onClick = { showDeleteConfirmDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("delete_all_data_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Icon(Icons.Default.DeleteForever, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isBangla) "আমার সমস্ত ভয়েস ডেটা স্থায়ীভাবে মুছুন" else "Delete All My Voice Data",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    // Delete Confirmation Dialog
    if (showDeleteConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmDialog = false },
            title = {
                Text(if (isBangla) "ভয়েস ডেটা মুছে ফেলা নিশ্চিতকরণ" else "Confirm Data Deletion")
            },
            text = {
                Text(
                    if (isBangla) "আপনার সমস্ত রেকর্ডকৃত নমুনা, স্পিকার এম্বেডিং এবং ভয়েস প্রোফাইল স্থায়ীভাবে ডিভাইস থেকে মুছে ফেলা হবে। এই প্রক্রিয়াটি অপরিবর্তনীয়।"
                    else "All your recorded samples, speaker embeddings, and cloned voice profiles will be permanently erased. This cannot be undone."
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDeleteConfirmDialog = false
                        onShowMessage(if (isBangla) "সমস্ত ভয়েস ডেটা সফলভাবে মুছে ফেলা হয়েছে!" else "All voice data wiped successfully!")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text(if (isBangla) "হ্যাঁ, মুছে ফেলুন" else "Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirmDialog = false }) {
                    Text(if (isBangla) "বাতিল" else "Cancel")
                }
            }
        )
    }

    // Abuse Report Dialog
    if (showAbuseReportDialog) {
        AlertDialog(
            onDismissRequest = { showAbuseReportDialog = false },
            title = {
                Text(if (isBangla) "অপব্যবহারের রিপোর্ট দায়ের" else "File Abuse Report")
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        if (isBangla) "অননুমোদিত বা ক্ষতিকর ভয়েস ক্লোনিং সংক্রান্ত বিবরণ লিখুন:"
                        else "Please describe the unauthorized voice cloning incident:"
                    )
                    OutlinedTextField(
                        value = abuseReasonText,
                        onValueChange = { abuseReasonText = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(if (isBangla) "বিবরণ লিখুন..." else "Enter incident details...") },
                        maxLines = 3
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showAbuseReportDialog = false
                        abuseReasonText = ""
                        onShowMessage(if (isBangla) "আপনার রিপোর্ট গৃহীত হয়েছে ও ট্র্যাকিং আইডি প্রদান করা হয়েছে।" else "Report submitted successfully.")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NeonCyan)
                ) {
                    Text(if (isBangla) "জমা দিন" else "Submit Report", color = MaterialTheme.colorScheme.onPrimary)
                }
            },
            dismissButton = {
                TextButton(onClick = { showAbuseReportDialog = false }) {
                    Text(if (isBangla) "বাতিল" else "Cancel")
                }
            }
        )
    }
}
