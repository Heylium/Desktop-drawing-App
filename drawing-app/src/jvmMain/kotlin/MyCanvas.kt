
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun MyCanvas() {

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        onDraw = {
            drawPath()
        }
    )
}