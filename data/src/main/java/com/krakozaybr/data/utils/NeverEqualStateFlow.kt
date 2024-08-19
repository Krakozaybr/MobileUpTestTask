package com.krakozaybr.data.utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow

fun <T> MutableNeverEqualStateFlow(value: T) =
    MutableStateFlow(value).withComparator { _, _ -> false }

fun <T> MutableStateFlow<T>.withComparator(comparator: (T, T) -> Boolean): MutableStateFlow<T> {
    @Suppress("EqualsOrHashCode", "UNCHECKED_CAST")
    class Wrapper(val wrapped: T) {
        override fun equals(other: Any?) = comparator(wrapped, (other as Wrapper).wrapped)
    }

    val flow = MutableStateFlow(Wrapper(value))

    return object : MutableStateFlow<T> {
        override var value
            get() = flow.value.wrapped
            set(value) {
                flow.value = Wrapper(value)
            }

        override suspend fun collect(collector: FlowCollector<T>) =
            flow.collect { collector.emit(it.wrapped) }

        override val replayCache get() = flow.replayCache.map { it.wrapped }
        override val subscriptionCount get() = flow.subscriptionCount
        override fun compareAndSet(expect: T, update: T) =
            flow.compareAndSet(Wrapper(expect), Wrapper(update))

        @ExperimentalCoroutinesApi
        override fun resetReplayCache() = flow.resetReplayCache()
        override fun tryEmit(value: T) = flow.tryEmit(Wrapper(value))
        override suspend fun emit(value: T) = flow.emit(Wrapper(value))
    }
}