# YemenFlex Android

This is the Android version of YemenFlex, an entertainment platform for movies and TV series.

## Features
- **Home Screen**: Discover trending, latest movies, and series via TMDB API.
- **Search Screen**: Search for your favorite movies and TV shows.
- **Watchlist**: Save movies to your local or cloud watchlist using Firebase Firestore.
- **Modern UI**: Built natively with Kotlin and Jetpack Compose following Material Design 3 guidelines.

## Setup
1. Define your TMDB API key in the `.env` file via the AI Studio Secrets Panel:
   `TMDB_ACCESS_TOKEN=your_token`
2. Run the application on your Android emulator or device.

## Architecture
- **Language**: Kotlin 2.0+
- **UI Toolkit**: Jetpack Compose
- **Networking**: Retrofit & Kotlinx Serialization
- **Image Loading**: Coil
- **Backend/Database**: Firebase Firestore
