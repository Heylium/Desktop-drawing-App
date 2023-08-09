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
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.*
import java.util.UUID
import kotlin.math.abs
import kotlin.math.hypot
import org.jetbrains.skia.PathMeasure as sPathMeasure

data class Point(val x: Float, val y: Float, var color: Color = Color.Black)

data class Line(val startPoint: Point, val endPoint: Point)
data class PathProperties(
    val startPoint: Point,
    val endPoint: Point,
)

/**
 * calculate the distance between two points
 */
fun Point.calcDist(secondPoint: Point): Float {
    val firstPoint = this
    val xDiff = abs(secondPoint.x - firstPoint.x)
    val yDiff = abs(secondPoint.y - firstPoint.y)
    return hypot(xDiff, yDiff)
}

/**
 * check if mouse pointer position on a point
 */
fun MutableMap<UUID, Point>.checkMouseOnPoint(mousePoint: Point, colorMap: MutableMap<UUID, Color>) {
    for ((idx, point) in this) {
        if (colorMap[idx] != null) {
            if (point.calcDist(mousePoint) < 6f) {
                //println("mouse moved: ${point.calcDist(mousePoint)} ")
                colorMap[idx] = Color.Green
            }
            else {
                colorMap[idx] = Color.Black
            }
        }
    }
}

/**
 * canvas which is monitoring mouse move and draw lines
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun clickCanvas() {
    val blackColor = Color.Black
    val grayColor = Color.Gray
    val redColor = Color.Red
    val pathList = remember { mutableStateListOf<Path>() }
    val colorList = remember { mutableStateListOf<Color>() }
    var dragging by remember { mutableStateOf(false) }
    var mousePosition by remember { mutableStateOf(Offset.Unspecified) }
    val mousePath by remember { mutableStateOf(Path()) }
    val pointsMap = remember { mutableStateMapOf<UUID, Point>() }
    val colorMap = remember { mutableStateMapOf<UUID, Color>() }
    val lineMap = remember { mutableStateMapOf<UUID, Line>() }
    var prevPoint:Point? = null

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
                //mouse tap(click) event
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { pressPointer: Offset ->
                        val pressPoint = Point(pressPointer.x, pressPointer.y)
                        val pointId = UUID.randomUUID()
                        if (pointsMap.size >= 1) {
                            if (prevPoint != null) {
                                lineMap[UUID.randomUUID()] = Line(prevPoint!!, pressPoint)
                                for ((idx, point) in pointsMap) {
                                    if (point.calcDist(pressPoint) < 3f) {


                                    }
                                }
                            }
                        }
                        prevPoint = pressPoint
                        pointsMap[pointId] = pressPoint
                        colorMap[pointId] = Color.Black
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
                val movePoint = Point(position.x, position.y)
                for (idx in pathList.indices.reversed()) {
                    if (pathList[idx].doIntersect(position.x, position.y, 3f)) {
                        colorList[idx] = redColor
                        return@onPointerEvent
                    } else {
                        colorList[idx] = grayColor
                    }
                }
                pointsMap.checkMouseOnPoint(movePoint, colorMap)
            }

    ) {
        val _startP = Offset(20f, 20f)
        val _endP = Offset(40f, 40f)
        drawLine(
            color = Color.Black,
            start = _startP,
            end = _endP,
            strokeWidth = 6f
        )

        drawLine(
            color = Color.Gray,
            start = _startP,
            end = _endP,
            strokeWidth = 2f
        )




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

        for ((idx, point) in pointsMap) {
            drawCircle(
                color = colorMap[idx]!!,
                radius = 6f,
                center = Offset(point.x, point.y)
            )
        }

        for ((id, line) in lineMap) {
            drawLine(
                color = Color.Black,
                start = Offset(line.startPoint.x, line.startPoint.y),
                end = Offset(line.endPoint.x, line.endPoint.y),
                strokeWidth = 2f,
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
    var distance = 3f
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