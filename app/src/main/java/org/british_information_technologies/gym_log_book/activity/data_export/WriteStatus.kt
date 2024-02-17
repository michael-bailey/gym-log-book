package org.british_information_technologies.gym_log_book.activity.data_export

enum class WriteStatus {
	NotStarted,
	RequestingFile,
	FailedToRequest,
	GotURL,
	FailedToFind,
	FailedToWrite,
	Success,

	Unknown
}