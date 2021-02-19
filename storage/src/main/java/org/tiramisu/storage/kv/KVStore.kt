package org.tiramisu.storage.kv

import org.tiramisu.storage.kv.IKVStoreProvider
import org.tiramisu.storage.kv.mmkv.MMKVStoreProvider

object KVStore : IKVStoreProvider by MMKVStoreProvider()