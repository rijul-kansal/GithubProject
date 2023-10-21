package com.example.githubproject.Apis

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Constants {


    const val TITLE="title"
    const val CREATED_DATE="created_date"
    const val CLOSED_DATE="closed_date"
    const val USERNAME="username"
    const val IMAGE_URL="image_url"
    const val BASE_URL= "https://api.github.com/"
    const val OWNER="owner"
    const val REPO_NAME="repo_name"
    fun getInstance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}