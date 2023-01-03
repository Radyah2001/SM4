package group4.sensimate.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.siddroid.holi.brushes.GradientMixer
import group4.sensimate.R



val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

@Composable
fun sensiMateColor(): Brush {
    return GradientMixer.leftToRight(
        colorResource(R.color.light_carmine_pink),
        colorResource(R.color.violets_blue)
    )
}


@Composable
fun sensiMateVerticalColor(): Brush{
    return Brush.verticalGradient(
        colors = listOf(
            colorResource(R.color.light_carmine_pink),
            colorResource(R.color.violets_blue)
        )
    )
}

@Composable
fun sensiMateHorizontalColor(): Brush{
    return Brush.horizontalGradient(
        colors= listOf(
        colorResource(R.color.light_carmine_pink),
        colorResource(R.color.violets_blue))
    )
}