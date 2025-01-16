package io.jimmyjossue.designsystemlibrary.template.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import io.jimmyjossue.designsystemlibrary.template.activity.model.DSActivityArgs
import io.jimmyjossue.designsystemlibrary.theme.AppTheme
import io.jimmyjossue.designsystemlibrary.theme.catalog.DSColors
import io.jimmyjossue.designsystemlibrary.theme.catalog.DSDimensions
import io.jimmyjossue.designsystemlibrary.theme.catalog.DSRadius
import io.jimmyjossue.designsystemlibrary.theme.catalog.DSShapes
import io.jimmyjossue.designsystemlibrary.theme.catalog.DSTypography
import io.jimmyjossue.designsystemlibrary.theme.catalog.alphaMedium
import io.jimmyjossue.designsystemlibrary.utils.NULL
import kotlinx.coroutines.delay

abstract class DSActivity : ComponentActivity() {

    @Composable
    abstract fun SetContentView(args: DSActivityArgs)

    //region set compose theme
    @Override
    protected fun getColors() = DSColors()

    @Override
    protected fun getShapes() = DSShapes()

    @Override
    protected fun getRadius() = DSRadius()

    @Override
    protected fun getDimens() = DSDimensions()

    @Override
    protected fun getTypographies() = DSTypography()
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent(
            args = DSActivityArgs()
        )
    }

    private fun setContent(args: DSActivityArgs) {
        setContent {
            AppTheme(
                colors = getColors(),
                shapes = getShapes(),
                radius = getRadius(),
                dimensions = getDimens(),
                typographies = getTypographies(),
                content = {
                    ParentContent {
                        SetContentView(args = args)
                    }
                }
            )
        }
    }
}

@Composable
private fun ParentContent(
    content: @Composable (BoxScope.() -> Unit)
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black.alphaMedium),
        content = {
            val contentRef = createRef()
            Box(
                content = content,
                modifier = Modifier
                    .animateContentSize(
                        animationSpec = spring(
                            stiffness = 750f,
                            visibilityThreshold = NULL
                        )
                    )
                    .constrainAs(ref = contentRef) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        linkTo(
                            top = contentRef.bottom,
                            bottom = parent.bottom,
                            bias = 0F
                        )
                    }
            )
        }
    )
}

@Preview
@Composable
private fun PreviewDialogContent() {
    val state = remember { mutableFloatStateOf(value = 0.5f) }

    LaunchedEffect(state.floatValue) {
        delay(2000L)
        when (state.floatValue) {
            0.5f -> state.floatValue = 0.1f
            0.1f -> state.floatValue = 0.75f
            0.75f -> state.floatValue = 1f
            1f -> state.floatValue = 0.5f
        }
    }

    ParentContent {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = state.floatValue)
                .background(color = Color.White)
        )
    }
}
