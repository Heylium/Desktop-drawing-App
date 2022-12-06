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
import kotlin.math.abs
import kotlin.math.hypot
import kotlin.math.sqrt
import org.jetbrains.skia.PathMeasure as sPathMeasure

data class Point(val x: Float, val y: Float, var color: Color = Color.Black)
data class PathProperties(
    val startPoint: Point,
    val endPoint: Point,
)

/**
 * calculate the distance between two points
 */
fun Point.calcDistance(secondPoint: Point): Float {
    val firstPoint = this
    val xDiff = abs(secondPoint.x - firstPoint.x)
    val yDiff = abs(secondPoint.y - firstPoint.y)
    return sqrt(hypot(xDiff, yDiff))
}


@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun clickCanvas() {
    val blackColor = Color.Black
    val grayColor = Color.Gray
    val redColor = Color.Red
    val pointList = remember { mutableStateListOf<Point>() }
    val pathList = remember { mutableStateListOf<Path>() }
    val rectList = remember { mutableStateListOf<Rect>() }
    val colorList = remember { mutableStateListOf<Color>() }
    var dragging by remember { mutableStateOf(false) }
    var mousePosition by remember { mutableStateOf(Offset.Unspecified) }
    val mousePath by remember { mutableStateOf(Path()) }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
                //mouse tap(click) event
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { pressPointer: Offset ->
                        var pressPoint = Point(pressPointer.x, pressPointer.y)
                        if (pointList.isNotEmpty()) {
                            //the last point position
                            val prevPosition = pointList.last()

                            //Build a new path object
                            val subPath = Path()
                            subPath.moveTo(prevPosition.x, prevPosition.y)
                            subPath.lineTo(pressPoint.x, pressPoint.y)
                            pathList.add(subPath)
                            pointList.forEachIndexed { idx, point ->
                                if (pressPoint.calcDistance(point) < 3f) {
                                    println("distance: ${pressPoint.calcDistance(prevPosition)}")
                                    pressPoint = point
                                }
                            }

//                            rectList.forEachIndexed { _, rect ->
//                                if (rect.contains(pressPointer)) {
//                                    return@detectTapGestures
//                                }
//                            }
                        }
                        colorList.add(grayColor)
                        //pointList.add(Point(pressPointer.x, pressPointer.y, Color.Black))
                        pointList.add(pressPoint)
                        rectList.add(
                            Rect(
                                left = pressPointer.x - 6f,
                                right = pressPointer.x + 6f,
                                top = pressPointer.y - 6f,
                                bottom = pressPointer.y + 6f
                            )
                        )


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
                for (idx in pathList.indices.reversed()) {
                    if (pathList[idx].doIntersect(position.x, position.y, 6f)) {
                        colorList[idx] = redColor
                        return@onPointerEvent
                    } else {
                        colorList[idx] = grayColor
                    }
                }
            }

    ) {

        pathList.forEachIndexed() { idx, path: Path ->
            drawPath(
                path = path,
                color = colorList[idx],
                style = Stroke(
                    width = 6f,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round,

                )
            )
            drawPath(
                path = path,
                color = blackColor,
                style = Stroke(
                    width = 2f,
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


/**
 * check whether a point is on a Compose Path(), the precision is limited by param width
 */
fun Path.doIntersect(x: Float, y: Float, width: Float): Boolean {
    val measure = sPathMeasure(this.asSkiaPath())
    val length = measure.length
    val delta = width / 2f
    var distance = 0f
    var intersects = false
    while (distance <= length) {
        val point = measure.getPosition(distance)!!
        val bounds = Rect(
            point.x - delta,
            point.y - delta,
            point.x + delta,
            point.y + delta
        )
        if (bounds.contains(Offset(x, y))) {
//            println("rect at: $bounds")
            intersects = true
            break
        }
        distance += delta / 2f
    }
    return intersects
}