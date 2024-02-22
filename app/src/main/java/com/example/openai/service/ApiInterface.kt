package com.example.openai.service

import com.example.openai.model.ChatRequest
import com.example.openai.model.ChatResponse
import com.example.openai.model.ImageRequest
import com.example.openai.model.ImageResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterface {
    @POST("/v1/completions")
    fun getChat(
        @Header("Content-Type") contentType:String,
        @Header("Authorization") authorization:String,
        @Body chatRequest: ChatRequest
    ):Call<ChatResponse>
    @POST("/v1/images/generations")
    fun getImage(
        @Header("Content-Type") contentType:String,
        @Header("Authorization") authorization:String,
        @Body imageRequest: ImageRequest
    ):Call<ImageResponse>
}