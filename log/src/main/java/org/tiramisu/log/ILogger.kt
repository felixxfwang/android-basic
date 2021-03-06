package org.tiramisu.log

import android.app.Application

interface ILogger {
    fun initialize(application: Application) {}

    fun v(tag: String, message: String)
    fun i(tag: String, message: String)
    fun d(tag: String, message: String)
    fun w(tag: String, message: String)
    fun e(tag: String, message: String, tr: Throwable? = null)
}