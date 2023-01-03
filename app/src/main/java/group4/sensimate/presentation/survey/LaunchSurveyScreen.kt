package group4.sensimate.presentation.survey


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.admin.SystemUpdatePolicy
import android.content.pm.ActivityInfo
import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import group4.sensimate.R
import group4.sensimate.ui.components.GradientTextField
import group4.sensimate.presentation.navigation.graphs.SurveyDetailsScreen
import group4.sensimate.presentation.user.UserViewModel
import group4.sensimate.ui.components.getPickedDateAsString
import group4.sensimate.ui.theme.SensiMateTheme
import java.util.*

@Composable
fun LaunchSurveyScreen(navController: NavController, vm: UserViewModel = viewModel()) {
    SystemUpdatePolicy.createPostponeInstallPolicy()
        val focusManager = LocalFocusManager.current
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.background))
                .padding(20.dp)
        ) {

            Text(
                text = "Before we start, we would like to know a little about you.",
                fontSize = 20.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(30.dp))

            var context = LocalContext.current
            GradientTextField(
                text = vm.birthday,
                label= "Birthday:",
                onChange = { vm.birthdayChange(it) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Info,
                        tint = Color.White,
                        contentDescription = "birthday"
                    )
                },
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text,
                keyBoardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                modifier = Modifier
                    .clickable {
                        val calendar = Calendar.getInstance()
                        DatePickerDialog(
                            context, { _, year, month, day ->
                                vm.birthdayChange(getPickedDateAsString(year, month, day))
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    }
            )

            GradientTextField(
                text = vm.gender,
                label = "Gender:",
                onChange = {vm.genderChange(it) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Info,
                        tint = Color.White,
                        contentDescription = "gender"
                    )
                },
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text,
                keyBoardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            )

            GradientTextField(
                text = vm.postalcode,
                label = "Postal Code:",
                onChange = { vm.postalcodeChange(it) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Info,
                        tint = Color.White,
                        contentDescription = "postalCode"
                    )
                },
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number,
                keyBoardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                OutlinedButton(
                    border = BorderStroke(1.dp, Color.Blue),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.violets_blue)),
                    onClick = {  navController.popBackStack(route= SurveyDetailsScreen.ScanBarCode.route, inclusive = false) },
                )
                {
                    Text("Back", fontSize = 20.sp, color = Color.White)
                }

                OutlinedButton(
                    onClick = {  navController.navigate(SurveyDetailsScreen.StartSurvey.route) },
                    border = BorderStroke(1.dp, Color.Blue),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.light_carmine_pink))
                )
                {
                    Text("Next", fontSize = 20.sp, color = Color.White)
                }
            }
        }

}

@Preview(showBackground = true)
@Composable
fun LaunchSurveyScreenPreview() {
    SensiMateTheme {
        LaunchSurveyScreen(navController = rememberNavController())
    }
}
