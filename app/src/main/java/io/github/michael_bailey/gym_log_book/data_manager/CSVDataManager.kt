package io.github.michael_bailey.gym_log_book.data_manager

//object CSVDataManager {
//
//	val TAG = "CSVDataManager"
//
//	val filename = "gymlog.csv"
//
//	fun save(ctx: Context, items: List<ExerciseItem>) {
//		Log.i(TAG, "Saving file")
//		var out = ctx.openFileOutput(filename, Context.MODE_PRIVATE)
//
//		out.write("'Date', 'Exercise', 'SetNumber', 'Weight', 'Reps'\n".toByteArray())
//		out.use { o ->
//			items.forEach {
//				Log.i(TAG, "writing item ${it.date} ${it.exercise}")
//				o.write("${it.date}, ${it.exercise}, ${it.setNumber}, ${it.weight}, ${it.reps}\n".toByteArray())
//			}
//		}
//		out.flush()
//	}
//
//	fun read(ctx: Context): List<ExerciseItem> {
//		val reader = ctx.openFileInput(filename).bufferedReader()
//
//		val nonEmpty = reader.lineSequence()
//			.filter { it.isNotBlank() }
//			.toList()
//
//		return nonEmpty.withIndex().map {
//			if (it.index == 0) return@map null
//			val (date, exercise, setNumber, Weight, Reps) = it.value.split(", ", ignoreCase = false, limit = 5)
//			ExerciseItem(LocalDate.parse(date), exercise, setNumber.toLong(), Weight.toLong(), Reps.toLong())
//		}.filterNotNull().toList()
//	}
//}