package org.tiramisu.utils.extensions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * @author felixxfwang
 */
fun <T> MutableLiveData<T>.observeOnce(observer: Observer<T>) {
    val liveData = this
    liveData.observeForever(object : Observer<T> {
        override fun onChanged(t: T) {
            observer.onChanged(t)
            liveData.removeObserver(this)
        }
    })
}