package oats.mobile.lazypannablelayout

import androidx.compose.foundation.lazy.layout.IntervalList
import androidx.compose.foundation.lazy.layout.LazyLayoutIntervalContent
import androidx.compose.foundation.lazy.layout.MutableIntervalList
import androidx.compose.runtime.Composable

interface LazyPannableLayoutScope {
    fun items(items: List<Positionable>, itemContent: @Composable (Int) -> Unit)
}

internal class LazyPannableLayoutLayerContent(
    buildContent: LazyPannableLayoutScope.() -> Unit
): LazyLayoutIntervalContent<LazyPannableLayoutLayer>, LazyPannableLayoutScope {

    private val _layers = MutableIntervalList<LazyPannableLayoutLayer>()
    val layers: IntervalList<LazyPannableLayoutLayer> = _layers

    override fun items(items: List<Positionable>, itemContent: @Composable (Int) -> Unit) {
        _layers.addInterval(
            items.size,
            LazyPannableLayoutLayer(items, itemContent)
        )
    }

    init { buildContent() }
}
