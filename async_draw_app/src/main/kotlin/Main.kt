import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState

import androidx.compose.ui.window.application
import drawbox.canvas.DrawBox2

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
        DrawBox2()
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = WindowState(size = DpSize.Unspecified),
    ) {
        Box(modifier = Modifier.size(1000.dp, 800.dp)) {
            App()
        }
    }
}
