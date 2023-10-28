package org.bugwriters

import android.util.Log
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.ui.graphics.Color
import com.google.gson.Gson
import org.bugwriters.ui.theme.Green

val customTextSelectionColors = TextSelectionColors(
    handleColor = Green, backgroundColor = Color.Blue.copy(alpha = 0.4f)
)

fun Any.printJsonify() {
    Log.d(
        "PrintJsonify ${this.javaClass.simpleName}",
        Gson().newBuilder().setPrettyPrinting().create().toJson(this)
    )
}