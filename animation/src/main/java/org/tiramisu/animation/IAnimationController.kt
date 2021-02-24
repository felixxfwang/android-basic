package org.tiramisu.animation

interface IAnimationController {

    fun start(from: Float = 0F, listener: IAnimationListener? = null)

    fun animateTo(from: Float = 0F, to: Float, listener: IAnimationListener? = null)
    fun animateTo(from: Float = 0F, to: Float, onAnimationEnd: (()->Unit)) {
        animateTo(from, to, object : IAnimationListener {
            override fun onAnimationEnd() { onAnimationEnd.invoke() }
        })
    }

    fun repeat(from: Float, to: Float, listener: IAnimationListener? = null)

}

interface IAnimationListener {
    fun onAnimationStart() {}
    fun onAnimationEnd() {}
    fun onAnimationRepeat() {}
}