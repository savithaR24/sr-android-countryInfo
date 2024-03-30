package com.kodeco.android.countryinfo.other

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

object Counter {
    private val _counterFlow: MutableStateFlow<Int> = MutableStateFlow(0)
    val counterFlow: StateFlow<Int> = _counterFlow

    init {
        GlobalScope.launch {
            while (true) {
                delay(1000)
                increment()
            }
        }
    }

    private fun increment() {
        _counterFlow.value += 1
    }
}