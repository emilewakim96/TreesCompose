package com.example.treescompose.treeslist

import android.util.Log
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.treescompose.R
import com.example.treescompose.data.remote.responses.Tree
import com.example.treescompose.destinations.TreeDetailScreenDestination
import com.example.treescompose.util.pxToDp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(start = true)
@Composable
fun TreesListScreen(navigator: DestinationsNavigator) {
    val context = LocalContext.current
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Text(text = context.getString(R.string.trees_list),
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(start = 10.dp, top = 15.dp, bottom = 10.dp)
            )
            TreeList(navigator = navigator)
        }
    }
}

@Composable
fun TreeList(navigator: DestinationsNavigator,
             viewModel: TreeListViewModel = hiltViewModel()
) {
    val treesList = remember { viewModel.treesList }.value
    val loadError = remember { viewModel.loadError }.value
    val isLoading = remember { viewModel.isLoading }.value

    val navBarHeight = LocalContext.current.resources.getDimensionPixelSize(R.dimen.nav_bar_dimension).pxToDp()
    LazyColumn(modifier = Modifier.padding(bottom = navBarHeight.dp)) {
        items(treesList) { tree ->
            TreeCard(navigator = navigator, tree = tree)
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
fun TreeCard(navigator: DestinationsNavigator, tree: Tree) {
    val context = LocalContext.current
    Column(modifier = Modifier.clickable {
        navigator.navigate(TreeDetailScreenDestination(tree))
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
            color = MaterialTheme.colors.secondaryVariant,
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = context.getString(R.string.tree_circumference, tree.fields.circonferenceencm.toString()),
            color = MaterialTheme.colors.secondaryVariant
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = context.getString(R.string.tree_address, tree.fields.adresse),
            color = MaterialTheme.colors.secondaryVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
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