package com.github.gitty.ui.activity

import androidx.activity.viewModels
import com.github.gitty.R
import com.github.gitty.databinding.ActivityMainBinding
import com.github.gitty.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override val viewModel: MainViewModel by viewModels()

    override fun initLayout() {
    }

}