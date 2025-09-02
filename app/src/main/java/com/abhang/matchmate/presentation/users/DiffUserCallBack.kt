package com.abhang.matchmate.presentation.users

import androidx.recyclerview.widget.DiffUtil
import com.abhang.matchmate.data.local.model.UserData

class DiffUserCallBack: DiffUtil.ItemCallback<UserData>() {
    override fun areItemsTheSame(oldItem: UserData, newItem: UserData): Boolean {
        return oldItem==newItem
    }

    override fun areContentsTheSame(oldItem: UserData, newItem: UserData): Boolean {
        return oldItem.userId == newItem.userId
    }

}