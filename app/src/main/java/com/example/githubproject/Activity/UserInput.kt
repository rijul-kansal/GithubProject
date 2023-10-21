package com.example.githubproject.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.githubproject.Apis.Constants
import com.example.githubproject.R
import com.example.githubproject.databinding.ActivityUserInputBinding

class UserInput : AppCompatActivity() {
    lateinit var binding:ActivityUserInputBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityUserInputBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
       binding.start.setOnClickListener {
            callfn()
       }
    }

    private fun callfn() {
        var username=binding.usernameEt.text.toString()
        var reponame=binding.repoEt.text.toString()

       if(username.isNotEmpty() && reponame.isNotEmpty())
       {
           val intent= Intent(this@UserInput,MainActivity::class.java)
           intent.putExtra(Constants.REPO_NAME,reponame)
           intent.putExtra(Constants.OWNER,username)
           startActivity(intent)
       }else if (username.isEmpty())
       {
           Toast.makeText(this@UserInput,"Please enter owner name",Toast.LENGTH_LONG).show()
       }
        else
       {
           Toast.makeText(this@UserInput,"Please enter repo name",Toast.LENGTH_LONG).show()
       }
    }
}