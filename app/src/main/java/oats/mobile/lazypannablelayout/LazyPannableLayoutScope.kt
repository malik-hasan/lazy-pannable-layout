package oats.mobile.lazypannablelayout

import androidx.compose.runtime.Composable

interface LazyPannableLayoutScope {
    fun <T : Positionable> items(items: List<T>, itemContent: LazyPannableLayoutItemContent<T>)
//    fun item(x: Int, y: Int, itemContent: @Composable () -> Unit)
}

internal class LazyPannableLayoutLayerContent(
    buildContent: LazyPannableLayoutScope.() -> Unit
): LazyPannableLayoutScope {

    private val _layers = mutableSetOf<LazyPannableLayoutLayer>()
    val layers: Set<LazyPannableLayoutLayer> = _layers

    override fun <T : Positionable> items(items: List<T>, itemContent: LazyPannableLayoutItemContent<T>) {
        _layers += @Composable { state ->
            LazyPannableLayoutLayer(state, items, itemContent)
        }
    }

//    override fun item(x: Int, y: Int, itemContent: @Composable () -> Unit) {
//        _layers += @Composable { state ->
//            LazyPannableLayout(state, items, itemContent)
//        }
//    }

    init { buildContent() }
}

internal typealias LazyPannableLayoutItemContent<T> = @Composable (T) -> Unit

internal typealias LazyPannableLayoutLayer = @Composable (state: LazyPannableLayoutState) -> Unit
