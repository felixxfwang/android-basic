package org.tiramisu.storage.kv.mmkv

import com.tencent.mmkv.MMKV
import org.tiramisu.storage.kv.IKVStore

class MMKVStore(
    id: String,
    mode: Int = IKVStore.SINGLE_PROCESS_MODE,
    cryptKey: String? = null
) : IKVStore {

    private val kv = MMKV.mmkvWithID(id, mode, cryptKey)

    override fun putString(key: String, value: String): IKVStore {
        kv.encode(key, value)
        return this
    }

    override fun putStringSet(key: String, values: Set<String>): IKVStore {
        kv.encode(key, values)
        return this
    }

    override fun putInt(key: String, value: Int): IKVStore {
        kv.encode(key, value)
        return this
    }

    override fun putLong(key: String, value: Long): IKVStore {
        kv.encode(key, value)
        return this
    }

    override fun putFloat(key: String, value: Float): IKVStore {
        kv.encode(key, value)
        return this
    }

    override fun putBoolean(key: String, value: Boolean): IKVStore {
        kv.encode(key, value)
        return this
    }

    override fun remove(key: String): IKVStore {
        kv.removeValueForKey(key)
        return this
    }

    override fun clear(): IKVStore {
        kv.clearAll()
        return this
    }

    override fun commit(): Boolean {
        kv.sync()
        return true
    }

    override fun apply() {
        kv.async()
    }

    override fun getString(key: String, defaultValue: String): String {
        return kv.getString(key, defaultValue) ?: defaultValue
    }

    override fun getStringSet(key: String, defaultValue: Set<String>): Set<String> {
        return kv.getStringSet(key, defaultValue) ?: defaultValue
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return kv.getInt(key, defaultValue)
    }

    override fun getLong(key: String, defaultValue: Long): Long {
        return kv.getLong(key, defaultValue)
    }

    override fun getFloat(key: String, defaultValue: Float): Float {
        return kv.getFloat(key, defaultValue)
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return kv.getBoolean(key, defaultValue)
    }

}