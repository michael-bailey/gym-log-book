package org.british_information_technologies.gym_log_book.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
inline fun <reified Ta> activity() = (LocalContext.current as Ta)
