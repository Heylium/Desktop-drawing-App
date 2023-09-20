package view

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope

class Bond(val path: Path, val color: Color): Drawable {
    override fun draw(drawScope: DrawScope) {
        drawScope.drawPath(
            path = path,
            color = color,
        )
    }
}