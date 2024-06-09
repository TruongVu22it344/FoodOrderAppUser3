package com.breens.orderfoodapp.feature_tasks.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.breens.orderfoodapp.feature_tasks.state.CardsScreenUiState
import java.text.DecimalFormat

@Composable
fun FavoriteFoodComponent(
    uiStateCard: CardsScreenUiState
){
    Column {
        Row(
            modifier = Modifier.padding(
                horizontal = 12.dp, vertical = 10.dp
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            androidx.compose.material.Text(
                text = "Danh sách yêu thích",
                style = MaterialTheme.typography.h6
            )
        }
        LazyColumn(contentPadding = PaddingValues(12.dp)) {
            items(uiStateCard.cards.size) { index ->
                if(uiStateCard.cards[index].favorite == 1){
                    Column {
                        Column(

                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Row(

                                verticalAlignment = Alignment.CenterVertically,

                                modifier = Modifier.padding(12.dp),
                            ) {
                                Image(
                                    painter = rememberImagePainter(uiStateCard.cards[index].imageCard),
                                    contentDescription = "Food Image",
                                    contentScale = ContentScale.FillBounds,
                                    modifier = Modifier
                                        .size(width = 100.dp, height = 90.dp)
                                        .padding(end = 10.dp)
                                        .border(
                                            width = 1.dp,
                                            color = Color.White,
                                            shape = RoundedCornerShape(15.dp)
                                        )
                                        .clip(RoundedCornerShape(15.dp))
                                        .background(color = Color.White)
                                )
                                Spacer(modifier = Modifier.width(15.dp))
                                Column(

                                    modifier = Modifier.weight(0.7f),
                                ) {
                                    Text(
                                        text = uiStateCard.cards[index].titleCard,
                                        color = Color.Black,
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = uiStateCard.cards[index].bodyCard,
                                        color = Color.Black,
                                        fontSize = 15.sp,
                                    )
                                    Text(
                                        text = "${DecimalFormat("#,###").format(uiStateCard.cards[index].priceCard)}đ",
                                        color = Color.Red,
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                }
                                Column(modifier = Modifier.padding(top= 52.dp),
                                ) {

                                    Icon(
                                        imageVector = Icons.Default.Favorite,
                                        contentDescription = "heart",
                                        tint = Color.Red
                                    )

                                }



                            }
                            Spacer(modifier = Modifier.height(40.dp))

                        }

                    }
                }
            }
        }
    }

}