package customCanvas.moves

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerInputChange
import customCanvas.moves.awaitClickEvent

@Composable
fun CustomCanvas(){
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
            .clickMotionEvent(
                onClick = { pointerInputChange: PointerInputChange ->

                }
            )
    ){}

}
