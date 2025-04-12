package org.british_information_technologies.gym_watch_app.tile

import android.content.Context
import androidx.wear.protolayout.ColorBuilders.argb
import androidx.wear.protolayout.LayoutElementBuilders
import androidx.wear.protolayout.ModifiersBuilders.Clickable
import androidx.wear.protolayout.ResourceBuilders
import androidx.wear.protolayout.TimelineBuilders
import androidx.wear.protolayout.material.Chip
import androidx.wear.protolayout.material.Colors
import androidx.wear.protolayout.material.Text
import androidx.wear.protolayout.material.Typography
import androidx.wear.protolayout.material.layouts.PrimaryLayout
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.TileBuilders
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.tiles.SuspendingTileService
import dagger.hilt.android.AndroidEntryPoint

private const val RESOURCES_VERSION = "0"

/**
 * Skeleton for a tile with no images.
 */
@OptIn(ExperimentalHorologistApi::class)
@AndroidEntryPoint
class MainTileService : SuspendingTileService() {

	override suspend fun resourcesRequest(
		requestParams: RequestBuilders.ResourcesRequest
	) = resources(requestParams)

	override suspend fun tileRequest(
		requestParams: RequestBuilders.TileRequest
	) = tile(requestParams, this)

	private fun resources(
		requestParams: RequestBuilders.ResourcesRequest
	): ResourceBuilders.Resources {
		return ResourceBuilders.Resources.Builder()
			.setVersion(RESOURCES_VERSION)
			.build()
	}

	private fun tile(
		requestParams: RequestBuilders.TileRequest,
		context: Context,
	): TileBuilders.Tile {
		val singleTileTimeline = TimelineBuilders.Timeline.Builder()
			.addTimelineEntry(
				TimelineBuilders.TimelineEntry.Builder()
					.setLayout(
						LayoutElementBuilders.Layout.Builder()
							.setRoot(tileLayout(requestParams, context))
							.build()
					)
					.build()
			)
			.build()

		return TileBuilders.Tile.Builder()
			.setResourcesVersion(RESOURCES_VERSION)
			.setTileTimeline(singleTileTimeline)
			.build()
	}

	private fun tileLayout(
		requestParams: RequestBuilders.TileRequest,
		context: Context,
	): LayoutElementBuilders.LayoutElement {
		return PrimaryLayout.Builder(requestParams.deviceConfiguration)
			.setResponsiveContentInsetEnabled(true)
			.setPrimaryChipContent(
				Chip.Builder(
					context,
					Clickable.Builder().build(),
					requestParams.deviceConfiguration
				).setPrimaryLabelContent("Hello").build()
			).setContent(
				Text.Builder(context, "Hello World!")
					.setColor(argb(Colors.DEFAULT.onSurface))
					.setTypography(Typography.TYPOGRAPHY_CAPTION1)
					.build()
			).build()
	}
}