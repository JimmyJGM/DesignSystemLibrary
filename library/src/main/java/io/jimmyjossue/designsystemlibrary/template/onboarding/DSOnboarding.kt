package io.jimmyjossue.designsystemlibrary.template.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import io.jimmyjossue.designsystemlibrary.R
import io.jimmyjossue.designsystemlibrary.template.screen.DSScreen
import io.jimmyjossue.designsystemlibrary.theme.catalog.color
import io.jimmyjossue.designsystemlibrary.theme.catalog.dimension
import io.jimmyjossue.designsystemlibrary.utils.DSConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class DSOnboardingColors(
    val primary: Color,
    val onPrimary: Color,
    val background: Color,
    val onBackground: Color,
)

@Composable
fun DSOnboarding(
    pages: List<DSOnboardingPage>,
    colors: DSOnboardingColors = DSOnboardingUtils.getColors(),
    textBtnFinish: String? = null,
    hasSkipButton: Boolean = true,
    onFinish: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val onboardingState = rememberPagerState(DSConstants.ZERO, pageCount = { pages.count() })

    DSScreen {
        Column(
            modifier = Modifier
                .background(color = colors.background)
                .fillMaxSize()
                .padding(top = dimension.small, bottom = dimension.medium),
            verticalArrangement = Arrangement.spacedBy(space = dimension.smalled),
        ) {
            HorizontalPager(
                modifier = Modifier.weight(1f),
                state = onboardingState,
                contentPadding = PaddingValues(horizontal = dimension.semiLarge),
                pageSpacing = dimension.semiLarge,
                pageContent = { page ->
                    OnboardingPage(
                        title = pages.getOrNull(page)?.title,
                        description = pages.getOrNull(page)?.description.orEmpty(),
                        image = pages.getOrNull(page)?.image,
                        contentColor = colors.onBackground,
                    )
                }
            )

            OnboardingIndicator(
                count = onboardingState.pageCount,
                current = onboardingState.currentPage,
                contentColor = colors.onBackground,
                accentColor = colors.primary
            )

            OnboardingButtons(
                textBtnFinish = textBtnFinish,
                pageCurrent = onboardingState.currentPage,
                pageLast = onboardingState.last,
                contentColor = colors.onBackground,
                backgroundColor = colors.primary,
                onBackgroundColor = colors.background,
                hasSkipButton = hasSkipButton,
                onNext = { scope.nextPage(pagerState = onboardingState) },
                onPrevious = { scope.previousPage(pagerState = onboardingState) },
                onFinish = onFinish,
            )
        }
    }
}

private fun CoroutineScope.nextPage(pagerState: PagerState) {
    launch {
        pagerState.animateScrollToPage(pagerState.currentPage.plus(1))
    }
}

private fun CoroutineScope.previousPage(pagerState: PagerState) {
    launch {
        pagerState.animateScrollToPage(pagerState.currentPage.minus(1))
    }
}

private val PagerState.last get() = pageCount.minus(other = 1)

@Preview(showBackground = true)
@Composable
private fun PreviewDSOnboardingPage() {
    DSOnboarding(
        colors = DSOnboardingColors(
            primary = color.primary,
            onPrimary = color.onPrimary,
            background = color.background,
            onBackground = color.typography,
        ),
        pages = listOf(
            DSOnboardingPage(
                title = "Título: <b>Primera</b> página",
                description = "<b>Lorem</b> Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto.",
                image = DSOnboardingImage.Resource(R.drawable.ic_launcher_foreground)
            ),
            DSOnboardingPage(
                title = "Título: <b>Segundo</b> página",
                description = "<b>Lorem</b> Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto.\n<b>Lorem</b> Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500, cuando un impresor.",
                image = DSOnboardingImage.Resource(R.drawable.ic_launcher_foreground)
            ),
            DSOnboardingPage(
                title = "Título: <b>Tercera</b> página",
                description = "<b>Lorem</b> Ipsum es simplemente el.\n<b>Lorem</b> Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500, cuando un impresor.",
                image = DSOnboardingImage.Resource(R.drawable.ic_launcher_foreground)
            ),
            DSOnboardingPage(
                title = "Título: <b>Cuarto</b> página",
                description = "<b>Lorem</b> Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto.\n<b>Lorem</b> Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500, cuando un impresor.",
                image = DSOnboardingImage.Resource(R.drawable.ic_launcher_foreground)
            ),
            DSOnboardingPage(
                title = "Título: <b>Quinto</b> página",
                description = "<b>Lorem</b> Ipsum es simplemente el.\n<b>Lorem</b> Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500, cuando un impresor.",
                image = DSOnboardingImage.Resource(R.drawable.ic_launcher_foreground)
            ),
        ),
        onFinish = { },
    )
}
