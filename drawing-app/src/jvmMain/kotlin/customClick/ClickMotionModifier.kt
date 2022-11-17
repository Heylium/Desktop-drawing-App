package customClick

import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.AwaitPointerEventScope
import androidx.compose.ui.input.pointer.pointerInput

enum class MouseEvent {
    Down, Move, Up
}

fun Modifier.mouseMotionEvent (

) =this.then(
    Modifier.pointerInput(Unit) {
        forEachGesture {
            awaitPointerEventScope {
                awaitMouseMotionEvent()
            }
        }
    }
)

suspend fun AwaitPointerEventScope.awaitMouseMotionEvent() {
    val p = awaitFirstDown()
//    println("pointer at ${p.position}")

}