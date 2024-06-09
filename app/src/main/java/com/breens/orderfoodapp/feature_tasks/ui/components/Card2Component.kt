package com.breens.orderfoodapp.feature_tasks.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.breens.orderfoodapp.R
import com.breens.orderfoodapp.data.model.Card
import com.breens.orderfoodapp.feature_tasks.state.CardsScreenUiState
import com.breens.orderfoodapp.theme.Xam

@Composable
fun Card2Component(
    uiStateCard: CardsScreenUiState,
    navController: NavHostController,
    setCardFavorite: (Int) -> Unit,
    updateFavorite: (Card) -> Unit,
    saveFavorite: () -> Unit,
    /*setImage:(String)->Unit,
    setTitle: (String) -> Unit,
    setBody: (String) -> Unit,
    setPrice: (Int) -> Unit,*/
    addCart: (Card) -> Unit,
    saveCart: () -> Unit
) {
    Column() {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = "Đề xuất cho bạn",
                style = MaterialTheme.typography.h6,
            )
            TextButton(onClick = { }) {
                Text(text = "Xem tất cả")
            }
        }
        LazyRow(
            contentPadding = PaddingValues(start = 12.dp,top=20.dp, bottom = 20.dp),
            modifier = Modifier.background(color = Color.White)
        ) {
            items(count = uiStateCard.cards.size) { index ->
                /*setImage(uiStateCard.cards[index].imageCard)
                setTitle(uiStateCard.cards[index].titleCard)
                setBody(uiStateCard.cards[index].bodyCard)
                setPrice(uiStateCard.cards[index].priceCard)*/

                Box(modifier = Modifier
                    .padding(end = 12.dp)
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
                                painter = rememberImagePainter(uiStateCard.cards[index].imageCard),
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
                                        addCart(uiStateCard.cards[index])
                                        saveCart()
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
                            text = uiStateCard.cards[index].titleCard,
                            style = MaterialTheme.typography.subtitle1,

                            modifier = Modifier
                                .size(width = 160.dp, height = 40.dp)
                                .padding(start = 7.dp)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .padding(start = 3.dp)
                        ) {
                            Row( verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_star_24),
                                    contentDescription = "star",
                                    tint = Color.Yellow
                                )
                                Text(text = "4.8 (${uiStateCard.cards[index].views} views)")
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            if(uiStateCard.cards[index].favorite == 1){

                                Icon(
                                    imageVector = Icons.Filled.Favorite,
                                    contentDescription = "heart",
                                    tint = Color.Red,
                                    modifier = Modifier.clickable {
                                        updateFavorite(uiStateCard.cards[index])
                                        setCardFavorite(0)
                                        saveFavorite()
                                    }
                                )

                            }else{

                                Icon(
                                    imageVector = Icons.Default.FavoriteBorder,
                                    contentDescription = "heart",
                                    modifier = Modifier.clickable {
                                        updateFavorite(uiStateCard.cards[index])
                                        setCardFavorite(1)
                                        saveFavorite()
                                    }
                                )
                            }



                        }

                    }
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
}