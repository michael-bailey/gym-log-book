package net.michael_bailey.gym_log_book.server.exercise.factory

import kotlinx.datetime.LocalDate
import net.michael_bailey.gym_log_book.shared.exercise.model.WeightEntry

interface IWeightEntryFactory {
	fun createEntry(date: LocalDate, weight: Double): WeightEntry
}