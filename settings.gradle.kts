pluginManagement {
	repositories {
		google()
		mavenCentral()
		gradlePluginPortal()
	}
}
dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
	repositories {
		google()
		mavenCentral()
		exclusiveContent {
			forRepository {
				ivy {
					name = "Node.js"
					setUrl("https://nodejs.org/dist")
					patternLayout { artifact("v[revision]/[artifact](-v[revision]-[classifier]).[ext]") }
					metadataSources { artifact() }
					content { includeModule("org.nodejs", "node") }
				}
			}
			filter { includeGroup("org.nodejs") }
		}
		exclusiveContent {
			forRepository {
				ivy {
					name = "Yarn"
					setUrl("https://github.com/yarnpkg/yarn/releases/download")
					patternLayout { artifact("v[revision]/[artifact](-v[revision]).[ext]") }
					metadataSources { artifact() }
					content { includeModule("com.yarnpkg", "yarn") }
				}
			}
			filter { includeGroup("com.yarnpkg") }
		}
	}
}
rootProject.name = "Gym_Log_Book"
include(":gym_mobile_app")
include(":gym_watch_app")
include(":gym_library")

include(":gym_log_book_server")
include(":gym_log_book_client")
include(":gym_log_book_shared")
