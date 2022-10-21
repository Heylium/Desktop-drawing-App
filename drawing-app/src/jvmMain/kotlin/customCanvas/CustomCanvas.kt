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
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.graphics.Path



@Composable
fun CustomCanvas(){

    var motionEvent by remember { mutableStateOf(false) }
    var currentPosition by remember { mutableStateOf(Offset.Unspecified) }
    var previousPosition by remember { mutableStateOf(Offset.Unspecified) }
    var currentPath by remember { mutableStateOf(Path()) }
    val paths = remember { mutableStateListOf<Path>() }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
            .pointerInput(Unit) {
                forEachGesture {
                    awaitPointerEventScope {
                        awaitFirstDown().also {
                            currentPosition = it.position
                            println("position: ${it.position}")
                            previousPosition = currentPosition
                            currentPath.moveTo(currentPosition.x, currentPosition.y)
                            currentPath.lineTo(previousPosition.x + 20, previousPosition.y + 20)

                            paths.add(currentPath)

                        }
                    }
                }
            }
    ){
        with(drawContext.canvas.nativeCanvas) {
            val checkPoint = saveLayer(null, null)
            paths.forEach { it: Path ->
                drawPath(
                    color = Color.Black,
                    path = it,
                    style = Stroke(
                        width = 4f,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round,
                    )
                )
            }
        }
    }
}

fun getPointByAngle(length: Float, angle: Float, srartPoint: Pair<Float, Float>) {
    return Pair<startPoint.first + length * cos(angle), startPoint.second + length * sin(angle)>
}