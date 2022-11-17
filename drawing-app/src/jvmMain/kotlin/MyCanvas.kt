
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.consumeDownChange
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.positionChange
import gesture.MotionEvent
import gesture.dragMotionEvent
import model.PathProperties


@Composable
fun MyCanvas() {
    val paths = remember { mutableStateListOf<Pair<Path, PathProperties>>() }
    var currentPathProperty by remember { mutableStateOf(PathProperties()) }
    val pathsUndone = remember { mutableStateListOf<Pair<Path, PathProperties>>() }
    var motionEvent by remember { mutableStateOf(MotionEvent.Idle) }
    var drawMode by remember { mutableStateOf(DrawMode.Draw) }

    var currentPosition by remember { mutableStateOf(Offset.Unspecified) }
    var currentPath by remember { mutableStateOf(Path()) }
    var previousPosition by remember { mutableStateOf(Offset.Unspecified) }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .dragMotionEvent(
                onDragStart = {
                    pointerInputChange -> motionEvent = MotionEvent.Down
                    currentPosition = pointerInputChange.position
                    pointerInputChange.consumeDownChange()
                },
                onDrag = {
                    pointerInputChange: PointerInputChange -> motionEvent = MotionEvent.Move
                    currentPosition = pointerInputChange.position
                    if (drawMode == DrawMode.Touch) {
                        val change = pointerInputChange.positionChange()
                        paths.forEach { entry ->
                            val path:Path = entry.first
                            path.translate(change)
                        }
                        currentPath.translate(change)
                    }
                    pointerInputChange.consumePositionChange()
                },
                onDragEnd = {pointerInputChange: PointerInputChange ->
                    motionEvent = MotionEvent.Up
                    pointerInputChange.consumeDownChange()
                },
            )
    ) {
        when(motionEvent) {
            MotionEvent.Down -> {
                if (drawMode != DrawMode.Touch) {
                    currentPath.moveTo(currentPosition.x, currentPosition.y)
                }
                previousPosition = currentPosition
            }
            MotionEvent.Move -> {
                if (drawMode != DrawMode.Touch) {
                    currentPath.quadraticBezierTo(
                        previousPosition.x,
                        previousPosition.y,
                        (previousPosition.x + currentPosition.x) / 2,
                        (previousPosition.y + previousPosition.y) / 2
                    )
                }
                currentPath.lineTo(previousPosition.x, previousPosition.y)
                previousPosition = currentPosition
            }
            MotionEvent.Up -> {
                if (drawMode != DrawMode.Touch) {
                    currentPath.lineTo(currentPosition.x, currentPosition.y)
                    paths.add(Pair(currentPath, currentPathProperty))
                    currentPath = Path()
                    currentPathProperty = PathProperties(
                        strokeWidth = currentPathProperty.strokeWidth,
                        color = currentPathProperty.color,
                        strokeCap = currentPathProperty.strokeCap,
                        strokeJoin = currentPathProperty.strokeJoin,
                        eraseMode = currentPathProperty.eraseMode
                    )
                }
                pathsUndone.clear()
                currentPosition = Offset.Unspecified
                previousPosition = currentPosition
                motionEvent = MotionEvent.Idle
            }
            else -> Unit
        }

        with(drawContext.canvas.nativeCanvas) {
            val checkPoint = saveLayer(null, null)
            paths.forEach {
                val path = it.first
                val properties = it.second

                if (!properties.eraseMode) {
                    drawPath(
                        color = properties.color,
                        path = path,
                        style = Stroke(
                            width = currentPathProperty.strokeWidth,
                            cap = currentPathProperty.strokeCap,
                            join = currentPathProperty.strokeJoin
                        )
                    )
                } else {
                    drawPath(
                        color = Color.Transparent,
                        path = path,
                        style = Stroke(
                            width = currentPathProperty.strokeWidth,
                            cap = currentPathProperty.strokeCap,
                            join = currentPathProperty.strokeJoin
                        ),
                        blendMode = BlendMode.Clear
                    )
                }

                if (motionEvent != MotionEvent.Idle) {
                    if (!currentPathProperty.eraseMode) {
                        drawPath(
                            color = currentPathProperty.color,
                            path = currentPath,
                            style = Stroke(
                                width = currentPathProperty.strokeWidth,
                                cap = currentPathProperty.strokeCap,
                                join = currentPathProperty.strokeJoin
                            )
                        )
                    } else {
                        drawPath(
                            color = currentPathProperty.color,
                            path = currentPath,
                            style = Stroke(
                                width = currentPathProperty.strokeWidth,
                                cap = currentPathProperty.strokeCap,
                                join = currentPathProperty.strokeJoin
                            )
                        )
                    }

                }
                restoreToCount(checkPoint)
            }
        }
    }
}