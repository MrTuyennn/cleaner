package com.inetkr.cleaner.presentation.scanfile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.inetkr.cleaner.domain.entity.MediaFile
import coil3.compose.AsyncImage


@Composable
fun ScanFileScreen(
    nav: NavController,
    viewModel: ScanFileViewModel = hiltViewModel()) {
    val scanState by viewModel.scanState.collectAsState()

    when (scanState) {
        is ScanFileState.Idle -> {
            // Empty state
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Ready to scan files")
            }
        }

        is ScanFileState.Loading -> {
            // Loading state
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

        is ScanFileState.Success -> {
            // Success state
            Column {
                Box(modifier = Modifier.fillMaxWidth().statusBarsPadding())

                LazyColumn {
                    item {
                        Text(
                            "Found ${(scanState as ScanFileState.Success).images.size} images and ${(scanState as ScanFileState.Success).videos.size} videos",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }

                    items((scanState as ScanFileState.Success).folders) { folder ->
                        Text(folder.name)
                    }

                    items((scanState as ScanFileState.Success).images) { image ->
                        ImageItem(image){
                            viewModel.deleteItemFile(image)
                        }
                    }

                    items((scanState as ScanFileState.Success).videos) { video ->
                        VideoItem(video) {
                            viewModel.deleteItemFile(video)
                        }
                    }
                }
            }
        }

        is ScanFileState.Error -> {
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
                        (scanState as ScanFileState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}


@Composable
fun ImageItem(image: MediaFile,onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = image.thumbnailUri,
                contentDescription = image.name,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = image.uri.toString(),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = image.thumbnailUri,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = image.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Size: ${image.size} bytes",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun VideoItem(video: MediaFile,onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
//            AsyncImage(
//                model = video.thumbnailUri,
//                contentDescription = video.name,
//                modifier = Modifier.size(64.dp)
//            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = video.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Duration: ${video.duration}ms, Size: ${video.size} bytes",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}