package net.michael_bailey.gym_log_book.client.home.tabs.type

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.michael_bailey.gym_log_book.client.config.Strings
import net.michael_bailey.gym_log_book.client.exercise.view.ExerciseTypeCard
import net.michael_bailey.gym_log_book.client.exercise.view.ExerciseTypeCreateFormDialogue
import net.michael_bailey.gym_log_book.client.home.tabs.type.IExerciseTypeTabViewModel.ExerciseTypeViewData
import net.michael_bailey.gym_log_book.client.theme.ClientTheme
import net.michael_bailey.gym_log_book.client.util.scopedInject
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Composable
fun ExerciseTypeTabView(
	modifier: Modifier = Modifier,
	vm: IExerciseTypeTabViewModel = scopedInject(),
) {

	val list by vm.typeList
	val isCreateDialogueShown by vm.isCreateTypeDialogueShown

	ExerciseTypeTabView(
		modifier = modifier,
		typeList = list,
		onShowCreateTypeDialogue = vm::showCreateTypeDialogue
	)

	if (isCreateDialogueShown) {
		ExerciseTypeCreateFormDialogue(
			formState = vm.createTypeFormState,
			onDismiss = vm::hideCreateTypeDialogue,
			onSubmit = vm::submitCreateTypeForm,
		)
	}
}

@Composable
fun ExerciseTypeTabView(
	modifier: Modifier = Modifier,
	typeList: List<ExerciseTypeViewData>,
	onShowCreateTypeDialogue: () -> Unit,
) {
	Box(
		modifier = modifier
			.fillMaxSize(),
		contentAlignment = Alignment.Center,
	) {
		Surface(
			modifier = Modifier.fillMaxSize(),
			shape = MaterialTheme.shapes.extraLarge
		) {
			LazyColumn(
				contentPadding = PaddingValues(24.dp),
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.spacedBy(12.dp)
			) {

				item {
					Text(
						text = Strings.HOME_PAGE_EXERCISE_TYPES_TITLE,
						fontSize = 32.sp,
						fontWeight = FontWeight(500),
					)
				}

				item {
					Card(
						modifier = Modifier.widthIn(min = 300.dp, max = 500.dp),
						shape = RoundedCornerShape(24.dp)
					) {
						Row(
							modifier = Modifier.padding(12.dp).fillMaxWidth()
						) {
							Button(onClick = onShowCreateTypeDialogue) {
								Text("New...")
							}
						}
					}
				}

				this.items(
					items = typeList
				) {
					ExerciseTypeCard(
						modifier = Modifier.widthIn(min = 300.dp, max = 500.dp),
						exerciseType = it
					)
				}
			}
		}
	}
}

@Preview
@Composable
fun ExerciseTypeOverviewList_Preview() {

	val typeList = buildList {
		repeat(5) {
			add(
				ExerciseTypeViewData(
					name = "Type $it",
					equipmentClass = "Class",
				)
			)
		}
	}


	ClientTheme {
		ExerciseTypeTabView(
			modifier = Modifier.fillMaxSize(),
			typeList = typeList,
			onShowCreateTypeDialogue = { },
		)
	}
}
