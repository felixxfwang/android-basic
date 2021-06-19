package org.tiramisu.utils.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 *
 *
 * @author felixxfwang
 */
object LiveDataTransformations {

    fun <X1, X2, Y> map(data1: LiveData<X1>, data2: LiveData<X2>, mapFunction: (X1?, X2?) -> Y): LiveData<Y> {
        val result = MediatorLiveData<Y>()
        result.addSource(data1) { x -> result.setValue(mapFunction(x, data2.value)) }
        result.addSource(data2) { x -> result.setValue(mapFunction(data1.value, x)) }
        return result
    }

}