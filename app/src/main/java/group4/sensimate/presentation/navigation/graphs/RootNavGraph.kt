package group4.sensimate.presentation.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import group4.sensimate.presentation.HomeScreen

@Composable
fun RootNavigationGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        route= Graph.ROOT,
        startDestination = Graph.AUTHENTICATION
    ){
        authNavGraph(navController = navController)
        composable(route= Graph.HOME){
            HomeScreen()
        }
    }
}

object Graph{
    const val ROOT= "root_graph"
    const val AUTHENTICATION= "auth_graph"
    const val HOME= "home_graph"
    const val SURVEY_DETAILS="survey_details_graph"
    const val EVENT_DETAILS="EVENT_details_graph"
    const val PROFILE_DETAILS="profile_details_graph"
}