package io.github.michael_bailey.gym_log_book.database.importer

import io.github.michael_bailey.gym_log_book.app.App
import io.github.michael_bailey.gym_log_book.lib.data_manager.BaseDataManager
import io.github.michael_bailey.gym_log_book.lib.one_shot.OneShotPreference

abstract class AbstractImporter<I, O>(
	protected val application: App,
) : Runnable {


	private val onShotName: String = this::class.java.simpleName
	private val oneShot: OneShotPreference = OneShotPreference.create(onShotName)

	/**
	 * convert from original type to new ent type
	 */
	abstract fun migrate(inputItem: I): O

	/**
	 * apply sorting to list,
	 * by default applies no sorting.
	 */
	protected open fun applySorting(inputData: List<I>): List<I> {
		return inputData
	}

	/**
	 * retuns the manager the importer will use
	 */
	abstract fun getManager(): BaseDataManager<I>

	/**
	 * gets the dao to use
	 */
	abstract fun getDao(): ExportDao<O>

	/**
	 * executes the import
	 */
	override fun run() {
		// short if already consumed
		if (oneShot.isConsumed()) {
			return
		}
		val inputData = getManager().liveData.value!!
		applySorting(inputData)
			.forEach {
				getDao().insert(migrate(it))
			}
	}
}