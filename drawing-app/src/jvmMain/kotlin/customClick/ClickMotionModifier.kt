//package customClick
//
//import androidx.compose.foundation.gestures.awaitFirstDown
//import androidx.compose.foundation.gestures.forEachGesture
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.input.pointer.AwaitPointerEventScope
//import androidx.compose.ui.input.pointer.pointerInput
//
//enum class MouseEvent {
//    Down, Move, Up
//}
//
//fun Modifier.mouseMotionEvent (
//
//) =this.then(
//    Modifier.pointerInput(Unit) {
//        forEachGesture {
//            awaitPointerEventScope {
//                awaitMouseMotionEvent()
//            }
//        }
//    }
//)
//
//suspend fun AwaitPointerEventScope.awaitMouseMotionEvent() {
//    val p = awaitFirstDown()
////    println("pointer at ${p.position}")
//
//}

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.asSkiaPath
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import org.jetbrains.skia.*

fun DrawScope.drawTextOnPath(
    text: String,
    textSize: Dp,
    isEmboldened: Boolean = false,
    path: androidx.compose.ui.graphics.Path,
    offset: Offset,
    textAlign: TextAlign = TextAlign.Center,
    paint: Paint,
    shadow: Shadow? = null
) {
    this.drawIntoCanvas {
        val nativeCanvas = it.nativeCanvas

        val skiaFont = Font(Typeface.makeDefault())
        skiaFont.size = textSize.toPx()
        skiaFont.isEmboldened = isEmboldened

        // Get string glyphs, and compute the width and position of each glyph in the string
        val glyphs = skiaFont.getStringGlyphs(text)
        val glyphWidths = skiaFont.getWidths(glyphs)
        val glyphPositions = skiaFont.getPositions(glyphs, Point(x = offset.x, y = offset.y))
//        println("For string $text we have ${glyphs.size} glyphs")
//        for (index in glyphs.indices) {
//            println("\t[$index] ${glyphs[index]} width=${glyphWidths[index]} position=${glyphPositions[index]}")
//        }

        val pathMeasure = PathMeasure(path.asSkiaPath())
        // How long (in pixels) is our path
        val pathPixelLength = pathMeasure.length
        // How long (in pixels) is our text
        val textPixelLength = glyphPositions[glyphs.size - 1].x + glyphWidths[glyphs.size - 1]
        // Where do we start to draw the first glyph along the path based on the requested
        // text alignment
        val textStartOffset = when (textAlign) {
            TextAlign.Left, TextAlign.Start -> glyphPositions[0].x
            TextAlign.Right, TextAlign.End -> pathPixelLength - textPixelLength + glyphPositions[0].x
            else -> (pathPixelLength - textPixelLength) / 2.0f + glyphPositions[0].x
        }

//        println("Path is $pathPixelLength px and text is $textPixelLength px")

        val visibleGlyphs = arrayListOf<Short>()
        val visibleGlyphTransforms = arrayListOf<RSXform>()

        // Go over each glyph in the string
        for (index in glyphs.indices) {
            val glyphStartOffset = glyphPositions[index]
            val glyphWidth = glyphWidths[index]
            // We're going to be rotating each glyph around its mid-horizontal point
            val glyphMidPointOffset = textStartOffset + glyphStartOffset.x + glyphWidth / 2.0f
            // There's no good solution for drawing glyphs that overflow at one of the ends of
            // the path (if the path is not long enough to position all the glyphs). Here we drop
            // (clip) the leading and the trailing glyphs
            if ((glyphMidPointOffset >= 0.0f) && (glyphMidPointOffset < pathPixelLength)) {
                // Where are we on our path?
                val glyphMidPointOnPath = pathMeasure.getPosition(glyphMidPointOffset)!!
                // And where is our path tangent pointing? (Needed for rotating the glyph)
                val glyphMidPointTangent = pathMeasure.getTangent(glyphMidPointOffset)!!

                var translationX = glyphMidPointOnPath.x
                var translationY = glyphMidPointOnPath.y

                // Horizontal offset based on the tangent
                translationX -= glyphMidPointTangent.x * glyphWidth / 2.0f
                translationY -= glyphMidPointTangent.y * glyphWidth / 2.0f

                // Vertically offset based on the normal vector
                // [-glyphMidPointTangent.y, glyphMidPointTangent.x]
                val glyphY = glyphPositions[index].y
                translationX -= glyphY * glyphMidPointTangent.y
                translationY += glyphY * glyphMidPointTangent.x

                // Compute the combined rotation-scale transformation matrix to be applied on
                // the current glyph
                visibleGlyphTransforms.add(
                    RSXform(
                        scos = glyphMidPointTangent.x,
                        ssin = glyphMidPointTangent.y,
                        tx = translationX,
                        ty = translationY
                    )
                )
                visibleGlyphs.add(glyphs[index])
            }
        }

        // Create a single text run with all visible glyphs and their transformation matrices
        val textBlobBuilder = TextBlobBuilder()
        textBlobBuilder.appendRunRSXform(
            font = skiaFont,
            glyphs = visibleGlyphs.toShortArray(),
            xform = visibleGlyphTransforms.toArray(emptyArray())
        )
        val textBlob = textBlobBuilder.build()!!

        if (shadow != null) {
            nativeCanvas.drawTextBlob(
                blob = textBlob,
                x = shadow.offset.x,
                y = shadow.offset.y,
                paint = org.jetbrains.skia.Paint().also { skiaPaint ->
                    skiaPaint.color4f = Color4f(
                        r = shadow.color.red,
                        g = shadow.color.green,
                        b = shadow.color.blue,
                        a = shadow.color.alpha
                    )
                    skiaPaint.maskFilter =
                        MaskFilter.makeBlur(FilterBlurMode.OUTER, shadow.blurRadius)
                }
            )
        }
        nativeCanvas.drawTextBlob(
            blob = textBlob,
            x = 0.0f, y = 0.0f,
            paint = paint.asFrameworkPaint()
        )
    }
}