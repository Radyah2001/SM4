package group4.sensimate.presentation.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import group4.sensimate.presentation.navigation.ButtomBarScreen
import group4.sensimate.presentation.event.CreateEventScreen
import group4.sensimate.presentation.event.SearchScreen
import group4.sensimate.presentation.event.EventsScreen
import group4.sensimate.presentation.survey.*
import group4.sensimate.presentation.user.ProfileScreen
import group4.sensimate.presentation.user.UpdateProfileScreen

@Composable
fun HomeNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        route= Graph.HOME,
        startDestination = ButtomBarScreen.Events.route
    ) {
        composable(route = ButtomBarScreen.Events.route) {
            EventsScreen(navController)
        }
        composable(route = ButtomBarScreen.Search.route) {
            SearchScreen()
        }
        composable(route = ButtomBarScreen.Profile.route) {
            ProfileScreen(navController)
        }
        profileDetailsNavGraph(navController)
        eventDetailsNavGraph(navController)
        surveyDetailsNavGraph(navController)
    }
}


fun NavGraphBuilder.profileDetailsNavGraph(navController: NavHostController){
    navigation(
        route= Graph.PROFILE_DETAILS,
        startDestination = ProfileDetailsScreen.Update.route
    ){
        composable(route= ProfileDetailsScreen.Update.route){
            UpdateProfileScreen(navController)
        }
    }
}

sealed class ProfileDetailsScreen(val route: String){
    object Update: ProfileDetailsScreen("UPDATE")
}

fun NavGraphBuilder.eventDetailsNavGraph(navController: NavHostController){
    navigation(
        route= Graph.EVENT_DETAILS,
        startDestination = EventDetailsNavGraph.CreateEvent.route
    ){
        composable(route= EventDetailsNavGraph.CreateEvent.route){
            CreateEventScreen(navController = navController)
        }
    }
}

sealed class EventDetailsNavGraph(val route: String){
    object CreateEvent: EventDetailsNavGraph("CREATE_EVENT")
}

fun NavGraphBuilder.surveyDetailsNavGraph(navController: NavHostController){
    navigation(
        route= Graph.SURVEY_DETAILS,
        startDestination = SurveyDetailsScreen.ScanBarCode.route
    ){
        composable(route= SurveyDetailsScreen.CreateSurvey.route){
            CreateSurveyScreen(navController)
        }
        composable(route= SurveyDetailsScreen.ScanBarCode.route){
            ScanBarCodeScreen(navController = navController)
        }
        composable(route= SurveyDetailsScreen.LaunchSurvey.route){
            LaunchSurveyScreen(navController = navController)
        }
        composable(route= SurveyDetailsScreen.StartSurvey.route){
            StartSurveyScreen(navController = navController)
        }

        /*composable(route= SurveyDetailsScreen.SurveyScreen.route){
            SurveyScreen(navController= navController)
        }*/

    }
}

sealed class SurveyDetailsScreen(val route: String){
    object CreateSurvey: SurveyDetailsScreen("CREATE_SURVEY")
    object ScanBarCode: SurveyDetailsScreen("SCAN_BARCODE")
    object LaunchSurvey: SurveyDetailsScreen("LUNCH_SURVEY")
    object StartSurvey: SurveyDetailsScreen("SURVEY_START")
    //object SurveyScreen: SurveyDetailsScreen("SURVEY_Screen")
}

