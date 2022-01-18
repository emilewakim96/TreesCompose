package com.example.treescompose.treeslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.treescompose.R
import com.example.treescompose.data.remote.responses.Tree
import com.google.gson.Gson

@Composable
fun TreesListScreen(navController: NavController) {
    val context = LocalContext.current
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Spacer(modifier = Modifier.height(15.dp))
            Text(text = context.getString(R.string.trees_list),
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onSurface)
            Spacer(modifier = Modifier.height(10.dp))
            TreeList(navController = navController)
        }
    }
}

@Composable
fun TreeList(navController: NavController,
             viewModel: TreeListViewModel = hiltViewModel()
) {
    val treesList = remember { viewModel.treesList }.value
    val loadError = remember { viewModel.loadError }.value
    val isLoading = remember { viewModel.isLoading }.value

    LazyColumn {
        items(treesList) { tree ->
            TreeCard(tree, navController = navController)
            Spacer(modifier = Modifier.height(5.dp))
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if(isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
        }
        if(loadError.isNotEmpty()) {
            RetrySection(error = loadError) {
                viewModel.loadTreeList()
            }
        }
    }
}

@Composable
fun TreeCard(tree: Tree, navController: NavController) {
    val context = LocalContext.current
    fun navigateToTreeInfo(tree: Tree) {
        val treeJsonString = Gson().toJson(tree)
        navController.navigate(
            "tree_detail_screen/${treeJsonString}"
        )
    }
    Column(modifier = Modifier.clickable {
        navigateToTreeInfo(tree)
    }) {
        Divider(color = Color.Black, thickness = 1.dp)
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = context.getString(R.string.tree_name, tree.fields.libellefrancais),
            color = MaterialTheme.colors.secondaryVariant
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = context.getString(R.string.tree_type, tree.fields.espece),
            color = MaterialTheme.colors.secondaryVariant
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = context.getString(R.string.tree_height, tree.fields.hauteurenm.toString()),
            color = MaterialTheme.colors.secondaryVariant
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = context.getString(R.string.tree_circumference, tree.fields.circonferenceencm.toString()),
            color = MaterialTheme.colors.secondaryVariant
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = context.getString(R.string.tree_address, tree.fields.adresse),
            color = MaterialTheme.colors.secondaryVariant
        )
        Spacer(modifier = Modifier.height(15.dp))
    }
}

@Composable
fun RetrySection(
    error: String,
    onRetry: () -> Unit
) {
    Column {
        Text(error, color = Color.Red, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Retry")
        }
    }
}