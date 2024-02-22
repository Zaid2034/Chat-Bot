package com.example.openai.activities
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.openai.R
import com.example.openai.adapter.OpenAiAdapter
import com.example.openai.databinding.ActivityMainBinding
import com.example.openai.model.ChatRequest
import com.example.openai.model.ChatResponse
import com.example.openai.model.MessageModel
import com.example.openai.service.ApiInterface
import com.example.openai.utils.API_KEY
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : BaseActivity() {
    private var binding:ActivityMainBinding?=null
    private var list:ArrayList<MessageModel>? = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)
        binding?.recyclerView?.layoutManager=LinearLayoutManager(this)
        binding?.recyclerView?.setHasFixedSize(true)
        list = getMessageListFromSharedPreferences()
        if(list?.size!!>0){
            val adapter=OpenAiAdapter(this, list!!)
            binding?.recyclerView?.layoutManager?.scrollToPosition(list!!.size - 1)
            binding?.recyclerView?.adapter=adapter
        }
        binding?.ibSend?.setOnClickListener {
            val s: String? =binding?.etQuestions?.text?.toString()
            if(s.isNullOrEmpty()){
                Toast.makeText(this,"Please say something",Toast.LENGTH_LONG).show()
            }
            else{
                binding?.etQuestions?.text?.clear()
                val obj=MessageModel(true,s)
                list!!.add(obj)
                val adapter=OpenAiAdapter(this, list!!)
                binding?.recyclerView?.layoutManager?.scrollToPosition(list!!.size - 1)
                binding?.recyclerView?.adapter=adapter
                saveMessageListToSharedPreferences(list!!)
                getChats(s)


            }
        }
    }
    private  fun getChats(s:String){

        val obj= ChatRequest(250,"text-davinci-003",s,0.7)
        //Toast.makeText(this@MainActivity,s,Toast.LENGTH_LONG).show()
        val contentType="application/json"
        val authorization="Bearer ${API_KEY.api_key}"
        val retrofit: Retrofit = Retrofit.Builder().baseUrl("https://api.openai.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiInterface=retrofit.create(ApiInterface::class.java)
        apiInterface.getChat(contentType,authorization,obj).enqueue(object : Callback<ChatResponse?> {
            override fun onResponse(call: Call<ChatResponse?>, response: Response<ChatResponse?>) {
                val responseBody=response.body()
              //  Toast.makeText(this@MainActivity,s,Toast.LENGTH_LONG).show()
                if(responseBody!=null){
                    val obj2=MessageModel(false,responseBody.choices.first().text)
                    list!!.add(obj2)

                    binding?.recyclerView?.layoutManager=LinearLayoutManager(this@MainActivity)
                    binding?.recyclerView?.setHasFixedSize(true)
                    val adapter=OpenAiAdapter(this@MainActivity, list!!)
                    binding?.recyclerView?.layoutManager?.scrollToPosition(list!!.size - 1)
                    binding?.recyclerView?.adapter=adapter
                    saveMessageListToSharedPreferences(list!!)
                }
                else{
                    Toast.makeText(this@MainActivity,"Response body is empty",Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ChatResponse?>, t: Throwable) {
                Toast.makeText(this@MainActivity,t.message,Toast.LENGTH_LONG).show()
            }
        })

    }
    private fun saveMessageListToSharedPreferences(messageList: ArrayList<MessageModel>) {

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        val gson = Gson()
        val listJson = gson.toJson(messageList)
        editor.putString("messageList", listJson)
        editor.apply()
    }
    private fun getMessageListFromSharedPreferences(): ArrayList<MessageModel> {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val listJson = sharedPreferences.getString("messageList", null)
        val gson = Gson()
        val type = object : TypeToken<ArrayList<MessageModel>>() {}.type
        return gson.fromJson(listJson, type) ?: ArrayList()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.clear_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_members->{
                list= ArrayList()
                binding?.recyclerView?.layoutManager=LinearLayoutManager(this@MainActivity)
                binding?.recyclerView?.setHasFixedSize(true)
                val adapter=OpenAiAdapter(this@MainActivity, list!!)
                binding?.recyclerView?.adapter=adapter
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}