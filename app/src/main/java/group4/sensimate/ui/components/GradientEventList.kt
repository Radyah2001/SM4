package group4.sensimate.ui.components

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import group4.sensimate.R
import group4.sensimate.data.model.Event
import group4.sensimate.presentation.navigation.graphs.SurveyDetailsScreen
import group4.sensimate.ui.theme.sensiMateColor
import group4.sensimate.ui.theme.sensiMateHorizontalColor
import group4.sensimate.ui.theme.sensiMateVerticalColor
import group4.sensimate.UserPreferences
import group4.sensimate.presentation.survey.SurveyActivity

@Composable
fun GradientEventList(eventList: List<Event>, page: String, navController: NavController) {

    LazyColumn(
        contentPadding = PaddingValues(10.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.background))
    ) {
        items(eventList){event ->
            Box(
                contentAlignment = Alignment.TopStart,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Transparent)

            )
            {
                val context= LocalContext.current
                val role = UserPreferences(context).getRole.collectAsState(initial = "")

                if (page == "Profile" && role.value !="Admin") {
                    Text(text = "Attended Event",color=Color.White, modifier = Modifier.padding(start= 8.dp))
                }

                EventCard(event = event, page= page, navController)
            }
        }
    }

}

@Composable
fun EventCard(event: Event, page: String, navController: NavController){
    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = 5.dp,
        border= BorderStroke( 2.dp, brush = sensiMateHorizontalColor() ),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 25.dp, bottom = 10.dp, start = 10.dp, end = 10.dp)
            .shadow(
                spotColor = colorResource(id = R.color.yellow),
                elevation = 8.dp,
                shape = RoundedCornerShape(21.dp)
            )
    )
    {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(brush = sensiMateHorizontalColor())
        )
        {
            Image(
                painter = painterResource(event.imageId!!),
                contentDescription = null,//stringResource(event.id),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(130.dp)
                    .padding(8.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
            )

            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.Transparent)
            )
            {
                Text(text = event.title, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold, fontSize = 19.sp)
                Text(text = event.description,fontFamily = FontFamily.Serif)
                Text(text = event.startDate,fontFamily = FontFamily.Serif)
                Text(text = event.endDate,fontFamily = FontFamily.Serif)


                if(page =="Profile") {
                    val context = LocalContext.current
                    OutlinedButton(
                        onClick = { /* should navigate to answer screen to see the result. but it is not implemented yet*/  },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                        border = BorderStroke(width = 2.dp, color = Color.Black)
                    ) {
                        Text("See Answer", fontSize = 20.sp, color = Color.Black)
                    }
                }else{
                    OutlinedButton(
                        onClick = {
                            navController.navigate(SurveyDetailsScreen.ScanBarCode.route)
                            //navController.navigate(SurveyDetailsScreen.LaunchSurvey.route)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                        border = BorderStroke(width = 2.dp, color = Color.Black)
                    ) {
                        Text("Evaluate", fontSize = 20.sp, color = Color.Black)
                    }
                }

            }
        }
    }

}