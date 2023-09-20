package view

import androidx.compose.ui.graphics.drawscope.DrawScope

interface Drawable {
    fun draw(drawScope: DrawScope)
}