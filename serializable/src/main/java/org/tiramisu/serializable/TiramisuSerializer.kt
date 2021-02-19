package org.tiramisu.serializable

import com.twitter.serial.serializer.ObjectSerializer
import com.twitter.serial.stream.bytebuffer.ByteBufferSerial
import java.io.File

object TiramisuSerializer {

    fun <T> writeToFile(filePath: String, data: T, serializer: ObjectSerializer<T>) {
        writeToFile(File(filePath), data, serializer)
    }

    fun <T> writeToFile(file: File, data: T, serializer: ObjectSerializer<T>) {
        file.writeBytes(ByteBufferSerial().toByteArray(data, serializer))
    }

    fun <T> readFromFile(file: File, serializer: ObjectSerializer<T>): T? {
        return if (file.exists()) {
            ByteBufferSerial().fromByteArray(file.readBytes(), serializer)
        } else null
    }
}