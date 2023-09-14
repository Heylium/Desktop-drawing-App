package drawbox.canvas

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import drawbox.canvas.controller.DrawController2
import io.github.markyav.drawbox.box.DrawBox
import io.github.markyav.drawbox.controller.DrawController



@Composable
fun DrawBox2() {

    val controller = remember { DrawController() }
//    val controller2 = remember { DrawController2() }


    DrawBox(
        controller = controller,
        modifier = Modifier
            .fillMaxHeight()
            .aspectRatio(1f)
            .padding(50.dp)
            .border(width = 1.dp, color = Color.Blue),
    )

}