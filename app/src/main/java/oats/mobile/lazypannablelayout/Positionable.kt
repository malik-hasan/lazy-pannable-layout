package oats.mobile.lazypannablelayout

import androidx.compose.ui.unit.IntRect

/**
 * A Positionable item in a LazyPannableLayout
 *
 * Only use this when the size of the Composable has no bounds
 * The LazyPannableLayout is smart enough to skip it if it is beyond the right or bottom bounds of the viewport
 * But it will have to measure this Composable on every pass to decide if it is beyond the left or top bounds of the viewport
 * **Always** use BoundedPositionable instead when possible for full optimization
 */
abstract class Positionable {
    abstract val x: Int
    abstract val y: Int

    internal open fun isVisible(density: Float, viewport: IntRect) = with(viewport) {
        x < right && y < bottom
    }
}
