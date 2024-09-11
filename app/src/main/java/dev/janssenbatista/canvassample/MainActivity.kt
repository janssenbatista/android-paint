package dev.janssenbatista.canvassample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.janssenbatista.canvassample.models.Line
import dev.janssenbatista.canvassample.ui.theme.CanvasSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CanvasSampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyCanvas(innerPadding = innerPadding)
                }
            }
        }
    }
}

@Composable
fun MyCanvas(innerPadding: PaddingValues) {
    val lines = remember {
        mutableStateListOf<Line>()
    }
    val selectedColor: MutableState<Color> = remember {
        mutableStateOf(Color.Black)
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(innerPadding)
            .padding(16.dp)
    ) {
        Row(Modifier.weight(1f)) {
            Canvas(modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(end = 8.dp)
                .border(2.dp, color = Color.Black)
                .pointerInput(key1 = Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        val canvasSize = size
                        val clampedStart = Offset(
                            x = (change.position.x - dragAmount.x).coerceIn(
                                0f,
                                canvasSize.width.toFloat()
                            ),
                            y = (change.position.y - dragAmount.y).coerceIn(
                                0f,
                                canvasSize.height.toFloat()
                            )
                        )

                        val clampedEnd = Offset(
                            x = change.position.x.coerceIn(0f, canvasSize.width.toFloat()),
                            y = change.position.y.coerceIn(0f, canvasSize.height.toFloat())
                        )

                        val line = Line(
                            start = clampedStart,
                            end = clampedEnd,
                            color = selectedColor.value
                        )
                        lines.add(line)
                    }

                }) {
                lines.forEach { line ->
                    drawLine(
                        color = line.color,
                        start = line.start,
                        end = line.end,
                        strokeWidth = line.stroke.toPx(),
                        cap = StrokeCap.Round
                    )
                }
            }
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    Modifier
                        .size(20.dp)
                        .background(Color.Black)
                        .border(
                            width = if (selectedColor.value == Color.Black) 4.dp else 0.dp,
                            color = Color.LightGray
                        )
                        .clickable {
                            selectedColor.value = Color.Black
                        }
                )
                Box(
                    Modifier
                        .size(20.dp)
                        .background(Color.Red)
                        .border(
                            width = if (selectedColor.value == Color.Red) 4.dp else 0.dp,
                            color = Color.Black
                        )
                        .clickable {
                            selectedColor.value = Color.Red
                        }
                )
                Box(
                    Modifier
                        .size(20.dp)
                        .background(Color.Green)
                        .border(
                            width = if (selectedColor.value == Color.Green) 4.dp else 0.dp,
                            color = Color.Black
                        )
                        .clickable {
                            selectedColor.value = Color.Green
                        }
                )
                Box(
                    Modifier
                        .size(20.dp)
                        .background(Color.Blue)
                        .border(
                            width = if (selectedColor.value == Color.Blue) 4.dp else 0.dp,
                            color = Color.Cyan
                        )
                        .clickable {
                            selectedColor.value = Color.Blue
                        }
                )
                Box(
                    Modifier
                        .size(20.dp)
                        .background(Color.Yellow)
                        .border(
                            width = if (selectedColor.value == Color.Yellow) 4.dp else 0.dp,
                            color = Color.Black
                        )
                        .clickable {
                            selectedColor.value = Color.Yellow
                        }
                )
                Box(
                    Modifier
                        .size(20.dp)
                        .background(Color(0xFF5C4033))
                        .border(
                            width = if (selectedColor.value == Color(0xFF5C4033)) 4.dp else 0.dp,
                            color = Color.Black
                        )
                        .clickable {
                            selectedColor.value = Color(0xFF5C4033)
                        }
                )
            }
        }
        OutlinedButton(
            onClick = { lines.clear() },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.clean_whiteboard))
        }

    }

}
