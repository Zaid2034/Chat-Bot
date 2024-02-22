package com.example.openai.activities

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.openai.R
import com.example.openai.adapter.ImageAdapter
import com.example.openai.adapter.OpenAiAdapter
import com.example.openai.databinding.ActivityImageBinding
import com.example.openai.model.ImageModel
import com.example.openai.model.ImageRequest
import com.example.openai.model.ImageResponse
import com.example.openai.model.MessageModel
import com.example.openai.service.ApiInterface
import com.example.openai.utils.API_KEY
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class ImageActivity : BaseActivity(){
    private var binding:ActivityImageBinding?=null
    private var list1:ArrayList<ImageModel>? = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityImageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)
        binding?.ibSendImage?.setOnClickListener {
            val s: String? =binding?.etQuestionsImage?.text?.toString()
            if(s.isNullOrEmpty()){
                Toast.makeText(this,"Please say something", Toast.LENGTH_LONG).show()
            }
            else{
                binding?.etQuestionsImage?.text?.clear()
                val obj= ImageModel(true,s)
                list1!!.add(obj)
                binding?.recyclerViewImage?.layoutManager= LinearLayoutManager(this)
                binding?.recyclerViewImage?.setHasFixedSize(true)
                val adapter= ImageAdapter(this, list1!!)
                binding?.recyclerViewImage?.scrollToPosition(list1!!.size - 1)
                binding?.recyclerViewImage?.adapter=adapter
                getImages(s)


            }
        }
    }
    private fun getImages(s:String){

        val obj=ImageRequest(1,s,"1024x1024")
        val contentType="application/json"
        val authorization="Bearer ${API_KEY.api_key}"
        val retrofit=Retrofit.Builder().baseUrl("https://api.openai.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiInterface=retrofit.create(ApiInterface::class.java)
        apiInterface.getImage(contentType,authorization,obj).enqueue(object : Callback<ImageResponse?> {
            override fun onResponse(
                call: Call<ImageResponse?>,
                response: Response<ImageResponse?>
            ) {
                val responseBody=response.body()

                if(responseBody!=null){
                    val obj2= ImageModel(false,responseBody.data.first().url)
                    list1!!.add(obj2)
                    binding?.recyclerViewImage?.layoutManager= LinearLayoutManager(this@ImageActivity)
                    binding?.recyclerViewImage?.setHasFixedSize(true)
                    val adapter= ImageAdapter(this@ImageActivity, list1!!)
                    binding?.recyclerViewImage?.scrollToPosition(list1!!.size - 1)
                    binding?.recyclerViewImage?.adapter=adapter
                }
                else{
                    Toast.makeText(this@ImageActivity,"In getImages",Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ImageResponse?>, t: Throwable) {
                Toast.makeText(this@ImageActivity,"Failed to generate image",Toast.LENGTH_LONG).show()
            }
        })
    }
}