package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.model.DistrictDialect
import com.example.model.ModelStatus
import com.example.ui.theme.AmberOrange
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.NeonCyan

@Composable
fun DistrictVoiceScreen(
    isBangla: Boolean,
    districts: List<DistrictDialect>,
    divisions: List<String>,
    selectedDivision: String,
    searchQuery: String,
    onDivisionSelect: (String) -> Unit,
    onSearchChange: (String) -> Unit,
    onPlaySample: (DistrictDialect) -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredDistricts = districts.filter { district ->
        val matchesDivision = selectedDivision == "সব বিভাগ (All)" || district.divisionBn == selectedDivision
        val matchesQuery = searchQuery.isBlank() ||
                district.districtBn.contains(searchQuery, ignoreCase = true) ||
                district.districtEn.contains(searchQuery, ignoreCase = true) ||
                district.dialectNameBn.contains(searchQuery, ignoreCase = true)
        matchesDivision && matchesQuery
    }

    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 16.dp).testTag("district_voice_screen"),
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
                    Text(if (isBangla) "বাংলাদেশের ৬৪ জেলার উপভাষা ও স্বর" else "Bangladesh 64 District Dialects", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(if (isBangla) "৮টি বিভাগ ও ৬৪টি জেলার স্থানীয় কথ্য উপভাষা, উচ্চারণ রীতি ও স্বরভঙ্গি।" else "Authentic dialects across all 64 districts.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchChange,
                modifier = Modifier.fillMaxWidth().testTag("district_search_field"),
                placeholder = { Text(if (isBangla) "জেলা বা আঞ্চলিক ভাষার নাম খুঁজুন..." else "Search district...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true,
                shape = RoundedCornerShape(14.dp)
            )
        }

        item {
            Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                divisions.forEach { div ->
                    FilterChip(selected = selectedDivision == div, onClick = { onDivisionSelect(div) }, label = { Text(div) }, shape = RoundedCornerShape(12.dp))
                }
            }
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(if (isBangla) "মোট জেলা তালিকা (${filteredDistricts.size}টি)" else "Districts (${filteredDistricts.size})", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                Text(if (isBangla) "আসল ডেটাসেট যাচাইকৃত" else "Dataset Verified", style = MaterialTheme.typography.labelSmall, color = EmeraldGreen)
            }
        }

        items(filteredDistricts, key = { it.id }) { dialect ->
            ElevatedCard(
                modifier = Modifier.fillMaxWidth().testTag("district_card_${dialect.id}"),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(36.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primaryContainer), contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.LocationCity, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.size(20.dp))
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text("${dialect.districtBn} (${dialect.districtEn})", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                Text("${dialect.divisionBn} • ${dialect.dialectNameBn}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                            }
                        }

                        if (dialect.status == ModelStatus.READY) {
                            Surface(shape = RoundedCornerShape(8.dp), color = EmeraldGreen.copy(alpha = 0.18f)) {
                                Text(if (isBangla) "মডেল প্রস্তুত" else "Ready", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = EmeraldGreen, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                            }
                        } else {
                            Surface(shape = RoundedCornerShape(8.dp), color = AmberOrange.copy(alpha = 0.18f)) {
                                Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.HourglassTop, contentDescription = null, tint = AmberOrange, modifier = Modifier.size(12.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(if (isBangla) "আসন্ন (Coming Soon)" else "Coming Soon", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = AmberOrange)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Surface(shape = RoundedCornerShape(12.dp), color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("“${dialect.sampleTextBn}”", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = NeonCyan)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("প্রমিত রূপ: ${dialect.standardTranslationBn}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text("উচ্চারণ রীতি: ${dialect.phoneticCharacteristics}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                    if (dialect.vocabularyNotes.isNotEmpty()) {
                        Text("আঞ্চলিক শব্দাবলি: ${dialect.vocabularyNotes}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    FilledTonalButton(onClick = { onPlaySample(dialect) }, modifier = Modifier.fillMaxWidth().testTag("play_district_${dialect.id}"), shape = RoundedCornerShape(12.dp)) {
                        Icon(if (dialect.status == ModelStatus.READY) Icons.Default.VolumeUp else Icons.Default.HourglassTop, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (dialect.status == ModelStatus.READY) (if (isBangla) "আঞ্চলিক স্বর শুনুন" else "Listen Sample") else (if (isBangla) "ডেটাসেট প্রক্রিয়াধীন (Coming Soon)" else "In Training"), fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}
