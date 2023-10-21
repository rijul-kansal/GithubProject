package com.example.githubproject.Apis

import com.example.githubproject.Model.MainModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("repos/{owner}/{repo}/issues?state=closed")
    suspend fun getSeries(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Response<MainModel>
}