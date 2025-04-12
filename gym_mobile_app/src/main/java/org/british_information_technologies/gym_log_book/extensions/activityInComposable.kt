package org.british_information_technologies.gym_log_book.extensions

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
inline fun <reified Ta : Activity> activity() = (LocalContext.current as Ta)
