package org.tiramisu.repository

interface DataCallback<P, R> {

    fun onSuccess(param: P, data: R)

    fun onError(param: P, error: DataException)
}