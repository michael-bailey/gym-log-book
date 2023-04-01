package io.github.michael_bailey.gym_log_book.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import com.google.android.material.elevation.SurfaceColors
import io.github.michael_bailey.gym_log_book.App
import io.github.michael_bailey.gym_log_book.extension.component_activity.isDebugNavBarColourEnabled
import io.github.michael_bailey.gym_log_book.extension.component_activity.isDebugStatusBarColourEnabled

private val DarkColorScheme = darkColorScheme(
	primary = Purple80,
	secondary = PurpleGrey80,
	tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
	primary = Purple40,
	secondary = PurpleGrey40,
	tertiary = Pink40

	/* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun Gym_Log_BookTheme(
	darkTheme: Boolean = isSystemInDarkTheme(),
	// Dynamic color is available on Android 12+
	dynamicColor: Boolean = true,
	colourNavBar: Boolean = false,
	content: @Composable () -> Unit
) {
	// debug colours and nav bar
	val app = LocalContext.current.applicationContext as App


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
	val view = LocalView.current
	if (!view.isInEditMode) {
		SideEffect {
			(view.context as Activity).window.statusBarColor =
				if (!app.isDebugStatusBarColourEnabled()) {
					colorScheme.background.toArgb()
				} else {
					Color.Red.toArgb()
				}
			(view.context as Activity).window.navigationBarColor =
				if (app.isDebugNavBarColourEnabled()) {
					Color.Red.toArgb()
				} else if (colourNavBar) {
					SurfaceColors.SURFACE_2.getColor(view.context)
				} else {
					colorScheme.surface.toArgb()
				}
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