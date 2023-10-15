package drawbox.controller

import drawbox.view.DrawAction
import kotlinx.coroutines.flow.*

class DrawController3 {

    /**
     * canvas [DrawAction] State list
     */
    private val drawActions: MutableStateFlow<List<DrawAction>> = MutableStateFlow(emptyList())


}