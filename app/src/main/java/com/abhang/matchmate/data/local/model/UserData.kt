package com.abhang.matchmate.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abhang.matchmate.utils.Constants

@Entity(tableName = Constants.DATABASE.DB_TABLE_NAME)
data class UserData(
    val userId: String,
    val name: String,
    val email: String,
    val gender: String,
    val address: String,
    val dob: String,
    val phone: String,
    val profileLogo: String
) {

    @PrimaryKey(true)
    var pid : Long = 0

    override fun equals(other: Any?): Boolean {
        val obj: UserData = other as UserData
        return userId == obj.userId &&
        name == obj.name &&
        email == obj.email &&
        gender == obj.gender &&
        address == obj.address &&
        dob == obj.dob &&
        phone == obj.phone &&
        profileLogo == obj.profileLogo
    }

    override fun hashCode(): Int {
        var result = pid.hashCode()
        result = 31 * result + userId.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + gender.hashCode()
        result = 31 * result + address.hashCode()
        result = 31 * result + dob.hashCode()
        result = 31 * result + phone.hashCode()
        result = 31 * result + profileLogo.hashCode()
        return result
    }


}