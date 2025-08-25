package oats.mobile.lazypannablelayout

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntRect

/**
 * A Positionable whose size bounds are known before composition
 * This allows the LazyLayout to skip it if out of bounds of viewport
 */
abstract class BoundedPositionable : Positionable() {
    abstract val maxWidth: Dp
    abstract val maxHeight: Dp

    override fun isVisible(density: Float, viewport: IntRect) = with(viewport) {
        x < right && y < bottom
            && x + maxWidth.value * density > left
            && y + maxHeight.value * density > top
    }
}
