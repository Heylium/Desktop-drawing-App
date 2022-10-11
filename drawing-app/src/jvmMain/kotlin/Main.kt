import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview
fun App() {
    MaterialTheme {
        DrawingApp()
    }
}

@Composable
@Preview
fun MyApp() {
    MaterialTheme {
        MyCanvas()
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose Drawing App"

    ) {
//        App()
        MyApp()

    }
}
