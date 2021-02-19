package org.tiramisu.repository

interface Disposable {
    fun isDisposed(): Boolean
    fun dispose()
}