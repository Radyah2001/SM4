package group4.sensimate.presentation.survey

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import group4.sensimate.ui.theme.SensiMateTheme

@Composable
fun CreateSurveyScreen(navController: NavController){
    Text(text = "Hello ")
}

@Preview(showBackground = true)
@Composable
fun CreateSurveyScreenPreview() {
    SensiMateTheme {
        CreateSurveyScreen(navController = rememberNavController())
    }
}
