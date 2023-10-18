package org.british_information_technologies.gym_log_book.repository


import org.british_information_technologies.gym_log_book.database.dao.WeightEntryDao
import org.british_information_technologies.gym_log_book.database.entity.EntWeightEntry
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

/**
 * Repository for managing weight data
 */
class WeightEntryRepository @Inject constructor(
	var weightEntryDao: WeightEntryDao
) {

	val weightEntryList = weightEntryDao.queryAllWeight()

	suspend fun import(createdDate: LocalDate, weight: Double) {
		weightEntryDao.insertWeight(
			EntWeightEntry(
				createdDate = createdDate,
				value = weight,
			)
		)
	}

	suspend fun create(weight: Double) {
		weightEntryDao.create(weight = weight)
	}

	suspend fun delete(uuid: UUID) {
		weightEntryDao.deleteWeight(weightEntryDao.getWeight(uuid))
	}


}