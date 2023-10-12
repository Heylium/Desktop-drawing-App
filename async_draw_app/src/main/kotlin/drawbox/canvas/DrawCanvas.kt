package drawbox.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import drawbox.view.DrawAction
import io.github.markyav.drawbox.controller.DrawController
import kotlinx.coroutines.flow.StateFlow

@Composable
fun DrawCanvas(
    controller: DrawController,
    drawActionListWrapper: StateFlow<List<DrawAction>>,
    alpha: Float,
    onSizeChanged: (IntSize) -> Unit,
    onTap: (Offset) -> Unit,
    onDragStart: (Offset) -> Unit,
    onDrag: (Offset) -> Unit,
    onDragEnd: () -> Unit,
    modifier: Modifier,
) {
    val onDragMapper: (change: PointerInputChange, dragAmount: Offset) -> Unit = remember {
        { change, _ -> onDrag(change.position) }
    }

    val drawActionList by drawActionListWrapper.collectAsState()


    Canvas(
        modifier = modifier
            .onSizeChanged(onSizeChanged)
            .pointerInput(Unit) {detectTapGestures(onTap = onTap)}
            .pointerInput(Unit) { detectDragGestures(onDragStart = onDragStart, onDrag = onDragMapper, onDragEnd = onDragEnd, onDragCancel = onDragEnd) }
            .clipToBounds()
            .alpha(alpha)
    ) {

        drawActionList.forEach { drawAction ->
            drawAction()
        }
    }
}