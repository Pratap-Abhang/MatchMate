package com.abhang.matchmate.presentation.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abhang.matchmate.data.local.model.UserData
import com.abhang.matchmate.databinding.UserItemCardBinding
import com.abhang.matchmate.utils.Constants
import com.abhang.matchmate.utils.MatchAlgorithm
import com.bumptech.glide.Glide

class UserAdapter(
    private val clickCallback: (id: String, isAccepted: Boolean) -> Unit
) : ListAdapter<UserData, UserAdapter.ViewHolder>(DiffUserCallBack()) {

    class ViewHolder(private val binding: UserItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userData: UserData, onClickListener: (id: String, isAccepted: Boolean) -> Unit){
            val context = binding.root.context

            with(userData) {
                binding.txtName.text = "$name ($age)"
                binding.txtEmail.text = email
                binding.txtMobile.text = phone
                Glide.with(context)
                    .load(profileLogo)
                    .centerCrop()
                    .into(binding.profileImg)
                binding.btnAccept.setOnClickListener { onClickListener(userId, true) }
                binding.btnDeny.setOnClickListener { onClickListener(userId, false) }
                binding.txtNationality.text = nat
            }
            binding.txtMatch.text = "${MatchAlgorithm.match(Constants.currentuser, userData).toString()}%"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = UserItemCardBinding.inflate(inflater, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userData = getItem(position), clickCallback)
    }

}