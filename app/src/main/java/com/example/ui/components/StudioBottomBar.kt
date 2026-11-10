package com.example.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.viewmodel.StudioScreen

@Composable
fun StudioBottomBar(
    currentScreen: StudioScreen,
    isBangla: Boolean,
    onNavigate: (StudioScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.navigationBars)
            .testTag("studio_bottom_nav_bar"),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        val navItems = listOf(
            Triple(StudioScreen.DASHBOARD, if (isBangla) "হোম" else "Home", Icons.Default.Dashboard),
            Triple(StudioScreen.TTS, if (isBangla) "টিটিএস" else "TTS", Icons.Default.RecordVoiceOver),
            Triple(StudioScreen.CLONING, if (isBangla) "ক্লোন" else "Clone", Icons.Default.Mic),
            Triple(StudioScreen.DISTRICTS, if (isBangla) "৬৪ জেলা" else "Districts", Icons.Default.LocationCity),
            Triple(StudioScreen.LIBRARY, if (isBangla) "স্টুডিও" else "Studio", Icons.Default.AutoFixHigh)
        )

        navItems.forEach { (screen, label, icon) ->
            val isSelected = currentScreen == screen || (screen == StudioScreen.LIBRARY && (
                    currentScreen == StudioScreen.AUDIO_EDITOR ||
                    currentScreen == StudioScreen.SPEECH_TO_TEXT ||
                    currentScreen == StudioScreen.AI_DUBBING ||
                    currentScreen == StudioScreen.SOUND_EFFECTS ||
                    currentScreen == StudioScreen.HISTORY ||
                    currentScreen == StudioScreen.SETTINGS ||
                    currentScreen == StudioScreen.PROFILE ||
                    currentScreen == StudioScreen.PRIVACY
            ))

            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(screen) },
                icon = { Icon(icon, contentDescription = label) },
                label = { Text(label, style = MaterialTheme.typography.labelSmall) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = Modifier.testTag("nav_${screen.name.lowercase()}")
            )
        }
    }
}
