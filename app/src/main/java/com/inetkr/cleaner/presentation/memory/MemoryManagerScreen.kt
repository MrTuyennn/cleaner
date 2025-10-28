package com.inetkr.cleaner.presentation.memory

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.inetkr.cleaner.utils.router.AppUsageRoute
import com.inetkr.cleaner.utils.router.StorageRoute

@Composable
fun MemoryManagerScreen(
    nav: NavController,
    memoryViewModel: MemoryManagerViewModel = hiltViewModel()
) {

    val memory by memoryViewModel.memory.collectAsState()
    val storage by memoryViewModel.storage.collectAsState()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.statusBarsPadding()
    ) {
        Column {
            Text("user using ${memory?.readableUsed}")
            Text("available ram${memory?.readableAvailable}")
            Text("total ram${memory?.readableTotal}")
            Text("---------///---------")
            Text("total storage ${storage?.readableTotal}")
            Text("total storage used ${storage?.readableUsed}")
            Text("total storage free ${storage?.readableFree}")
            Button(
                onClick = {
                    nav.navigate(StorageRoute)
                }
            ) {
                Text("Go to App Storage")
            }
        }
    }
}