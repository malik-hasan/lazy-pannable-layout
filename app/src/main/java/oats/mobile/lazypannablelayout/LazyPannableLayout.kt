package oats.mobile.lazypannablelayout

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.lazy.layout.LazyLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.input.pointer.pointerInput
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
        val viewport = state.getViewport(constraints)
        val indexes = mutableListOf<Int>()
        content.layers.forEach { layer ->
            indexes += layer.value.items.mapIndexedNotNull { index, positionable ->
                index.takeIf { positionable.isVisible(density, viewport) }
            }
        }

        layout(constraints.maxWidth, constraints.maxHeight) {
            indexes.forEach { index ->
                val item = content.withInterval(index) { localIndex, layer ->
                    layer.items[localIndex]
                }

                val x = item.x - state.offset.x
                val y = item.y - state.offset.y

                compose(index).forEach { measurable ->
                    val placeable = measurable.measure(constraints)
                    if (item is BoundedPositionable || (x + placeable.width > viewport.left && y + placeable.height > viewport.top)) {
                        placeable.placeRelative(x, y)
                    }
                }
            }
        }
    }
}
