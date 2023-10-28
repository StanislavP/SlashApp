package org.bugwriters.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp

var Shapes by mutableStateOf(
    Shapes(
        small = RoundedCornerShape(1.dp),
        medium = RoundedCornerShape(3.dp),
        large = RoundedCornerShape(5.dp)
    )
)