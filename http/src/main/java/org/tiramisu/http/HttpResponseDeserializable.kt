package org.tiramisu.http

interface HttpResponseDeserializable<T> {

    fun deserializable(headers: Map<String, String>, body: ByteArray): T
}