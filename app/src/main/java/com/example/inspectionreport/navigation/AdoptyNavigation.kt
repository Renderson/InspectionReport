package com.example.inspectionreport.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.navArgument
import com.example.inspectionreport.model.Report
import com.example.inspectionreport.screen.ScreenCreateReport
import com.example.inspectionreport.screen.ScreenReportList
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.gson.Gson

@ExperimentalAnimationApi
@Composable
fun AppNavigator(report: List<Report>) {

    val navController = rememberAnimatedNavController()

    AnimatedNavHost(navController = navController, startDestination = Screen.REPORT_LIST.route) {
        composable(
            route = Screen.REPORT_LIST.route,
            enterTransition = { initial, _ ->
                when (initial.destination.route) {
                    Screen.REPORT_CREATE.route -> slideInHorizontally(
                        initialOffsetX = { 800 },
                        animationSpec = tween(800)
                    ) + fadeIn(animationSpec = tween(800))
                    else -> null
                }
            },
            exitTransition = { _, target ->
                when (target.destination.route) {
                    Screen.REPORT_CREATE.route ->
                        slideOutHorizontally(
                            targetOffsetX = { -800 },
                            animationSpec = tween(800)
                        ) + fadeOut(animationSpec = tween(800))
                    else -> null
                }
            },
            popEnterTransition = { initial, _ ->
                when (initial.destination.route) {
                    Screen.REPORT_CREATE.route ->
                        slideInHorizontally(
                            initialOffsetX = { -800 },
                            animationSpec = tween(800)
                        ) + fadeIn(animationSpec = tween(800))
                    else -> null
                }
            }
        ) { ScreenReportList(navController = navController, report = report, isSystemInDarkTheme()) }
        composable(
            route = Screen.REPORT_CREATE.route,
            enterTransition = { initial, _ ->
                when (initial.destination.route) {
                    Screen.REPORT_LIST.route -> slideInHorizontally(
                        initialOffsetX = { 800 },
                        animationSpec = tween(800)
                    ) + fadeIn(animationSpec = tween(800))
                    else -> null
                }
            },
            exitTransition = { _, target ->
                when (target.destination.route) {
                    Screen.REPORT_LIST.route ->
                        slideOutHorizontally(
                            targetOffsetX = { -800 },
                            animationSpec = tween(800)
                        ) + fadeOut(animationSpec = tween(800))
                    else -> null
                }
            },
            popExitTransition = { _, target ->
                when (target.destination.route) {
                    Screen.REPORT_LIST.route ->
                        slideOutHorizontally(
                            targetOffsetX = { 800 },
                            animationSpec = tween(800)
                        ) + fadeOut(animationSpec = tween(800))
                    else -> null
                }
            },
            arguments = listOf(
                navArgument("report") { type = NavType.StringType }
            )) { backStackEntry ->
            backStackEntry.arguments?.getString("report")?.let { json ->
                val reportJson = Gson().fromJson(json, Report::class.java)
                ScreenCreateReport(navController = navController, report = reportJson)
            }
        }
    }
}