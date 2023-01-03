package group4.sensimate.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.siddroid.holi.brushes.GradientMixer
import group4.sensimate.R

@Composable
fun GradientText(text: String, fontSize:Int){
    val gradient = GradientMixer.bottomLeftToTopRight(colorResource(R.color.light_carmine_pink), colorResource(R.color.violets_blue))

     Text(
        text = text,
        fontSize = fontSize.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer(alpha = 0.99f)
            .drawWithCache {
                onDrawWithContent {
                    drawContent()
                    drawRect(brush= gradient, blendMode = BlendMode.SrcAtop)
                }
            }
    )
}