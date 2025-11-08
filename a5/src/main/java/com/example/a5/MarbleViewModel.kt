package com.example.a5

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Monitor marble's movement
 */
class MarbleViewModel : ViewModel() {

    // StateFlow for marble's current state
    private val _marbleState = MutableStateFlow(MarbleState())
    val marbleState: StateFlow<MarbleState> = _marbleState.asStateFlow()

    // Variables for physics
    private val friction = 0.98f // 마찰 계수 (값이 낮을수록 빨리 멈춤)
    private val scale = 50f // 센서 값을 속도로 변환하는 스케일

    // Screen Size
    private var screenWidth = 0f
    private var screenHeight = 0f
    private val marbleSize = 40f // 마블 크기 (dp)

    /**
     * Set Screen Size
     */
    fun setScreenSize(width: Float, height: Float) {
        screenWidth = width
        screenHeight = height
    }

    /**
     * Update marble's state based on sensor data
     * @param gravityX
     * @param gravityY
     * @param deltaTime time from last update
     */
    fun updateFromSensor(gravityX: Float, gravityY: Float, deltaTime: Float) {
        val currentState = _marbleState.value

        // Velocity based on gravity
        var newVelocityX = currentState.velocityX + gravityX * scale * deltaTime
        var newVelocityY = currentState.velocityY + gravityY * scale * deltaTime

        // Friction
        newVelocityX *= friction
        newVelocityY *= friction

        // Calculate new location
        var newX = currentState.x + newVelocityX * deltaTime
        var newY = currentState.y + newVelocityY * deltaTime

        // Check Screen boarder and reflection
        if (newX < 0) {
            newX = 0f
            newVelocityX = -newVelocityX * 0.7f
        } else if (newX > screenWidth - marbleSize) {
            newX = screenWidth - marbleSize
            newVelocityX = -newVelocityX * 0.7f
        }

        if (newY < 0) {
            newY = 0f
            newVelocityY = -newVelocityY * 0.7f
        } else if (newY > screenHeight - marbleSize) {
            newY = screenHeight - marbleSize
            newVelocityY = -newVelocityY * 0.7f
        }

        _marbleState.value = MarbleState(
            x = newX,
            y = newY,
            velocityX = newVelocityX,
            velocityY = newVelocityY
        )
    }

    /**
     * Reset marble position
     */
    fun resetPosition() {
        _marbleState.value = MarbleState(
            x = (screenWidth - marbleSize) / 2,
            y = (screenHeight - marbleSize) / 2,
            velocityX = 0f,
            velocityY = 0f
        )
    }
}
