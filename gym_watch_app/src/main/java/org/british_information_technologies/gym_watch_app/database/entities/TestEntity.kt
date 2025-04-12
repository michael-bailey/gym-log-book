package org.british_information_technologies.gym_watch_app.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
	"test_entities",
)
data class TestEntity(
	@PrimaryKey val id: UUID = UUID.randomUUID(),
)