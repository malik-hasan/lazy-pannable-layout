package oats.mobile.lazypannablelayout

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import kotlin.math.roundToInt

abstract class Positionable {

    abstract val x: Int
    abstract val y: Int
    abstract val width: Dp
    abstract val height: Dp

    fun getWidthPx(density: Density) = with(density) {
        width.toPx().roundToInt()
    }

    fun getHeightPx(density: Density) = with(density) {
        height.toPx().roundToInt()
    }

    fun getEndX(density: Density) = x + getWidthPx(density)

    fun getEndY(density: Density) = y + getHeightPx(density)

    internal fun isVisible(density: Density, visibleCoordinates: VisibleCoordinates) =
        with(visibleCoordinates) {
            (x in xRange || getEndX(density) in xRange)
                && (y in yRange || getEndY(density) in yRange)
        }
}
