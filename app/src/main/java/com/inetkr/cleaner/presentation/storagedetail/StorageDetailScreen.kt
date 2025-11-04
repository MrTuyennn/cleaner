package com.inetkr.cleaner.presentation.storagedetail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ConfigurationScreenWidthHeight")
@Composable
fun StorageDetailScreen(
    nav: NavController,
    viewModel: StorageDetailViewModel = hiltViewModel()
) {
    val currentFolder = viewModel.currentFolderSystem.collectAsState().value
    val showBottomSheet = viewModel.showBottomSheet.collectAsState().value

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )

    val configuration = LocalConfiguration.current
    val maxSheetHeight = (configuration.screenHeightDp * 2 / 3).dp

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Show bottom sheet") },
                icon = { Icon(Icons.Filled.Add, contentDescription = null) },
                onClick = {
                    viewModel.showBottomSheet()
                }
            )
        }
    ) {

        if (showBottomSheet) {
            ModalBottomSheet(
                modifier = Modifier.fillMaxHeight(),
                sheetState = sheetState,
                onDismissRequest = {
                    viewModel.hideBottomSheet()
                },
            ) {
                Box(
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(8.dp)
                        .heightIn(max = maxSheetHeight)
                ) {
                    LazyColumn {
                        items(currentFolder.lsFile) { file ->
                            Card {
                                Text(file.name)
                            }
                        }
                    }
                }
            }
        }
    }
}
