package com.example.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


private const val ARG_PARAM1 = "param1"

class DetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var mailId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mailId = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vpAdapter = ViewPagerAdapter(this, mailId!!)
        val viewPager = view.findViewById<ViewPager2>(R.id.viewpager)
        viewPager.adapter = vpAdapter
        val tab = view.findViewById<TabLayout>(R.id.tablayout)
        TabLayoutMediator(tab, viewPager) { tab, position ->
            tab.text = if(position == 0) "Text" else "Image"
        }.attach()
    }

    companion object {
        @JvmStatic
        fun newInstance(mailId: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, mailId)
                }
            }
    }
}