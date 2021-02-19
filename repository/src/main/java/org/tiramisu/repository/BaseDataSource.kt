package org.tiramisu.repository

abstract class BaseDataSource<P, D, REQ, RSP> : DataSource<P, D> {

    protected val client: DataClient<REQ, RSP> by lazy { getDataClient() }

    protected var request: Disposable? = null

    override fun cancel() {
        if (request?.isDisposed() == false) {
            request?.dispose()
        }
    }

    protected abstract fun getDataClient(): DataClient<REQ, RSP>
}