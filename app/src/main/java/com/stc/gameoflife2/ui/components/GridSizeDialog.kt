package com.stc.gameoflife2.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.stc.gameoflife2.model.GameConfig

@Composable
fun GridSizeDialog(
    currentRows: Int,
    currentCols: Int,
    onDismiss: () -> Unit,
    onConfirm: (rows: Int, cols: Int) -> Unit
) {
    var rowsText by remember { mutableStateOf(currentRows.toString()) }
    var colsText by remember { mutableStateOf(currentCols.toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Grid Size") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Set grid dimensions (${GameConfig.MIN_SIZE}-${GameConfig.MAX_SIZE})")

                OutlinedTextField(
                    value = rowsText,
                    onValueChange = { rowsText = it.filter { c -> c.isDigit() } },
                    label = { Text("Rows") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )

                OutlinedTextField(
                    value = colsText,
                    onValueChange = { colsText = it.filter { c -> c.isDigit() } },
                    label = { Text("Columns") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val rows = rowsText.toIntOrNull() ?: currentRows
                    val cols = colsText.toIntOrNull() ?: currentCols
                    onConfirm(rows, cols)
                }
            ) {
                Text("Apply")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}