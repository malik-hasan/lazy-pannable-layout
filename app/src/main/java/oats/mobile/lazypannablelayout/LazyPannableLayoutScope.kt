package oats.mobile.lazypannablelayout

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

interface LazyPannableLayoutScope {
    fun item(x: Int, y: Int, maxWidth: Dp?, maxHeight: Dp?, content: @Composable () -> Unit)
    fun item(item: Positionable, content: @Composable () -> Unit)
    fun items(items: List<Positionable>, content: @Composable (Int) -> Unit)
    fun <T : Positionable> items(items: List<T>, content: @Composable (T) -> Unit)
}
