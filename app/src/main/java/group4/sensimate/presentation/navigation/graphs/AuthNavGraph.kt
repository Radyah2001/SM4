package group4.sensimate.presentation.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import group4.sensimate.presentation.user.WelcomeScreen
import group4.sensimate.presentation.user.SignInScreen
import group4.sensimate.presentation.user.SignUpScreen


fun NavGraphBuilder.authNavGraph(navController: NavHostController){
    navigation(
        route= Graph.AUTHENTICATION,
        startDestination = AuthScreen.Welcome.route
    ){
        composable(route = AuthScreen.Welcome.route){
            WelcomeScreen(navController = navController)
        }

        composable(route = AuthScreen.SignIn.route){
            SignInScreen(navController = navController)
        }

        composable(route = AuthScreen.SignUp.route){
            SignUpScreen(navController = navController)
        }
    }
}

sealed class AuthScreen(val route: String){
    object Welcome: AuthScreen("WELCOME")
    object SignIn: AuthScreen("SIGN_IN")
    object SignUp: AuthScreen("SIGN_UP")
}