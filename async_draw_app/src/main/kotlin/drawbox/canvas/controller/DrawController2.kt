package drawbox.canvas.controller

import io.github.markyav.drawbox.controller.DrawBoxConnectionState
import kotlinx.coroutines.flow.MutableStateFlow

class DrawController2 {

    private var state: MutableStateFlow<DrawBoxConnectionState> = MutableStateFlow(DrawBoxConnectionState.Disconnected)



}