package org.british_information_technologies.gym_log_book.lib.navigation

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController

// LocalElevations.kt file
// Define a CompositionLocal global object with a default
// This instance can be accessed by all composables in the app
val NavLocal = compositionLocalOf<NavHostController?> { null }