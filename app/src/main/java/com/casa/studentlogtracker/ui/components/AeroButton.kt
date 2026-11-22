package com.casa.studentlogtracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.casa.studentlogtracker.ui.theme.AeroButtonBorder
import com.casa.studentlogtracker.ui.theme.AeroButtonBottom
import com.casa.studentlogtracker.ui.theme.AeroButtonMid
import com.casa.studentlogtracker.ui.theme.AeroButtonPressedBottom
import com.casa.studentlogtracker.ui.theme.AeroButtonPressedTop
import com.casa.studentlogtracker.ui.theme.AeroButtonTop
import com.casa.studentlogtracker.ui.theme.AeroTextDark

enum class AeroButtonStyle {
    Primary,
    Secondary,
    Danger
}

/**
 * Windows Vista / 7 Glossy Aero Button with multi-stop gradients and inset borders.
 */
@Composable
fun AeroButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    style: AeroButtonStyle = AeroButtonStyle.Primary,
    enabled: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val cornerShape = RoundedCornerShape(5.dp)

    val backgroundBrush = when {
        !enabled -> Brush.verticalGradient(
            colors = listOf(Color(0xFFE5E5E5), Color(0xFFCCCCCC))
        )
        isPressed -> Brush.verticalGradient(
            colors = listOf(AeroButtonPressedTop, AeroButtonPressedBottom)
        )
        style == AeroButtonStyle.Primary -> Brush.verticalGradient(
            colors = listOf(
                AeroButtonTop,
                AeroButtonMid,
                AeroButtonBottom
            )
        )
        style == AeroButtonStyle.Danger -> Brush.verticalGradient(
            colors = listOf(
                Color(0xFFFFAAAA),
                Color(0xFFE84444),
                Color(0xFFB81D1D)
            )
        )
        else -> Brush.verticalGradient( // Secondary
            colors = listOf(
                Color(0xFFFFFFFF),
                Color(0xFFF0F0F0),
                Color(0xFFE0E0E0)
            )
        )
    }

    val borderColor = when {
        !enabled -> Color(0xFFA0A0A0)
        style == AeroButtonStyle.Danger -> Color(0xFF8B0000)
        else -> AeroButtonBorder
    }

    val textColor = when {
        !enabled -> Color(0xFF888888)
        style == AeroButtonStyle.Danger -> Color.White
        else -> AeroTextDark
    }

    Box(
        modifier = modifier
            .clip(cornerShape)
            .background(backgroundBrush)
            .border(1.2.dp, borderColor, cornerShape)
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 14.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        // Subtle top glass sheen inside button
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(horizontal = 2.dp, vertical = 1.dp)
                .background(Color.White.copy(alpha = 0.5f))
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = textColor,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
            }
            Text(
                text = text,
                color = textColor,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
