package view

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import io.github.markyav.drawbox.model.PathWrapper
import io.github.markyav.drawbox.util.createPath

class Vertex(val pathWrapper: PathWrapper, val color: Color = Color.Black): Drawable {

    override fun draw(drawScope: DrawScope) {
        val path = createPath(pathWrapper.points)
        drawScope.drawPath(
            path = path,
            color = color,
            style = Stroke(
                width = pathWrapper.strokeWidth,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round,
            )
        )
    }
}