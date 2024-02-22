package com.example.openai.model

data class ImageResponse(
    val created: Int,
    val `data`: List<Data>
)