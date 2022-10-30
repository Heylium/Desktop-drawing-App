import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import customCanvas.customCanvas
//@Composable
//@Preview
//fun App() {
//    MaterialTheme {
//        DrawingApp()
//    }
//}
//
//@Composable
//@Preview
//fun MyApp() {
//    MaterialTheme {
//        MyCanvas()
//
//    }
//}

@Composable
fun ClickApp() {
    customCanvas()
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose Drawing App"

    ) {
        ClickApp()
    }
}
