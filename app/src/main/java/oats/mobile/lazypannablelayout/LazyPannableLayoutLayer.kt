package oats.mobile.lazypannablelayout

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.layout.LazyLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun <T : Positionable> LazyPannableLayoutLayer(
    state: LazyPannableLayoutState,
    items: List<T>,
    itemContent: LazyPannableLayoutItemContent<T>,
    density: Density = LocalDensity.current
) {
    val itemProvider = remember(items, itemContent) {
        LazyPannableLayoutItemProvider(items, itemContent)
    }

    LazyLayout({ itemProvider }) { constraints ->
        val viewport = state.getViewport(constraints)
        val indexes = items.mapIndexedNotNull { index, item ->
            index.takeIf {
                item.isVisible(density, viewport)
            }
        }

        layout(constraints.maxWidth, constraints.maxHeight) {
            indexes.forEach { index ->
                val item = items[index]
                val x = item.x - state.offset.x
                val y = item.y - state.offset.y

                measure(index, constraints).forEach {
                    it.placeRelative(x, y)
                }
            }
        }
    }
}
