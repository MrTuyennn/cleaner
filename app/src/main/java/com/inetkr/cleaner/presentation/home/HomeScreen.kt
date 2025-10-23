package com.inetkr.cleaner.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val state = homeViewModel.appConfig.collectAsState().value

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
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
        }
    }
}