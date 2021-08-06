package com.indialone.indievoicerecorder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.indialone.indievoicerecorder.adapters.ViewPagerAdapter
import com.indialone.indievoicerecorder.databinding.ActivityMainBinding
import com.indialone.indievoicerecorder.fragments.RecorderFragment
import com.indialone.indievoicerecorder.fragments.RecordingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setSupportActionBar(mBinding.toolbar)

        setUpViewPager(mBinding.viewpager)

        mBinding.tabLayout.setupWithViewPager(mBinding.viewpager)

    }

    private fun setUpViewPager(viewPager: ViewPager) {
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)

        viewPagerAdapter.addFragment(RecorderFragment(), "Recorder")
        viewPagerAdapter.addFragment(RecordingsFragment(), "Recordings")

        viewPager.adapter = viewPagerAdapter

    }

}