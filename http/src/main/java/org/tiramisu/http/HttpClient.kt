package org.tiramisu.http

interface HttpClient {

    /**
     * 异步请求
     */
    fun <P: HttpParam, T: Any> sendHttpRequest(
        url: String,
        method: HttpMethod,
        clazz: Class<T>,
        params: P,
        headers: Map<String, Any>? = null,
        callback: HttpCallback<P, T>? = null
    ): HttpCancellable

    /**
     * 同步请求
     */
    fun <P: HttpParam, T: Any> sendHttpRequest(
        url: String,
        method: HttpMethod,
        clazz: Class<T>,
        params: P,
        headers: Map<String, Any>? = null
    ): HttpResult<T>
}