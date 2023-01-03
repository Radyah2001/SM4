package group4.sensimate.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ButtomBarScreen(
    val title: String,
    val route: String,
    val icon: ImageVector
){
    object Events : ButtomBarScreen(
        "Events",
        "events",
        Icons.Default.DateRange
    )

    object Search : ButtomBarScreen(
        "Search",
        "search",
        Icons.Default.Search
    )

    object Profile : ButtomBarScreen(
        "Profile",
        "profile",
        Icons.Default.Person
    )
}