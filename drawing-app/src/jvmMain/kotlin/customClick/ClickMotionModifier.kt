package customClick

import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.AwaitPointerEventScope
import androidx.compose.ui.input.pointer.pointerInput


fun Modifier.clickMotionEvent(

) = this.then(
    Modifier.pointerInput(Unit) {
        forEachGesture {
            awaitPointerEventScope {
                awaitFirstDown ()
            }
        }
    }
)


suspend fun AwaitPointerEventScope.awaitMouseEvent(
) {

}