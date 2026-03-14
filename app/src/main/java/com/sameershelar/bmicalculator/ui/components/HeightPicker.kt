package com.sameershelar.bmicalculator.ui.components

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs
import kotlin.math.roundToInt

sealed class DegreeLineType {
    object TenTypeLine : DegreeLineType()

    object FiveTypeLine : DegreeLineType()

    object NormalTypeLine : DegreeLineType()
}

data class PickerStyle(
    var modifier: Modifier = Modifier,
    var pickerWidth: Dp = 150.dp,
    var minHeight: Int = 100,
    var maxHeight: Int = 300,
    var initialHeight: Int = 150,
    var normalTypeLineColor: Int = Color.LTGRAY,
    var tenTypeLineColor: Int = Color.BLACK,
    var fiveTypeLineColor: Int = Color.RED,
    var normalTypeLineHeight: Int = 28,
    var tenTypeLineHeight: Int = 50,
    var fiveTypeLineHeight: Int = 38,
    var spaceInterval: Int = 36,
    var numberPadding: Int = 28,
    var lineStroke: Float = 6f,
)

@Composable
fun HeightPicker(
    pickerStyle: PickerStyle,
    onHeightChange: (Int) -> Unit = {},
) {
    var targetDistant by remember {
        mutableFloatStateOf(0f)
    }

    var startDragPoint by remember {
        mutableFloatStateOf(0f)
    }

    var oldDragPoint by remember {
        mutableFloatStateOf(0f)
    }

    var selectedHeight by remember {
        mutableIntStateOf(0)
    }

    BoxWithConstraints(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(200.dp),
    ) {
        Canvas(
            modifier =
                Modifier
                    .fillMaxSize()
                    .align(Alignment.TopCenter)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = {
                                startDragPoint = it.x
                            },
                            onDragEnd = {
                                oldDragPoint = targetDistant
                            },
                        ) { change, _ ->
                            val newDistance = oldDragPoint + (change.position.x - startDragPoint)
                            targetDistant =
                                newDistance.coerceIn(
                                    minimumValue =
                                        (
                                            (pickerStyle.initialHeight) * pickerStyle.spaceInterval -
                                                pickerStyle.maxHeight * pickerStyle.spaceInterval
                                        ).toFloat(),
                                    maximumValue =
                                        (
                                            (pickerStyle.initialHeight) * pickerStyle.spaceInterval -
                                                pickerStyle.minHeight * pickerStyle.spaceInterval
                                        ).toFloat(),
                                )
                        }
                    },
        ) {
            val middlePoint = Offset(x = maxWidth.toPx() / 2f, y = maxHeight.toPx() / 2f)

            drawContext.canvas.nativeCanvas.apply {
                val pickerLinesPath =
                    Path().apply {
                        moveTo(0f, middlePoint.y - pickerStyle.pickerWidth.toPx() / 2)
                        lineTo(
                            constraints.maxWidth.toFloat(),
                            middlePoint.y - pickerStyle.pickerWidth.toPx() / 2,
                        )
                        moveTo(0f, middlePoint.y + pickerStyle.pickerWidth.toPx() / 2)
                        lineTo(
                            constraints.maxWidth.toFloat(),
                            middlePoint.y + pickerStyle.pickerWidth.toPx() / 2,
                        )
                    }

                drawPath(
                    pickerLinesPath,
                    Paint().apply {
                        this.style = Paint.Style.STROKE
                        this.strokeWidth = pickerStyle.lineStroke
                        this.color = pickerStyle.normalTypeLineColor
                        this.setShadowLayer(86f, 0f, 0f, Color.LTGRAY)
                    },
                )

                val indicator =
                    Path().apply {
                        moveTo(middlePoint.x, (middlePoint.y + 10f))
                        lineTo((middlePoint.x - 2f), middlePoint.y + pickerStyle.pickerWidth.toPx() / 2)
                        moveTo(middlePoint.x, (middlePoint.y + 10f))
                        lineTo((middlePoint.x + 2f), middlePoint.y + pickerStyle.pickerWidth.toPx() / 2)
                        fillType = Path.FillType.EVEN_ODD
                    }

                drawPath(
                    indicator,
                    Paint().apply {
                        this.color = pickerStyle.normalTypeLineColor
                        this.style = Paint.Style.FILL_AND_STROKE
                        this.strokeWidth = pickerStyle.lineStroke
                        this.isAntiAlias = true
                    },
                )

                for (height in pickerStyle.minHeight..pickerStyle.maxHeight) {
                    val degreeLineScaleX =
                        middlePoint.x + (pickerStyle.spaceInterval * (height - pickerStyle.initialHeight.toFloat()) + targetDistant)
                    val lineType =
                        when {
                            height % 10 == 0 -> DegreeLineType.TenTypeLine
                            height % 5 == 0 -> DegreeLineType.FiveTypeLine
                            else -> DegreeLineType.NormalTypeLine
                        }

                    val lineColor =
                        when (lineType) {
                            DegreeLineType.TenTypeLine -> pickerStyle.tenTypeLineColor
                            DegreeLineType.FiveTypeLine -> pickerStyle.fiveTypeLineColor
                            else -> pickerStyle.normalTypeLineColor
                        }

                    val lineHeightSize =
                        when (lineType) {
                            DegreeLineType.TenTypeLine -> pickerStyle.tenTypeLineHeight
                            DegreeLineType.FiveTypeLine -> pickerStyle.fiveTypeLineHeight
                            else -> pickerStyle.normalTypeLineHeight
                        }

                    this.drawLine(
                        degreeLineScaleX,
                        middlePoint.y - pickerStyle.pickerWidth.toPx() / 2 + 4,
                        degreeLineScaleX,
                        middlePoint.y - pickerStyle.pickerWidth.toPx() / 2 + lineHeightSize * 2,
                        Paint().apply {
                            this.style = Paint.Style.STROKE
                            this.strokeWidth = pickerStyle.lineStroke
                            this.color = lineColor
                            this.isAntiAlias = true
                        },
                    )

                    if (abs(middlePoint.x - degreeLineScaleX.roundToInt()) < 5) {
                        selectedHeight = height
                        onHeightChange(selectedHeight)
                    }

                    if (lineType == DegreeLineType.TenTypeLine) {
                        val textBound = Rect()
                        Paint().getTextBounds(
                            abs(height).toString(),
                            0,
                            height.toString().length,
                            textBound,
                        )

                        drawText(
                            abs(height).toString(),
                            (degreeLineScaleX) - textBound.width() / 2,
                            middlePoint.y - pickerStyle.pickerWidth.toPx() / 2 + lineHeightSize * 2 + textBound.height() * 2 +
                                pickerStyle.numberPadding,
                            Paint().apply {
                                this.textSize = 20.sp.toPx()
                                this.textAlign = Paint.Align.CENTER
                                this.color = pickerStyle.normalTypeLineColor
                                this.style = Paint.Style.FILL
                                this.isAntiAlias = true
                            },
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewPickerScreen() {
    HeightPicker(pickerStyle = PickerStyle())
}
