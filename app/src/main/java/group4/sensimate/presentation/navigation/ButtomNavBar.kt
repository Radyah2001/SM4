package group4.sensimate.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import group4.sensimate.ui.theme.sensiMateColor
import group4.sensimate.UserPreferences

@Composable
fun ButtonNavBar(navController: NavController){
    val context= LocalContext.current
    val role = UserPreferences(context).getRole.collectAsState(initial = "")
    val screens = if(role.value.equals("Guest")){
        listOf(ButtomBarScreen.Events, ButtomBarScreen.Search)
    } else{
        listOf(ButtomBarScreen.Events, ButtomBarScreen.Search, ButtomBarScreen.Profile)
    }
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination

    val bottomBarDestination= screens.any{it.route == currentDestination?.route}
    if(bottomBarDestination) {
        Row(
            modifier = Modifier
                .graphicsLayer {
                    clip = true
                    shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp)
                    shadowElevation = 2.2f
                }
                .height(80.dp)
                .background(brush = sensiMateColor())
        ) {
            screens.forEach { screen ->
                AddScreen(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddScreen(
    screen : ButtomBarScreen,
    currentDestination: NavDestination?,
    navController: NavController
){
    BottomNavigationItem(
        label = { Text(text= screen.title) },
        icon = { Icon(imageVector = screen.icon, contentDescription = "Navigation Icon") },
        selected = currentDestination?.hierarchy?.any{ it.route == screen.route } == true,
        unselectedContentColor= LocalContentColor.current.copy(alpha= ContentAlpha.disabled),
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}