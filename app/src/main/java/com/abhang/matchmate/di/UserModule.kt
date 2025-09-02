package com.abhang.matchmate.di


import android.content.Context
import com.abhang.matchmate.data.remote.network.INetworkService
import com.abhang.matchmate.data.remote.repository.UserDataRepositoryImpl
import com.abhang.matchmate.domain.repository.UserDataLocalRepository
import com.abhang.matchmate.domain.repository.UserDataRepository
import com.abhang.matchmate.utils.Constants
import com.abhang.matchmate.utils.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Provides
    @Singleton
    fun provideNetworkAPI(): INetworkService {
        return Retrofit.Builder()
            .baseUrl(Constants.NETWORK.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(INetworkService::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkHelper(@ApplicationContext context: Context): NetworkHelper {
        return NetworkHelper(context)
    }
    @Provides
    @Singleton
    fun provideUserRepository(
        api: INetworkService,
        localRepository: UserDataLocalRepository,
        networkHelper: NetworkHelper
    ): UserDataRepository {
        return UserDataRepositoryImpl(api, localRepository, networkHelper)
    }



}