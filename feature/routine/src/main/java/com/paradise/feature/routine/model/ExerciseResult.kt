package com.paradise.feature.routine.model

import java.time.LocalDateTime

data class ExerciseResult(
    val exerciseName: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val totalDuration: Int,
    val totalSets: Int,
    val totalReps: Int,
    val averageRepsPerSet: Float,
    val totalRestTime: Int,
    val averageRestTime: Float,
    val setDetails: List<SetDetail>,
)
