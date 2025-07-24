package oats.mobile.lazypannablelayout

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.layout.LazyLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Constraints

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun <T : Positionable> LazyPannableLayoutLayer(
    state: LazyPannableLayoutState,
    items: List<T>,
    itemContent: LazyPannableLayoutItemContent<T>
) {
    val itemProvider = remember(items, itemContent) {
        LazyPannableLayoutItemProvider(items, itemContent)
    }

    LazyLayout({ itemProvider }) { constraints ->
        val boundaries = state.getBoundaries(constraints)
        val indexes = items.mapIndexedNotNull { index, item ->
            index.takeIf {
                item.x in boundaries.fromX..boundaries.toX &&
                    item.y in boundaries.fromY..boundaries.toY
            }
        }

        layout(constraints.maxWidth, constraints.maxHeight) {
            indexes.forEach { index ->
                val item = items[index]
                val x = item.x - state.offset.x
                val y = item.y - state.offset.y

                measure(index, Constraints()).forEach {
                    it.placeRelative(x, y)
                }
            }
        }
    }
}
