package group4.sensimate.presentation.survey

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.siddroid.holi.brushes.GradientMixer
import group4.sensimate.R
import group4.sensimate.presentation.navigation.graphs.SurveyDetailsScreen
import group4.sensimate.ui.components.SensiMateLogo
import group4.sensimate.ui.theme.SensiMateTheme

@Preview(showBackground = true)
@Composable
fun StartSurveyPreview() {
    SensiMateTheme {
        StartSurveyScreen(navController = rememberNavController())
    }
}

@Composable
fun StartSurveyScreen(navController: NavController){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.pink_50))
        ) {
            val brush = GradientMixer.topToBottom(
                colorResource(R.color.pink_300),
                colorResource(R.color.pink_100)
            )
            Box(
                modifier = Modifier
                    .size(375.dp)
                    .clip(RoundedCornerShape(50))
                    .align(alignment = Alignment.TopCenter)
                    .background(brush = brush)
            )

            Box(
                modifier = Modifier
                    .size(375.dp)
                    .clip(CircleShape)
                    .align(alignment = Alignment.Center)
                    .background(colorResource(R.color.pink_200)),
            )

            Box(
                modifier = Modifier
                    .size(375.dp)
                    .clip(CircleShape)
                    .align(alignment = Alignment.BottomCenter)
                    .background(brush = brush)
                    .fillMaxWidth()
            )

            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp)
            ) {
                item {
                    SensiMateLogo(size = 300)

                    Spacer(modifier = Modifier.padding(10.dp))
                    Image(
                        painter = painterResource(id = R.drawable.product_logo),
                        contentDescription = "product Image",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(shape = RoundedCornerShape(20.dp))
                            .padding(25.dp)
                            .background(brush = brush)
                    )

                    var context = LocalContext.current
                    OutlinedButton(
                        onClick = {
                            //navController.navigate(SurveyDetailsScreen.SurveyScreen.route)
                            context.startActivity(Intent(context, SurveyActivity::class.java))
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(R.color.light_green)
                        ),
                        modifier = Modifier.padding(top = 5.dp)
                    ) {
                        Text(
                            text = "Start",
                            fontSize = 30.sp,
                            color = Color.Black
                        )
                    }

                    Text(
                        text = "takes 5 minutes",
                        fontSize = 15.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(5.dp)

                    )

                    Text(
                        text = "Tell us about your experience",
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(15.dp)
                    )
                }
            }


        }

}

