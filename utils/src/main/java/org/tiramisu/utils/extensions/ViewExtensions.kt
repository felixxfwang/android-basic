package org.tiramisu.utils.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/**
 * @author felixxfwang
 */
class DelayedAnimationRepeater(private val delay: Long): AnimatorListenerAdapter() {

    private val handler = Handler(Looper.getMainLooper())

    override fun onAnimationRepeat(animation: Animator?) {
        animation?.pause()
        handler.postDelayed({ animation?.resume() }, delay)
    }
}

class DelayedAnimatorRepeater(private val delay: Long): AnimatorListenerAdapter() {

    private var isCanceled = false

    override fun onAnimationStart(animation: Animator?) {
        isCanceled = false
    }

    override fun onAnimationCancel(animation: Animator?) {
        isCanceled = true
    }

    override fun onAnimationEnd(animation: Animator?) {
        if (!isCanceled) {
            animation?.startDelay = delay
            animation?.start()
        }
    }
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}