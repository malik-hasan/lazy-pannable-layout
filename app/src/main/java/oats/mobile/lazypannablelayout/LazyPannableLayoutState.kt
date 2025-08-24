package oats.mobile.lazypannablelayout

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset

@Stable
class LazyPannableLayoutState {

    var offset by mutableStateOf(IntOffset(0, 0))
        private set

    fun pan(dragOffset: IntOffset) {
        val x = (offset.x - dragOffset.x).coerceAtLeast(0)
        val y = (offset.y - dragOffset.y).coerceAtLeast(0)
        offset = IntOffset(x, y)
    }

    internal fun getVisibleCoordinates(
        constraints: Constraints,
        threshold: Int = 500 // threshold before it disappears
    ): VisibleCoordinates {
        val startX = offset.x - threshold
        val endX = constraints.maxWidth + offset.x + threshold
        val startY = offset.y - threshold
        val endY = constraints.maxHeight + offset.y + threshold

        return VisibleCoordinates(
            xRange = startX..endX,
            yRange = startY..endY
        )
    }
}
