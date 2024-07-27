package yunuiy_hacker.ryzhaya_tetenka.composecalculator.presentation.main_activity.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import yunuiy_hacker.ryzhaya_tetenka.composecalculator.R
import kotlin.math.roundToInt

@Composable
fun ThemeSwitch(
    modifier: Modifier = Modifier,
    isDark: Boolean,
    onCheckedChange: () -> Unit
) {
    Switch(
        modifier = modifier,
        checked = !isDark,
        onCheckedChange = {
            onCheckedChange()
        },
        thumbContent = {
            Box(modifier = Modifier.size(2.dp))
        },
        nearbyContent = {
            Icon(
                painter = painterResource(
                    id = R.drawable.ic_sun
                ), contentDescription = "", Modifier.size(24.dp), tint = Color(0xFF4B5EFC)
            )
        },
        nearbyActiveIcon = R.drawable.ic_sun,
        nearbyInactiveIcon = R.drawable.ic_moon,
        colors = yunuiy_hacker.ryzhaya_tetenka.composecalculator.presentation.main_activity.components.SwitchColors(
            checkedThumbColor = Color(0xFFD2D3DA),
            checkedTrackColor = Color.White,
            checkedBorderColor = Color.Transparent,
            checkedIconColor = Color.Transparent,
            uncheckedThumbColor = Color(0xFF4E505F),
            uncheckedTrackColor = Color(0xFF2E2F38),
            uncheckedBorderColor = Color.Transparent,
            uncheckedIconColor = Color.Transparent,
            disabledCheckedBorderColor = Color.Transparent,
            disabledCheckedIconColor = Color.Transparent,
            disabledCheckedThumbColor = Color.Transparent,
            disabledCheckedTrackColor = Color.Transparent,
            disabledUncheckedBorderColor = Color.Transparent,
            disabledUncheckedIconColor = Color.Transparent,
            disabledUncheckedThumbColor = Color.Transparent,
            disabledUncheckedTrackColor = Color.Transparent
        )
    )
}

@Composable
@Suppress("ComposableLambdaParameterNaming", "ComposableLambdaParameterPosition")
fun Switch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    thumbContent: (@Composable () -> Unit)? = null,
    nearbyContent: (@Composable () -> Unit)? = null,
    @DrawableRes nearbyActiveIcon: Int,
    @DrawableRes nearbyInactiveIcon: Int,
    enabled: Boolean = true,
    colors: yunuiy_hacker.ryzhaya_tetenka.composecalculator.presentation.main_activity.components.SwitchColors = yunuiy_hacker.ryzhaya_tetenka.composecalculator.presentation.main_activity.components.SwitchDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val uncheckedThumbDiameter = if (thumbContent == null) {
        UncheckedThumbDiameter
    } else {
        ThumbDiameter
    }

    val thumbPaddingStart = (SwitchHeight - uncheckedThumbDiameter) / 2
    val minBound = with(LocalDensity.current) { thumbPaddingStart.toPx() }
    val maxBound = with(LocalDensity.current) { ThumbPathLength.toPx() }
    val valueToOffset = remember<(Boolean) -> Float>(minBound, maxBound) {
        { value -> if (value) maxBound else minBound }
    }

    val targetValue = valueToOffset(checked)
    val offset = remember { Animatable(targetValue) }
    val scope = rememberCoroutineScope()

    SideEffect {
        // min bound might have changed if the icon is only rendered in checked state.
        offset.updateBounds(lowerBound = minBound)
    }

    DisposableEffect(checked) {
        if (offset.targetValue != targetValue) {
            scope.launch {
                offset.animateTo(targetValue, AnimationSpec)
            }
        }
        onDispose { }
    }

    // TODO: Add Swipeable modifier b/223797571
    val toggleableModifier = if (onCheckedChange != null) {
        Modifier.toggleable(
            value = checked,
            onValueChange = onCheckedChange,
            enabled = enabled,
            role = Role.Switch,
            interactionSource = interactionSource,
            indication = null
        )
    } else {
        Modifier
    }

    Box(
        modifier
            .then(
                if (onCheckedChange != null) {
                    Modifier.minimumInteractiveComponentSize()
                } else {
                    Modifier
                }
            )
            .then(toggleableModifier)
            .wrapContentSize(Alignment.Center)
            .requiredSize(SwitchWidth, SwitchHeight)
    ) {
        SwitchImpl(
            checked = checked,
            enabled = enabled,
            colors = colors,
            thumbValue = offset.asState(),
            interactionSource = interactionSource,
            thumbShape = RoundedCornerShape(50),
            uncheckedThumbDiameter = uncheckedThumbDiameter,
            minBound = thumbPaddingStart,
            maxBound = ThumbPathLength,
            thumbContent = thumbContent,
            nearbyContent = nearbyContent,
            nearbyActiveIcon = nearbyActiveIcon,
            nearbyInactiveIcon = nearbyInactiveIcon
        )
    }
}

