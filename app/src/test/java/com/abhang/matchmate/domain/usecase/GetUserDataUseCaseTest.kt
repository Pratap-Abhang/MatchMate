package com.abhang.matchmate.domain.usecase

import com.abhang.matchmate.domain.repository.UserDataRepository
import com.abhang.matchmate.data.handler.ResponseState
import com.abhang.matchmate.data.local.model.UserData
import com.abhang.matchmate.utils.StatusEnum
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class GetUserDataUseCaseTest {


    private lateinit var repository: UserDataRepository
    private lateinit var getUserDataUseCase: GetUserDataUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        getUserDataUseCase = GetUserDataUseCase(repository)
    }

    @Test
    fun `invoke returns error state when repository returns error`() = runTest {
        val errorCode = 500
        val errorMessage = "Server Error"
        coEvery { repository.getUserData(any(), any(), any()) } returns ResponseState.Error(errorCode, errorMessage, null)

        val flow = getUserDataUseCase.invoke(10, 0, "male").toList()

        assert(flow[0] is ResponseState.Loading)
        assert(flow[1] is ResponseState.Error)
        assert((flow[1] as ResponseState.Error).message == errorMessage)

        coVerify { repository.getUserData(10, 0, "male") }
    }

    @Test
    fun `invoke returns success state when repository returns data`() = runTest {
        val dummyData = listOf(UserData(userId = "1",
            "Pratap",
                    "pratapabhangg1@gmail.com",
                    "male",
                    "Mumbai",
                    "13/12/1997",
                    "8767418076",
                    "",
            StatusEnum.NEW,
                    "28",
                    "IN"))
        coEvery { repository.getUserData(any(), any(), any()) } returns ResponseState.Success(
            dummyData
        )

        val flow = getUserDataUseCase.invoke(10, 0, "male").toList()

        assert(flow[0] is ResponseState.Loading)
        assert(flow[1] is ResponseState.Success)
        assert((flow[1] as ResponseState.Success).data == dummyData)

        coVerify { repository.getUserData(10, 0, "male") }
    }
}