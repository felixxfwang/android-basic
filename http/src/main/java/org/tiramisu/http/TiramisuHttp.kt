package org.tiramisu.http

import android.content.Context
import com.github.kittinunf.fuel.core.FuelManager
import org.tiramisu.http.fuel.FuelHttpClient
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.net.ssl.*
import kotlin.properties.Delegates

class TiramisuHttp {

    companion object {
        private var context: Context by Delegates.notNull()
        private var isDebug: Boolean = false

        fun initialize(cxt: Context, debug: Boolean) {
            context = cxt.applicationContext
            isDebug = debug
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
        return client.sendHttpRequest(wrapUrl(url), HttpMethod.GET, T::class.java, params, headers, callback)
    }

    inline fun <P: HttpParam, reified T : Any> post(
        url: String, params: P,
        headers: Map<String, Any>? = null,
        callback: HttpCallback<P, T>? = null
    ) : HttpCancellable {
        return client.sendHttpRequest(wrapUrl(url), HttpMethod.POST, T::class.java, params, headers, callback)
    }

    inline fun <P: HttpParam, reified T : Any> put(
        url: String, params: P,
        headers: Map<String, Any>? = null,
        callback: HttpCallback<P, T>? = null
    ) : HttpCancellable {
        return client.sendHttpRequest(wrapUrl(url), HttpMethod.PUT, T::class.java, params, headers, callback)
    }

    inline fun <P: HttpParam, reified T : Any> delete(
        url: String, params: P,
        headers: Map<String, Any>? = null,
        callback: HttpCallback<P, T>? = null
    ) : HttpCancellable {
        return client.sendHttpRequest(wrapUrl(url), HttpMethod.DELETE, T::class.java, params, headers, callback)
    }

    private fun initFuelHttpClient(): HttpClient {
        val sslSocketFactory = getSSLSocketFactory(context)
        FuelManager.instance.socketFactory = sslSocketFactory
        if (isDebug) {
            FuelManager.instance.hostnameVerifier = HostnameVerifier { _, _ -> true }
        }
        return FuelHttpClient()
    }

    private fun getSSLSocketFactory(context: Context): SSLSocketFactory {
        // 取到证书的输入流
        val ca = context.assets.open("ca.crt").use {
            CertificateFactory.getInstance("X.509").generateCertificate(it)
        }

        // 创建 Keystore 包含我们的证书
        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
        keyStore.load(null)
        keyStore.setCertificateEntry("anchor", ca)

        // 创建一个 TrustManager 仅把 Keystore 中的证书 作为信任的锚点
        val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(keyStore)
        val trustManagers = trustManagerFactory.trustManagers

        // 用 TrustManager 初始化一个 SSLContext
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, trustManagers, null)
        return sslContext.socketFactory
    }
}