package oats.mobile.lazypannablelayout

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.round

@Composable
fun LazyPannableLayout(
    modifier: Modifier = Modifier,
    state: LazyPannableLayoutState = remember { LazyPannableLayoutState() },
    contentBuilder: LazyPannableLayoutScope.() -> Unit
) {
    val content = remember(contentBuilder) {
        LazyPannableLayoutLayerContent(contentBuilder)
    }

    Box(modifier
        .clipToBounds()
        .pointerInput(Unit) {
            detectDragGestures { change, dragOffset ->
                if (!change.isConsumed) {
                    change.consume()
                    state.pan(dragOffset.round())
                }
            }
        }
    ) {
        content.layers.forEach { layer ->
            layer(state)
        }
    }
}
