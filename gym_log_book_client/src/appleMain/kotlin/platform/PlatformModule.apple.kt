package net.michael_bailey.gym_log_book.client.platform

import androidx.compose.material3.CalendarLocale
import androidx.room3.Room
import androidx.room3.RoomDatabase
import io.ktor.client.engine.*
import io.ktor.client.engine.darwin.*
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.cancel
import kotlinx.rpc.RpcClient
import kotlinx.rpc.withService
import net.michael_bailey.gym_log_book.client.ApplicationViewModel
import net.michael_bailey.gym_log_book.client.di.scopes.AuthenticatedScope
import net.michael_bailey.gym_log_book.shared.authentication.controller.ViewerContextDebuggerController
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.dsl.onClose
import platform.Foundation.*

@OptIn(ExperimentalForeignApi::class)
actual val platformModule: Module = module {

	single<CoroutineScope> { CoroutineScope(Dispatchers.Main) } onClose { it?.cancel() }

	single { NSLocale.currentLocale } bind CalendarLocale::class

	single { Darwin.create() } bind HttpClientEngine::class

	singleOf(::ApplicationViewModel)

	scope<AuthenticatedScope> {
		scoped {
			val client = get<RpcClient>()
			client.withService<ViewerContextDebuggerController>()
		}
	}

	single {
		val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
			directory = NSDocumentDirectory,
			inDomain = NSUserDomainMask,
			appropriateForURL = null,
			create = false,
			error = null,
		)!!

		val dbFilePath = documentDirectory.path() + "/my_room.db"
		Room.databaseBuilder<AppDatabase>(
			name = dbFilePath,
		)
	}

	single {
		val builder = get<RoomDatabase.Builder<AppDatabase>>()
		builder
			.setDriver(androidx.sqlite.driver.bundled.BundledSQLiteDriver())
			.setQueryCoroutineContext(Dispatchers.IO)
			.build()
	}
}
