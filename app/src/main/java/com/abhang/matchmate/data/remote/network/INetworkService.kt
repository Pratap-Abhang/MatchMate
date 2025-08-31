package com.abhang.matchmate.data.remote.network

import com.abhang.matchmate.data.remote.model.response.User
import com.abhang.matchmate.data.remote.model.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface INetworkService {

    @GET
    suspend fun getUserData(@Query("results") results: Int) : UserResponse




}