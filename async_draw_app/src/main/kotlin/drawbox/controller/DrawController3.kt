package drawbox.controller

import drawbox.view.DrawAction
import io.github.markyav.drawbox.controller.DrawBoxSubscription
import io.github.markyav.drawbox.util.combineStates
import kotlinx.coroutines.flow.*

class DrawController3 {

    /**
     * canvas [DrawAction] State list.
     */
    private val drawActions: MutableStateFlow<List<DrawAction>> = MutableStateFlow(emptyList())

    private fun _getDynamicUpdateDrawAction(): StateFlow<List<DrawAction>> {
//        return combineStates(dr)
    }

    internal fun getDrawAction(subscription: DrawBoxSubscription): StateFlow<List<DrawAction>> {
        return when (subscription) {
            is DrawBoxSubscription.DynamicUpdate -> _getDynamicUpdateDrawAction()
            is DrawBoxSubscription.FinishDrawingUpdate -> drawActions
        }
    }

}