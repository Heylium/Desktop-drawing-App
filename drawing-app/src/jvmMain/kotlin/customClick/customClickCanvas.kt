package customClick

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerInput

data class Point(val x: Float, val y: Float)
data class PathProperties(
    val startPoint: Point,
    val endPoint: Point,
)

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun clickCanvas() {
    var pointList = remember { mutableStateListOf<Point>() }


    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
            .onPointerEvent(PointerEventType.Move) {
                val position = it.changes.first().position
//                color = Color(position.x.toInt() % 256, position.y.toInt() %256, 0)
            }
            .onPointerEvent(PointerEventType.Press) {
                val presPosit = it.changes.first().position
                println("pressed: $presPosit")
            }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    println("drag change: ${change.position}")
                }
            }
    ) {



    }



}