package com.example.githubproject.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.githubproject.Apis.Constants
import com.example.githubproject.Model.ShownModel
import com.example.githubproject.R
import com.example.githubproject.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    lateinit var item:ShownModel
    lateinit var binding:ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val item1 = intent.getStringExtra(Constants.TITLE)
        val item2 = intent.getStringExtra(Constants.CREATED_DATE)
        val item3 = intent.getStringExtra(Constants.CLOSED_DATE)
        val item4 = intent.getStringExtra(Constants.USERNAME)
        val item5=intent.getStringExtra(Constants.IMAGE_URL)
        item= ShownModel(item1!!,item2!!,item3!!,item4!!,item5!!)
        if (item != null) {
            SetUpToolbar()
            try {
                Glide
                    .with(this)
                    .load(item.userImageUrl)
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(binding.userImage)
            }catch (e:Exception)
            {
                Log.d("rk","image error + ${e.message.toString()}")
            }

            binding.createdOn.text="Created on: "+item.createdDate.substring(0,10)
            binding.username.text=item.username
            binding.closedOn.text="Closed on: "+item.closedDate.substring(0,10)

        } else {
            Log.d("rk", "Error: Unable to retrieve ShownModel")
        }
    }

    private fun SetUpToolbar()
    {
        setSupportActionBar(binding.toolbar)
        if (supportActionBar != null) {
            // displaying arrow
            getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
            getSupportActionBar()?.setDisplayShowHomeEnabled(true)
            // custom arrow design
            // setting title for tolbar
            supportActionBar!!.title=item.title
        }
        // back navigation when we click back btn present on toolbar
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}