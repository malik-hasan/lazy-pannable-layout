package oats.mobile.lazypannablelayout

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.layout.LazyLayoutItemProvider
import androidx.compose.runtime.Composable

@OptIn(ExperimentalFoundationApi::class)
internal class LazyPannableLayoutItemProvider<T : Positionable>(
    private val items: List<T>,
    private val itemContent: LazyPannableLayoutItemContent<T>
) : LazyLayoutItemProvider {

    override val itemCount
        get() = items.size

    @Composable
    override fun Item(index: Int, key: Any) {
        items.getOrNull(index)?.let { item ->
            itemContent(item)
        }
    }
}
