package com.aistudio.yemenflex.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.aistudio.yemenflex.data.api.MediaItem
import com.aistudio.yemenflex.ui.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: MainViewModel,
    onNavigateToDetails: (MediaItem) -> Unit
) {
    var query by remember { mutableStateOf("") }
    val searchResults by viewModel.searchResults.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = query,
            onValueChange = { 
                query = it
                viewModel.search(it)
            },
            placeholder = { Text("Search movies or TV series...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = Color.DarkGray
            )
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(searchResults) { item ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigateToDetails(item) }
                ) {
                    AsyncImage(
                        model = item.posterUrl,
                        contentDescription = item.displayTitle,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(2f / 3f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.DarkGray)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = item.displayTitle,
                        color = Color.White,
                        fontSize = 12.sp,
                        maxLines = 1
                    )
                }
            }
        }
    }
}
