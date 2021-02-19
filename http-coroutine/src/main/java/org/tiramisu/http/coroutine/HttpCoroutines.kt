package org.tiramisu.http.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.tiramisu.http.*
import kotlin.coroutines.CoroutineContext

suspend inline fun <P: HttpParam, reified T : Any> TiramisuHttp.coroutineGet(
    url: String, params: P,
    headers: Map<String, Any>? = null,
    scope: CoroutineContext = Dispatchers.IO
): HttpResult<T> = withContext(scope) {
    client.sendHttpRequest(url, HttpMethod.GET, T::class.java, params, headers)
}

suspend inline fun <P: HttpParam, reified T : Any> TiramisuHttp.coroutinePost(
    url: String, params: P,
    headers: Map<String, Any>? = null,
    scope: CoroutineContext = Dispatchers.IO
): HttpResult<T> = withContext(scope) {
    client.sendHttpRequest(url, HttpMethod.POST, T::class.java, params, headers)
}

suspend inline fun <P: HttpParam, reified T : Any> TiramisuHttp.coroutinePut(
    url: String, params: P,
    headers: Map<String, Any>? = null,
    scope: CoroutineContext = Dispatchers.IO
): HttpResult<T> = withContext(scope) {
    client.sendHttpRequest(url, HttpMethod.PUT, T::class.java, params, headers)
}

suspend inline fun <P: HttpParam, reified T : Any> TiramisuHttp.coroutineDelete(
    url: String, params: P,
    headers: Map<String, Any>? = null,
    scope: CoroutineContext = Dispatchers.IO
): HttpResult<T> = withContext(scope) {
    client.sendHttpRequest(url, HttpMethod.DELETE, T::class.java, params, headers)
}