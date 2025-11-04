package com.inetkr.cleaner.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.inetkr.cleaner.utils.Utilities
import com.inetkr.cleaner.utils.perm.RequestMediaPermission
import com.inetkr.cleaner.utils.router.AppUsageRoute
import com.inetkr.cleaner.utils.router.MemoryRoute
import com.inetkr.cleaner.utils.router.ScanFileRoute
import com.inetkr.cleaner.utils.router.ScannerRoute

@Composable
fun HomeScreen(
    nav: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val state = homeViewModel.appConfig.collectAsState().value
    val context = LocalContext.current

    var askOpenSettings by remember { mutableStateOf(false) }
    var canScan by remember { mutableStateOf(false) }

    if (!canScan) {
        RequestMediaPermission(
            onGranted = {  },
            onDenied = { permanently ->
                if (permanently) askOpenSettings = true
                else canScan = false
            }
        )
    }


    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (askOpenSettings) {
                Spacer(Modifier.height(100.dp))
                Text("Bạn đã từ chối quyền và chọn 'Don't ask again'. Vui lòng mở cài đặt để cấp quyền.")
                Button(onClick = {
                    Utilities().openAppSetting(context)
                }) { Text("Mở App Settings") }
            }
            Text(text = "Version number: ${state.versionNumber}")
            Text(text = "Build number: ${state.buildNumber}")
            Text(text = "Display name: ${state.displayName}")
            Text(text = "Bundle name: ${state.bundleName}")
            Text(text = "UUID: ${state.uuid}")
            Text(text = "Locales: ${state.locales}")
            Text(text = "Alpha code: ${state.alphaCode}")
            Text(text = "Time zone: ${state.timeZone}")
            Text(text = "Is low RAM device: ${state.isLowRamDevice}")
            Text(text = "Physical RAM size: ${state.physicalRamSize} MB")
            Text(text = "Available RAM size: ${state.availableRamSize} MB")
            Button(
                onClick = {
                    nav.navigate(ScanFileRoute)
                }
            ) {
                Text("Go to Scan File")
            }

            Button(
                onClick = {
                    nav.navigate(AppUsageRoute)
                }
            ) {
                Text("Go to App Usage")
            }

            Button(
                onClick = {
                    nav.navigate(MemoryRoute)
                }
            ) {
                Text("Go to App Memory")
            }

            Button(
                onClick = {
                    nav.navigate(ScannerRoute)
                }
            ) {
                Text("Go to Scanner")
            }
        }
    }
}