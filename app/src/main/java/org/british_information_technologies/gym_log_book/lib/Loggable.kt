package org.british_information_technologies.gym_log_book.lib

import android.util.Log

abstract class Loggable {
	val TAG: String get() = this::class.java.name

	fun log(message: String) = Log.i(TAG, message)
}