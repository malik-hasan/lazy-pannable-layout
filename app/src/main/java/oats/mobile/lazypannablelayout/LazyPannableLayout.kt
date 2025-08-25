package oats.mobile.lazypannablelayout

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.layout.LazyLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
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
        val indexes = content.layers.mapIndexedNotNull { index, item ->
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
