package group4.sensimate.presentation

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import group4.sensimate.presentation.navigation.ButtonNavBar
import group4.sensimate.presentation.navigation.graphs.HomeNavGraph
import group4.sensimate.ui.theme.SensiMateTheme


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavHostController = rememberNavController())
{
    Scaffold(
        bottomBar = {  ButtonNavBar(navController)  }
    ) {
        HomeNavGraph(navController)
    }
}

@Preview(showBackground = true)
@Composable
fun SenSiMateAppPreview() {
    SensiMateTheme {
        HomeScreen()
    }
}