@Composable
@Suppress("ComposableLambdaParameterNaming", "ComposableLambdaParameterPosition")
private fun BoxScope.SwitchImpl(
    checked: Boolean,
    enabled: Boolean,
    colors: yunuiy_hacker.ryzhaya_tetenka.composecalculator.presentation.main_activity.components.SwitchColors,
    thumbValue: State<Float>,
    thumbContent: (@Composable () -> Unit)?,
    nearbyContent: (@Composable () -> Unit)?,
    @DrawableRes nearbyActiveIcon: Int,
    @DrawableRes nearbyInactiveIcon: Int,
    interactionSource: InteractionSource,
    thumbShape: Shape,
    uncheckedThumbDiameter: Dp,
    minBound: Dp,
    maxBound: Dp,
) {
    val trackColor by colors.trackColor(enabled, checked)
    val isPressed by interactionSource.collectIsPressedAsState()

    val thumbValueDp = with(LocalDensity.current) { thumbValue.value.toDp() }
    val thumbSizeDp = if (isPressed) {
        28.dp
    } else {
        uncheckedThumbDiameter + (ThumbDiameter - uncheckedThumbDiameter) * ((thumbValueDp - minBound) / (maxBound - minBound))
    }

    val thumbOffset = if (isPressed) {
        with(LocalDensity.current) {
            if (checked) {
                ThumbPathLength - 2.dp
            } else {
                2.dp
            }.toPx()
        }
    } else {
        thumbValue.value
    }

    val trackShape = RoundedCornerShape(50)
    val modifier = Modifier
        .width(SwitchWidth)
        .height(SwitchHeight)
        .background(trackColor, trackShape)

    Box(
        modifier.align(Alignment.Center)
    ) {
        val thumbColor by colors.thumbColor(enabled, checked)
        val resolvedThumbColor = thumbColor
        if (nearbyContent != null) {
            Icon(
                painter = painterResource(
                    id = if (checked) nearbyActiveIcon else nearbyInactiveIcon
                ),
                contentDescription = "",
                Modifier
                    .size(28.dp)
                    .align(if (checked) Alignment.CenterStart else Alignment.CenterEnd)
                    .padding(
                        start = if (checked) 4.dp else 0.dp,
                        end = if (checked) 0.dp else 4.dp
                    ),
                tint = Color(0xFF4B5EFC)
            )
        }
        Box(modifier = Modifier
            .offset { IntOffset(thumbOffset.roundToInt(), 0) }
            .indication(
                interactionSource = interactionSource,
                indication = rememberRipple(bounded = false, 80.dp / 2)
            )
            .requiredSize(thumbSizeDp)
            .background(resolvedThumbColor, thumbShape)
            .align(Alignment.CenterStart)) {
            if (thumbContent != null) {
                val iconColor = colors.iconColor(enabled, checked)
                CompositionLocalProvider(
                    LocalContentColor provides iconColor.value, content = thumbContent
                )
            }
        }
    }
}

internal val ThumbDiameter = 24.dp
internal val UncheckedThumbDiameter = 16.dp
private val SwitchWidth = 72.dp
private val SwitchHeight = 32.dp
private val ThumbPadding = (SwitchHeight - ThumbDiameter) / 2
private val ThumbPathLength = (SwitchWidth - ThumbDiameter) - ThumbPadding

private val AnimationSpec = TweenSpec<Float>(durationMillis = 100)

/**
 * Contains the default values used by [Switch]
 */
