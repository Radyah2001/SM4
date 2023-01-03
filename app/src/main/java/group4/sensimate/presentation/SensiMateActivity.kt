package group4.sensimate.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import group4.sensimate.ui.theme.SensiMateTheme
import group4.sensimate.presentation.navigation.graphs.RootNavigationGraph

class SensiMateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SensiMateTheme {
                RootNavigationGraph(navController= rememberNavController())
            }
        }
    }
}














