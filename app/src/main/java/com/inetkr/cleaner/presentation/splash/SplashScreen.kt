package com.inetkr.cleaner.presentation.splash

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.inetkr.cleaner.utils.router.HomeRoute
import com.junnew.design_system.theme.appColors
import com.junnew.design_system.theme.dimens
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.runtime.collectAsState

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SplashScreen (
    nav: NavController,
    splashViewModel: SplashViewModel = hiltViewModel()
) {

    val viewState = splashViewModel.events
    val appConfig = splashViewModel.appInfo.collectAsState().value
    val color = MaterialTheme.appColors
    val d = MaterialTheme.dimens
    val text = MaterialTheme.typography
    val shape = MaterialTheme.shapes

    LaunchedEffect(Unit) {
      viewState.collectLatest { event ->
          when(event){
              SplashEvent.NavigateToHome -> nav.navigate(HomeRoute)
          }
      }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = color.purpleBlueOpa,
                    titleContentColor = color.error,
                ),
                title = {
                    Text("Top app bar")
                }
            )
        },
    ) {  padding ->
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
            Text(appConfig.versionNumber)
        }

    }
}