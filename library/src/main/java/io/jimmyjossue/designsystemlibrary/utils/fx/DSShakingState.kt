package io.jimmyjossue.designsystemlibrary.utils.fx

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

internal data class ShakingListener(
    val key: String,
    val positionX:  Animatable<Float, AnimationVector1D> = Animatable(0f),
)

class DSShakingState {

    val xPosition = Animatable(0f)
    private val listeners: MutableSet<ShakingListener> = mutableSetOf()

    fun addShakeble(key: String) {
        val finned = listeners.find { it.key == key }
        if (key.isNotBlank() && finned == null) {
            listeners.add(element = ShakingListener(key = key))
        }
    }

    internal fun getShakeble(key: String) = listeners.find { it.key == key }

    suspend fun shake(
        key: String? = null,
        animationDuration: Int = 50,
        strength: Strength = Strength.Normal
    ) {
        val animationSpec: AnimationSpec<Float> = tween(durationMillis = animationDuration)
        val shakable = listeners.find { it.key == key }

        if (shakable != null) {
            shakeIndividualElement(
                shakable = shakable,
                animationSpec = animationSpec,
                strength = strength,
            )
        } else {
            listeners.forEach {
                shakeIndividualElement(
                    shakable = it,
                    animationSpec = animationSpec,
                    strength = strength,
                )
            }
        }
    }

    private suspend fun shakeIndividualElement(
        shakable: ShakingListener,
        animationSpec: AnimationSpec<Float>,
        strength: Strength = Strength.Normal
    ) {
        repeat(3) {
            shakable.positionX.animateTo(-strength.value, animationSpec)
            shakable.positionX.animateTo(0f, animationSpec)
            shakable.positionX.animateTo(strength.value / 2, animationSpec)
            shakable.positionX.animateTo(0f, animationSpec)
        }
    }

    sealed class Strength(val value: Float) {
        data object Normal : Strength(17f)
        data object Strong : Strength(40f)
    }
}

@Composable
fun rememberShakingState(): DSShakingState {
    return remember { DSShakingState() }
}

fun Modifier.shakable(
    key: String,
    state: DSShakingState,
): Modifier {
    return graphicsLayer {
        val shekable = state.getShakeble(key)
        if (shekable != null) {
            translationX = shekable.positionX.value
        }
    }
}