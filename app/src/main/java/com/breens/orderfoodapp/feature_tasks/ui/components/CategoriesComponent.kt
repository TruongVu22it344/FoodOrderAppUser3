package com.breens.orderfoodapp.feature_tasks.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.breens.orderfoodapp.feature_tasks.state.CatesScreenUiState

@Composable
fun CategoriesComponent(uiStateCate: CatesScreenUiState, navController: NavHostController) {


    Column(
        modifier = Modifier.background(color = Color.White)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = "Danh mục",
                style = MaterialTheme.typography.h6,
            )
            TextButton(onClick = { }) {
                Text(text = "Xem tất cả")
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Column(
            /// order matters
            modifier = Modifier

                .clickable { }
                .padding(12.dp)
        ) {
            LazyHorizontalGrid(rows = GridCells.Fixed(2),
                modifier = Modifier.height(200.dp)
            ) {

                items(uiStateCate.cates.size) { index ->
                    Column(
                        Modifier
                            .padding(end = 20.dp)
                            .background(color = Color.White)
                            .clickable {
                                navController.navigate("DetailCateComponent/${uiStateCate.cates[index].titleCate}")
                            }
                    ) {
                        Image(painter = rememberImagePainter(uiStateCate.cates[index].imageCate), contentDescription ="icon_1", modifier = Modifier
                            .padding(bottom = 10.dp)
                            .size(width = 40.dp, height = 40.dp)
                            .border(
                                width = 1.dp,
                                color = Color.White,
                                shape = RoundedCornerShape(50.dp)
                            )
                            .clip(RoundedCornerShape(50.dp))


                        )

                        Text(text = uiStateCate.cates[index].titleCate, style = MaterialTheme.typography.caption, modifier = Modifier.width(60.dp))

                    }
                }
            }
        }


    }
    Spacer(modifier = Modifier.height(16.dp))
}