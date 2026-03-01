package com.karsatech.mypokedex.core.common.ui.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import androidx.palette.graphics.Palette
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.allowHardware
import coil3.request.crossfade
import coil3.toBitmap
import com.karsatech.mypokedex.core.common.ui.component.attr.PokeImageAttr.getImageUrl
import kotlin.random.Random

@Composable
fun PokeCard(
    id: Int,
    name: String,
    modifier: Modifier = Modifier
) {
    var dominantColor by remember { mutableStateOf(Color(0xFFF5F5F5)) }
    var imageBitmap by remember { mutableStateOf<android.graphics.Bitmap?>(null) }
    val isDarkTheme = isSystemInDarkTheme()
    val offsetY = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        val randomDuration = Random.nextInt(2000, 5000)

        offsetY.animateTo(
            targetValue = 8f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = randomDuration, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    LaunchedEffect(imageBitmap) {
        imageBitmap?.let { bitmap ->
            Palette.from(bitmap).generate { palette ->
                palette?.dominantSwatch?.rgb?.let { colorValue ->
                    val hsl = FloatArray(3)
                    ColorUtils.colorToHSL(colorValue, hsl)

                    if (isDarkTheme) {
                        hsl[2] = (hsl[2] * 0.5f).coerceIn(0f, 1f)
                    } else {
                        hsl[2] = (hsl[2] * 1.3f).coerceIn(0f, 1f)
                    }

                    val adjustedColor = ColorUtils.HSLToColor(hsl)
                    dominantColor = Color(adjustedColor)
                }
            }
        }
    }

    Card(
        modifier = modifier
            .width(160.dp)
            .height(220.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = dominantColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(getImageUrl(id))
                    .allowHardware(false)
                    .crossfade(true)
                    .build(),
                contentDescription = name,
                modifier = Modifier
                    .size(100.dp)
                    .graphicsLayer {
                        translationY = offsetY.value.dp.toPx()
                    },
                onSuccess = { success ->
                    imageBitmap = success.result.image.toBitmap()
                }
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = id.toString(), fontSize = 14.sp)
            }
        }
    }
}

@Preview
@Composable
private fun PokeCardPreview() {
    PokeCard(
        id = 1,
        name = "Bulbasaur",
    )
}