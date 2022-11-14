package customClick

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerInput

data class Point(val x: Float, val y: Float, var color: Color = Color.Black)
data class PathProperties(
    val startPoint: Point,
    val endPoint: Point,
)

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun clickCanvas() {
    val pointList = remember { mutableStateListOf<Point>() }
    val pathList = remember { mutableStateListOf<Path>() }
    val rectList = remember { mutableStateListOf<Rect>() }
    val colorList = remember { mutableStateListOf<Color>() }
    val path by remember { mutableStateOf(Path()) }
    var dragging by remember { mutableStateOf(false) }
    var mousePosition by remember { mutableStateOf(Offset.Unspecified) }
    var mousePath by remember { mutableStateOf(Path()) }


    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
            .onPointerEvent(PointerEventType.Press) {pressPointerEvent: PointerEvent ->
                val currPosition = pressPointerEvent.changes.first().position

                if (pointList.isNotEmpty()) {
                    val prevPosition = pointList.last()
                    path.moveTo(prevPosition.x, prevPosition.y)
                    path.lineTo(currPosition.x, currPosition.y)
                    pathList.add(path)
                }
                pointList.add(Point(currPosition.x, currPosition.y, Color.Black))
                rectList.add(Rect(left = currPosition.x - 6f, right = currPosition.x + 6f, top = currPosition.y - 6f, bottom = currPosition.y + 6f))
                colorList.add(Color.Black)

            }
            .onPointerEvent(PointerEventType.Move) {movePointerEvent: PointerEvent ->
                val position = movePointerEvent.changes.first().position
                for (idx in rectList.indices.reversed()) {
                    if (rectList[idx].contains(position)) {
                        pointList[idx] = pointList[idx].copy(color = Color.Red)
                        return@onPointerEvent
                    } else {
                        pointList[idx] = pointList[idx].copy(color = Color.Black)
                    }
                }
            }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    dragging = true
//                    println("drag change: ${change.position}")
                    mousePosition = change.position
                    if (pointList.isNotEmpty()) {
                        val lastPoint = pointList.last()
                        mousePath.moveTo(lastPoint.x, lastPoint.y)
                    }
                }
            }
            .combinedClickable(
                onClick = {

                }
            )
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
        pointList.forEachIndexed() { idx, point: Point ->
            drawCircle(
                color = point.color,
                radius = 6f,
                center = Offset(point.x, point.y),
            )
        }

        if (dragging) {
            drawCircle(
                color = Color.Green,
                radius = 6f,
                center = mousePosition
            )
            drawPath(
                path = mousePath,
                color = Color.Black,
                style = Stroke(
                    width = 3f,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round,
                )
            )

        }



    }

}