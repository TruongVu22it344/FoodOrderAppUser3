package com.breens.orderfoodapp.feature_tasks.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.breens.orderfoodapp.R
import com.breens.orderfoodapp.feature_tasks.state.TasksScreenUiState
import com.breens.orderfoodapp.theme.Cam
import com.breens.orderfoodapp.theme.Xam

@Composable
fun CardComponent(
    uiState: TasksScreenUiState,
    navController: NavHostController,
) {
    LazyRow(
        contentPadding = PaddingValues(start = 12.dp,top=20.dp, bottom = 20.dp),
        modifier = Modifier.background(color = Color.White)
    ) {
        items(count = uiState.tasks.size) { index ->
            Box(modifier = Modifier
                .padding(end = 12.dp)
                .clickable { }
                .border(width = 0.dp, color = Xam)
                .background(color = Xam)
            ) {
                Column(
                    modifier = Modifier.wrapContentHeight(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Box(
                        contentAlignment = Alignment.BottomCenter

                    ){
                        Image(
                            painter = rememberImagePainter(uiState.tasks[index].image),
                            contentDescription = "Movie Image",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.size(width = 160.dp, height = 170.dp)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentHeight()
                                .padding(4.dp)
                                .align(Alignment.TopEnd)
                                .clickable {

                                }
                            ,
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_add_shopping_cart_24),
                                contentDescription = "cart",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(24.dp)  // Điều chỉnh kích thước icon
                                    .shadow(4.dp)  // Thêm bóng đổ để làm icon nổi bật hơn
                            )

                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = uiState.tasks[index].title,
                        style = MaterialTheme.typography.subtitle1,

                        modifier = Modifier
                            .size(width = 160.dp, height = 40.dp)
                            .padding(start = 7.dp)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Box(
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .border(width = 1.dp, color = Cam, shape = RoundedCornerShape(4.dp))
                            .clip(RoundedCornerShape(4.dp))
                    ) {
                        Text(
                            text = "Mã giảm 15%",
                            fontSize = 12.sp,
                            color = Cam,
                            modifier = Modifier.padding(3.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                }
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}