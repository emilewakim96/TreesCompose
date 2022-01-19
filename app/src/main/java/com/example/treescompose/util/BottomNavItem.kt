package com.example.treescompose.util

import com.example.treescompose.R

sealed class BottomNavItem(var title: String, var icon: Int, var screen_route: String){
    object Home : BottomNavItem("Home", R.drawable.ic_home,"tree_list_screen")
    object Other: BottomNavItem("Other",R.drawable.ic_bookmark,"other")
}
