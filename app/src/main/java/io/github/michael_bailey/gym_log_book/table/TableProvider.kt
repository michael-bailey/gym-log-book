package io.github.michael_bailey.gym_log_book.table

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TableProvider {

	@Provides
	@Singleton
	fun provideExerciseTypeTable(
		@ApplicationContext context: Context,
	): ExerciseTypeTable {
		return ExerciseTypeTable(context)
	}

	@Provides
	@Singleton
	fun provideExerciseTable(
		@ApplicationContext context: Context,
	): ExerciseTable {
		return ExerciseTable(context)
	}

	@Provides
	@Singleton
	fun provideWeightTable(
		@ApplicationContext context: Context,
	): WeightTable {
		return WeightTable(context)
	}
}