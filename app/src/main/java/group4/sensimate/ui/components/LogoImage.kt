package group4.sensimate.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import group4.sensimate.R

@Composable
fun SensiMateLogo(size : Int){
    Image(
        alignment = Alignment.Center,
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "SensiMate Logo",
        modifier = Modifier
            .size(size.dp)
            .padding(25.dp)
            .background(Color.Transparent)
            .clip(CircleShape)
    )
}