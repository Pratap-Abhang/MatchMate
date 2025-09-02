package com.abhang.matchmate.utils

import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallBack<T>: DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T & Any, newItem: T & Any): Boolean {
        return oldItem==newItem
    }

    override fun areContentsTheSame(oldItem: T & Any, newItem: T & Any): Boolean {
        return oldItem == newItem
    }

}