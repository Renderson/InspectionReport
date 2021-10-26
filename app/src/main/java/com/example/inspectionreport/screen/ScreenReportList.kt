package com.example.inspectionreport.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.inspectionreport.model.Report
import com.example.inspectionreport.ui.theme.InspectionReportTheme
import com.google.gson.Gson


@Composable
fun ScreenReportList(navController: NavHostController, report: List<Report>) {

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Relatórios") },
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { Toast.makeText(context, "Fab", Toast.LENGTH_SHORT).show()},
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
fun ReportListItem(navController: NavHostController, report: List<Report>) {
    fun navigateCreateReport(report: Report) {
        val reportJson = Gson().toJson(report)
        navController.navigate("screenCreateReport/$reportJson")
    }

    LazyColumn {
        items(report) { reports ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable(onClick = { navigateCreateReport(reports) }),
                elevation = 4.dp
            ) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp))
                {
                    Text(text = reports.company, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(text = reports.date, Modifier.padding(0.dp, 4.dp), fontSize = 12.sp)
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val report = listOf(
        Report(1, "Google", "Renderson","Observação vazia","19-02-2022")
    )
    val navController = rememberNavController()

    InspectionReportTheme {
        ScreenReportList(navController, report)
    }
}