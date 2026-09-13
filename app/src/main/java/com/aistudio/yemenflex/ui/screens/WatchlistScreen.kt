package com.aistudio.yemenflex.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.aistudio.yemenflex.data.api.MediaItem
import com.aistudio.yemenflex.ui.viewmodels.MainViewModel

@Composable
fun WatchlistScreen(
    viewModel: MainViewModel,
    onNavigateToDetails: (MediaItem) -> Unit
) {
    val watchlist by viewModel.watchlist.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "My Watchlist",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        if (watchlist.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Your watchlist is empty.", color = Color.Gray)
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(watchlist) { item ->
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
}
