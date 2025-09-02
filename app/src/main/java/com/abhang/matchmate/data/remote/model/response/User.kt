package com.abhang.matchmate.data.remote.model.response

import com.abhang.matchmate.data.local.model.UserData
import com.abhang.matchmate.utils.StatusEnum

data class User(
    val name: Name,
    val email: String,
    val gender: String,
    val location: Location,
    val dob : DOB,
    val phone: String,
    val picture: Picture,
    val login: LoginData,
    val nat: String
) {
    fun toUserData(): UserData {
        return UserData(
            login.uuid,
            "${name.title} ${name.first} ${name.last}",
            email,
            gender,
            "${location.city}, ${location.state}, ${location.country}",
            dob.date.split("T").first(),
            phone,
            picture.medium,
            StatusEnum.NEW,
            dob.age,
            nat
        )
    }
}

data class LoginData (
    val uuid: String
)

data class Name(
    val title: String,
    val first: String,
    val last: String
)

data class Location(
    val city: String,
    val state: String,
    val country: String,
    val postcode: String
)

data class DOB(
    val date: String,
    val age: String
)

data class Picture(
    val large: String,
    val medium: String,
    val thumbnail: String
)