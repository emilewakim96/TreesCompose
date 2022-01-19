package com.example.treescompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.treescompose.data.remote.responses.Tree
import com.example.treescompose.treedetail.TreeDetailScreen
import com.example.treescompose.treeslist.TreesListScreen
import com.example.treescompose.ui.theme.TreesComposeTheme
import com.example.treescompose.util.BottomNavItem
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TreesComposeTheme {
                MainScreenView()
            }
        }
    }
}

@Composable
fun MainScreenView() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) }
    ) {
        NavigationGraph(navController = navController)
    }
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.screen_route) {
                    composable(BottomNavItem.Home.screen_route) {
                        TreesListScreen(navController = navController)
                    }
                    composable(BottomNavItem.Other.screen_route) {
                        Text(text = "Other tab",
                            fontWeight = FontWeight.Bold,
                            fontSize = 50.sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colors.onSurface)
                    }
                    composable("${BottomNavItem.Home.screen_route}/tree_detail_screen/{tree}",
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

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Other
    )
    BottomNavigation(
        backgroundColor = colorResource(id = R.color.white),
        contentColor = Color.Black
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = { Text(text = item.title,
                    fontSize = 9.sp) },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute?.contains(item.screen_route) ?: false,
                onClick = {
                    navController.navigate(item.screen_route) {
                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}