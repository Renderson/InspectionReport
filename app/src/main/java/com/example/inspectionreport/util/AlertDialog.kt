package com.example.inspectionreport.util

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.example.inspectionreport.R

@Composable
fun ShowAlertDialog(
    showDialog: MutableState<Boolean>,
    title: String,
    text: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog.value)
        AlertDialog(
            onDismissRequest = { },
            confirmButton = {
                TextButton(onClick = {
                    showDialog.value = false
                    onConfirm()
                }) {
                    Text(stringResource(R.string.dialog_btn_confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    onDismiss()
                    showDialog.value = false
                }) {
                    Text(stringResource(R.string.dialog_btn_cancel))
                }
            },
            title = { Text(text = title) },
            text = { Text(text = text) }
        )
}