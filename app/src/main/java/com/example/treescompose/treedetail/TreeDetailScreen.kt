package com.example.treescompose.treedetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.treescompose.R
import com.example.treescompose.data.remote.responses.Tree
import com.example.treescompose.util.TreesTransitions
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(route = "trees_screen/details", style = TreesTransitions::class)
@Composable
fun TreeDetailScreen(navigator: DestinationsNavigator, tree: Tree) {
    val context = LocalContext.current
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Row(modifier = Modifier.padding(start = 10.dp, top = 15.dp)) {
                Text(text = context.getString(R.string.tree_detail),
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colors.onSurface)
                Row(horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth().padding(end = 60.dp)) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .padding(top = (7.5).dp)
                            .align(Alignment.CenterVertically)
                            .size(30.dp)
                            .clickable {
                                navigator.popBackStack()
                            }
                    )
                }
            }
            Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier.padding(top = 15.dp))
            TreeDetailsCard(tree)
        }
    }
}

@Composable
fun TreeDetailsCard(tree: Tree) {
    val context = LocalContext.current
    Card(
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column {
            Text(
                text = context.getString(R.string.tree_name, tree.fields.libellefrancais),
                color = MaterialTheme.colors.secondaryVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, top = 15.dp)
            )
            Text(
                text = context.getString(R.string.tree_type, tree.fields.espece),
                color = MaterialTheme.colors.secondaryVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, top = 10.dp)
            )
            Text(
                text = context.getString(R.string.tree_height, tree.fields.hauteurenm.toString()),
                color = MaterialTheme.colors.secondaryVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, top = 10.dp)
            )
            Text(
                text = context.getString(
                    R.string.tree_circumference,
                    tree.fields.circonferenceencm.toString()
                ),
                color = MaterialTheme.colors.secondaryVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, top = 10.dp)
            )
            Text(
                text = context.getString(R.string.tree_address, tree.fields.adresse),
                color = MaterialTheme.colors.secondaryVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, top = 10.dp)
            )
            val painter =
                rememberImagePainter(data = tree.fields.image ?: "https://images.unsplash.com/photo-1628373383885-4be0bc0172fa?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1301&q=80")
            Image(
                painter = painter,
                contentDescription = "Forest Image",
                modifier = Modifier.size(100.dp).align(Alignment.CenterHorizontally)
                    .padding(start = 10.dp, top = 10.dp, bottom = 15.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}