package com.example.a5

/**
 * Data of marble's current state
 * @param x x coordinates of marble (dp)
 * @param y y coordinates of marble (dp)
 * @param velocityX
 * @param velocityY
 */
data class MarbleState(
    val x: Float = 0f,
    val y: Float = 0f,
    val velocityX: Float = 0f,
    val velocityY: Float = 0f
)
