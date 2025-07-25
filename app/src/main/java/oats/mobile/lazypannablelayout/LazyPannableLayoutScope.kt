package oats.mobile.lazypannablelayout

import androidx.compose.runtime.Composable

interface LazyPannableLayoutScope {
    fun <T : Positionable> items(items: List<T>, itemContent: LazyPannableLayoutItemContent<T>)
}

internal class LazyPannableLayoutLayerContent(
    buildContent: LazyPannableLayoutScope.() -> Unit
): LazyPannableLayoutScope {

    private val _layers = mutableListOf<LazyPannableLayoutLayer>()
    val layers: List<LazyPannableLayoutLayer> = _layers

    override fun <T : Positionable> items(items: List<T>, itemContent: LazyPannableLayoutItemContent<T>) {
        _layers += @Composable { state ->
            LazyPannableLayoutLayer(state, items, itemContent)
        }
    }

    init { buildContent() }
}

internal typealias LazyPannableLayoutItemContent<T> = @Composable (T) -> Unit

internal typealias LazyPannableLayoutLayer = @Composable (state: LazyPannableLayoutState) -> Unit
