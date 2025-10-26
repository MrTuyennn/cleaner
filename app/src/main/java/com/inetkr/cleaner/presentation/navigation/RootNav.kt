package com.inetkr.cleaner.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.inetkr.cleaner.presentation.splash.SplashScreen
import com.inetkr.cleaner.utils.router.HomeRoute
import com.inetkr.cleaner.utils.router.SplashRoute
import androidx.navigation.compose.composable
import com.inetkr.cleaner.presentation.appusage.AppUsageScreen
import com.inetkr.cleaner.presentation.home.HomeScreen
import com.inetkr.cleaner.presentation.scanfile.ScanFileScreen
import com.inetkr.cleaner.utils.router.AppUsageRoute
import com.inetkr.cleaner.utils.router.ScanFileRoute


@Composable
fun RootNav() {
    val nav = rememberNavController()
    NavHost(
        navController = nav,
        startDestination = SplashRoute,
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        }
    ) {
        composable<SplashRoute> {
            SplashScreen(nav)
        }
        composable<HomeRoute> {
            HomeScreen(nav)
        }
        composable<ScanFileRoute> {
            ScanFileScreen(nav)
        }
        composable<AppUsageRoute> {
            AppUsageScreen(nav)
        }
    }
}