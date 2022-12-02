package customClick

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.*
import org.jetbrains.skia.PathMeasure as sPathMeasure

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

    var pointerPath by remember { mutableStateOf(Path()) }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
                //mouse tap(click) event
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { pressPointer: Offset ->
                        if (pointList.isNotEmpty()) {
                            val prevPosition = pointList.last()
                            path.moveTo(prevPosition.x, prevPosition.y)
                            path.lineTo(pressPointer.x, pressPointer.y)
                            pathList.add(path)

                            rectList.forEachIndexed { index, rect ->
                                if (rect.contains(pressPointer)) {
                                    return@detectTapGestures
                                }
                            }
                        }
                        pointList.add(Point(pressPointer.x, pressPointer.y, Color.Black))
                        rectList.add(
                            Rect(
                                left = pressPointer.x - 6f,
                                right = pressPointer.x + 6f,
                                top = pressPointer.y - 6f,
                                bottom = pressPointer.y + 6f
                            )
                        )
                        colorList.add(Color.Black)

                    }
                )
            }
                //mouse drag event
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        dragging = true
                    },
                    onDrag = { change: PointerInputChange, _: Offset ->
                        mousePosition = change.position
                    },
                    onDragEnd = {
                        dragging = false
                        mousePosition = Offset.Unspecified
                    }
                )
            }
                //mouse move event
            .onPointerEvent(PointerEventType.Move) { movePointerEvent: PointerEvent ->
                val position = movePointerEvent.changes.first().position
//                for (idx in rectList.indices.reversed()) {
//                    if (rectList[idx].contains(position)) {
//                        pointList[idx] = pointList[idx].copy(color = Color.Red)
//                        return@onPointerEvent
//                    } else {
//                        pointList[idx] = pointList[idx].copy(color = Color.Black)
//                    }
//                }

//                val rectF = Rect(
//                    position.x - 1, position.y - 1 , position.x + 1, position.y + 1
//                )
//                pointerPath.moveTo(position.x, position.y)
//                pointerPath.addRect(rectF)
//                for (idx in pathList.indices.reversed()) {
//                    if (Path().op(pathList[idx], pointerPath, PathOperation.Difference)) {
//                        pointList[idx] = pointList[idx].copy(color = Color.Red)
//                        return@onPointerEvent
//                    } else {
//                        pointList[idx] = pointList[idx].copy(color = Color.Black)
//                    }
//                }
                for (idx in pathList.indices.reversed()) {
                    if (pathList[idx].doIntersect(position.x, position.y, 3f)) {
//                        pointList[idx] = pointList[idx].copy(color = Color.Red)
                        colorList[idx] = Color.Red
                        return@onPointerEvent
                    } else {
//                        pointList[idx] = pointList[idx].copy(color = Color.Black)
                        colorList[idx] = Color.Black
                    }
                }
            }

    ) {

        pathList.forEachIndexed() { idx, path: Path ->
            drawPath(
                path = path,
                color = colorList[idx],
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



fun Path.doIntersect(x: Float, y: Float, width: Float): Boolean {
    val measure = sPathMeasure(this.asSkiaPath())
    val length = measure.length
    val delta = width / 2f
    val position = floatArrayOf(0f, 0f)
//    val bounds = RectF()
    var distance = 0f
    var intersects = false
    while (distance <= length) {
        val point = measure.getPosition(distance)!!

//        bounds.set(
//            position[0] - delta,
//            position[1] - delta,
//            position[0] + delta,
//            position[1] + delta
//        )
        val bounds = Rect(
            point.x - delta,
            point.y - delta,
            point.x + delta,
            point.y + delta
        )
        if (bounds.contains(Offset(x, y))) {
            println("point as: $point")
            intersects = true
            break
        }
        distance += delta / 2f
    }
    return intersects
}