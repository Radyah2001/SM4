package group4.sensimate.presentation.event

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import group4.sensimate.R
import group4.sensimate.ui.components.GradientButton
import group4.sensimate.ui.components.GradientTextField
import group4.sensimate.ui.components.ProfileImage
import group4.sensimate.ui.theme.SensiMateTheme

@Composable
fun CreateEventScreen(navController: NavController, vm:EventsViewModel = viewModel()) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background))
            .padding(20.dp)

    ) {

        ProfileImage()
        Text(
            text = "Add Image ",
            fontSize = 20.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        GradientTextField(
            text = vm.name,
            onChange ={vm.nameChange(it)},
            label = "Event Name:",
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Info,
                    tint = Color.White,
                    contentDescription = "name"
                )
            }
        )

        GradientTextField(
            text = vm.location,
            onChange ={vm.locationChange(it)},
            label = "Location:",
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    tint = Color.White,
                    contentDescription = "location"
                )
            }
        )

        GradientTextField(
            text = vm.date,
            onChange ={vm.dateChange(it)},
            label = "Date:",
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.CalendarMonth ,
                    tint = Color.White,
                    contentDescription = "date"
                )
            }
        )
        GradientTextField(
            text = vm.time,
            onChange ={vm.timeChange(it)},
            label = "Time:",
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.PunchClock,
                    tint = Color.White,
                    contentDescription = "time"
                )
            }
        )
        GradientTextField(
            text = vm.survey,
            onChange ={vm.surveyChange(it)},
            label = "Survey",
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Info,
                    tint = Color.White,
                    contentDescription = "survey"
                )
            }
        )
        GradientTextField(
            text = vm.link,
            onChange ={vm.linkChange(it)},
            label = "Link",
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Info,
                    tint = Color.White,
                    contentDescription = "link"
                )
            }
        )

        Spacer(modifier = Modifier.padding(20.dp))
        val context= LocalContext.current
        GradientButton(
            onClick = {
                if(vm.createEvent()) {
                    navController.popBackStack()
                }else{
                    Toast.makeText(context, "Error Occurred!!", Toast.LENGTH_SHORT).show()
                }
            },
            text = "Create Event",
            fontSize= 20,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )

    }
}

@Preview(showBackground = true)
@Composable
fun RegisterEventPreview() {
    SensiMateTheme {
        CreateEventScreen(navController = rememberNavController())
    }
}