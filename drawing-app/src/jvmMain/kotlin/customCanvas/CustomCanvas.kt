package customCanvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.math.cos
import kotlin.math.sin

data class Point(val x: Float, val y: Float)

data class PathProperties(
    val Angle: Float,
    val length: Float,
    val startPoint: Point,
    val endPoint: Point
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun customCanvas() {
    var currentPosition by remember { mutableStateOf(Offset.Unspecified) }
    var previousPosition by remember { mutableStateOf(Offset.Unspecified) }
    var angle by remember { mutableStateOf(-150f) }

    val paths = remember { mutableStateListOf<Pair<Path, PathProperties>>() }
    var currentPath by remember { mutableStateOf(Path()) }

    val lineLength = 30f

    var cPaths = remember { mutableStateListOf<Rect>() }
    var dotList = remember { mutableStateListOf<Color>() }


    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
            .pointerInput(Unit) {
                forEachGesture {
                    awaitPointerEventScope {
                        awaitFirstDown().also {

                            currentPosition = it.position
                            cPaths.forEachIndexed { index, rect ->
                                if (rect.contains(currentPosition)) {
                                    dotList[index] = Color.Black

                                }
                            }

                            previousPosition = currentPosition
                            currentPath.moveTo(currentPosition.x, currentPosition.y)
                            angle *= -1

                            val startPoint = Point(currentPosition.x, currentPosition.y)
                            val endPoint = getPointByAngle(lineLength, angle, startPoint)
                            currentPath.lineTo(endPoint.x, endPoint.y)
                            paths.add(Pair(currentPath, PathProperties(angle, lineLength, startPoint, endPoint)))

                            cPaths.add(
                                Rect(
                                    left = startPoint.x - 4,
                                    right = startPoint.x + 4,
                                    top = startPoint.y - 4,
                                    bottom = startPoint.y + 4,
                                )
                            )
                            dotList.add(Color.Cyan)
                            cPaths.add(
                                Rect(
                                    left = endPoint.x - 4,
                                    right = endPoint.x + 4,
                                    top = endPoint.y - 4,
                                    bottom = endPoint.y + 4,
                                )
                            )
                            dotList.add(Color.Gray)
                        }
                    }
                }
            }
            .onPointerEvent(PointerEventType.Move) {
                val position = it.changes.first().position
                for ((idx, rect) in cPaths.withIndex()) {
                    if (rect.contains(position)) {
                        dotList[idx] = Color.Black
                        break
                    } else {
                        dotList[idx] = Color.Gray
                    }
                }
            }

    ) {
//        with(drawContext.canvas.nativeCanvas) {

            paths.forEachIndexed() { idx, it: Pair<Path, PathProperties> ->


                drawCircle(
                    color = dotList[idx],
                    radius = 8f,
                    center = Offset(it.second.startPoint.x, it.second.startPoint.y),
                )
                drawCircle(
                    color = dotList[idx],
                    radius = 8f,
                    center = Offset(it.second.endPoint.x, it.second.endPoint.y),
                )
                drawPath(
                    color = Color.Black,
                    path = it.first,
                    style = Stroke(
                        width = 3f,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round,
                    )
                )

            }
//        }
    }
}

//calculate the end point x and y coordinate by cos() and sin()
fun getPointByAngle(length: Float, angle: Float, startPoint: Point): Point {
    return Point(startPoint.x + length * cos(angle), startPoint.y - length * sin(angle))
}


