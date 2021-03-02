package org.tiramisu.http

interface HttpResponseDeserializable<T> {

    fun deserialize(headers: Map<String, String>, body: ByteArray): T
}