package org.tiramisu.http.fuel

import com.github.kittinunf.fuel.core.*
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpPut
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import org.tiramisu.http.*
import org.tiramisu.http.HttpException
import java.io.Reader

class FuelHttpClient : HttpClient {

    override fun <P : HttpParam, T: Any> sendHttpRequest(
        url: String,
        method: HttpMethod,
        clazz: Class<T>,
        params: P,
        headers: Map<String, Any>?,
        deserializer: HttpResponseDeserializable<T>?,
        callback: HttpCallback<P, T>?
    ): HttpCancellable {
        val request = buildRequest(url, method, params, headers)
            .response(
                FuelResponseDeserializable(clazz, deserializer),
                FuelResponseHandler(params, callback)
            )
        request.join()
        return FuelCancellable(request)
    }

    override fun <P : HttpParam, T: Any> sendHttpRequest(
        url: String,
        method: HttpMethod,
        clazz: Class<T>,
        params: P,
        headers: Map<String, Any>?,
        deserializer: HttpResponseDeserializable<T>?
    ): HttpResult<T> {
        val request = buildRequest(url, method, params, headers)
        val (_, _, result) = request.responseObject(FuelResponseDeserializable(clazz, deserializer))
        return when (result) {
            is Result.Success -> HttpResult.success(result.get())
            is Result.Failure -> {
                val error = result.error
                val exception = HttpException(error.response.statusCode, error.message, error.cause)
                HttpResult.error(exception)
            }
        }
    }

    private fun <P: HttpParam> buildRequest(
        url: String, method: HttpMethod,
        params: P, headers: Map<String, Any>?
    ): Request {
        val parameters = params.toMap().entries.map { it.key to it.value }
        val request = when (method) {
            HttpMethod.GET -> url.httpGet(parameters)
            HttpMethod.POST -> url.httpPost(parameters)
            HttpMethod.PUT -> url.httpPut(parameters)
            HttpMethod.DELETE -> url.httpDelete(parameters)
        }
        headers?.let { request.header(it) }
        return request
    }

    class FuelResponseDeserializable<T: Any>(
        private val clazz: Class<T>,
        private val deserializer: HttpResponseDeserializable<T>? = null
    ) : ResponseDeserializable<T> {

        override fun deserialize(response: Response): T {
            if (deserializer != null) {
                val headers = response.headers.toMap()
                return deserializer.deserialize(headers, response.data)
            }
            return super.deserialize(response)
        }

        override fun deserialize(reader: Reader): T? {
            return Gson().fromJson<T>(reader, clazz)
        }
    }

    class FuelResponseHandler<P: HttpParam, T: Any>(
        private val param: P,
        private val callback: HttpCallback<P, T>?
    ) : ResponseResultHandler<T> {
        override fun invoke(request: Request, response: Response, result: Result<T, FuelError>) {
            when (result) {
                is Result.Failure -> {
                    val ex = result.getException()
                    callback?.onError(param, HttpException(response.statusCode, ex.message))
                }
                is Result.Success -> {
                    val data = result.get()
                    callback?.onSuccess(param, data)
                }
            }
        }

    }
}

fun Headers.toMap(): Map<String, String> {
    return this.mapValues { it.value.joinToString(",") }
}