package org.devvikram.quizo.models

data class Question(
    val questionText: String,
    val options: List<String>,
    val correctAnswer: Int
)