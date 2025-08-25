package oats.mobile.lazypannablelayout

import androidx.compose.foundation.lazy.layout.IntervalList
import androidx.compose.foundation.lazy.layout.LazyLayoutIntervalContent
import androidx.compose.foundation.lazy.layout.MutableIntervalList
import androidx.compose.runtime.Composable

interface LazyPannableLayoutScope {
    fun item(item: Positionable, content: @Composable () -> Unit)
    fun items(items: List<Positionable>, content: @Composable (Int) -> Unit)
}

internal class LazyPannableLayoutLayerContent(
    buildContent: LazyPannableLayoutScope.() -> Unit
): LazyLayoutIntervalContent<LazyPannableLayoutLayer>, LazyPannableLayoutScope {

    private val _layers = MutableIntervalList<LazyPannableLayoutLayer>()
    val layers: IntervalList<LazyPannableLayoutLayer> = _layers

    override fun item(item: Positionable, content: @Composable () -> Unit) {
        _layers.addInterval(
            1,
            LazyPannableLayoutLayer(listOf(item)) {
                content()
            }
        )
    }

    override fun items(items: List<Positionable>, content: @Composable (Int) -> Unit) {
        _layers.addInterval(
            items.size,
            LazyPannableLayoutLayer(items, content)
        )
    }

    init { buildContent() }
}
