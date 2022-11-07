package customClick

import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.*


@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.clickMotionEvent(

) = this.then(
    Modifier.pointerInput(Unit) {
        forEachGesture {
            awaitPointerEventScope {
//                awaitFirstDown ()
                onPointerEvent(PointerEventType.Move) {pointerEvent: PointerEvent ->
                    val position = pointerEvent.changes.first().position
                }
            }
        }
    }
)


suspend fun AwaitPointerEventScope.awaitMouseEvent(
) {

}