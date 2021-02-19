package org.tiramisu.repository.coroutine

import kotlinx.coroutines.Dispatchers
import org.tiramisu.repository.DataResult
import kotlin.coroutines.CoroutineContext

interface CoroutineDataClient<PARAM, RESULT> {
    /**
     * 协程请求
     */
    suspend fun sendRequest(
        param: PARAM,
        scope: CoroutineContext = Dispatchers.IO
    ): DataResult<RESULT>
}