package drawbox.view

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import io.github.markyav.drawbox.model.PathWrapper
import io.github.markyav.drawbox.util.createPath

class Vertex(
    val pathWrapper: PathWrapper,
    val startPoint: Offset,
    val endPoint: Offset,
) : Drawable {


    override fun createDrawAction(): DrawAction {
        val drawObject: Vertex = this
        return {
            val drawScope: DrawScope = this
            drawPath(
                path = createPath(pathWrapper.points),
                color = pathWrapper.strokeColor,
                style = Stroke(
                    width = pathWrapper.strokeWidth,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round,
                )
            )
        }
    }
}