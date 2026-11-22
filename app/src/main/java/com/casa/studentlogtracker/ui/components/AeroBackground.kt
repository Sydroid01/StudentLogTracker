package com.casa.studentlogtracker.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import com.casa.studentlogtracker.ui.theme.AeroAuroraBottom
import com.casa.studentlogtracker.ui.theme.AeroAuroraGlowCyan
import com.casa.studentlogtracker.ui.theme.AeroAuroraGlowGreen
import com.casa.studentlogtracker.ui.theme.AeroAuroraMid
import com.casa.studentlogtracker.ui.theme.AeroAuroraTop

/**
 * Renders the iconic Windows Vista / 7 "Aurora" wallpaper effect with smooth glowing ribbons.
 */
@Composable
fun AeroBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        AeroAuroraTop,
                        AeroAuroraMid,
                        AeroAuroraBottom
                    )
                )
            )
    ) {
        // Aurora ribbons canvas
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            // Ribbon 1 - Cyan / Teal glow
            val path1 = Path().apply {
                moveTo(0f, height * 0.45f)
                cubicTo(
                    width * 0.3f, height * 0.2f,
                    width * 0.6f, height * 0.7f,
                    width, height * 0.35f
                )
                lineTo(width, height * 0.85f)
                cubicTo(
                    width * 0.5f, height * 0.95f,
                    width * 0.2f, height * 0.6f,
                    0f, height * 0.75f
                )
                close()
            }
            drawPath(
                path = path1,
                brush = Brush.linearGradient(
                    colors = listOf(
                        AeroAuroraGlowCyan.copy(alpha = 0.35f),
                        AeroAuroraGlowGreen.copy(alpha = 0.20f),
                        Color.Transparent
                    ),
                    start = Offset(0f, height * 0.3f),
                    end = Offset(width, height * 0.7f)
                )
            )

            // Ribbon 2 - Soft green highlight curve
            val path2 = Path().apply {
                moveTo(0f, height * 0.25f)
                cubicTo(
                    width * 0.4f, height * 0.55f,
                    width * 0.7f, height * 0.15f,
                    width, height * 0.4f
                )
            }
            drawPath(
                path = path2,
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        AeroAuroraGlowGreen.copy(alpha = 0.45f),
                        AeroAuroraGlowCyan.copy(alpha = 0.3f),
                        Color.Transparent
                    )
                ),
                style = Stroke(width = 18f)
            )

            // Soft Specular radial glow at center top
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.18f),
                        AeroAuroraGlowCyan.copy(alpha = 0.08f),
                        Color.Transparent
                    ),
                    center = Offset(width * 0.5f, height * 0.25f),
                    radius = width * 0.7f
                ),
                center = Offset(width * 0.5f, height * 0.25f),
                radius = width * 0.7f
            )
        }

        // Overlay main UI content
        content()
    }
}
