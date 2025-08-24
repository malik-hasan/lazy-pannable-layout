package oats.mobile.lazypannablelayout

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import kotlin.math.roundToInt

abstract class Positionable {

    abstract val x: Int
    abstract val y: Int
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

    internal fun isVisible(density: Density, visibleCoordinates: VisibleCoordinates) =
        with(visibleCoordinates) {
            (x in xRange || getEndX(density) in xRange)
                && (y in yRange || getEndY(density) in yRange)
        }
}
