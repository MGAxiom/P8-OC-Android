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

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AllTabFragment.newInstance("Tous")
            1 -> FavoritesTabFragment.newInstance("Favoris")
            else -> throw IllegalArgumentException("Invalid position $position")
        }
    }

    fun getFragment(position: Int): TabFragment? {
        return try {
            createFragment(position) as? TabFragment
        } catch (e: Exception) {
            null
        }
    }
}