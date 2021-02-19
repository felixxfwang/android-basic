package org.tiramisu.repository.coroutine

abstract class AbstractCoroutineDataSource<P, D, REQ, RSP> : CoroutineDataSource<P, D> {

    protected val client: CoroutineDataClient<REQ, RSP> by lazy { getDataClient() }

    protected abstract fun getDataClient(): CoroutineDataClient<REQ, RSP>

}