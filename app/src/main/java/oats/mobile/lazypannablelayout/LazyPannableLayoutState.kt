package oats.mobile.lazypannablelayout

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize

@Stable
class LazyPannableLayoutState {

    var offset by mutableStateOf(IntOffset(0, 0))
        private set

    fun pan(dragOffset: IntOffset) {
        val x = (offset.x - dragOffset.x).coerceAtLeast(0)
        val y = (offset.y - dragOffset.y).coerceAtLeast(0)
        offset = IntOffset(x, y)
    }

    internal fun getViewport(constraints: Constraints) =
        IntRect(
            offset = offset,
            size = IntSize(
                width = constraints.maxWidth,
                height = constraints.maxHeight
            )
        )
}
