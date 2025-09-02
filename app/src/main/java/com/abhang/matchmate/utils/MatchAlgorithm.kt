package com.abhang.matchmate.utils

import com.abhang.matchmate.data.local.model.UserData

object MatchAlgorithm {
    fun match(selfUser: UserData, newUser:UserData): Int{
        var total = 0

        when{
            selfUser.age.toInt() <= newUser.age.toInt()+2 || selfUser.age.toInt() >=newUser.age.toInt()+2 -> total+=20
            selfUser.address.split(",").first() == newUser.address.split(",").first() -> total+=20
            selfUser.address.split(",")[1] == newUser.address.split(",")[1] -> total+=20
            selfUser.address.split(",")[2] == newUser.address.split(",")[2] -> total+=20
            selfUser.nat == newUser.nat-> total+=20
            else ->{}
        }

        return total
    }
}