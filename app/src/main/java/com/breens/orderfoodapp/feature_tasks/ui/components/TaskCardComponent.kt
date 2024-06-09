package com.breens.orderfoodapp.feature_tasks.ui.components
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.layout.lerp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.breens.orderfoodapp.feature_tasks.state.TasksScreenUiState
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalPagerApi::class
)
@Composable
fun TaskCardComponent(
    uiState: TasksScreenUiState,
    navController: NavHostController,
    ) {
    Column( modifier = Modifier

        .background(color = Color.White)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            androidx.compose.material.Text(
                text = "Bộ sưu tập",
                style = androidx.compose.material.MaterialTheme.typography.h6,
            )
            androidx.compose.material.TextButton(onClick = { }) {
                androidx.compose.material.Text(text = "Xem tất cả")
            }
        }
        Spacer(modifier = Modifier.height(14.dp))

        com.google.accompanist.pager.HorizontalPager(
            count = uiState.tasks.size,
            contentPadding = PaddingValues(start = 48.dp, end = 48.dp)
        ) { page ->

            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                        lerp(
                            start = ScaleFactor(1f, 0.85f),
                            stop = ScaleFactor(1f, 1f),
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale.scaleX
                            scaleY = scale.scaleY
                        }
                    }
                    .clickable {
                        navController.navigate("DetailScreen/${uiState.tasks[page].taskId}")
                    },

                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.clip(RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.BottomCenter

                ) {
                    Image(
                        painter = rememberImagePainter(uiState.tasks[page].image),
                        contentDescription = "Movie Image",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxWidth(fraction = 0.85f)
                            .height(280.dp)
                    )
                    Box(
                        modifier = Modifier
                            .graphicsLayer {
                                val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                                val translation = pageOffset.coerceIn(0f, 1f)

                                translationY = translation * 200
                            }
                            .fillMaxWidth(fraction = 0.85f)
                            .wrapContentHeight()
                            .background(
                                com.example.movieui.core.theme.BlueVariant
                            )
                            .padding(vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        androidx.compose.material.Text(
                            "Mua ngay",
                            style = androidx.compose.material.MaterialTheme.typography.subtitle1.copy(
                                color = com.example.movieui.core.theme.Yellow,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = uiState.tasks[page].title,
                    style = androidx.compose.material.MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }

}








