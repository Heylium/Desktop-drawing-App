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
import kotlin.math.cos
import kotlin.math.sin

data class PathProperties(val Angle: Float, val length: Float, val startPoint: Pair<Float, Float>)

@Composable
fun customCanvas(){
    var currentPosition by remember { mutableStateOf(Offset.Unspecified) }
    var previousPosition by remember { mutableStateOf(Offset.Unspecified) }
//    var currentPath by remember { mutableStateOf(Path()) }
//    val paths = remember { mutableStateListOf<Path>() }
    val randomAngle = listOf(45f, -45f)

    var paths = remember { mutableStateListOf<Pair<Path, PathProperties>>() }
    var currentPath by remember { mutableStateOf(Path()) }


    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
            .pointerInput(Unit) {
                forEachGesture {
                    awaitPointerEventScope {
                        awaitFirstDown().also {
//                            currentPosition = it.position
//                            println("position: ${it.position}")
//                            previousPosition = currentPosition
//                            currentPath.moveTo(currentPosition.x, currentPosition.y)
//                            val angle = randomAngle.random()
//                            //get the end point of the path
//                            val toPoint = getPointByAngle(40f, angle, Pair(currentPosition.x, currentPosition.y))
//                            currentPath.lineTo(toPoint.first, toPoint.second)
//                            paths.add(currentPath)

                            currentPosition = it.position
                            previousPosition = currentPosition
                            currentPath.moveTo(currentPosition.x, currentPosition.y)
                            val angle = randomAngle.random()

                            paths.add(Pair(currentPath, PathProperties(angle, 30f, Pair(currentPosition.x, currentPosition.y))))

                        }
                    }
                }
            }
    ){
        with(drawContext.canvas.nativeCanvas) {
            val checkPoint = saveLayer(null, null)
//            paths.forEach { it: Path ->
//                drawPath(
//                    color = Color.Black,
//                    path = it,
//                    style = Stroke(
//                        width = 4f,
//                        cap = StrokeCap.Round,
//                        join = StrokeJoin.Round,
//                    )
//                )
//            }

            paths.forEach { it: Pair<Path, PathProperties> ->
                rotate(it.second.Angle, it.second.startPoint.first, it.second.startPoint.second )
                drawLine(
                    color = Color.Black,
                    start = Offset(it.second.startPoint.first, it.second.startPoint.second ),
                    end = Offset(it.second.startPoint.first + it.second.length, it.second.startPoint.second),
                    cap = StrokeCap.Round
                )
                rotate(-it.second.Angle, it.second.startPoint.first, it.second.startPoint.second)

            }
        }
    }
}

//calculate the end point x and y coordinate by cos() and sin()
fun getPointByAngle(length: Float, angle: Float, startPoint: Pair<Float, Float>): Pair<Float, Float> {
    return Pair(startPoint.first + length * cos(angle), startPoint.second + length * sin(angle))
}