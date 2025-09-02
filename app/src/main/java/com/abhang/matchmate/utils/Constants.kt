package com.abhang.matchmate.utils

import com.abhang.matchmate.data.local.model.UserData

object Constants {

    object NETWORK{
        const val BASE_URL = "https://randomuser.me/"
        const val GET_DATA = "api/"
    }

    object DATABASE{
        const val DB_NAME = "MatchMate.db"
        const val DB_TABLE_NAME = "User"
    }

    val currentuser: UserData = UserData(
        userId = "p1",
        "Jon Doe",
        "pratapabhangg1@gmail.com",
        "male",
        "Mumbai, Maharashtra, India",
        "13/12/1997",
        "8767418076",
        "",
        StatusEnum.NEW,
        "34",
        "IN"
    )

}