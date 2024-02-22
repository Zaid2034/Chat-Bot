package com.example.openai.model

data class ImageRequest(
    val n: Int,
    val prompt: String,
    val size: String
)