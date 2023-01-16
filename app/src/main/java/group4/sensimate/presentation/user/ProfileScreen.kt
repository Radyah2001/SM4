package group4.sensimate.presentation.user

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import group4.sensimate.data.repository.EventRepository
import group4.sensimate.ui.theme.SensiMateTheme
import group4.sensimate.R
import group4.sensimate.ui.components.GradientEventList
import group4.sensimate.ui.components.ProfileImage
import group4.sensimate.ui.components.RowTexField
import group4.sensimate.presentation.navigation.graphs.Graph
import group4.sensimate.ui.theme.sensiMateColor
import group4.sensimate.UserPreferences

@Composable
fun ProfileScreen(navController: NavController, vm: UserViewModel = viewModel()){
    val context= LocalContext.current
    val role = UserPreferences(context).getRole.collectAsState(initial = "")
    vm.getUserInfo(role.value!!)
    val user by vm.userState.collectAsState()
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(R.color.background)),
    ){

        // User's image
        ProfileImage()

        OutlinedButton(
            shape= CircleShape,
            border = BorderStroke( 2.dp,   brush= sensiMateColor()),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            onClick = { navController.navigate(Graph.PROFILE_DETAILS) }
        ) {
            Text(
                text="Edit Profile",
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.padding(start=30.dp, end=30.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(
                    brush = sensiMateColor()
                )
                .border
                    (
                    border = BorderStroke(2.dp, color = Color.White),
                    shape = RoundedCornerShape(15.dp)
                )
            )
        {

            RowTexField("Birthday:", user.birthday)

            RowTexField("Gender:", user.gender)

            RowTexField("Postal Code:", user.postalcode)

            RowTexField("Email:", user.email)


        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue)
        ) {
            GradientEventList(
                eventList = EventRepository().getEvents(),
                page="Profile",
                navController = navController
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    SensiMateTheme {
        ProfileScreen(navController = rememberNavController())
    }
}