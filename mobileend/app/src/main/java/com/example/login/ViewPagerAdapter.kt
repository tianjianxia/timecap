package com.example.login

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragment : Fragment, mailId: String): FragmentStateAdapter(fragment) {
    var id : String? = null

    init {
        id = mailId
    }

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val frag = PagerFragment.newInstance(id!!, position + 1)
        return frag
    }
}