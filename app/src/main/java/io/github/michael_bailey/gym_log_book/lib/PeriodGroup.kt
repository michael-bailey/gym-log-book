package io.github.michael_bailey.gym_log_book.lib

import java.time.Period

sealed class PeriodGroup(
	private val priority: Int,
	private val count: Int,
) : Comparable<PeriodGroup> {

	class Days(count: Int) : PeriodGroup(1, count)
	class Weeks(count: Int) : PeriodGroup(2, count)
	class Months(count: Int) : PeriodGroup(3, count)
	class Years(count: Int) : PeriodGroup(4, count)

	companion object {
		fun getPeriodGroup(period: Period): PeriodGroup {
			if (period.years >= 1) {
				return Years(period.years)
			}

			if (period.months >= 1 || period.days >= 28) {
				return Months(period.months)
			}

			if (period.days >= 7) {
				val weeks: Int = period.days / 7
				return Weeks(weeks)
			}

			return Days(period.days)
		}
	}

	override fun compareTo(other: PeriodGroup): Int {
		if ((priority - other.priority) != 0) {
			return priority - other.priority
		}

		return count - other.count
	}

	override fun hashCode(): Int = this.count * 127 * priority

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other == null || javaClass != other.javaClass) return false

		other as PeriodGroup

		if (priority != other.priority) return false
		if (count != other.count) return false

		return true
	}

	override fun toString(): String {
		return when (this) {
			is Days -> when (this.count) {
				0 -> "Today"
				1 -> "Yesterday"
				else -> "${this.count} Days ago"
			}

			is Months -> when (this.count) {
				1 -> "Last Month"
				else -> "${this.count} Months ago"
			}

			is Weeks -> when (this.count) {
				1 -> "Last Week"
				else -> "${this.count} Weeks ago"
			}

			is Years -> when (this.count) {
				1 -> "Last Year"
				else -> "${this.count} Years Ago"
			}
		}
	}
}

