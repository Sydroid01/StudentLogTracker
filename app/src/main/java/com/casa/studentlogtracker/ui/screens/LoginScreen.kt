package com.casa.studentlogtracker.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.casa.studentlogtracker.StudentUiState
import com.casa.studentlogtracker.ui.components.AeroBackground
import com.casa.studentlogtracker.ui.components.AeroButton
import com.casa.studentlogtracker.ui.components.AeroButtonStyle
import com.casa.studentlogtracker.ui.components.AeroTextField
import com.casa.studentlogtracker.ui.components.AeroWindow
import com.casa.studentlogtracker.ui.theme.AeroTextDark
import com.casa.studentlogtracker.ui.theme.AeroTextMuted

@Composable
fun LoginScreen(
    state: StudentUiState,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AeroBackground(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            AeroWindow(
                title = "Student Info System - Log In",
                icon = Icons.Default.School,
                modifier = Modifier.width(360.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Vista Avatar Frame
                    Box(
                        modifier = Modifier
                            .size(76.dp)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        Color(0xFFE6F2FF),
                                        Color(0xFF99C2EC),
                                        Color(0xFF4A84C4)
                                    )
                                )
                            )
                            .border(2.5.dp, Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "User Avatar",
                            tint = Color(0xFF1B3B5A),
                            modifier = Modifier.size(68.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Windows Aero Authentication",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = AeroTextDark
                    )

                    Text(
                        text = "Enter your credentials to access student records",
                        fontSize = 11.sp,
                        color = AeroTextMuted,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Username Input
                    AeroTextField(
                        value = state.loginUsernameInput,
                        onValueChange = onUsernameChange,
                        label = "Username",
                        placeholder = "e.g. admin or student",
                        icon = Icons.Default.Person
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Password Input
                    AeroTextField(
                        value = state.loginPasswordInput,
                        onValueChange = onPasswordChange,
                        label = "Password",
                        placeholder = "••••••••",
                        icon = Icons.Default.Lock,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        errorMessage = state.loginError
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Actions Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Demo quick fill hint button
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0x30FFFFFF))
                                .border(1.dp, Color(0x60FFFFFF), RoundedCornerShape(4.dp))
                                .clickable {
                                    onUsernameChange("admin")
                                    onPasswordChange("password123")
                                }
                                .padding(horizontal = 8.dp, vertical = 6.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Key,
                                    contentDescription = null,
                                    tint = AeroTextDark,
                                    modifier = Modifier.size(13.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Auto-fill Demo",
                                    fontSize = 11.sp,
                                    color = AeroTextDark,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        // Glossy Login Button
                        AeroButton(
                            text = "Log in  ➜",
                            onClick = onLoginClick,
                            style = AeroButtonStyle.Primary,
                            modifier = Modifier.height(36.dp)
                        )
                    }
                }
            }
        }
    }
}
