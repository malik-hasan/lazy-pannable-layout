package oats.mobile.lazypannablelayout

internal class LazyPannableLayoutLayerContent(
    buildContent: LazyPannableLayoutScope.() -> Unit
): androidx.compose.foundation.lazy.layout.LazyLayoutIntervalContent<LazyPannableLayoutLayer>, LazyPannableLayoutScope {

    private val _layers =
        _root_ide_package_.androidx.compose.foundation.lazy.layout.MutableIntervalList<LazyPannableLayoutLayer>()
    val layers: androidx.compose.foundation.lazy.layout.IntervalList<LazyPannableLayoutLayer> = _layers

    override fun item(
        x: Int,
        y: Int,
        maxWidth: androidx.compose.ui.unit.Dp?,
        maxHeight: androidx.compose.ui.unit.Dp?,
        content: @Composable () -> Unit
    ) = item(
        object : Positionable {
            override val x = x
            override val y = y
            override val maxWidth = maxWidth
            override val maxHeight = maxHeight
        },
        content
    )

    override fun item(item: Positionable, content: @Composable () -> Unit) {
        _layers.addInterval(
            1,
            LazyPannableLayoutLayer(listOf(item)) {
                content()
            }
        )
    }

    override fun items(items: List<Positionable>, content: @Composable (Int) -> Unit) {
        _layers.addInterval(
            items.size,
            LazyPannableLayoutLayer(items, content)
        )
    }

    override fun <T : Positionable> items(items: List<T>, content: @Composable (T) -> Unit) = 
        items(items) { content(items[it]) }

    init { buildContent() }
}
