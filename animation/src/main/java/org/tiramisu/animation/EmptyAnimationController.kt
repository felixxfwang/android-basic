package org.tiramisu.animation

class EmptyAnimationController : IAnimationController {
    override fun showFirstFrame() {
    }

    override fun showLastFrame() {
    }

    override fun start(from: Float, listener: IAnimationListener?) {
    }

    override fun animateTo(from: Float, to: Float, listener: IAnimationListener?) {
    }

    override fun repeat(from: Float, to: Float, listener: IAnimationListener?) {
    }

}