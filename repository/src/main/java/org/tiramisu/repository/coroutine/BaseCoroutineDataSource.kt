package org.tiramisu.repository.coroutine

import org.tiramisu.repository.DataResult
import org.tiramisu.repository.transform

abstract class BaseCoroutineDataSource<P, D, REQ, RSP> : AbstractCoroutineDataSource<P, D, REQ, RSP>() {

    override suspend fun loadData(param: P): DataResult<D> {
        return client.sendRequest(getRequest(param)).transform { getResponse(it) }
    }

    protected abstract fun getRequest(param: P): REQ

    protected abstract fun getResponse(response: RSP): D

}