package oats.mobile.lazypannablelayout

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.layout.LazyLayoutItemProvider
import androidx.compose.runtime.Composable

@OptIn(ExperimentalFoundationApi::class)
class LazyPannableLayoutItemProvider<T : Positionable>(
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

    fun getItemIndexesInRange(boundaries: ViewBoundaries): List<Int> {
        val result = mutableListOf<Int>()

        items.forEachIndexed { index, item ->
            if (item.x in boundaries.fromX..boundaries.toX &&
                item.y in boundaries.fromY..boundaries.toY
            ) {
                result.add(index)
            }
        }

        return result
    }
}
