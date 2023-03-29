package io.github.michael_bailey.gym_log_book.extension.component_activity

import android.app.Application
import io.github.michael_bailey.gym_log_book.extension.application.preferences

inline fun Application.isDebugEnabled() = preferences().let {
	it.getBoolean("show_debug_nav_bar", false)
}

inline fun Application.setIsDebugEnabled(value: Boolean) = preferences().let {
	it.edit().putBoolean("show_debug_nav_bar", value).apply()
}