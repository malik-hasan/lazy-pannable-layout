package oats.mobile.lazypannablelayout

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

interface LazyPannableLayoutScope {
    fun item(x: Int, y: Int, maxWidth: Dp?, maxHeight: Dp?, content: @Composable () -> Unit)
    fun <T : Positionable> item(item: T, content: @Composable (T) -> Unit)
    fun <T : Positionable> items(items: List<T>, content: @Composable (Int, T) -> Unit)
}
