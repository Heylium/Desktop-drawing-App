package customCanvas

import androidx.compose.foundation.Canvas
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
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun customCanvas(){
    var currentPosition by remember { mutableStateOf(Offset.Unspecified) }
    var previousPosition by remember { mutableStateOf(Offset.Unspecified) }
    var currentPath by remember { mutableStateOf(Path()) }
    val paths = remember { mutableStateListOf<Path>() }
    val randomAngle = listOf<Float>(45f, -45f)

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
                            val angle = randomAngle.random()
                            //get the end point of the path
                            val toPoint = getPointByAngle(40f, angle, Pair(currentPosition.x, currentPosition.y))
                            currentPath.lineTo(toPoint.first, toPoint.second)

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

//calculate the end point x and y coordinate by cos() and sin()
fun getPointByAngle(length: Float, angle: Float, startPoint: Pair<Float, Float>): Pair<Float, Float> {
    return Pair(startPoint.first + length * cos(angle), startPoint.second + length * sin(angle))
}