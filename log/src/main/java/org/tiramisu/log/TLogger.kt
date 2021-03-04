package org.tiramisu.log

import java.util.*

/**
 * @author felixxfwang
 */
internal class TLogger : ILogger {

    companion object {
        fun logger(): ILogger {
            val tLogger = TLogger()
            return if (tLogger.isSingleLogger()) tLogger.loggers.first() else tLogger
        }
    }

    private val loggers: List<ILogger> by lazy { getLoggerList() }

    private fun getLoggerList(): List<ILogger> {
        val list = ServiceLoader.load(ILogger::class.java).toMutableList()
        if (BuildConfig.DEBUG) {
            list.add(SystemLogger())
        }
        if (list.isEmpty()) {
            list.add(EmptyLogger())
        }
        return list
    }

    private fun isSingleLogger(): Boolean = loggers.size == 1

    override fun v(tag: String, message: String) {
        loggers.forEach { it.v(tag, message) }
    }

    override fun i(tag: String, message: String) {
        loggers.forEach { it.i(tag, message) }
    }

    override fun d(tag: String, message: String) {
        loggers.forEach { it.d(tag, message) }
    }

    override fun w(tag: String, message: String) {
        loggers.forEach { it.w(tag, message) }
    }

    override fun e(tag: String, message: String, tr: Throwable?) {
        loggers.forEach { it.e(tag, message, tr) }
    }
}