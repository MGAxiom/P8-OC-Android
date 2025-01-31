package com.example.vitesseapp.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

import com.example.vitesseapp.ui.fragments.FavoritesTabFragment
import com.example.vitesseapp.ui.fragments.AllTabFragment
import com.example.vitesseapp.ui.fragments.TabFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    private val fragments: List<Fragment> = listOf(
        AllTabFragment(),
        FavoritesTabFragment()
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]

    fun getFragment(position: Int): TabFragment = fragments[position] as TabFragment
}