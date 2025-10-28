package com.inetkr.cleaner.presentation.storage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun StorageScreen(
    nav: NavController,
    viewModel: StorageViewModel = hiltViewModel()
) {
    val lsFolder = viewModel.folderSystem.collectAsState().value


    Box(modifier = Modifier.statusBarsPadding()) {
        LazyColumn {
            items(lsFolder) { folder ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .align(Alignment.Center)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(folder.name)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("------")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(folder.totalSize.toString())
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("------")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(folder.date)
                        Text("------")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(folder.lsFile.size.toString())
                    }
                }
            }
        }
    }
}