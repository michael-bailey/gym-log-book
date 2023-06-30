package io.github.michael_bailey.gym_log_book.theme

import android.app.Activity
import android.app.Application
import android.os.Build
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import com.google.android.material.elevation.SurfaceColors
import io.github.michael_bailey.gym_log_book.app.App
import io.github.michael_bailey.gym_log_book.lib.DebugPreferencesManager

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
	scrollState: ScrollState? = null
) {
	val app = LocalContext.current.applicationContext
	val activity = LocalContext.current as Activity
	val window = activity.window
	val view = LocalView.current
	val colorScheme = when {
		dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
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
	
	val appDebugPreferencesManager = DebugPreferencesManager(app as Application)

	if (app is App) {
		val debugStatusBarEnabled by appDebugPreferencesManager
			.isDebugStatusBarColourEnabled.observeAsState()

		val debugNavBarEnabled by appDebugPreferencesManager
			.isDebugNavBarColourEnabled.observeAsState()

		LaunchedEffect(debugNavBarEnabled, debugStatusBarEnabled) {
			(view.context as Activity).window.statusBarColor =
				if (debugStatusBarEnabled == true) {
					Color.Red.toArgb()
				} else {
					colorScheme.background.toArgb()
				}

			(view.context as Activity).window.navigationBarColor =
				if (debugNavBarEnabled == true) {
					Color.Red.toArgb()
				} else if (colourNavBar) {
					SurfaceColors.SURFACE_2.getColor(view.context)
				} else {
					colorScheme.surface.toArgb()
				}
		}
	}

	if (!view.isInEditMode) {
		SideEffect {
			ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars =
				!darkTheme
		}
	}

	MaterialTheme(
		colorScheme = colorScheme,
		typography = Typography,
		content = content
	)
}