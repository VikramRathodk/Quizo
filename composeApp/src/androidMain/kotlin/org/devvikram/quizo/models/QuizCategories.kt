package org.devvikram.quizo.models

data class QuizCategories(
    val id: String,
    val title: String,
    val description: String,
    val questions: List<Question> = emptyList(),
    val color: androidx.compose.ui.graphics.Color
)
