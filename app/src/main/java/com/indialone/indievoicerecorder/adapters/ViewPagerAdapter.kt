package com.indialone.indievoicerecorder.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(
    private val fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager) {

    private val fragments = ArrayList<Fragment>()
    private val fragmentTitles = ArrayList<String>()

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragments.add(fragment)
        fragmentTitles.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitles[position]
    }
}