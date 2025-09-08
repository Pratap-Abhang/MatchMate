package com.abhang.matchmate.data.remote.repository

import com.abhang.matchmate.data.handler.ResponseState
import com.abhang.matchmate.data.local.model.UserData
import com.abhang.matchmate.data.remote.model.response.DOB
import com.abhang.matchmate.data.remote.model.response.Location
import com.abhang.matchmate.data.remote.model.response.LoginData
import com.abhang.matchmate.data.remote.model.response.Name
import com.abhang.matchmate.data.remote.model.response.Picture
import com.abhang.matchmate.data.remote.model.response.User
import com.abhang.matchmate.data.remote.model.response.UserResponse
import com.abhang.matchmate.data.remote.network.INetworkService
import com.abhang.matchmate.domain.repository.UserDataLocalRepository
import com.abhang.matchmate.utils.StatusEnum
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.bouncycastle.util.test.SimpleTest.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class UserDataRepositoryImplTest {

    private lateinit var networkApi: INetworkService
    private lateinit var localRepository: UserDataLocalRepository
    private lateinit var repository: UserDataRepositoryImpl

    @BeforeEach
    fun setUp() {
        networkApi = mockk()
        localRepository = mockk()
        repository = UserDataRepositoryImpl(networkApi, localRepository)
    }

    @Test
    fun `returns local data when local data size equals page size`() = runTest {
        val localData = List(10) { UserData(userId = "1",
            "Pratap",
            "pratapabhangg1@gmail.com",
            "male",
            "Mumbai",
            "13/12/1997",
            "8767418076",
            "",
            StatusEnum.NEW,
            "28",
            "IN") }
        coEvery { localRepository.getUserData(any(), any()) } returns localData

        val result = repository.getUserData(pageSize = 10, pageNo = 0, gender = "male")

        assert(result is ResponseState.Success)
        assert((result as ResponseState.Success).data == localData)

        coVerify(exactly = 1) { localRepository.getUserData(10, 0) }
        coVerify(exactly = 0) { networkApi.getUserData(any(), any(), any()) }
    }

//    @Test
//    fun `calls API and saves data when local data is insufficient`() = runTest {
//        val localData = List(5) { UserData(userId = "1",
//            "Pratap",
//            "pratapabhangg1@gmail.com",
//            "male",
//            "Mumbai",
//            "13/12/1997",
//            "8767418076",
//            "",
//            StatusEnum.NEW,
//            "28",
//            "IN") }
//        val apiUserDtos = List(5) { User(
//            Name("Mr", "Pratap", "Abhang"),
//            "pratap@gmail.com",
//            "male",
//            Location("Mumbai", "Maharashtra", "India", "400022"),
//            DOB("13/12/1997", "28"),
//            "8767418076",
//            Picture("", "", ""),
//            LoginData("001"),
//            "IN" )
//        }
//        val apiResponse = UserResponse(results = apiUserDtos)
//        val apiRetrofitResponse = Response.success(apiResponse)
//
//        coEvery { localRepository.getUserData(any(), any()) } returnsMany listOf(localData, localData + listOf(UserData(userId = "1",
//            "Pratap",
//            "pratapabhangg1@gmail.com",
//            "male",
//            "Mumbai",
//            "13/12/1997",
//            "8767418076",
//            "",
//            StatusEnum.NEW,
//            "28",
//            "IN")))
//        coEvery { networkApi.getUserData(any(), any(), any()) } returns apiRetrofitResponse
//        coEvery { localRepository.addUsers(any()) }
//
//        val result = repository.getUserData(pageSize = 10, pageNo = 0, gender = "male")
//
//        assert(result is ResponseState.Success)
//        coVerify { networkApi.getUserData(5, 0, "male") }
//        coVerify { localRepository.addUsers(any()) }
//    }

    @Test
    fun `returns error when API returns failure`() = runTest {
        val localData = emptyList<UserData>()
        val errorResponse = Response.error<UserResponse>(500, mockk(relaxed = true))

        coEvery { localRepository.getUserData(any(), any()) } returns localData
        coEvery { networkApi.getUserData(any(), any(), any()) } returns errorResponse

        val result = repository.getUserData(pageSize = 10, pageNo = 0, gender = "male")

        assert(result is ResponseState.Error)

        coVerify { networkApi.getUserData(10, 0, "male") }
    }

}