package io.github.markyav.drawbox.controller

import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.IntSize
import drawbox.view.DrawAction
import io.github.markyav.drawbox.model.PathWrapper
import io.github.markyav.drawbox.util.addNotNull
import io.github.markyav.drawbox.util.combineStates
import io.github.markyav.drawbox.util.createPath
import io.github.markyav.drawbox.util.mapState
import kotlinx.coroutines.flow.*

/**
 * DrawController interacts with [DrawBox] and it allows you to control the canvas and all the components with it.
 */
class DrawController {
    private var state: MutableStateFlow<DrawBoxConnectionState> = MutableStateFlow(DrawBoxConnectionState.Disconnected)

    /** A stateful list of [Path] that is drawn on the [Canvas]. */
    private val drawnPaths: MutableStateFlow<List<PathWrapper>> = MutableStateFlow(emptyList())

    private val activeDrawingPath: MutableStateFlow<List<Offset>?> = MutableStateFlow(null)

    /** A stateful list of [Path] that was drawn on the [Canvas] but user retracted his action. */
    private val canceledPaths: MutableStateFlow<List<PathWrapper>> = MutableStateFlow(emptyList())

    val openedImage: MutableStateFlow<ImageBitmap?> = MutableStateFlow(null)

    /** An [canvasOpacity] of the [Canvas] in the [DrawBox] */
    var canvasOpacity: MutableStateFlow<Float> = MutableStateFlow(1f)

    /** An [opacity] of the stroke */
    var opacity: MutableStateFlow<Float> = MutableStateFlow(1f)

    /** A [strokeWidth] of the stroke */
    var strokeWidth: MutableStateFlow<Float> = MutableStateFlow(10f)

    /** A [color] of the stroke */
    var color: MutableStateFlow<Color> = MutableStateFlow(Color.Red)

    /** A [background] of the background of DrawBox */
    var background: MutableStateFlow<DrawBoxBackground> = MutableStateFlow(DrawBoxBackground.NoBackground)

    /** Indicate how many redos it is possible to do. */
    val undoCount = drawnPaths.mapState { it.size }

    /** Indicate how many undos it is possible to do. */
    val redoCount = canceledPaths.mapState { it.size }

    /** Executes undo the drawn path if possible. */
    fun undo() {
        if (drawnPaths.value.isNotEmpty()) {
            val _drawnPaths = drawnPaths.value.toMutableList()
            val _canceledPaths = canceledPaths.value.toMutableList()

            _canceledPaths.add(_drawnPaths.removeLast())

            drawnPaths.value = _drawnPaths
            canceledPaths.value = _canceledPaths
        }
    }

    /** Executes redo the drawn path if possible. */
    fun redo() {
        if (canceledPaths.value.isNotEmpty()) {
            val _drawnPaths = drawnPaths.value.toMutableList()
            val _canceledPaths = canceledPaths.value.toMutableList()

            _drawnPaths.add(_canceledPaths.removeLast())

            drawnPaths.value = _drawnPaths
            canceledPaths.value = _canceledPaths
        }
    }

    /** Clear drawn paths and the bitmap image. */
    fun reset() {
        drawnPaths.value = emptyList()
        canceledPaths.value = emptyList()
    }

    fun open(image: ImageBitmap) {
        reset()
        openedImage.value = image
    }

    /** Call this function when user starts drawing a path. */
    internal fun updateLatestPath(newPoint: Offset) {
        (state.value as? DrawBoxConnectionState.Connected)?.let {
            require(activeDrawingPath.value != null)
            val list = activeDrawingPath.value!!.toMutableList()
            list.add(newPoint.div(it.size.toFloat()))
            activeDrawingPath.value = list
        }
    }

    /** When dragging call this function to update the last path. */
    internal fun insertNewPath(newPoint: Offset) {
        (state.value as? DrawBoxConnectionState.Connected)?.let {
            require(activeDrawingPath.value == null)
            activeDrawingPath.value = listOf(newPoint.div(it.size.toFloat()))
            canceledPaths.value = emptyList()
        }
    }

