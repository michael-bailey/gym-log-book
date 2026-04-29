package net.michael_bailey.gym_log_book.client.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun ClientTheme(
	content: @Composable () -> Unit,
) {
	val shapes = Shapes(
		extraSmall = RoundedCornerShape(4.dp),
		small = RoundedCornerShape(8.dp),
		medium = RoundedCornerShape(12.dp),
		large = RoundedCornerShape(18.dp),
		extraLarge = RoundedCornerShape(24.dp),
	)

	val typography = Typography().copy(
		titleLarge = TextStyle.Default.copy(),
		headlineSmall = TextStyle.Default.copy(),
		bodyLarge = TextStyle.Default.copy(),
		bodyMedium = TextStyle.Default.copy(),
		labelLarge = TextStyle.Default.copy(),
		labelSmall = TextStyle.Default.copy(),
	)

	val lightColourScheme = lightColorScheme(
		// Primary — cool green
		primary = Color(0xFF2E7D52),
		onPrimary = Color(0xFFFFFFFF),
		primaryContainer = Color(0xFFB7F1D1),
		onPrimaryContainer = Color(0xFF00210F),

		// Secondary — sage
		secondary = Color(0xFF52796F),
		onSecondary = Color(0xFFFFFFFF),
		secondaryContainer = Color(0xFFA8D5C2),
		onSecondaryContainer = Color(0xFF0A2920),

		// Tertiary — electric blue
		tertiary = Color(0xFF0060C7),
		onTertiary = Color(0xFFFFFFFF),
		tertiaryContainer = Color(0xFFD6E4FF),
		onTertiaryContainer = Color(0xFF001C45),

		// Background & Surface
		background = Color(0xFFF4FBF4),
		onBackground = Color(0xFF191C1A),
		surface = Color(0xFFF4FBF4),
		onSurface = Color(0xFF191C1A),
		surfaceVariant = Color(0xFFDCE5DC),
		onSurfaceVariant = Color(0xFF414941),
		outline = Color(0xFF717970),
	)

	val darkColourScheme = darkColorScheme(
		// Primary — cool green
		primary = Color(0xFF6DDBA0),
		onPrimary = Color(0xFF003920),
		primaryContainer = Color(0xFF1A5E3A),
		onPrimaryContainer = Color(0xFFB7F1D1),

		// Secondary — sage
		secondary = Color(0xFFA8D5C2),
		onSecondary = Color(0xFF0A2920),
		secondaryContainer = Color(0xFF2E5446),
		onSecondaryContainer = Color(0xFFC4EBD8),

		// Tertiary — electric blue
		tertiary = Color(0xFFABC7FF),
		onTertiary = Color(0xFF003070),
		tertiaryContainer = Color(0xFF00469A),
		onTertiaryContainer = Color(0xFFD6E4FF),

		// Background & Surface
		background = Color(0xFF111411),
		onBackground = Color(0xFFE1E9E1),
		surface = Color(0xFF111411),
		onSurface = Color(0xFFE1E9E1),
		surfaceVariant = Color(0xFF414941),
		onSurfaceVariant = Color(0xFFC0C9C0),
		outline = Color(0xFF8A9389),
	)

	MaterialTheme(
		colorScheme = lightColourScheme,
		shapes = shapes,
		typography = typography,
		content = content,
	)
}


