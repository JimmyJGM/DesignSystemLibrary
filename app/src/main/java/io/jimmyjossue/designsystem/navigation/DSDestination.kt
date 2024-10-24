package io.jimmyjossue.designsystem.navigation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.jimmyjossue.designsystem.DSMenu
import io.jimmyjossue.designsystem.components.button.DSButtonPrimary
import io.jimmyjossue.designsystem.components.separator.DSSpacerFilled
import io.jimmyjossue.designsystem.theme.dimension
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable data object Modal: Routes()
    @Serializable data object Home: Routes()
    @Serializable data class Details(val id: String): Routes()
    @Serializable data object Info: Routes()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NavigationGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Home,
        modifier = Modifier.fillMaxSize()
    ) {
        composable<Routes.Home> {
            Screen(
                title = "Home",
                color = Color(0xFFDAECB1)
            ) {
                navController.navigate(Routes.Modal)
            }
        }

        composable<Routes.Modal> {
            DSMenu(
                onClick = {
                    navController.navigate(route = Routes.Details(id = "ID_DETAILS"))
                }
            )
        }

        composable<Routes.Details> {
            val skipPartiallyExpanded = remember { mutableStateOf(false) }
            val bottomSheetState = rememberStandardBottomSheetState(initialValue = SheetValue.PartiallyExpanded, skipHiddenState = true)
            val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)
            val cornerRadius = animateDpAsState(
                label = "AnimatedRadius",
                targetValue = if (skipPartiallyExpanded.value) 0.dp else dimension.medium,
            )

            LaunchedEffect(skipPartiallyExpanded.value) {
                when (skipPartiallyExpanded.value) {
                    true -> {
                        bottomSheetState.expand()
                        delay(350)
                        navController.navigate(Routes.Info)
                    }
                    false -> bottomSheetState.partialExpand()
                }
            }

            BottomSheetScaffold(
                scaffoldState = scaffoldState,
                content = { EmptyBox(it) },
                sheetDragHandle = null,
                sheetSwipeEnabled = false,
                sheetPeekHeight = 150.dp,
                sheetShape = RoundedCornerShape(
                    topStart = cornerRadius.value,
                    topEnd = cornerRadius.value,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                ),
                sheetContent = {
                    Column {
                        DSButtonPrimary(
                            modifier = Modifier.padding(dimension.semiLarge),
                            text = "Press click me!!!",
                            onClick = {
                                skipPartiallyExpanded.value = !skipPartiallyExpanded.value
                            }
                        )
                        DSSpacerFilled()
                    }
                },
            )
        }

        composable<Routes.Info> {
            Screen(
                title = "Info",
                color = Color(0xFFB5B1EC)
            ) {
                navController.popBackStack()
            }
        }
    }
}

@Composable
private fun EmptyBox(
    pd: PaddingValues
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFECB1D4))
            .padding(pd)
    )
}

@Composable
private fun Screen(
    title: String,
    color: Color,
    onClick: () -> Unit
) {
    Text(
        text = title,
        textAlign = TextAlign.Center,
        fontSize = 32.sp,
        modifier = Modifier
            .background(color = color)
            .clickable(onClick = onClick)
            .padding(dimension.extraLarge)
            .fillMaxSize()
    )
}
