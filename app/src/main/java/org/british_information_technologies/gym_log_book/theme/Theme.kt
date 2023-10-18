package org.british_information_technologies.gym_log_book.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
	primary = Purple80,
	secondary = PurpleGrey80,
	tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
	primary = Purple40,
	secondary = PurpleGrey40,
	tertiary = Pink40
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Gym_Log_BookTheme(
	darkTheme: Boolean = isSystemInDarkTheme(),
	// Dynamic color is available on Android 12+
	dynamicColor: Boolean = true,
	colourNavBar: Boolean = false,
	content: @Composable () -> Unit,
) {
	val app = LocalContext.current.applicationContext
	val activity = LocalContext.current as Activity
	val window = activity.window

	WindowCompat.setDecorFitsSystemWindows(window, false)
	window.statusBarColor = Color.Transparent.toArgb()
	window.navigationBarColor = Color.Transparent.toArgb()
	WindowCompat.getInsetsController(
		window,
		window.decorView
	).isAppearanceLightStatusBars = !darkTheme
	WindowCompat.getInsetsController(
		window,
		window.decorView
	).isAppearanceLightNavigationBars = !darkTheme

	val colorScheme = when {
		dynamicColor -> {
			val context = LocalContext.current
			if (darkTheme) dynamicDarkColorScheme(
				context
			) else dynamicLightColorScheme(
				context
			)
		}

		darkTheme -> DarkColorScheme
		else -> LightColorScheme
	}

	MaterialTheme(
		colorScheme = colorScheme,
		typography = Typography,
		content = content
	)
}