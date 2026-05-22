package net.michael_bailey.gym_log_book.client.component.transform

import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.placeCursorAtEnd

class RepsTransformer : InputTransformation, OutputTransformation {

	override fun TextFieldBuffer.transformInput() {
		val raw = asCharSequence().toString()
		val result = raw.filter { it.isDigit() }
		if (raw != result) replace(0, length, result)
	}

	override fun TextFieldBuffer.transformOutput() {
		if (length == 0) return
		append(" Reps")
		placeCursorAtEnd()
	}
}