package com.aistudio.yemenflex.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    item: MediaItem,
    viewModel: MainViewModel,
    onBack: () -> Unit
) {
    val watchlist by viewModel.watchlist.collectAsState()
    val isSaved = watchlist.any { it.id == item.id }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    navigationIconContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.toggleWatchlist(item) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = if (isSaved) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                    contentDescription = "Watchlist"
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = item.backdropUrl,
                contentDescription = item.displayTitle,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(Color.DarkGray)
            )
            
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = item.displayTitle,
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(text = item.displayDate, color = Color.Gray)
                    Text(text = "Rating: ${item.voteAverage ?: "N/A"}", color = Color.Gray)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = item.overview ?: "No description available.",
                    color = Color.LightGray,
                    fontSize = 16.sp,
                    lineHeight = 24.sp
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { /* TODO Open Video Player */ },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Watch Now", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
