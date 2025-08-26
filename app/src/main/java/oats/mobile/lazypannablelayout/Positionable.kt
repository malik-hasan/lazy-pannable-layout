package oats.mobile.lazypannablelayout

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntRect

/**
 * A Positionable item in a LazyPannableLayout
 *
 * **Always** provide maxWidth and maxHeight if possible for full lazy optimization.
 * The LazyPannableLayout is will always skip composition if it is beyond the right or bottom bounds of the viewport,
 * but if `maxWidth` or `maxHeight` are not provided, it will have to re-measure this Composable on every pass
 * to decide if it is beyond the left or top bounds of the viewport respectively.
 *
 * @property x X coordinate of the top left of the composable
 * @property y Y coordinate of the top left of the composable
 * @property maxWidth maximum width of the composable. This will be the width used to determine
 * visibility for lazy composition, even if the actual width of the composable is less.
 * This should match the Composable's size Modifiers e.g `Modifier.width()` or `Modifier.widthIn(max=)`.
 * Only pass null if the Composable has unbounded width (e.g. unbounded text)
 * @property maxHeight maximum height of the composable. This will be the height used to determine
 * visibility for lazy composition, even if the actual height of the composable is less.
 * This should match the Composable's size Modifiers e.g `Modifier.height()` or `Modifier.heightIn(max=)`.
 * Only pass null if the Composable has unbounded height (e.g. unbounded text)
 */
abstract class Positionable {
    abstract val x: Int
    abstract val y: Int

    abstract val maxWidth: Dp?
    abstract val maxHeight: Dp?

    internal fun isVisible(density: Float, viewport: IntRect) = with(viewport) {
        x < right && y < bottom
            && maxWidth?.let { x + it.value * density > left } != false
            && maxHeight?.let { y + it.value * density > top } != false
    }
}
