package com.example.openai.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.openai.R
import com.example.openai.databinding.ActivityBaseBinding

open class BaseActivity : AppCompatActivity() {
    private lateinit var mProgressDialog:Dialog
    private var binding:ActivityBaseBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityBaseBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)
    }
    fun showProgressDialog(text:String){
        mProgressDialog= Dialog(this)
        mProgressDialog.setContentView(R.layout.dialog_progress)
        mProgressDialog.show()
    }
    fun hideProgressBar(){
            mProgressDialog.dismiss()
    }
}