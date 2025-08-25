package oats.mobile.lazypannablelayout

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntRect
import kotlin.math.roundToInt

abstract class Positionable {
    abstract val x: Int
    abstract val y: Int
}

abstract class FixedSizePositionable : Positionable() {
    abstract val maxWidth: Dp
    abstract val maxHeight: Dp

    fun getMaxWidthPx(density: Density) = with(density) {
        maxWidth.toPx().roundToInt()
    }

    fun getMaxHeightPx(density: Density) = with(density) {
        maxHeight.toPx().roundToInt()
    }

    fun getEndX(density: Density) = x + getMaxWidthPx(density)

    fun getEndY(density: Density) = y + getMaxHeightPx(density)

    internal fun isVisible(density: Density, viewport: IntRect) = with(viewport) {
        (x < right && getEndX(density) > left)
            && (y < bottom && getEndY(density) > top)
    }
}
