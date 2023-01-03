package group4.sensimate.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import group4.sensimate.R

@Composable
fun ProfileImage(){
    val imageUri= rememberSaveable{ mutableStateOf("") }
    val painter = rememberImagePainter(
        imageUri.value.ifEmpty {
            R.drawable.ic_user
        }
    )

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ){
            uri: Uri?->
        uri?.let { imageUri.value = it.toString() }
    }

    Card(
        shape= CircleShape,
        modifier = Modifier
            .padding(8.dp)
            .size(150.dp)
    ){
        Image(
            painter = painter,
            contentDescription = "profile Image",
            contentScale= ContentScale.Crop,
            modifier = Modifier
                .wrapContentSize()
                .clickable { launcher.launch("image/* ") }
            //.size(140.dp)
            //.clip(shape = CircleShape)
        )
    }
}