    internal fun finalizePath() {
        (state.value as? DrawBoxConnectionState.Connected)?.let {
            require(activeDrawingPath.value != null)
            val _drawnPaths = drawnPaths.value.toMutableList()

            val pathWrapper = PathWrapper(
                points = activeDrawingPath.value!!,
                strokeColor = color.value,
                alpha = opacity.value,
                strokeWidth = strokeWidth.value.div(it.size.toFloat()),
            )
            _drawnPaths.add(pathWrapper)

            drawnPaths.value = _drawnPaths
            activeDrawingPath.value = null
        }
    }

    /** Call this function to connect to the [DrawBox]. */
    internal fun connectToDrawBox(size: IntSize) {
        if (
            size.width > 0 &&
            size.height > 0 &&
            size.width == size.height
        ) {
            state.value = DrawBoxConnectionState.Connected(size = size.width)
        }
    }

    internal fun onTap(newPoint: Offset) {
        insertNewPath(newPoint)
        finalizePath()
    }

    internal fun onTap2(newPoint: Offset) {
        insertNewPath(newPoint)

//        finalizePath()

        val Point2 = Offset(x = newPoint.x + 30, y = newPoint.y + 30)

        updateLatestPath(Point2)
        finalizePath()
//        insertNewPath(Point2)

    }

    private fun List<PathWrapper>.scale(size: Float): List<PathWrapper> {
        return this.map { pw ->
            val t = pw.points.map { it.times(size) }
            pw.copy(
                points = mutableListOf<Offset>().also { it.addAll(t) },
                strokeWidth = pw.strokeWidth * size
            )
        }
    }

    fun getDrawPath(subscription: DrawBoxSubscription): StateFlow<List<PathWrapper>> {
        return when (subscription) {
            is DrawBoxSubscription.DynamicUpdate -> getDynamicUpdateDrawnPath()
            is DrawBoxSubscription.FinishDrawingUpdate -> drawnPaths
        }
    }

    private fun getDynamicUpdateDrawnPath(): StateFlow<List<PathWrapper>> {
        return combineStates(drawnPaths, activeDrawingPath) { a, b ->
            val _a = a.toMutableList()
            (state.value as? DrawBoxConnectionState.Connected)?.let {
                val pathWrapper = PathWrapper(
                    points = activeDrawingPath.value ?: emptyList(),
                    strokeColor = color.value,
                    alpha = opacity.value,
                    strokeWidth = strokeWidth.value.div(it.size.toFloat()),
                )
                _a.addNotNull(pathWrapper)
            }
            _a
        }
    }

    internal fun getPathWrappersForDrawbox(subscription: DrawBoxSubscription): StateFlow<List<PathWrapper>> {
        return combineStates(getDrawPath(subscription), state) { paths, st ->
            val size = (st as? DrawBoxConnectionState.Connected)?.size ?: 1
            paths.scale(size.toFloat())
        }
    }

    internal fun getOpenImageForDrawbox(size: Int?): StateFlow<OpenedImage> {
        return combineStates(openedImage, state) { image, st ->
            if (image !=  null) {
                OpenedImage.Image(
                    image,
                    dstSize = IntSize(
                        width = size ?: (st as? DrawBoxConnectionState.Connected)?.size ?: 1,
                        height = size ?: (st as? DrawBoxConnectionState.Connected)?.size ?: 1,
                    ),
                )
            } else {
                OpenedImage.None
            }
        }
    }

    fun getBitmap(size: Int, subscription: DrawBoxSubscription): StateFlow<ImageBitmap> {
        val path = getDrawPath(subscription)
        return combineStates(getOpenImageForDrawbox(size), path) { openImage, p ->
            val bitmap = ImageBitmap(size, size, ImageBitmapConfig.Argb8888)
            val canvas = Canvas(bitmap)
            (openImage as? OpenedImage.Image)?.let {
                canvas.drawImageRect(
                    image = it.image,
                    srcOffset = it.srcOffset,
                    srcSize = it.srcSize,
                    dstSize = it.dstSize,
                    paint = Paint()
                )
            }
            p.scale(size.toFloat()).forEach { pw ->
                canvas.drawPath(
                    createPath(pw.points),
                    paint = Paint().apply {
                        color = pw.strokeColor
                        alpha = pw.alpha
                        style = PaintingStyle.Stroke
                        strokeCap = StrokeCap.Round
                        strokeJoin = StrokeJoin.Round
                        strokeWidth = pw.strokeWidth
                    }
                )
            }
            bitmap
        }
    }
}