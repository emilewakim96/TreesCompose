package com.example.treescompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.treescompose.data.remote.responses.Tree
import com.example.treescompose.treedetail.TreeDetailScreen
import com.example.treescompose.treeslist.TreesListScreen
import com.example.treescompose.ui.theme.TreesComposeTheme
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TreesComposeTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "tree_list_screen") {
                    composable("tree_list_screen") {
                        TreesListScreen(navController = navController)
                    }
                    composable("tree_detail_screen/{tree}",
                        arguments = listOf(
                            navArgument("tree") {
                                type = NavType.StringType
                            }
                        ))
                    {
                        val tree = remember {
                            it.arguments?.getString("tree")
                        }
                        val treeObject = Gson().fromJson(tree, Tree::class.java)
                        TreeDetailScreen(treeObject, navController = navController)
                    }
                }
            }
        }
    }
}