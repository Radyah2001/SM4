package group4.sensimate.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RowTexField(text1: String, text2: String){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = text1,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        )
        Text(
            text = text2,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        )
    }

}