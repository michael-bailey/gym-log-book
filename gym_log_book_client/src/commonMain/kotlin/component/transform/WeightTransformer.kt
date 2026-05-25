package net.michael_bailey.gym_log_book.client.component.transform

import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer

class WeightTransformer : InputTransformation, OutputTransformation {
	override fun TextFieldBuffer.transformInput() {
		val raw = asCharSequence().toString()
		val firstDot = raw.indexOf('.')
		val result = raw.filterIndexed { i, c ->
			c.isDigit() || (c == '.' && i == firstDot)
		}
		if (raw != result) replace(0, length, result)
	}

	override fun TextFieldBuffer.transformOutput() {
		this.append(" KG")
		this.placeCursorAfterCharAt(this.length - 4)
	}
}