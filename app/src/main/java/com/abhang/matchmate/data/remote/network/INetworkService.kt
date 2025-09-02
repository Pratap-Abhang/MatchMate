package com.abhang.matchmate.data.remote.network

import com.abhang.matchmate.data.remote.model.response.UserResponse
import com.abhang.matchmate.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface INetworkService {

    @GET(Constants.NETWORK.GET_DATA)
    suspend fun getUserData(@Query("results") results: Int,
                            @Query("page") page: Int,
                            @Query("gender") gender: String) : UserResponse




}