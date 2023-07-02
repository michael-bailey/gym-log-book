package io.github.michael_bailey.gym_log_book.extension

import android.content.Context
import android.util.TypedValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit


fun Dp.toPx(context: Context): Float {
	return TypedValue.applyDimension(
		TypedValue.COMPLEX_UNIT_DIP,
		this.value,
		context.resources.displayMetrics
	)
}

fun TextUnit.toPx(context: Context): Float {
	return TypedValue.applyDimension(
		TypedValue.COMPLEX_UNIT_SP,
		this.value,
		context.resources.displayMetrics
	)
}

fun Dp.addSp(sp: TextUnit, context: Context): Float {
	return this.toPx(context) + sp.toPx(context)
}

fun TextUnit.addDp(dp: Dp, context: Context): Float {
	return this.toPx(context) + dp.toPx(context)
}

