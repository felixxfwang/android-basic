package org.tiramisu.http.fuel

import com.github.kittinunf.fuel.core.requests.CancellableRequest
import org.tiramisu.http.HttpCancellable

class FuelCancellable(
    private val request: CancellableRequest
) : HttpCancellable {

    override fun cancel() {
        request.cancel()
    }
}