package org.british_information_technologies.gym_log_book.activity.onboarding_activity

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import io.github.michael_bailey.android_chat_kit.utils.page.PageNavigation
import org.british_information_technologies.gym_log_book.R

sealed class OnboardingActivityPage(
	route: String,
	@StringRes label: Int,
	icon: ImageVector
) : PageNavigation<OnboardingActivityViewModel>(
	route = route,
	label = label,
	icon = icon,
) {
	object StartPage : OnboardingActivityPage(
		route = "start_page",
		label = R.string.onboarding_start_page,
		icon = Icons.Outlined.Star
	) {
		override val pageFunction: @Composable (OnboardingActivityViewModel) -> Unit
			get() = { TODO("Not yet implemented") }
	}
}