object SwitchDefaults {
    /**
     * Creates a [SwitchColors] that represents the different colors used in a [Switch] in
     * different states.
     *
     * @param checkedThumbColor the color used for the thumb when enabled and checked
     * @param checkedTrackColor the color used for the track when enabled and checked
     * @param checkedBorderColor the color used for the border when enabled and checked
     * @param checkedIconColor the color used for the icon when enabled and checked
     * @param uncheckedThumbColor the color used for the thumb when enabled and unchecked
     * @param uncheckedTrackColor the color used for the track when enabled and unchecked
     * @param uncheckedBorderColor the color used for the border when enabled and unchecked
     * @param uncheckedIconColor the color used for the icon when enabled and unchecked
     * @param disabledCheckedThumbColor the color used for the thumb when disabled and checked
     * @param disabledCheckedTrackColor the color used for the track when disabled and checked
     * @param disabledCheckedBorderColor the color used for the border when disabled and checked
     * @param disabledCheckedIconColor the color used for the icon when disabled and checked
     * @param disabledUncheckedThumbColor the color used for the thumb when disabled and unchecked
     * @param disabledUncheckedTrackColor the color used for the track when disabled and unchecked
     * @param disabledUncheckedBorderColor the color used for the border when disabled and unchecked
     * @param disabledUncheckedIconColor the color used for the icon when disabled and unchecked
     */
    @Composable
    fun colors(
        checkedThumbColor: Color = MaterialTheme.colorScheme.primary,
        checkedTrackColor: Color = MaterialTheme.colorScheme.primary,
        checkedBorderColor: Color = Color.Transparent,
        checkedIconColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
        uncheckedThumbColor: Color = MaterialTheme.colorScheme.outline,
        uncheckedTrackColor: Color = MaterialTheme.colorScheme.surfaceVariant,
        uncheckedBorderColor: Color = MaterialTheme.colorScheme.outline,
        uncheckedIconColor: Color = MaterialTheme.colorScheme.surfaceVariant,
        disabledCheckedThumbColor: Color = MaterialTheme.colorScheme.surface.copy(alpha = 1.0f)
            .compositeOver(MaterialTheme.colorScheme.surface),
        disabledCheckedTrackColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
            .compositeOver(MaterialTheme.colorScheme.surface),
        disabledCheckedBorderColor: Color = Color.Transparent,
        disabledCheckedIconColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
            .compositeOver(MaterialTheme.colorScheme.surface),
        disabledUncheckedThumbColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
            .compositeOver(MaterialTheme.colorScheme.surface),
        disabledUncheckedTrackColor: Color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.12f)
            .compositeOver(MaterialTheme.colorScheme.surface),
        disabledUncheckedBorderColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
            .compositeOver(MaterialTheme.colorScheme.surface),
        disabledUncheckedIconColor: Color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.38f)
            .compositeOver(MaterialTheme.colorScheme.surface),
    ): yunuiy_hacker.ryzhaya_tetenka.composecalculator.presentation.main_activity.components.SwitchColors =
        SwitchColors(
            checkedThumbColor = checkedThumbColor,
            checkedTrackColor = checkedTrackColor,
            checkedBorderColor = checkedBorderColor,
            checkedIconColor = checkedIconColor,
            uncheckedThumbColor = uncheckedThumbColor,
            uncheckedTrackColor = uncheckedTrackColor,
            uncheckedBorderColor = uncheckedBorderColor,
            uncheckedIconColor = uncheckedIconColor,
            disabledCheckedThumbColor = disabledCheckedThumbColor,
            disabledCheckedTrackColor = disabledCheckedTrackColor,
            disabledCheckedBorderColor = disabledCheckedBorderColor,
            disabledCheckedIconColor = disabledCheckedIconColor,
            disabledUncheckedThumbColor = disabledUncheckedThumbColor,
            disabledUncheckedTrackColor = disabledUncheckedTrackColor,
            disabledUncheckedBorderColor = disabledUncheckedBorderColor,
            disabledUncheckedIconColor = disabledUncheckedIconColor
        )

    /**
     * Icon size to use for `thumbContent`
     */
    val IconSize = 16.dp
}

/**
 * Represents the colors used by a [Switch] in different states
 *
 * See [SwitchDefaults.colors] for the default implementation that follows Material
 * specifications.
 */
@Immutable
class SwitchColors constructor(
    private val checkedThumbColor: Color,
    private val checkedTrackColor: Color,
    private val checkedBorderColor: Color,
    private val checkedIconColor: Color,
    private val uncheckedThumbColor: Color,
    private val uncheckedTrackColor: Color,
    private val uncheckedBorderColor: Color,
    private val uncheckedIconColor: Color,
    private val disabledCheckedThumbColor: Color,
    private val disabledCheckedTrackColor: Color,
    private val disabledCheckedBorderColor: Color,
    private val disabledCheckedIconColor: Color,
    private val disabledUncheckedThumbColor: Color,
    private val disabledUncheckedTrackColor: Color,
    private val disabledUncheckedBorderColor: Color,
    private val disabledUncheckedIconColor: Color
) {
    /**
     * Represents the color used for the switch's thumb, depending on [enabled] and [checked].
     *
     * @param enabled whether the [Switch] is enabled or not
     * @param checked whether the [Switch] is checked or not
     */
    @Composable
    fun thumbColor(enabled: Boolean, checked: Boolean): State<Color> {
        return rememberUpdatedState(
            if (enabled) {
                if (checked) checkedThumbColor else uncheckedThumbColor
            } else {
                if (checked) disabledCheckedThumbColor else disabledUncheckedThumbColor
            }
        )
    }

    /**
     * Represents the color used for the switch's track, depending on [enabled] and [checked].
     *
     * @param enabled whether the [Switch] is enabled or not
     * @param checked whether the [Switch] is checked or not
     */
    @Composable
    fun trackColor(enabled: Boolean, checked: Boolean): State<Color> {
        return rememberUpdatedState(
            if (enabled) {
                if (checked) checkedTrackColor else uncheckedTrackColor
            } else {
                if (checked) disabledCheckedTrackColor else disabledUncheckedTrackColor
            }
        )
    }

    /**
     * Represents the content color passed to the icon if used
     *
     * @param enabled whether the [Switch] is enabled or not
     * @param checked whether the [Switch] is checked or not
     */
    @Composable
    fun iconColor(enabled: Boolean, checked: Boolean): State<Color> {
        return rememberUpdatedState(
            if (enabled) {
                if (checked) checkedIconColor else uncheckedIconColor
            } else {
                if (checked) disabledCheckedIconColor else disabledUncheckedIconColor
            }
        )
    }
}