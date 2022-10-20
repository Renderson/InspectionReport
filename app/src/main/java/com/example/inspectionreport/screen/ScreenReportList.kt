package com.example.inspectionreport.screen

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.inspectionreport.R
import com.example.inspectionreport.model.Report
import com.example.inspectionreport.ui.theme.InspectionReportTheme
import com.example.inspectionreport.util.formatDate
import com.example.inspectionreport.util.getToday
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalAnimationApi
@Composable
fun ScreenReportList(
    navController: NavHostController,
    report: List<Report>,
    systemInDarkTheme: Boolean
) {

    val context = LocalContext.current
    val iconState = remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Relatórios") },
                backgroundColor = MaterialTheme.colors.surface,
                actions = {
                    IconButton(onClick = { setTheme(systemInDarkTheme, iconState) }) {
                        if (iconState.value) {

                            ChangeIcon(R.drawable.ic_night)
                        }
                        else { ChangeIcon(R.drawable.ic_day) }
                    }
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val reportEmpty = Report(0, "", "","", getToday())
                    navigateCreateReport(navController, reportEmpty)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "fab icon")
            }
        },
        content = {
            ReportListItem(navController, report)
        },
    )
}

@Composable
private fun ChangeIcon(icon: Int) {
    Icon(
        painter = painterResource(id = icon),
        contentDescription = "Change theme button",
        tint = MaterialTheme.colors.onSurface
    )
}

private fun setTheme(darkTheme: Boolean, iconState: MutableState<Boolean>) {
    if (darkTheme) {
        setLightTheme()
        iconState.value = true
    } else
        setDarkTheme()
        iconState.value = false
}

private fun setLightTheme() {
    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
}

private fun setDarkTheme() {
    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
}

@Composable
fun ReportListItem(navController: NavHostController, report: List<Report>) {

    LazyColumn {
        items(report) { reports ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable(onClick = { navigateCreateReport(navController, reports) }),
                elevation = 4.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                {
                    Text(text = reports.company, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(text = formatDate(reports.date), Modifier.padding(0.dp, 8.dp), fontSize = 12.sp)
                }
            }

        }
    }
}

private fun navigateCreateReport(navController: NavHostController, report: Report) {
    val reportJson = Gson().toJson(report)
    navController.navigate("screenCreateReport/$reportJson")
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val report = listOf(
        Report(1, "Google", "Renderson", "Observação vazia", getToday())
    )
    val navController = rememberAnimatedNavController()

    InspectionReportTheme {
        ScreenReportList(navController, report, isSystemInDarkTheme())
    }
}