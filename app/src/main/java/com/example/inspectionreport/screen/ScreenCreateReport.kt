package com.example.inspectionreport.screen

import android.app.DatePickerDialog
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.inspectionreport.R
import com.example.inspectionreport.model.Report
import com.example.inspectionreport.ui.theme.InspectionReportTheme
import com.example.inspectionreport.util.datePickerDialog
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import com.example.inspectionreport.ui.theme.backgroundColor
import com.example.inspectionreport.util.ShowAlertDialog
import com.example.inspectionreport.util.formatDate
import com.example.inspectionreport.util.getToday

@ExperimentalAnimationApi
@Composable
fun ScreenCreateReport(navController: NavHostController, report: Report) {

    var showMenu by remember { mutableStateOf(false) }
    val showDialog = remember { mutableStateOf(false) }
    val type = remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.screen_name_create_report)) },
                backgroundColor = MaterialTheme.colors.surface,
                actions = {
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(Icons.Default.MoreVert, "menu")
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(onClick = {
                            showMenu = false
                            showDialog.value = true
                            type.value = 1
                        }) {
                            Text(text = stringResource(R.string.salve))
                        }
                        DropdownMenuItem(onClick = {
                            showMenu = false
                            showDialog.value = true
                            type.value = 2
                        }) {
                            Text(text = stringResource(R.string.delete))
                        }
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }) {
                        Icon(Icons.Filled.ArrowBack, "voltar")
                    }
                },
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // Open Camera
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_photo_camera_24), contentDescription = "camera" )
            }
        },
        content = {
            FillTextField(report)
            val title: String
            val description: String

            when(type.value) {
                1 -> {
                    title = "Salvar"
                    description = "Deseja salvar o relatório?"
                } else -> {
                    title = "Deletar"
                    description = "Deseja deletar o relatório?"
                }
            }
            ShowAlertDialog(
                showDialog,
                title = title,
                text = description,
                onConfirm = { salveReport(navController/*, viewModel*/) },
                onDismiss = { })
        },
    )
}

fun salveReport(navController: NavHostController) {
    /*viewModel.saveReport()*/
    navController.popBackStack()
}

@Composable
fun FillTextField(report: Report) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(state = scrollState)
            .fillMaxSize()
    ) {
        val context = LocalContext.current
        val myCalendar = Calendar.getInstance()

        var company by remember { mutableStateOf(report.company) }
        var sanitarian by remember { mutableStateOf(report.sanitarian) }
        var date by remember { mutableStateOf(formatDate(report.date)) }
        var note by remember { mutableStateOf(report.note) }

        var inputErrorCompany by remember { mutableStateOf(false) }
        var inputErrorSanitarian by remember { mutableStateOf(false) }

        var msgError by remember { mutableStateOf(R.string.msg_error_default) }

        val format = stringResource(R.string.format_date)
        val maxChar = 20

        val datePickerOnDataSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val sdf = SimpleDateFormat(format, Locale.UK)
                date = sdf.format(myCalendar.time)
            }

        // Date
        OutlinedTextField(
            value = date,
            onValueChange = { newText ->
                date = newText
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable {
                    datePickerDialog(context, datePickerOnDataSetListener, myCalendar)
                },
            label = {
                Text(text = stringResource(R.string.date))
            },
            trailingIcon = {
                Icon(imageVector = Icons.Filled.DateRange, contentDescription = "")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            maxLines = 1,
            enabled = false
        )

        // Company
        OutlinedTextField(
            value = company,
            onValueChange = { newText ->
                if (newText.length <= maxChar) {
                    company = newText
                }
                company.isEmpty().let {
                    inputErrorCompany = it
                    msgError = R.string.msg_error_company
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = {
                Text(text = stringResource(R.string.company))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            maxLines = 1,
            isError = inputErrorCompany
        )
        BoxInteraction(inputErrorCompany, company, msgError)

        // Sanitarian
        OutlinedTextField(
            value = sanitarian,
            onValueChange = { newText ->
                if (newText.length <= maxChar) {
                    sanitarian = newText
                }
                sanitarian.isEmpty().let {
                    inputErrorSanitarian = it
                    msgError = R.string.msg_error_sanitarian
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = {
                Text(text = stringResource(R.string.sanitarian))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            maxLines = 1,
            isError = inputErrorSanitarian
        )
        BoxInteraction(inputErrorSanitarian, sanitarian, msgError)

        // Note
        OutlinedTextField(
            value = note,
            onValueChange = { newText ->
                if (newText.length <= 120) {
                    note = newText
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = {
                Text(text = stringResource(R.string.note))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
        )
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "${note.length}/120",
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .padding(end = 14.dp)
                    .align(Alignment.BottomEnd)
            )
        }
    }
}

@Composable
private fun BoxInteraction(error: Boolean, maxChar: String, msgError: Int) {
    Box(modifier = Modifier.fillMaxWidth()) {
        if (error) {
            Text(
                text = stringResource(id = msgError),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        Text(
            text = "${maxChar.length}/20",
            style = MaterialTheme.typography.caption,
            modifier = Modifier
                .padding(end = 14.dp)
                .align(Alignment.BottomEnd)
        )
    }
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreviewCreate() {
    val report = Report(1, "Google", "Renderson", "Observação vazia", getToday())
    val navController = rememberAnimatedNavController()

    InspectionReportTheme {
        ScreenCreateReport(navController, report)
    }
}