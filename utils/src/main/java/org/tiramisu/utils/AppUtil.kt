package org.tiramisu.utils

import androidx.annotation.Keep
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner

object AppUtil : LifecycleObserver {

    interface LifecycleListener {
        fun onForeground()
        fun onBackground()
    }

    private val listeners = ArrayList<LifecycleListener>()

    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    fun registerAppLifecycleListener(listener: LifecycleListener) {
        listeners.add(listener)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START) @Keep
    fun onForeground() {
        listeners.forEach { it.onForeground() }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP) @Keep
    fun onBackground() {
        listeners.forEach { it.onBackground() }
    }
}