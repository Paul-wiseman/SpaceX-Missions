package com.mindera.rocketscience.spacex_presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mindera.rocketscience.core.R
import com.mindera.rocketscience.core_ui.ui.LocalSpacing
import com.mindera.rocketscience.core_ui.ui.ui.theme.MyApplicationTheme

@Composable
fun FilterDialog(
    isOpen: Boolean,
    onClose: () -> Unit,
    onApplyFilters: (year: Int?, successful: Boolean) -> Unit
) {
    val localSpacing = LocalSpacing.current
    var selectedYear by remember { mutableStateOf<Int?>(null) }
    var isSuccess by remember { mutableStateOf(false) }
    if (isOpen) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            tonalElevation = AlertDialogDefaults.TonalElevation,
            color = MaterialTheme.colorScheme.onPrimary,
            shape = RoundedCornerShape(localSpacing.space12)
        ) {

            AlertDialog(
                onDismissRequest = onClose,
                title = { Text(stringResource(R.string.successful_launch)) },
                text = {
                    Column(modifier = Modifier.padding(localSpacing.spaceMedium)) {
                        Text(stringResource(R.string.year))
                        Spacer(modifier = Modifier.height(localSpacing.spaceSmall))
                        YearDropdown(selectedYear) { selectedYear = it }

                        Spacer(modifier = Modifier.height(localSpacing.spaceMedium))

                        Text(stringResource(R.string.successful_launch))
                        Spacer(modifier = Modifier.height(localSpacing.spaceSmall))
                        SuccessCheckbox(isSuccess) { isSuccess = !isSuccess }

                        Spacer(modifier = Modifier.height(localSpacing.spaceMedium))
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onApplyFilters(selectedYear, isSuccess)
                            onClose()
                        }) {
                        Text(stringResource(R.string.apply))
                    }
                },
                dismissButton = {
                    TextButton(onClick = onClose) {
                        Text(stringResource(R.string.cancel))
                    }
                }
            )
        }
    }

}

@Composable
fun YearDropdown(selectedYear: Int?, onYearSelected: (Int?) -> Unit) {
    val years = (2000..2017).toList()
    var expanded by remember { mutableStateOf(false) }

    Box {
        TextButton(onClick = { expanded = true }) {
            Text(selectedYear?.toString() ?: "Select Year")
        }

        DropdownMenu(
            modifier = Modifier.heightIn(max = LocalSpacing.current.space200),
            expanded = expanded, onDismissRequest = { expanded = false }) {
            years.forEach { year ->
                DropdownMenuItem(
                    text = {
                        Text(year.toString())
                    },
                    onClick = {
                        onYearSelected(year)
                        expanded = false
                    })
            }
        }
    }
}

@Composable
fun SuccessCheckbox(isSuccess: Boolean?, onSuccessSelected: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = isSuccess == true,
            onCheckedChange = { onSuccessSelected() }
        )
        Text(stringResource(R.string.only_successful_launches))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFilterDialog() {
    MyApplicationTheme {
        var isDialogOpen by remember { mutableStateOf(true) }

        FilterDialog(
            isOpen = isDialogOpen,
            onClose = { isDialogOpen = false },
            onApplyFilters = { year, success -> }
        )
    }

}
