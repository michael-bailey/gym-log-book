package org.british_information_technologies.gym_log_book.database.edge

import androidx.room.Entity
import java.util.UUID

@Entity(
	tableName = "group_to_type_edge",
	primaryKeys = ["groupId", "typeId"],
)
data class EdgeGroupToType(
	val groupId: UUID,
	val typeId: UUID
)
