package com.example.verygoodtaskplanner.presentation.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet

class LinedEditText(context: Context, attributes: AttributeSet) :
    androidx.appcompat.widget.AppCompatEditText(context, attributes) {

    override fun onDraw(canvas: Canvas) {
        val bounds = Rect()
        val firstLineY = getLineBounds(0, bounds)
        val lineHeight = lineHeight
        val totalLines = lineCount.coerceAtLeast(height / lineHeight)
        for (i in 0 until totalLines) {
            val lineY = firstLineY + i * lineHeight
            canvas.drawLine(
                bounds.left.toFloat(),
                lineY.toFloat(),
                bounds.right.toFloat(),
                lineY.toFloat(),
                linePaint!!
            )
        }
        super.onDraw(canvas)
    }

    companion object {
        private var linePaint: Paint? = null

        init {
            linePaint = Paint()
            linePaint?.color = Color.BLACK
            linePaint?.style = Paint.Style.STROKE
        }
    }
}