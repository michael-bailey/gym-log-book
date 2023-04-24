package io.github.michael_bailey.gym_log_book.lib.componenets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.material.elevation.SurfaceColors
import io.github.michael_bailey.gym_log_book.theme.Gym_Log_BookTheme
import io.github.michael_bailey.gym_log_book.theme.Title

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun testBackdropScaffold() {

	val a = (1..10).toList()

	BottomSheetScaffold(
		content = {
			BackdropScaffold(
				modifier = Modifier.padding(it),
				backLayerContentColor = MaterialTheme.colorScheme.onBackground,
				backLayerBackgroundColor = MaterialTheme.colorScheme.background,
				appBar = {
					TopAppBar(
						title = { androidx.compose.material3.Text("Settings") }
					)
				},
				backLayerContent = {
					LazyColumn {
						items(a) {
							Text("item $it")
						}
					}
				},
				frontLayerContent = {
					Column(
						Modifier.fillMaxSize(),
						Arrangement.Center,
						Alignment.CenterHorizontally
					) {
						Text("TITLE", fontSize = Title)
					}
				})
		},
		sheetBackgroundColor = Color(SurfaceColors.SURFACE_2.getColor(LocalContext.current)),
		sheetContent = {
			LazyColumn(content = {
				items(a) {
					Text("item $it")
				}
			})
		}
	)
}


@Composable
@Preview
fun backdropScaffoldPreview() {
	Gym_Log_BookTheme(colourNavBar = true) {
		testBackdropScaffold()
	}
}