package drawbox.view

import androidx.compose.ui.graphics.drawscope.DrawScope

typealias DrawAction = DrawScope.() -> Unit

interface Drawable {
    fun createDrawAction(): DrawAction
}