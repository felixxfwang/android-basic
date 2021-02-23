package org.tiramisu.animation

interface IAnimationController {

    fun start(from: Float = 0F)

    fun animateTo(from: Float, to: Float)

    fun repeat(from: Float, to: Float)

}