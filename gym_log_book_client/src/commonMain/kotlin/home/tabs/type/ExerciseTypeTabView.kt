package net.michael_bailey.gym_log_book.client.home.tabs.type

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.michael_bailey.gym_log_book.client.exercise.view.ExerciseTypeOverviewCard
import net.michael_bailey.gym_log_book.client.exercise.view_model.ExerciseTypeListViewModel
import net.michael_bailey.gym_log_book.client.util.scopedInject
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseType
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Composable
fun ExerciseTypeOverviewList(
	modifier: Modifier = Modifier,
	vm: ExerciseTypeListViewModel = scopedInject(),
) {
	val list by vm.exerciseTypes.collectAsState(emptyList())
	var typeName by rememberSaveable { mutableStateOf("") }

	Box(
		modifier = modifier.fillMaxSize(),
		contentAlignment = Alignment.Center,
	) {
		Surface(
			modifier = Modifier.fillMaxSize(), shape = MaterialTheme.shapes.extraLarge
		) {
			LazyColumn(
				modifier = modifier.fillMaxSize(),
				contentPadding = PaddingValues(12.dp),
				verticalArrangement = Arrangement.spacedBy(6.dp)
			) {
				this.items(
					items = list
				) {
					ExerciseTypeOverviewCard(
						name = it.name,
						details = buildExerciseTypeDetails(it),
						id = it.id.toString(),
					)
				}

			}
		}
	}
}

private fun buildExerciseTypeDetails(
	exerciseType: ExerciseType,
): String {
	val equipmentLabel = when (val equipmentClass = exerciseType.equipmentClass) {
		EquipmentClass.Machine -> "Machine"
		EquipmentClass.FreeWeight -> "Free Weight"
		EquipmentClass.UsesUserWeight -> "Bodyweight"
		EquipmentClass.None -> "No equipment"
		is EquipmentClass.Undefined -> equipmentClass.text
	}

	val weightLabel = if (exerciseType.isUsingUserWeight) {
		"Tracks body weight"
	} else {
		"Tracks external load"
	}

	return "$equipmentLabel • $weightLabel"
}
