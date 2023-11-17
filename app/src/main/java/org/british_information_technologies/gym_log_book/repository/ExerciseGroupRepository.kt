package org.british_information_technologies.gym_log_book.repository

import org.british_information_technologies.gym_log_book.database.dao.ExerciseGroupDao
import javax.inject.Inject

class ExerciseGroupRepository @Inject constructor(
	exerciseGroupDao: ExerciseGroupDao
)