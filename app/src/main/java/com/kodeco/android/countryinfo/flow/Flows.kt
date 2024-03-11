package com.kodeco.android.countryinfo.flow

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

object Flows {
    private val _tapFlow: MutableStateFlow<Int> = MutableStateFlow(0)
    private val _backFlow: MutableStateFlow<Int> = MutableStateFlow(0)
    private val _counterFlow: MutableStateFlow<Int> = MutableStateFlow(0)
    private val _refreshFlow: MutableStateFlow<Int> = MutableStateFlow(0)

    val tapFlow: StateFlow<Int> = _tapFlow
    val backFlow: StateFlow<Int> = _backFlow
    val counterFlow: StateFlow<Int> = _counterFlow
    val refreshFlow: StateFlow<Int> = _refreshFlow

    fun tap() {
        _tapFlow.value += 1
    }

    fun tapBack() {
        _backFlow.value += 1
    }

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

    fun refresh() {
        _refreshFlow.value += 1
    }
}