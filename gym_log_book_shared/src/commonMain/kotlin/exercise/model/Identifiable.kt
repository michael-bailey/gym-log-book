@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.shared.exercise.model

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

abstract class Identifiable {
	abstract val id: Uuid
}
