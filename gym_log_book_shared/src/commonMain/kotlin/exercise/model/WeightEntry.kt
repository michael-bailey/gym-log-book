@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.shared.exercise.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
data class WeightEntry(
	override val id: Uuid,
	val date: LocalDateTime,
	val weight: Double,
) : Identifiable()