@file:OptIn(ExperimentalCoroutinesApi::class)

package net.michael_bailey.gym_log_book.client.counter.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import net.michael_bailey.gym_log_book.shared.counter.controller.CounterController

class CounterRepository(
	controller: Flow<CounterController?>,
) {

	val count = controller.flatMapLatest { it?.observeCounter() ?: emptyFlow() }

}