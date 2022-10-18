package customCanvas.moves

import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitTouchSlopOrCancellation
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.AwaitPointerEventScope
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput


suspend fun AwaitPointerEventScope.awaitClickEvent(
    onClick: (PointerInputChange) -> Unit = {}
) {
    val down: PointerInputChange = awaitFirstDown()

    var pointer = down

    val change: PointerInputChange? =
        awaitTouchSlopOrCancellation(down.id) {
            change: PointerInputChange, overSlop: Offset ->
            change.consumePositionChange()
        }

    onClick(pointer)
}

fun Modifier.clickMotionEvent (
    onClick: (PointerInputChange) -> Unit = {}
) = this.then(
    Modifier.pointerInput(Unit) {
        forEachGesture {
            awaitPointerEventScope {
                awaitClickEvent(onClick)
            }
        }
    }
)