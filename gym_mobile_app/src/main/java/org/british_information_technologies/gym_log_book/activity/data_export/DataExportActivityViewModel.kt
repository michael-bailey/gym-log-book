package org.british_information_technologies.gym_log_book.activity.data_export

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.michael_bailey.android_chat_kit.extension.any.log
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.british_information_technologies.gym_library.data_type.shareable.ShareableExerciseEntryData
import org.british_information_technologies.gym_log_book.repository.ExerciseEntryRepository
import org.british_information_technologies.gym_log_book.repository.ExerciseTypeRepository
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class DataExportActivityViewModel @Inject constructor(
	private var exerciseEntryRepository: ExerciseEntryRepository,
	private var exerciseTypeRepository: ExerciseTypeRepository
) : ViewModel(
), DefaultLifecycleObserver {

	private val _status = MutableStateFlow(WriteStatus.NotStarted)
	val status = _status.asLiveData()

	private val _exerciseData =
		exerciseEntryRepository.exercises.combine(exerciseTypeRepository.exerciseTypes) { entries, type ->
			entries.map { entry ->
				ShareableExerciseEntryData(
					created = LocalDateTime.of(entry.createdDate, entry.createdTime),
					exerciseType = type.find { entry.exerciseTypeId == it.id }?.name
						?: "unknown",
					setNumber = entry.setNumber,
					weight = entry.weight,
					reps = entry.reps,
				)
			}
		}

	var updaterJob: Job? = null
	var exerciseData: List<ShareableExerciseEntryData> = listOf()

	val itemCount = exerciseEntryRepository.exerciseCount.asLiveData()

	init {
		log("Init")
	}

	override fun onCreate(owner: LifecycleOwner) {
		super.onCreate(owner)
		viewModelScope.launch {
			_exerciseData.collect {
				log("Got new array")
				log("$it")
				exerciseData = it
			}
		}
	}

	override fun onStop(owner: LifecycleOwner) {
		super.onStop(owner)
		updaterJob?.cancel()
		updaterJob = null
	}

	fun writeData(
		requestCode: Int,
		resultCode: Int,
		intent: Intent?,
		contentResolver: ContentResolver
	) {
		viewModelScope.launch {
			if (resultCode != Activity.RESULT_OK) {
				_status.emit(WriteStatus.FailedToRequest)
			}

			_status.emit(WriteStatus.GotURL)

			intent?.data?.also { uri ->
				try {
					contentResolver.openFileDescriptor(uri, "w")?.use { fd ->
						FileOutputStream(fd.fileDescriptor).use { o ->
							o.write(Json.encodeToString(exerciseData).toByteArray())
						}
					}
				} catch (e: FileNotFoundException) {
					Log.e(this::class.java.simpleName, "error finding file", e)
					_status.emit(WriteStatus.FailedToFind)
					return@launch
				} catch (e: IOException) {
					Log.e(this::class.java.simpleName, "error with IO", e)
					_status.emit(WriteStatus.FailedToWrite)
					return@launch
				}
			}
			_status.emit(WriteStatus.Success)
		}
	}
}