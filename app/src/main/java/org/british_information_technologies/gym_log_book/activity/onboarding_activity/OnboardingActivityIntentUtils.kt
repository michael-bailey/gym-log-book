package org.british_information_technologies.gym_log_book.activity.onboarding_activity

import android.content.Context
import android.content.Intent

object OnboardingActivityIntentUtils {
	fun startActivity(context: Context) {
		context.startActivity(Intent(context, OnboardingActivity::class.java))
	}
}