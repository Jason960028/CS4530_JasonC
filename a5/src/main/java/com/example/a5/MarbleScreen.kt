package com.example.a5

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

/**
 * Screen that marble's move
 */
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun MarbleScreen(viewModel: MarbleViewModel) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        val density = LocalDensity.current

        // Change screen size dp -> Float
        val screenWidthDp = with(density) { maxWidth.toPx() / density.density }
        val screenHeightDp = with(density) { maxHeight.toPx() / density.density }

        // Screen size to View Model
        LaunchedEffect(screenWidthDp, screenHeightDp) {
            viewModel.setScreenSize(screenWidthDp, screenHeightDp)
            viewModel.resetPosition()
        }

        val marbleState by viewModel.marbleState.collectAsState()

        // Display Marble
        Box(
            modifier = Modifier
                .offset(x = marbleState.x.dp, y = marbleState.y.dp)
                .size(40.dp)
                .background(Color.Blue, CircleShape)
        )
    }
}
