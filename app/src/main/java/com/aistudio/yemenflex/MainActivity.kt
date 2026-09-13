package com.aistudio.yemenflex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.aistudio.yemenflex.data.api.MediaItem
import com.aistudio.yemenflex.ui.screens.DetailsScreen
import com.aistudio.yemenflex.ui.screens.HomeScreen
import com.aistudio.yemenflex.ui.screens.SearchScreen
import com.aistudio.yemenflex.ui.screens.WatchlistScreen
import com.aistudio.yemenflex.ui.theme.YemenFlexTheme
import com.aistudio.yemenflex.ui.viewmodels.MainViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YemenFlexTheme {
                MainScreen(viewModel)
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val navController = rememberNavController()
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Home", "Search", "Watchlist")
    val icons = listOf(Icons.Default.Home, Icons.Default.Search, Icons.Default.Bookmark)
    val routes = listOf("home", "search", "watchlist")
    
    // To handle detail screen state, we can pass it via navigation or keep it in a state
    var selectedMedia by remember { mutableStateOf<MediaItem?>(null) }

    if (selectedMedia != null) {
        DetailsScreen(
            item = selectedMedia!!,
            viewModel = viewModel,
            onBack = { selectedMedia = null }
        )
    } else {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary
                ) {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            icon = { Icon(icons[index], contentDescription = item) },
                            label = { Text(item) },
                            selected = selectedItem == index,
                            onClick = {
                                selectedItem = index
                                navController.navigate(routes[index]) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("home") {
                    HomeScreen(viewModel = viewModel) {
                        selectedMedia = it
                    }
                }
                composable("search") {
                    SearchScreen(viewModel = viewModel) {
                        selectedMedia = it
                    }
                }
                composable("watchlist") {
                    WatchlistScreen(viewModel = viewModel) {
                        selectedMedia = it
                    }
                }
            }
        }
    }
}
