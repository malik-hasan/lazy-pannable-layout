package oats.mobile.lazypannablelayout

import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.layout.LazyLayoutIntervalContent
import androidx.compose.foundation.lazy.layout.MutableIntervalList
import androidx.compose.foundation.lazy.layout.IntervalList
import androidx.compose.ui.unit.Dp

internal class LazyPannableLayoutLayerContent(
    buildContent: LazyPannableLayoutScope.() -> Unit
): LazyLayoutIntervalContent<LazyPannableLayoutLayer>(), LazyPannableLayoutScope {

    val _layers = MutableIntervalList<LazyPannableLayoutLayer>()
    override val intervals = _layers

    override fun item(x: Int, y: Int, maxWidth: Dp?, maxHeight: Dp?, content: @Composable () -> Unit) {
        val positionable = object : Positionable() {
            override val x = x
            override val y = y
            override val maxWidth = maxWidth
            override val maxHeight = maxHeight
        }

        item(positionable) {
            content(positionable)
        }
    }

    override fun <T : Positionable> item(item: T, content: @Composable (T) -> Unit) {
        _layers.addInterval(
            size = 1,
            value = LazyPannableLayoutLayer(listOf(item)) {
                content(item)
            }
        )
    }

    override fun <T : Positionable> items(items: List<T>, content: @Composable (Int, T) -> Unit) {
        _layers.addInterval(
            size = items.size,
            value = LazyPannableLayoutLayer(items) { i ->
                content(i, items[i])
            }
        )
    }

    init { buildContent() }
}
