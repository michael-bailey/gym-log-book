package io.github.michael_bailey.gym_log_book.activity.amend_exercise_activity_v2

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import java.util.UUID

object AmendExerciseActivityV2IntentUtils {
	const val INTENT_ID = "id"
	fun startAmendActivity(ctx: Context, exerciseID: UUID): Intent =
		Intent(ctx, AmendExerciseActivityV2::class.java).apply {
			putExtra(INTENT_ID, exerciseID)
			flags = FLAG_ACTIVITY_NEW_TASK
			ctx.startActivity(this)
		}
}

