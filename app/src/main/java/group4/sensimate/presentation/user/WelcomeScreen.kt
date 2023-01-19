package group4.sensimate.presentation.user

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.RowScopeInstance.align
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import group4.sensimate.R
import group4.sensimate.UserPreferences
import group4.sensimate.data.model.User
import group4.sensimate.ui.components.GradientButton
import group4.sensimate.ui.components.GradientText
import group4.sensimate.presentation.navigation.graphs.AuthScreen
import group4.sensimate.presentation.navigation.graphs.Graph
import group4.sensimate.ui.theme.SensiMateTheme
import kotlinx.coroutines.launch

@Composable
fun WelcomeScreen(navController: NavController, vm:UserViewModel =viewModel()) {
    val user by vm.userState.collectAsState(initial= User())

    Column(

        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background))
    ) {

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "SensiMate Logo",
            modifier = Modifier
                .size(250.dp)
                .clip(CircleShape)
                .padding(start = 8.dp, end = 8.dp)
                .background(Color.Transparent)
                .align(Alignment.CenterHorizontally)
        )

        Text(
            text = "Welcome",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.white),
            modifier = Modifier.align(Alignment.CenterHorizontally)


        )

        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(start=15.dp, end= 15.dp)
        ) {
            Checkbox(
                checked = vm.cookieCheckedState,
                onCheckedChange = { vm.cookieCheckedStateChange(it) },
                colors = CheckboxDefaults.colors(
                    checkedColor = colorResource(R.color.violets_blue),
                    uncheckedColor = colorResource(R.color.light_carmine_pink ),
                    colorResource(R.color.background )
                )
            )
            Text(
                text = "Continue using cookies*",
                color = Color.White,
                modifier = Modifier
                    .padding(16.dp)
                    .background(colorResource(R.color.background))
            )
        }
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(start=15.dp, end= 15.dp)
        ) {
            Checkbox(
                checked = vm.ageCheckedState,
                onCheckedChange = { vm.ageCheckedStateChange(it)},
                colors = CheckboxDefaults.colors(
                    checkedColor = colorResource(R.color.violets_blue),
                    uncheckedColor = colorResource(R.color.light_carmine_pink ),
                    checkmarkColor = colorResource(R.color.background )
                )
            )
            Text(
                text = "I'm 18 years or older* ",
                color = Color.White,
                modifier = Modifier
                    .padding(16.dp)
                    .background(colorResource(R.color.background)).semantics { testTag = "18" }
            )
        }

        val context = LocalContext.current
        val scope = rememberCoroutineScope()
        GradientButton(
            onClick = {
                vm.signInAsGuest()
                scope.launch {
                    if (user.isLoggedIn && vm.cookieCheckedState && vm.ageCheckedState) {
                        UserPreferences(context).saveRole("Guest")
                        navController.popBackStack()
                        navController.navigate(Graph.HOME)
                    } else {
                        Toast.makeText(
                            context,
                            "please approve both using cookie & you're older than 18!!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            },
            text = "Join as Guest",
            fontSize= 20,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp).semantics { testTag = "Guest" }
        )

        Spacer(modifier = Modifier.padding(30.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp)
        ) {
            OutlinedButton(
                border = BorderStroke(1.dp, color = Color.Transparent),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.violets_blue)),
                onClick = {
                    navController.navigate(AuthScreen.SignUp.route)
                }
            ) {
                Text("SignUp", fontSize = 18.sp, color = Color.White)
            }
            OutlinedButton(
                border = BorderStroke(1.dp, color = Color.Transparent),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.light_carmine_pink)),
                onClick = {
                    navController.navigate(AuthScreen.SignIn.route)
                }
            ) {
                Text("SignIn", fontSize = 18.sp, color = Color.White, modifier = Modifier.semantics { testTag = "Signin" })
            }
        }

    }
    //}
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    SensiMateTheme {
        WelcomeScreen(navController = rememberNavController())
    }
}