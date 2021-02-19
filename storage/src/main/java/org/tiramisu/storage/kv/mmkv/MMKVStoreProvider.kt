package org.tiramisu.storage.kv.mmkv

import android.app.Application
import com.tencent.mmkv.MMKV
import org.tiramisu.storage.kv.IKVStore
import org.tiramisu.storage.kv.IKVStoreProvider

class MMKVStoreProvider : IKVStoreProvider {
    override fun initialize(application: Application, rootDir: String?) {
        if (rootDir != null) {
            MMKV.initialize(rootDir)
        } else {
            MMKV.initialize(application)
        }
    }

    override fun kvWithId(id: String, mode: Int, cryptKey: String?): IKVStore {
        return MMKVStore(id, mode, cryptKey)
    }
}