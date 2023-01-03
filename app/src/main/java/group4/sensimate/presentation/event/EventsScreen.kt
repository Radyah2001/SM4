package group4.sensimate.presentation.event

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import group4.sensimate.R
import group4.sensimate.data.repository.EventRepository
import group4.sensimate.ui.components.GradientButton
import group4.sensimate.ui.components.GradientEventList
import group4.sensimate.ui.components.GradientText
import group4.sensimate.presentation.navigation.graphs.Graph
import group4.sensimate.ui.theme.SensiMateTheme
import group4.sensimate.UserPreferences
import kotlinx.coroutines.launch

@Composable
fun EventsScreen(navController: NavController, vm:EventsViewModel= viewModel()){
    val events by vm.eventsState.collectAsState()
    val context= LocalContext.current
    val role = UserPreferences(context).getRole.collectAsState(initial = "")
    Column(
        horizontalAlignment= Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.background))
    ){
        Spacer(modifier = Modifier.height(32.dp))

        GradientText(text= "Events", fontSize= 40)

        val scope = rememberCoroutineScope()

        if(role.value.toString() == "Admin"){
            Spacer(modifier = Modifier.padding(10.dp))
            GradientButton(
                onClick = {
                    scope.launch {
                        navController.navigate(Graph.EVENT_DETAILS)
                    }
                },
                text = "Create Event",
                fontSize= 20,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

        }

        GradientEventList(
            eventList = events,
            page="EventPage",
            navController
        )
    }

}

@Preview(showBackground = true)
@Composable
fun EventPreview() {
    SensiMateTheme {
        EventsScreen(navController=  rememberNavController())
        //EventCard(Event(1, R.drawable.ic_launcher_background,"Test", "Test", "11",""))
    }
}
