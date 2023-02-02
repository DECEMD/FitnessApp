package com.fitnesskit.fitnessappsample.presentation.main

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.fitnesskit.fitnessappsample.R
import com.fitnesskit.fitnessappsample.databinding.ActivityMainBinding
import com.fitnesskit.fitnessappsample.presentation.fragments.splashfragment.ReplaceSplashCallback
import com.fitnesskit.fitnessappsample.presentation.fragments.splashfragment.SplashFragment
import com.fitnesskit.fitnessappsample.presentation.fragments.homescreenfragment.HomeScreenFragment
import com.fitnesskit.fitnessappsample.presentation.fragments.navigation.Navigator

open class MainActivity: AppCompatActivity(), Navigator, ReplaceSplashCallback {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, SplashFragment())
                .commit()
        } else {
            showBottomNavPanel()
        }
        initBottomNavigationView()
    }

    private fun initBottomNavigationView() {
        binding.bottomNav.setOnItemSelectedListener { item ->
            item.avoidMultipleClicks()
            when (item.itemId) {
                R.id.id_home -> {
                    launchHomeFragment()
                    true
                }
                R.id.id_requests -> {
                    makeToast(getString(R.string.this_module_still_running_flow))
                    true
                }
                R.id.id_add_new -> {
                    makeToast(getString(R.string.this_module_still_running_flow))
                    true
                }
                R.id.id_chat -> {
                    makeToast(getString(R.string.this_module_still_running_flow))
                    true
                }
                R.id.id_more -> {
                    makeToast(getString(R.string.this_module_still_running_flow))
                    true
                }
                else -> {
                    launchHomeFragment()
                    true
                }
            }
        }
    }

    override fun launchHomeFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeScreenFragment.newInstance())
            .commitAllowingStateLoss()
    }

    private fun showBottomNavPanel() = with(binding){
        val slideUp = AnimationUtils.loadAnimation(this@MainActivity, R.anim.slide_up)
        if (bottomNav.visibility == View.GONE) {
            bottomNav.visibility = View.VISIBLE
            bottomNav.startAnimation(slideUp)
            binding.toolbarTitle.text = getString(R.string.schedule)
        }
    }

    override fun replaceSplash() {
        supportFragmentManager.beginTransaction().apply {
            this.setCustomAnimations(R.animator.enter_anim, R.animator.exit_anim)
            this.replace(R.id.fragment_container, HomeScreenFragment.newInstance()).commit()
            showBottomNavPanel()
        }
    }


}