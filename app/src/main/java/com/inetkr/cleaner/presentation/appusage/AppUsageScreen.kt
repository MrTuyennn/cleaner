package com.inetkr.cleaner.presentation.appusage

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.inetkr.cleaner.presentation.scanfile.ImageItem
import com.inetkr.cleaner.presentation.scanfile.ScanFileState
import com.inetkr.cleaner.presentation.scanfile.VideoItem

@Composable
fun AppUsageScreen(
    nav: NavController,
    appUsageViewModel: AppUsageViewModel = hiltViewModel()
) {
    val appUsage by appUsageViewModel.appUsage.collectAsState()

    when {
         appUsage.isLoading  -> {
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
                Box(modifier = Modifier.fillMaxWidth().statusBarsPadding())

                LazyColumn {
                    item {
                        Text(
                            "Found ${(appUsage as AppUsageState.Success).appUsage.size} app ",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }

                    items((appUsage as AppUsageState.Success).appUsage) { app ->
                        Row {
                            AsyncImage(
                                model = app.icon,
                                contentDescription = app.appName,
                                modifier = Modifier.size(64.dp)
                            )
                            Text(app.appName)
                        }
                    }
                }
            }
        }

        appUsage  is AppUsageState.Error -> {
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