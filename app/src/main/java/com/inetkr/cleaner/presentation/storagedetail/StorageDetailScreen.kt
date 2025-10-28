package com.inetkr.cleaner.presentation.storagedetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier

@Composable
fun StorageDetailScreen(
    nav: NavController,
    viewModel: StorageDetailViewModel = hiltViewModel()
) {
    val currentFolder = viewModel.currentFolderSystem.collectAsState().value

    Box(modifier = Modifier.statusBarsPadding()) {
        LazyColumn {
            items(currentFolder.lsFile) { file ->
                Card {
                    Text(file.name)
                }
            }
        }
    }
}