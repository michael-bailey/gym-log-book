package net.michael_bailey.gym_log_book.client.sync

enum class SyncStatus(val code: String) {
	SavePending("SavePending"),
	DeletePending("DeletePending"),
	Synchronised("Synchronised");

	val isPending: Boolean get() = this != Synchronised

	companion object {
		fun fromCode(code: String) = entries.first { it.code == code }
	}
}
