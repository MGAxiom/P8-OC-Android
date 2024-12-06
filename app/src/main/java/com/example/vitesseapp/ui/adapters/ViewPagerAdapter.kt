package com.example.vitesseapp.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

import com.example.vitesseapp.ui.fragments.FavorisTabFragment
import com.example.vitesseapp.ui.fragments.TousTabFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    private val fragments = listOf(
        TousTabFragment.newInstance("Tous"),
        FavorisTabFragment.newInstance("Favoris")
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}