package customCanvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput


@Composable
fun CustomCanvas(){
    @OptIn(ExperimentalFoundationApi::class)

    var motionEvent by remember { mutableStateOf(false) }
    var currentPosition by remember { mutableStateOf(Offset.Unspecified) }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
            .pointerInput(Unit) {
                forEachGesture {
                    awaitPointerEventScope {
                        awaitFirstDown().also {
                            motionEvent = true
                            currentPosition = it.position
                        }
                    }
                }
            }
    ){}

}
