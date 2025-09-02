package com.abhang.matchmate.presentation.users

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abhang.matchmate.R
import com.abhang.matchmate.data.local.model.UserData
import com.abhang.matchmate.databinding.ActivityMatchBinding
import com.abhang.matchmate.utils.Constants
import com.abhang.matchmate.utils.StatusEnum
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MatchActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMatchBinding
    private lateinit var mAdapter: UserAdapter
    private var pageNumber: Int = 0
    private var mList : ArrayList<UserData> = ArrayList()
    private val userViewmodel : UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMatchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initView()
    }

    private fun initView(){
        setupTabLayout()
        setupRecyclerview()
        scrollHandlingPagination()
        observer()
        getUsersData(pageNumber)
    }

    private fun setupTabLayout() {
        (binding.tabLayout as TabLayout).apply {
            addTab(binding.tabLayout.newTab().apply {
                id = 1
                text = "All"
            })
            addTab(binding.tabLayout.newTab().apply {
                id = 2
                text = "Approved"
            })
            addTab(binding.tabLayout.newTab().apply {
                id = 3
                text = "Denied"
            })
        }
        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(p0: TabLayout.Tab?) {
                when(p0?.id){
                    1->{
                        pageNumber = 0
                        getUsersData(pageNumber)}
                    2->{ userViewmodel.getUserDataBaseOnStatus("ACCEPTED", pageNumber) }
                    3->{ userViewmodel.getUserDataBaseOnStatus("DENIED", pageNumber) }
                }
            }
            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabReselected(p0: TabLayout.Tab?) {}

        })
    }

    private fun getUsersData(pageNo: Int){
        val interestedGender = if(Constants.currentuser.gender == "male") "female" else "male"
        userViewmodel.getUserData(pageNo, interestedGender)
    }

    private fun observer(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                userViewmodel.userValue.collectLatest { data ->

                    binding.progressBar.isVisible = data.isLoading
                    if(data.isLoading){
                        binding.progressBar.visibility = View.VISIBLE
                    } else {
                        binding.progressBar.visibility = View.GONE
                        if(data.data?.isNotEmpty() == true){
                            mList.addAll(data.data)
                            Log.e("COLLECTED_DATA: ", data.data.toString())
                            binding.noData.visibility = View.GONE
                            binding.recyclerView.visibility = View.VISIBLE

                            if(::mAdapter.isInitialized){
                                mAdapter.submitList(mList)
                            }
                        }else{
                            binding.noData.visibility = View.VISIBLE
                            binding.recyclerView.visibility = View.GONE
                        }
                    }
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                userViewmodel.userUpdateValue.collectLatest { data ->

                    binding.progressBar.isVisible = data.isLoading
                    if(data.isLoading){
                        binding.progressBar.visibility = View.VISIBLE
                    } else {
                        binding.progressBar.visibility = View.GONE
                        if(data.data!=0){
                            Toast.makeText(this@MatchActivity, "Done", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerview(){
        mAdapter = UserAdapter { id, isAccepted ->
            removeItem(id, isAccepted)
        }
        val cLayoutManager = LinearLayoutManager(this@MatchActivity, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.apply {
            layoutManager = cLayoutManager
            adapter = mAdapter
        }
    }

    private fun removeItem(userId: String, isAccepted: Boolean) {
        val newList = mAdapter.currentList.toMutableList().apply {
            removeAll { it.userId == userId }
        }
        mList = newList as ArrayList<UserData>
        mAdapter.submitList(mList)
        userViewmodel.updateUserStatus(userId, if(isAccepted) StatusEnum.ACCEPTED.value else StatusEnum.DENIED.value)
    }

    private fun scrollHandlingPagination(){
        binding.recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisible = layoutManager.findLastVisibleItemPosition()

                if (lastVisible >= mAdapter.currentList.size-3) {
                    pageNumber+=1
                    getUsersData(pageNumber)
                }
            }
        })
    }
}