package com.casa.studentlogtracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.ui.window.Dialog
import com.casa.studentlogtracker.ui.theme.AeroGlassBackground
import com.casa.studentlogtracker.ui.theme.AeroGlassBorderLight
import com.casa.studentlogtracker.ui.theme.AeroGlassBorderShadow
import com.casa.studentlogtracker.ui.theme.AeroTextDark
import com.casa.studentlogtracker.ui.theme.AeroTitleBarBottom
import com.casa.studentlogtracker.ui.theme.AeroTitleBarTop

enum class AeroDialogType {
    Info,
    Success,
    Warning,
    Error
}

/**
 * Windows Vista styled Aero Dialog / MessageBox.
 */
@Composable
fun AeroMessageBox(
    title: String,
    message: String,
    onDismiss: () -> Unit,
    type: AeroDialogType = AeroDialogType.Info,
    confirmText: String = "OK",
    onConfirm: (() -> Unit)? = null,
    cancelText: String? = null,
    onCancel: (() -> Unit)? = null
) {
    Dialog(onDismissRequest = onDismiss) {
        val windowShape = RoundedCornerShape(10.dp)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(16.dp, windowShape)
                .clip(windowShape)
                .background(AeroGlassBackground)
                .border(1.5.dp, AeroGlassBorderLight, windowShape)
                .border(0.5.dp, AeroGlassBorderShadow, windowShape)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Title Bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(34.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(AeroTitleBarTop, AeroTitleBarBottom)
                            )
                        )
                        .padding(horizontal = 10.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = title,
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Dialog Content Body
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFAFAFA))
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.Top,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val icon: ImageVector
                        val iconColor: Color

                        when (type) {
                            AeroDialogType.Info -> {
                                icon = Icons.Default.Info
                                iconColor = Color(0xFF0078D7)
                            }
                            AeroDialogType.Success -> {
                                icon = Icons.Default.CheckCircle
                                iconColor = Color(0xFF28A745)
                            }
                            AeroDialogType.Warning -> {
                                icon = Icons.Default.Warning
                                iconColor = Color(0xFFFF9800)
                            }
                            AeroDialogType.Error -> {
                                icon = Icons.Default.Error
                                iconColor = Color(0xFFD32F2F)
                            }
                        }

                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = iconColor,
                            modifier = Modifier.size(36.dp)
                        )

                        Spacer(modifier = Modifier.width(14.dp))

                        Text(
                            text = message,
                            color = AeroTextDark,
                            fontSize = 13.sp,
                            lineHeight = 18.sp,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                // Button Footer Bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF0F0F0))
                        .border(
                            width = 1.dp,
                            color = Color(0xFFDFDFDF),
                            shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        if (cancelText != null) {
                            AeroButton(
                                text = cancelText,
                                onClick = {
                                    onCancel?.invoke()
                                    onDismiss()
                                },
                                style = AeroButtonStyle.Secondary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }

                        AeroButton(
                            text = confirmText,
                            onClick = {
                                onConfirm?.invoke()
                                onDismiss()
                            },
                            style = if (type == AeroDialogType.Error) AeroButtonStyle.Danger else AeroButtonStyle.Primary
                        )
                    }
                }
            }
        }
    }
}
