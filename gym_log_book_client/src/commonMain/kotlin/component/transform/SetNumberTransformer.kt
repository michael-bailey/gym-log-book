package net.michael_bailey.gym_log_book.client.component.transform

import androidx.compose.foundation.text.input.*

class SetNumberTransformer : InputTransformation, OutputTransformation {

	override fun TextFieldBuffer.transformInput() {
		val raw = asCharSequence().toString()
		val result = raw.filter { it.isDigit() }
		if (raw != result) replace(0, length, result)
	}

	override fun TextFieldBuffer.transformOutput() {
		if (length == 0) return
		insert(0, "Set: ")
		placeCursorAtEnd()
	}
}