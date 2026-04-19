@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.exercise.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import net.michael_bailey.gym_log_book.server.exercise.factory.IWeightEntryFactory
import net.michael_bailey.gym_log_book.shared.exercise.model.WeightEntry
import org.koin.core.annotation.Single
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Single
class InMemoryWeightEntryRepository(
	private val weightEntryFactory: IWeightEntryFactory,
	private val scope: CoroutineScope,
) : IMutableWeightEntryRepository {

	private val _mapState: MutableStateFlow<Map<Uuid, WeightEntry>> =
		MutableStateFlow(mapOf())

	override val allWeightEntries: Flow<Collection<WeightEntry>> = _mapState
		.map { it.values }

	override fun addWeightEntry(
		date: LocalDate,
		weight: Double,
	): Deferred<WeightEntry> = scope.async {
		val entry = weightEntryFactory.createEntry(
			date = date,
			weight = weight
		)

		_mapState.emit(_mapState.value + mapOf(entry.id to entry))

		entry
	}


}