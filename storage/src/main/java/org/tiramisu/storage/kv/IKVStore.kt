package org.tiramisu.storage.kv

import android.os.Parcelable

interface IKVStore {

    companion object {
        const val SINGLE_PROCESS_MODE = 1
        const val MULTI_PROCESS_MODE = 2
    }

    fun putString(key: String, value: String): IKVStore

    fun putStringSet(key: String, values: Set<String>): IKVStore

    fun putInt(key: String, value: Int): IKVStore

    fun putLong(key: String, value: Long): IKVStore

    fun putFloat(key: String, value: Float): IKVStore

    fun putBoolean(key: String, value: Boolean): IKVStore

    fun <T: Parcelable> put(key: String, value: T): IKVStore

    fun put(key: String, value: ByteArray): IKVStore

    fun remove(key: String): IKVStore

    fun clear(): IKVStore

    fun commit(): Boolean

    fun apply()

    fun getString(key: String, defaultValue: String): String
    fun getStringSet(key: String, defaultValue: Set<String>): Set<String>
    fun getInt(key: String, defaultValue: Int): Int
    fun getLong(key: String, defaultValue: Long): Long
    fun getFloat(key: String, defaultValue: Float): Float
    fun getBoolean(key: String, defaultValue: Boolean): Boolean
    fun <T: Parcelable> getParcelable(key: String, defaultValue: T): T
    fun <T: Parcelable> getParcelable(key: String, clazz: Class<T>): T?
    fun getByteArray(key: String, defaultValue: ByteArray): ByteArray
}