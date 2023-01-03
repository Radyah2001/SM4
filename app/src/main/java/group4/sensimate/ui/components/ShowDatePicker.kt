package group4.sensimate.ui.components

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.squaredem.composecalendar.ComposeCalendar
import java.time.LocalDate
import java.util.*

@Composable
fun ShowDatePicker(context: Context): String? {
    var birthday: String =""
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        val showDialog = rememberSaveable { mutableStateOf(false) }
        val selectedDateMillis = rememberSaveable { mutableStateOf<LocalDate?>(null) }


        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            selectedDateMillis.value?.let {
                Text(text = it.toString())
            }

            Button(onClick = { showDialog.value = true }) {
                Text("Show dialog")
            }
        }

        if (showDialog.value) {
            ComposeCalendar(
                startDate = LocalDate.now(),
                minDate = LocalDate.now(),
                maxDate = LocalDate.MAX,
                onDone = { it: LocalDate ->
                    selectedDateMillis.value = it
                    showDialog.value = false
                },
                onDismiss = { showDialog.value = false }
            )
        }
        birthday = selectedDateMillis.value.toString()
    }
    return birthday
}
