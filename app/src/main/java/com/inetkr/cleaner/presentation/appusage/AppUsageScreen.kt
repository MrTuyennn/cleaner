package com.inetkr.cleaner.presentation.appusage

import android.content.Intent
import android.content.IntentFilter
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.inetkr.cleaner.presentation.scanfile.ScanFileState

@Composable
fun AppUsageScreen(
    nav: NavController,
    appUsageViewModel: AppUsageViewModel = hiltViewModel()
) {
    val appUsage by appUsageViewModel.appUsage.collectAsState()

    val context = LocalContext.current
    val receiver = remember { UninstallReceiver() }

    DisposableEffect(Unit) {
        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_PACKAGE_REMOVED)
            addAction(Intent.ACTION_PACKAGE_FULLY_REMOVED)
            addDataScheme("package")
        }
        ContextCompat.registerReceiver(
            context,
            receiver,
            intentFilter,
            ContextCompat.RECEIVER_EXPORTED
        )

        onDispose {
            context.unregisterReceiver(receiver)
        }
    }

    when {
        appUsage.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Scanning files...")
                }
            }
        }

        appUsage is AppUsageState.Success -> {
            // Success state
            Column {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding())

                LazyColumn {
                    item {
                        Text(
                            "Found ${(appUsage as AppUsageState.Success).appUsage.size} app ",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }

                    items((appUsage as AppUsageState.Success).appUsage) { app ->
                        Card(
                            onClick = {
                                //appUsageViewModel.unInstallAppUsage(app)
                                appUsageViewModel.cleanCacheAppUsage(app)
                            }
                        ) {
                            Row() {
                                AsyncImage(
                                    model = app.icon,
                                    contentDescription = app.appName,
                                    modifier = Modifier.size(64.dp)
                                )
                                Column {
                                    Text(app.appName)
                                    Text(app.readableSize)
                                }
                            }
                        }
                    }
                }
            }
        }

        appUsage is AppUsageState.Error -> {
            // Error state
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.Error,
                        contentDescription = "Error",
                        tint = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        (appUsage as ScanFileState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}