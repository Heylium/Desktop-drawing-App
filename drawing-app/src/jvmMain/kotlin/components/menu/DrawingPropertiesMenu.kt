package components.menu

import DrawMode
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.TooltipArea
import androidx.compose.foundation.TooltipPlacement
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Redo
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material.icons.filled.Undo
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import model.PathProperties
import ui.ColorWheel

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun DrawingPropertiesMenu (
    modifier: Modifier = Modifier,
    pathProperties: PathProperties,
    drawMode: DrawMode,
    onUndo: () -> Unit,
    onRedo: () -> Unit,
    onPathPropertiesChange: (PathProperties) -> Unit,
    onDrawModeChanged: (DrawMode) -> Unit
) {
    var showColorDialog by remember { mutableStateOf(false) }
    var showPropertiesDialog by remember { mutableStateOf(false) }
    var currentDrawMode = drawMode
    val (snackbarVisibleState, setSnackBarState) = remember { mutableStateOf(false) }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        //drag button
        IconButton(
            onClick = {
                currentDrawMode = if (currentDrawMode == DrawMode.Touch) {
                    DrawMode.Draw
                } else {
                    DrawMode.Touch
                }
                onDrawModeChanged(currentDrawMode)
            },
        ) {
            Icon(
                Icons.Filled.TouchApp,
                contentDescription = "Move canvas Icon",
                tint = if (currentDrawMode == DrawMode.Touch) Color.Black else Color.LightGray
            )
        }

        //erase button
        IconButton(
            onClick = {
                currentDrawMode = if (currentDrawMode == DrawMode.Erase) {
                    DrawMode.Draw
                } else {
                    DrawMode.Erase
                }
                onDrawModeChanged(currentDrawMode)
            }
        ) {
            Icon(
                painter = painterResource("drawable/ic_eraser_black_24dp.xml"),
                contentDescription = null,
                tint = if (currentDrawMode == DrawMode.Erase) Color.Black else Color.LightGray
            )
        }

        IconButton(onClick = { showColorDialog = !showColorDialog }) {
            ColorWheel(modifier = Modifier.size(24.dp))
        }

        IconButton(onClick = { showPropertiesDialog = !showPropertiesDialog }) {
            Icon(Icons.Filled.Brush, contentDescription = "showpropertiesdialog", tint = Color.LightGray)
        }

        IconButton(onClick = {
            onUndo()
        }) {
            Icon(Icons.Filled.Undo, contentDescription = null, tint = Color.LightGray)
        }

//        IconButton(onClick = {
//            onRedo()
//        }) {
//            Icon(Icons.Filled.Redo, contentDescription = null, tint = Color.LightGray)
//        }

    }
}