package org.tiramisu.serializable

import android.app.Application
import com.twitter.serial.serializer.CoreSerializers
import com.twitter.serial.serializer.ObjectSerializer
import com.twitter.serial.stream.bytebuffer.ByteBufferSerial
import java.io.File
import java.io.Serializable
import kotlin.properties.Delegates

object TiramisuSerializer {

    var application: Application by Delegates.notNull()

    private var baseDir: File by Delegates.notNull()

    fun initialize(app: Application, baseDir: File) {
        this.application = app
        this.baseDir = baseDir
    }

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

    fun <T: Serializable> write(key: String, data: T) {
        getFile(key).writeBytes(ByteBufferSerial().toByteArray(data, CoreSerializers.getSerializableSerializer()))
    }

    fun <T: Serializable> read(key: String, default: T): T {
        return read(key) ?: default
    }

    fun <T: Serializable> read(key: String): T? {
        val file = getFile(key)
        return if (file.exists()) {
            ByteBufferSerial().fromByteArray(file.readBytes(), CoreSerializers.getSerializableSerializer())
        } else null
    }

    private fun getFile(key: String): File {
        if (!baseDir.exists()) {
            baseDir.mkdirs()
        }
        return File(baseDir, key)
    }
}