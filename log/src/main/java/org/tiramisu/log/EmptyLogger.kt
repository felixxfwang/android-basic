package org.tiramisu.log

/**
 * @author felixxfwang
 */
class EmptyLogger: ILogger {
    override fun v(tag: String, message: String) {
    }

    override fun i(tag: String, message: String) {
    }

    override fun d(tag: String, message: String) {
    }

    override fun w(tag: String, message: String) {
    }

    override fun e(tag: String, message: String, tr: Throwable?) {
    }
}