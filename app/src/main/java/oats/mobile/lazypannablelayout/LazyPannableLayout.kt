package oats.mobile.lazypannablelayout

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.lazy.layout.LazyLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.round

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyPannableLayout(
    modifier: Modifier = Modifier,
    state: LazyPannableLayoutState = remember { LazyPannableLayoutState() },
    contentBuilder: LazyPannableLayoutScope.() -> Unit
) {
    val content = remember(contentBuilder) {
        LazyPannableLayoutLayerContent(contentBuilder)
    }

    val itemProvider = remember(content) {
        LazyPannableLayoutItemProvider(content)
    }

    LazyLayout(
        itemProvider = { itemProvider },
        modifier = modifier
            .clipToBounds()
            .pointerInput(Unit) {
                detectDragGestures { change, dragOffset ->
                    if (!change.isConsumed) {
                        change.consume()
                        state.pan(dragOffset.round())
                    }
                }
            }
    ) { constraints ->
        val viewport = IntRect(
            offset = state.offset,
            size = IntSize(
                width = constraints.maxWidth,
                height = constraints.maxHeight
            )
        )

        val indexedVisibleItems = mutableListOf<IndexedValue<Positionable>>()
        content.layers.forEach { layer ->
            val startIndex = layer.startIndex
            layer.value.items.forEachIndexed { localIndex, positionable ->
                if (positionable.isVisible(density, viewport)) {
                    indexedVisibleItems += IndexedValue(startIndex + localIndex, positionable)
                }
            }
        }

        layout(constraints.maxWidth, constraints.maxHeight) {
            indexedVisibleItems.forEach { (index, item) ->
                val x = item.x - state.offset.x
                val y = item.y - state.offset.y

                compose(index).forEach { measurable ->
                    val placeable = measurable.measure(constraints)
                    // only check the left and top again after measurement
                    if (x + placeable.width > viewport.left && y + placeable.height > viewport.top) {
                        placeable.placeRelative(x, y)
                    }
                }
            }
        }
    }
}
