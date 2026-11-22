package com.casa.studentlogtracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.casa.studentlogtracker.ui.theme.AeroGlassBackground
import com.casa.studentlogtracker.ui.theme.AeroGlassBorderLight
import com.casa.studentlogtracker.ui.theme.AeroGlassBorderShadow
import com.casa.studentlogtracker.ui.theme.AeroRedClose
import com.casa.studentlogtracker.ui.theme.AeroTitleBarBottom
import com.casa.studentlogtracker.ui.theme.AeroTitleBarText
import com.casa.studentlogtracker.ui.theme.AeroTitleBarTop

/**
 * Windows Vista / 7 Glass Window Container with classic Aero title bar and glass borders.
 */
@Composable
fun AeroWindow(
    title: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    onCloseClick: (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val cornerRadius = 12.dp
    val windowShape = RoundedCornerShape(cornerRadius)

    Box(
        modifier = modifier
            .shadow(
                elevation = 16.dp,
                shape = windowShape,
                ambientColor = Color.Black.copy(alpha = 0.5f),
                spotColor = Color.Black.copy(alpha = 0.7f)
            )
            .clip(windowShape)
            .background(AeroGlassBackground)
            .border(1.5.dp, AeroGlassBorderLight, windowShape)
            .border(0.5.dp, AeroGlassBorderShadow, windowShape)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Aero Title Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(38.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                AeroTitleBarTop,
                                AeroTitleBarBottom
                            )
                        )
                    )
                    .padding(horizontal = 10.dp)
            ) {
                // Outer highlight line on top of titlebar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.White.copy(alpha = 0.45f))
                        .align(Alignment.TopCenter)
                )

                Row(
                    modifier = Modifier.fillMaxWidth().height(38.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Title section
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        if (icon != null) {
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        Text(
                            text = title,
                            color = AeroTitleBarText,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1
                        )
                    }

                    // Window Actions or standard Window buttons
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        actions?.invoke(this)

                        // Vista Window Controls: Minimize, Maximize, Close
                        WindowControlButton(symbol = "—")
                        WindowControlButton(symbol = "☐")
                        WindowControlButton(
                            symbol = "✕",
                            isClose = true,
                            onClick = onCloseClick
                        )
                    }
                }
            }

            // Window Content Area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                content()
            }
        }
    }
}

@Composable
private fun WindowControlButton(
    symbol: String,
    isClose: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    val bgBrush = if (isClose) {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFFFF7878),
                AeroRedClose,
                Color(0xFF990000)
            )
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(
                Color(0x80FFFFFF),
                Color(0x30FFFFFF),
                Color(0x10FFFFFF)
            )
        )
    }

    Box(
        modifier = Modifier
            .size(width = 24.dp, height = 20.dp)
            .clip(RoundedCornerShape(3.dp))
            .background(bgBrush)
            .border(1.dp, Color.White.copy(alpha = 0.35f), RoundedCornerShape(3.dp))
            .clickable(enabled = onClick != null) { onClick?.invoke() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = symbol,
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
