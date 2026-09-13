package com.aistudio.yemenflex.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
fun HomeScreen(
    viewModel: MainViewModel,
    onNavigateToDetails: (MediaItem) -> Unit
) {
    val trending by viewModel.trending.collectAsState()
    val movies by viewModel.movies.collectAsState()
    val series by viewModel.series.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    if (isLoading && trending.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 80.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            SectionTitle("Trending Now")
            MediaRow(items = trending, onClick = onNavigateToDetails)
            
            Spacer(modifier = Modifier.height(24.dp))
            SectionTitle("Latest Movies")
            MediaRow(items = movies, onClick = onNavigateToDetails)
            
            Spacer(modifier = Modifier.height(24.dp))
            SectionTitle("Latest Series")
            MediaRow(items = series, onClick = onNavigateToDetails)
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        color = Color.White,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
fun MediaRow(items: List<MediaItem>, onClick: (MediaItem) -> Unit) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items) { item ->
            MediaCard(item = item, onClick = { onClick(item) })
        }
    }
}

@Composable
fun MediaCard(item: MediaItem, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(120.dp)
            .clickable(onClick = onClick)
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
            maxLines = 1,
            fontWeight = FontWeight.SemiBold
        )
    }
}
