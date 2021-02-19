package org.tiramisu.repository

interface DataClient<PARAM, RESULT> {

    /**
     * 异步请求
     */
    fun sendDataRequest(param: PARAM, callback: DataCallback<PARAM, RESULT>): Disposable

    /**
     * 同步请求
     */
    fun sendDataRequest(param: PARAM): DataResult<RESULT>
}