package com.example.treescompose.util

import com.example.treescompose.R
import com.example.treescompose.destinations.Destination
import com.example.treescompose.destinations.GithubRepositoriesScreenDestination
import com.example.treescompose.destinations.TreesListScreenDestination

sealed class BottomNavItem(var title: String, var icon: Int, var destination: Destination) {
    object Home : BottomNavItem(
        title = "Home",
        icon = R.drawable.ic_home,
        destination = TreesListScreenDestination
    )
    object Other: BottomNavItem(
        title = "Other",
        icon = R.drawable.ic_bookmark,
        destination = GithubRepositoriesScreenDestination
    )
}
