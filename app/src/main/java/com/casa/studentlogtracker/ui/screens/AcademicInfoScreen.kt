package com.casa.studentlogtracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.casa.studentlogtracker.StudentUiState
import com.casa.studentlogtracker.ui.theme.AeroTextDark
import com.casa.studentlogtracker.ui.theme.AeroTextMuted

@Composable
fun AcademicInfoScreen(
    state: StudentUiState,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    val programCounts = state.students.groupBy { it.program }
    val yearCounts = state.students.groupBy { it.yearLevel }
    val specializationCounts = state.students.filter { it.specialization.isNotBlank() }.groupBy { it.specialization }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Top Overview Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFFE8F3FC), Color(0xFFCBE3FA))
                    )
                )
                .border(1.dp, Color(0xFF96C2EC), RoundedCornerShape(6.dp))
                .padding(14.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF1B3B5A)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Assessment,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(26.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "Academic Information Dashboard",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = AeroTextDark
                    )
                    Text(
                        text = "Distribution metrics across registered programs & year levels",
                        fontSize = 11.sp,
                        color = AeroTextMuted
                    )
                }
            }
        }

        // Program Distribution Card
        AcademicInfoCard(
            title = "Program Distribution",
            icon = Icons.Default.School
        ) {
            if (programCounts.isEmpty()) {
                Text("No data available.", fontSize = 12.sp, color = AeroTextMuted)
            } else {
                programCounts.forEach { (program, list) ->
                    MetricRow(
                        label = program,
                        count = list.size,
                        total = state.students.size,
                        barColor = Color(0xFF0078D7)
                    )
                }
            }
        }

        // Year Level Distribution Card
        AcademicInfoCard(
            title = "Year Level Breakdown",
            icon = Icons.Default.DateRange
        ) {
            if (yearCounts.isEmpty()) {
                Text("No data available.", fontSize = 12.sp, color = AeroTextMuted)
            } else {
                yearCounts.forEach { (year, list) ->
                    MetricRow(
                        label = year,
                        count = list.size,
                        total = state.students.size,
                        barColor = Color(0xFF28A745)
                    )
                }
            }
        }

        // Specializations Card (if any)
        if (specializationCounts.isNotEmpty()) {
            AcademicInfoCard(
                title = "Specializations Tracked",
                icon = Icons.Default.Stars
            ) {
                specializationCounts.forEach { (spec, list) ->
                    MetricRow(
                        label = spec,
                        count = list.size,
                        total = state.students.size,
                        barColor = Color(0xFFFF9800)
                    )
                }
            }
        }
    }
}

@Composable
private fun AcademicInfoCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(Color.White)
            .border(1.dp, Color(0xFFD0DCE5), RoundedCornerShape(6.dp))
            .padding(14.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF1B3B5A),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = AeroTextDark
                )
            }

            content()
        }
    }
}

@Composable
private fun MetricRow(
    label: String,
    count: Int,
    total: Int,
    barColor: Color
) {
    val fraction = if (total > 0) count.toFloat() / total.toFloat() else 0f
    val percentText = "${(fraction * 100).toInt()}%"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = AeroTextDark
            )
            Text(
                text = "$count ($percentText)",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = AeroTextMuted
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Progress bar (Vista glossy bar style)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFE5EBF0))
                .border(0.5.dp, Color(0xFFB0C4DE), RoundedCornerShape(4.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction)
                    .height(8.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                barColor.copy(alpha = 0.7f),
                                barColor,
                                barColor.copy(alpha = 0.9f)
                            )
                        )
                    )
            )
        }
    }
}
