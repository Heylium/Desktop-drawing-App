package drawbox.controller

import drawbox.view.DrawAction
import drawbox.view.Vertex
import io.github.markyav.drawbox.controller.DrawBoxConnectionState
import io.github.markyav.drawbox.controller.DrawBoxSubscription
import io.github.markyav.drawbox.model.PathWrapper
import io.github.markyav.drawbox.util.combineStates
import kotlinx.coroutines.flow.*

class DrawController3 {

    /** draw path connect state */
    private var connectState: MutableStateFlow<DrawBoxConnectionState> = MutableStateFlow(DrawBoxConnectionState.Disconnected)

    /**
     * canvas [DrawAction] State list.
     */
    private val _drawActions: MutableStateFlow<List<DrawAction>> = MutableStateFlow(emptyList())

    private val _activeDrawActions: MutableStateFlow<List<DrawAction>> = MutableStateFlow(emptyList())

    private fun _getDynamicUpdateDrawAction(): StateFlow<List<DrawAction>> {
        return combineStates(_drawActions, _activeDrawActions) { flowA, flowB ->
            val _flowA = flowA.toMutableList()
            _flowA
        }
    }

    internal fun getDrawAction(subscription: DrawBoxSubscription): StateFlow<List<DrawAction>> {
        return when (subscription) {
            is DrawBoxSubscription.DynamicUpdate -> _getDynamicUpdateDrawAction()
            is DrawBoxSubscription.FinishDrawingUpdate -> _drawActions
        }
    }

}