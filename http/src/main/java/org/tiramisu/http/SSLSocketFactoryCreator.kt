package org.tiramisu.http

import android.content.Context
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManagerFactory

abstract class SSLSocketFactoryCreator {

    fun create(context: Context): SSLSocketFactory {
        // 取到证书的输入流
        val ca = context.assets.open(getAssetName()).use {
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

    abstract fun getAssetName(): String
}