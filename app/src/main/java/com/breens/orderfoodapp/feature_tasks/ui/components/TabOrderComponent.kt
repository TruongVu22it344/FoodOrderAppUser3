package com.breens.orderfoodapp.feature_tasks.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.breens.orderfoodapp.R
import com.breens.orderfoodapp.data.model.Order
import com.breens.orderfoodapp.data.model.TabItemsOrder
import com.breens.orderfoodapp.feature_tasks.state.OrderScreenUiState
import com.breens.orderfoodapp.feature_tasks.state.SignInScreenUiState
import com.breens.orderfoodapp.theme.Green
import java.text.DecimalFormat

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TabOrderComponent(
    navControllerNotes: NavController,
    uiStateAccount: SignInScreenUiState,
    uiStateOrder: OrderScreenUiState,
    updateStatus: (Order) -> Unit,
    setOrderStatus: (String) -> Unit,
    saveStatus: () -> Unit,


){
    val tabItemsOrder = listOf(
        TabItemsOrder(
            id = 1,
            title = "Chờ xác nhận",

            ),
        TabItemsOrder(
            id = 2,
            title = "Đang giao",

            ),
        TabItemsOrder(
            id = 3,
            title = "Hoàn thành",

            ),
        TabItemsOrder(
            id = 4,
            title = "Đã hủy",

            ),
    )
    var selectedTabIndexOrder by remember {
        mutableStateOf(0)
    }
    val pagerStateOrder = rememberPagerState {
        tabItemsOrder.size
    }
    var userID by remember {
        mutableStateOf("")
    }
    LazyColumn(contentPadding = PaddingValues(12.dp)){
        items(uiStateAccount.accounts.size) { indexAccount ->

            userID = uiStateAccount.accounts[indexAccount].userID


        }
    }
    LaunchedEffect(selectedTabIndexOrder){
        pagerStateOrder.animateScrollToPage(selectedTabIndexOrder)
    }
    LaunchedEffect(pagerStateOrder.currentPage){
        selectedTabIndexOrder = pagerStateOrder.currentPage
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),

        ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 16.dp, vertical = 8.dp
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "Quản lý đơn đặt", style = MaterialTheme.typography.titleSmall)
        }
        TabRow(

            selectedTabIndex = selectedTabIndexOrder
        ) {
            tabItemsOrder.forEachIndexed { index, item ->
                Tab(
                    modifier = Modifier
                        .background(color = Color.White)
                        .height(63.dp),
                    selected = index == selectedTabIndexOrder,
                    onClick = { selectedTabIndexOrder = index },
                    text = {
                        Text(
                            text = item.title,
                            fontSize = 15.sp,
                            modifier = Modifier,
                            color = if (index == selectedTabIndexOrder) {
                                Color.Red
                            } else {
                                Color.Gray
                            }
                        )

                    },
                )
            }
        }
        HorizontalPager(
            state = pagerStateOrder,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) { page ->

            Box(
                modifier = Modifier.fillMaxSize(),

                ) {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = tabItemsOrder[page].id.toString()
                ) {
                    composable(route = tabItemsOrder[page].id.toString()) {
                        when (tabItemsOrder[page].id) {

                                1 -> LazyColumn(contentPadding = PaddingValues(12.dp)) {
                                   items(uiStateOrder.orders.size) { index ->
                                       if( uiStateOrder.orders[index].userID == userID) {
                                           if(uiStateOrder.orders[index].status == "0"){
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
                                                               painter = rememberImagePainter(uiStateOrder.orders[index].imageOrder),
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
                                                           )
                                                           Spacer(modifier = Modifier.width(15.dp))
                                                           Column(

                                                               modifier = Modifier.weight(0.7f),
                                                           ) {
                                                               Text(
                                                                   text = uiStateOrder.orders[index].titleOrder,
                                                                   color = Color.Black,
                                                                   fontSize = 20.sp,
                                                                   fontWeight = FontWeight.Bold
                                                               )

                                                               Row(
                                                                   modifier = Modifier.padding(top=15.dp)
                                                               ) {
                                                                   androidx.compose.material.Icon(
                                                                       painter = painterResource(id = R.drawable.baseline_location_on_24),
                                                                       contentDescription = "Movie Image",
                                                                       modifier = Modifier
                                                                           .size(40.dp)
                                                                           .padding(end = 10.dp)
                                                                           .border(
                                                                               width = 1.dp,
                                                                               color = Color.White,
                                                                               shape = RoundedCornerShape(
                                                                                   50.dp
                                                                               )
                                                                           )
                                                                           .clip(RoundedCornerShape(50.dp)),
                                                                       tint = Color.Red

                                                                   )
                                                                   Text(
                                                                       text = uiStateOrder.orders[index].address,
                                                                       color = Color.Black,
                                                                       fontSize = 13.sp,
                                                                   )



                                                               }
                                                               Spacer(modifier = Modifier.height(2.dp))
                                                               Text(
                                                                   text = "${DecimalFormat("#,###").format(uiStateOrder.orders[index].total)}đ",
                                                                   color = Color.Red,
                                                                   fontSize = 22.sp,
                                                                   fontWeight = FontWeight.Bold
                                                               )
                                                               Spacer(modifier = Modifier.height(2.dp))
                                                           }


                                                       }
                                                       Spacer(modifier = Modifier.height(10.dp))
                                                       Row()
                                                       {
                                                           Button(
                                                               onClick = {
                                                                   updateStatus(uiStateOrder.orders[index])
                                                                   setOrderStatus("3")
                                                                   saveStatus()
                                                               },
                                                               colors = ButtonDefaults.buttonColors(Color.White),
                                                               modifier = Modifier

                                                                   .border(
                                                                       width = 2.dp,
                                                                       color = Color.LightGray,
                                                                       shape = RoundedCornerShape(30.dp)
                                                                   )
                                                                   .height(45.dp)
                                                                   .padding(horizontal = 12.dp)
                                                           ) {
                                                               Text(
                                                                   text = "Hủy đơn",
                                                                   color = Color.Black
                                                               )

                                                           }
                                                           Button(
                                                               onClick = { /*TODO*/ },
                                                               colors = ButtonDefaults.buttonColors(Green),
                                                               modifier = Modifier

                                                                   .clip(RoundedCornerShape(30.dp))
                                                                   .height(45.dp)
                                                                   .padding(horizontal = 12.dp)
                                                           ) {
                                                               Text(
                                                                   text = "Xem chi tiết",

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

                            2 ->

                                Column {
                                    LazyColumn(contentPadding = PaddingValues(12.dp)) {
                                        items(uiStateOrder.orders.size) { index ->
                                            if( uiStateOrder.orders[index].userID == userID){
                                                if(uiStateOrder.orders[index].status == "1"){
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
                                                                    painter = rememberImagePainter(uiStateOrder.orders[index].imageOrder),
                                                                    contentDescription = "Food Image",
                                                                    contentScale = ContentScale.FillBounds,
                                                                    modifier = Modifier
                                                                        .size(
                                                                            width = 100.dp,
                                                                            height = 90.dp
                                                                        )
                                                                        .padding(end = 10.dp)
                                                                        .border(
                                                                            width = 1.dp,
                                                                            color = Color.White,
                                                                            shape = RoundedCornerShape(
                                                                                15.dp
                                                                            )
                                                                        )
                                                                        .clip(RoundedCornerShape(15.dp))
                                                                )
                                                                Spacer(modifier = Modifier.width(15.dp))
                                                                Column(

                                                                    modifier = Modifier.weight(0.7f),
                                                                ) {
                                                                    Text(
                                                                        text = uiStateOrder.orders[index].titleOrder,
                                                                        color = Color.Black,
                                                                        fontSize = 20.sp,
                                                                        fontWeight = FontWeight.Bold
                                                                    )

                                                                    Row(
                                                                        modifier = Modifier.padding(top=15.dp)
                                                                    ) {
                                                                        androidx.compose.material.Icon(
                                                                            painter = painterResource(id = R.drawable.baseline_location_on_24),
                                                                            contentDescription = "Movie Image",
                                                                            modifier = Modifier
                                                                                .size(40.dp)
                                                                                .padding(end = 10.dp)
                                                                                .border(
                                                                                    width = 1.dp,
                                                                                    color = Color.White,
                                                                                    shape = RoundedCornerShape(
                                                                                        50.dp
                                                                                    )
                                                                                )
                                                                                .clip(
                                                                                    RoundedCornerShape(
                                                                                        50.dp
                                                                                    )
                                                                                ),
                                                                            tint = Color.Red

                                                                        )
                                                                        Text(
                                                                            text = uiStateOrder.orders[index].address,
                                                                            color = Color.Black,
                                                                            fontSize = 13.sp,
                                                                        )



                                                                    }
                                                                    Spacer(modifier = Modifier.height(2.dp))
                                                                    Text(
                                                                        text = "${DecimalFormat("#,###").format(uiStateOrder.orders[index].total)}đ",
                                                                        color = Color.Red,
                                                                        fontSize = 22.sp,
                                                                        fontWeight = FontWeight.Bold
                                                                    )
                                                                    Spacer(modifier = Modifier.height(2.dp))
                                                                }

                                                            }
                                                            Spacer(modifier = Modifier.height(10.dp))
                                                            Row()
                                                            {
                                                                Button(
                                                                    onClick = {
                                                                        updateStatus(uiStateOrder.orders[index])
                                                                        setOrderStatus("2")
                                                                        saveStatus()
                                                                    },
                                                                    colors = ButtonDefaults.buttonColors(Color.White),
                                                                    modifier = Modifier

                                                                        .border(
                                                                            width = 2.dp,
                                                                            color = Color.LightGray,
                                                                            shape = RoundedCornerShape(30.dp)
                                                                        )
                                                                        .height(45.dp)
                                                                        .padding(horizontal = 12.dp)
                                                                ) {
                                                                    Text(
                                                                        text = "Đã nhận",
                                                                        color = Color.Black
                                                                    )

                                                                }
                                                                Button(
                                                                    onClick = { /*TODO*/ },
                                                                    colors = ButtonDefaults.buttonColors(Green),
                                                                    modifier = Modifier

                                                                        .clip(RoundedCornerShape(30.dp))
                                                                        .height(45.dp)
                                                                        .padding(horizontal = 12.dp)
                                                                ) {
                                                                    Text(
                                                                        text = "Xem chi tiết",

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



                            3 ->
                                LazyColumn(contentPadding = PaddingValues(12.dp)) {
                                    items(uiStateOrder.orders.size) { index ->
                                        if( uiStateOrder.orders[index].userID == userID) {
                                            if(uiStateOrder.orders[index].status == "2"){
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
                                                                painter = rememberImagePainter(uiStateOrder.orders[index].imageOrder),
                                                                contentDescription = "Food Image",
                                                                contentScale = ContentScale.FillBounds,
                                                                modifier = Modifier
                                                                    .size(
                                                                        width = 100.dp,
                                                                        height = 90.dp
                                                                    )
                                                                    .padding(end = 10.dp)
                                                                    .border(
                                                                        width = 1.dp,
                                                                        color = Color.White,
                                                                        shape = RoundedCornerShape(15.dp)
                                                                    )
                                                                    .clip(RoundedCornerShape(15.dp))
                                                            )
                                                            Spacer(modifier = Modifier.width(15.dp))
                                                            Column(

                                                                modifier = Modifier.weight(0.7f),
                                                            ) {
                                                                Text(
                                                                    text = uiStateOrder.orders[index].titleOrder,
                                                                    color = Color.Black,
                                                                    fontSize = 20.sp,
                                                                    fontWeight = FontWeight.Bold
                                                                )

                                                                Row(
                                                                    modifier = Modifier.padding(top=15.dp)
                                                                ) {
                                                                    androidx.compose.material.Icon(
                                                                        painter = painterResource(id = R.drawable.baseline_location_on_24),
                                                                        contentDescription = "Movie Image",
                                                                        modifier = Modifier
                                                                            .size(40.dp)
                                                                            .padding(end = 10.dp)
                                                                            .border(
                                                                                width = 1.dp,
                                                                                color = Color.White,
                                                                                shape = RoundedCornerShape(
                                                                                    50.dp
                                                                                )
                                                                            )
                                                                            .clip(RoundedCornerShape(50.dp)),
                                                                        tint = Color.Red

                                                                    )
                                                                    Text(
                                                                        text = uiStateOrder.orders[index].address,
                                                                        color = Color.Black,
                                                                        fontSize = 13.sp,
                                                                    )



                                                                }
                                                                Spacer(modifier = Modifier.height(2.dp))
                                                                Text(
                                                                    text = "${DecimalFormat("#,###").format(uiStateOrder.orders[index].total)}đ",
                                                                    color = Color.Red,
                                                                    fontSize = 22.sp,
                                                                    fontWeight = FontWeight.Bold
                                                                )
                                                                Spacer(modifier = Modifier.height(2.dp))
                                                            }



                                                        }
                                                        Spacer(modifier = Modifier.height(10.dp))
                                                        Row(){
                                                            Button(
                                                                onClick = { /*TODO*/ },
                                                                colors = ButtonDefaults.buttonColors(Color.White),
                                                                modifier = Modifier
                                                                    .border(
                                                                        width = 1.dp,
                                                                        color = Color.LightGray,
                                                                        shape = RoundedCornerShape(30.dp)
                                                                    )
                                                                    .height(45.dp)
                                                                    .padding(horizontal = 12.dp)

                                                            ) {
                                                                Text(text = "Trả đơn", color = Color.Black, fontSize = 13.sp)
                                                            }

                                                            Button(
                                                                onClick = { },
                                                                colors = ButtonDefaults.buttonColors(Green),
                                                                modifier = Modifier
                                                                    .clip(RoundedCornerShape(30.dp))
                                                                    .height(45.dp)
                                                                    .padding(horizontal = 12.dp)
                                                            ) {
                                                                Text(
                                                                    text = "Đánh giá",

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

                            4 ->
                                LazyColumn(contentPadding = PaddingValues(12.dp)) {
                                    items(uiStateOrder.orders.size) { index ->
                                        if( uiStateOrder.orders[index].userID == userID) {
                                            if(uiStateOrder.orders[index].status == "3"){
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
                                                                painter = rememberImagePainter(uiStateOrder.orders[index].imageOrder),
                                                                contentDescription = "Food Image",
                                                                contentScale = ContentScale.FillBounds,
                                                                modifier = Modifier
                                                                    .size(
                                                                        width = 100.dp,
                                                                        height = 90.dp
                                                                    )
                                                                    .padding(end = 10.dp)
                                                                    .border(
                                                                        width = 1.dp,
                                                                        color = Color.White,
                                                                        shape = RoundedCornerShape(15.dp)
                                                                    )
                                                                    .clip(RoundedCornerShape(15.dp))
                                                            )
                                                            Spacer(modifier = Modifier.width(15.dp))
                                                            Column(

                                                                modifier = Modifier.weight(0.7f),
                                                            ) {
                                                                Text(
                                                                    text = uiStateOrder.orders[index].titleOrder,
                                                                    color = Color.Black,
                                                                    fontSize = 20.sp,
                                                                    fontWeight = FontWeight.Bold
                                                                )

                                                                Row(
                                                                    modifier = Modifier.padding(top=15.dp)
                                                                ) {
                                                                    androidx.compose.material.Icon(
                                                                        painter = painterResource(id = R.drawable.baseline_location_on_24),
                                                                        contentDescription = "Movie Image",
                                                                        modifier = Modifier
                                                                            .size(40.dp)
                                                                            .padding(end = 10.dp)
                                                                            .border(
                                                                                width = 1.dp,
                                                                                color = Color.White,
                                                                                shape = RoundedCornerShape(
                                                                                    50.dp
                                                                                )
                                                                            )
                                                                            .clip(RoundedCornerShape(50.dp)),
                                                                        tint = Color.Red

                                                                    )
                                                                    Text(
                                                                        text = uiStateOrder.orders[index].address,
                                                                        color = Color.Black,
                                                                        fontSize = 13.sp,
                                                                    )



                                                                }
                                                                Spacer(modifier = Modifier.height(2.dp))
                                                                Text(
                                                                    text = "${DecimalFormat("#,###").format(uiStateOrder.orders[index].total)}đ",
                                                                    color = Color.Red,
                                                                    fontSize = 22.sp,
                                                                    fontWeight = FontWeight.Bold
                                                                )
                                                                Spacer(modifier = Modifier.height(2.dp))
                                                            }
                                                        }
                                                        Spacer(modifier = Modifier.height(10.dp))
                                                        val foodId = uiStateOrder.orders[index].code
                                                        Button(
                                                            onClick = {
                                                                navControllerNotes.navigate("CartComponent/$foodId")
                                                            },
                                                            colors = ButtonDefaults.buttonColors(Green),
                                                            modifier = Modifier
                                                                .fillMaxWidth()
                                                                .clip(RoundedCornerShape(30.dp))
                                                                .height(45.dp)
                                                                .padding(horizontal = 12.dp)

                                                        ) {
                                                            Text(
                                                                text = "Mua lại",
                                                            )

                                                        }
                                                        Spacer(modifier = Modifier.height(40.dp))

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                        }
                    }
                }
            }
        }
    }
}

