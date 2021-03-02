package org.tiramisu.http

import android.content.Context
import com.github.kittinunf.fuel.core.FuelManager
import org.tiramisu.http.fuel.FuelHttpClient
import javax.net.ssl.*
import kotlin.properties.Delegates

class TiramisuHttp {

    companion object {
        private var context: Context by Delegates.notNull()
        private var isDebug: Boolean = false
        private var sslCreator: SSLSocketFactoryCreator? = null

        fun initialize(cxt: Context, debug: Boolean, ssl: SSLSocketFactoryCreator? = null) {
            context = cxt.applicationContext
            isDebug = debug
            sslCreator = ssl
        }
    }

    private var baseUrl: String = ""

    fun baseUrl(url: String): TiramisuHttp {
        this.baseUrl = url
        return this
    }

    fun wrapUrl(url: String): String {
        if (url.startsWith("http")) return url
        return baseUrl + url
    }

    val client: HttpClient = initFuelHttpClient()

    inline fun <P: HttpParam, reified T : Any> get(
        url: String, params: P,
        headers: Map<String, Any>? = null,
        callback: HttpCallback<P, T>? = null
    ): HttpCancellable {
        return client.sendHttpRequest(wrapUrl(url), HttpMethod.GET, T::class.java, params, headers, null, callback)
    }

    inline fun <P: HttpParam, reified T : Any> post(
        url: String, params: P,
        headers: Map<String, Any>? = null,
        callback: HttpCallback<P, T>? = null
    ) : HttpCancellable {
        return client.sendHttpRequest(wrapUrl(url), HttpMethod.POST, T::class.java, params, headers, null, callback)
    }

    inline fun <P: HttpParam, reified T : Any> put(
        url: String, params: P,
        headers: Map<String, Any>? = null,
        callback: HttpCallback<P, T>? = null
    ) : HttpCancellable {
        return client.sendHttpRequest(wrapUrl(url), HttpMethod.PUT, T::class.java, params, headers, null, callback)
    }

    inline fun <P: HttpParam, reified T : Any> delete(
        url: String, params: P,
        headers: Map<String, Any>? = null,
        callback: HttpCallback<P, T>? = null
    ) : HttpCancellable {
        return client.sendHttpRequest(wrapUrl(url), HttpMethod.DELETE, T::class.java, params, headers, null, callback)
    }

    private fun initFuelHttpClient(): HttpClient {
        val creator = sslCreator
        if (creator != null) {
            FuelManager.instance.socketFactory = creator.create(context)
        }
        if (isDebug) {
            FuelManager.instance.hostnameVerifier = HostnameVerifier { _, _ -> true }
        }
        return FuelHttpClient()
    }
}