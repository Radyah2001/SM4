package group4.sensimate.presentation.user

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
import group4.sensimate.ui.components.GradientButton
import group4.sensimate.ui.components.GradientTextField
import group4.sensimate.presentation.navigation.graphs.Graph
import group4.sensimate.ui.theme.SensiMateTheme
import group4.sensimate.UserPreferences
import group4.sensimate.data.model.User
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(navController: NavController,vm: UserViewModel = viewModel()) {
    val user by vm.userState.collectAsState(initial= User())
    ProvideWindowInsets {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.background))
                .verticalScroll(rememberScrollState())
                .navigationBarsWithImePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo Image",
                modifier = Modifier
                    .size(250.dp)
                    .clip(CircleShape)
                    .padding(8.dp, 0.dp, 8.dp, 0.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(Color.Transparent)
            )

            Text(
                text = "SensiMate",
                fontSize = 40.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            val focusManager = LocalFocusManager.current

            GradientTextField(
                text = vm.username,
                label= "UserName:",
                onChange = { vm.usernameChange(it) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Info,
                        tint = Color.White,
                        contentDescription = "username"
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

            var isPasswordVisible by remember { mutableStateOf(false) }
            GradientTextField(
                text = vm.password,
                label= "Password:",
                onChange = { vm.passwordChange(it) },
                leadingIcon = {
                    IconButton(onClick = {
                        isPasswordVisible = !isPasswordVisible
                    }) {
                        Icon(
                            imageVector = if (isPasswordVisible)
                                Icons.Filled.Visibility
                            else
                                Icons.Filled.VisibilityOff,
                            contentDescription = "Password Visibility",
                            tint = Color.White
                        )
                    }
                },
                visualTransformation = if (isPasswordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Password,
                keyBoardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            )


            val context = LocalContext.current
            val coroutineScope = rememberCoroutineScope()
            GradientButton(
                onClick = {
                    vm.signIn()
                    coroutineScope.launch {
                        if(user.isLoggedIn) {
                            UserPreferences(context).saveRole(vm.username)
                            navController.popBackStack()
                            navController.navigate(Graph.HOME)
                        }else{
                            Toast.makeText(context, "forget username || password!!", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                text = "Sign In",
                fontSize = 20,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp).semantics { testTag = "Sign In" }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInPreview() {
    SensiMateTheme {
        SignInScreen(navController = rememberNavController())
    }
}
