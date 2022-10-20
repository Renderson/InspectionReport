package com.example.inspectionreport

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.inspectionreport.model.Report
import com.example.inspectionreport.navigation.AppNavigator
import com.example.inspectionreport.ui.theme.InspectionReportTheme

@ExperimentalAnimationApi
class MainActivity : AppCompatActivity() {

    private val report = listOf(
        Report(1, "Google", "Renderson", "Observação vazia", "19-02-1984")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InspectionReportTheme {
                // A surface container using the 'background' color from the theme
                Surface {
                    AppNavigator(report)
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val report = listOf(
        Report(1, "Google", "Renderson", "Observação vazia", "19-02-1984")
    )
    InspectionReportTheme {
        AppNavigator(report)
    }
}