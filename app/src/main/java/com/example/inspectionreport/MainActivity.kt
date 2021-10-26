package com.example.inspectionreport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.inspectionreport.model.Screen
import com.example.inspectionreport.model.Report
import com.example.inspectionreport.screen.ScreenCreateReport
import com.example.inspectionreport.screen.ScreenReportList
import com.example.inspectionreport.ui.theme.InspectionReportTheme
import com.google.gson.Gson

class MainActivity : ComponentActivity() {

    private val report = listOf(
        Report(1, "Google", "Renderson", "Observação vazia","19-02-1984")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InspectionReportTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    AppNavigator(report)
                }
            }
        }
    }
}

@Composable
fun AppNavigator(report: List<Report>) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.REPORT_LIST.route) {
        composable(route = Screen.REPORT_LIST.route,) { ScreenReportList(navController, report = report) }
        composable(route = Screen.REPORT_CREATE.route, arguments = listOf(
            navArgument("report") { type = NavType.StringType }
        )) { backStackEntry ->
            backStackEntry.arguments?.getString("report")?.let { json ->
                val reportJson = Gson().fromJson(json, Report::class.java)
                ScreenCreateReport(navController, report = reportJson)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
        val report = listOf(
            Report(1, "Google", "Renderson", "Observação vazia","19-02-1984")
        )
    InspectionReportTheme {
        AppNavigator(report)
    }
}