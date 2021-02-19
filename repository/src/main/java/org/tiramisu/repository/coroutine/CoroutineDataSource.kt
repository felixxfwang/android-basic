package org.tiramisu.repository.coroutine

import org.tiramisu.repository.DataResult

interface CoroutineDataSource<P, D> {
    suspend fun loadData(param: P): DataResult<D>
}
