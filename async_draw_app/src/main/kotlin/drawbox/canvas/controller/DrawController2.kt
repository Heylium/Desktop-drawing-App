package drawbox.canvas.controller

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import io.github.markyav.drawbox.controller.DrawBoxConnectionState
import io.github.markyav.drawbox.model.PathWrapper
import kotlinx.coroutines.flow.MutableStateFlow

class DrawController2 {

    private var state: MutableStateFlow<DrawBoxConnectionState> = MutableStateFlow(DrawBoxConnectionState.Disconnected)

    /** A stateful list of [Path] that is drawn on the [Canvas]. */
    private val drawnPaths: MutableStateFlow<List<PathWrapper>> = MutableStateFlow(emptyList())

    private val activeDrawingPath: MutableStateFlow<List<Offset>?> = MutableStateFlow(null)

    /** A stateful list of [Path] that was drawn on the [Canvas] but user retracted his action. */
    private val canceledPaths: MutableStateFlow<List<PathWrapper>> = MutableStateFlow(emptyList())

    val openedImage: MutableStateFlow<ImageBitmap?> = MutableStateFlow(null)

}