package org.tiramisu.storage.kv

import android.app.Application
import org.tiramisu.storage.kv.IKVStore

interface IKVStoreProvider {

    fun initialize(application: Application, rootDir: String? = null)

    fun kvWithId(id: String,
                 mode: Int = IKVStore.SINGLE_PROCESS_MODE,
                 cryptKey: String? = null): IKVStore
}