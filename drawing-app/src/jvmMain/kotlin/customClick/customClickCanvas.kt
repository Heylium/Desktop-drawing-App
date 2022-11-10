package customClick

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerEvent
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
    val pointList = remember { mutableStateListOf<Point>() }
    val pathList = remember { mutableStateListOf<Path>() }
    val path by remember { mutableStateOf(Path()) }



    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
            .onPointerEvent(PointerEventType.Move) {movePointerEvent: PointerEvent ->
                val position = movePointerEvent.changes.first().position
//                color = Color(position.x.toInt() % 256, position.y.toInt() %256, 0)
            }
            .onPointerEvent(PointerEventType.Press) {pressPointerEvent: PointerEvent ->
                val currPosition = pressPointerEvent.changes.first().position
//                println("pressed as :$currPosition")



                if (pointList.isEmpty()) {
                    pointList.add(Point(currPosition.x, currPosition.y))
                } else {
                    val prevPosition = pointList.last()
                    pointList.add(Point(currPosition.x, currPosition.y))
                    path.moveTo(prevPosition.x, prevPosition.y)
                    path.lineTo(currPosition.x, currPosition.y)
                    pathList.add(path)
                }

            }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    println("drag change: ${change.position}")
                }
            }
    ) {

        pathList.forEach { path: Path ->
            drawPath(
                path = path,
                color = Color.Black,
                style = Stroke(
                    width = 3f,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round,
                )
            )
        }
        pointList.forEach { point: Point ->
            drawCircle(
                color = Color.Black,
                radius = 6f,
                center = Offset(point.x, point.y)
            )
        }



    }

}