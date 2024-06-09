package com.breens.orderfoodapp.feature_tasks.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
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
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.breens.orderfoodapp.R
import com.breens.orderfoodapp.data.model.Task
import com.breens.orderfoodapp.feature_tasks.state.OrderScreenUiState
import com.breens.orderfoodapp.feature_tasks.state.SignInScreenUiState

@Composable
fun RatingScreen(navController: NavController, food: Task?, uiStateAccount: SignInScreenUiState, uiStateOrder: OrderScreenUiState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        Scaffold (
            topBar = { RatingBar(navController = navController) }
        ) { innerPadding ->
            Column (
                modifier = Modifier.padding(innerPadding)
            ) {
                RatingContent()
            }
        }
    }
}
@Composable
fun RatingBar(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
            Text(text = "4.8 (4,799 reviews)", fontWeight = FontWeight.Bold)
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Filled.MoreVert, contentDescription = "More")
        }
    }
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RatingContent() {
    data class TabItemsOrder(
        val id: Int,
        val title: String,
    )
    val tabItemsOrder = listOf(
        TabItemsOrder(
            id = 1,
            title = "All",

            ),
        TabItemsOrder(
            id = 2,
            title = "5",

            ),
        TabItemsOrder(
            id = 3,
            title = "4",

            ),
        TabItemsOrder(
            id = 4,
            title = "3",

            ),
        TabItemsOrder(
            id = 5,
            title = "2",

            ),
        TabItemsOrder(
            id = 6,
            title = "1",

            ),
    )
    var selectedTabIndexOrder by remember {
        mutableStateOf(0)
    }


    val pagerStateOrder = rememberPagerState {
        tabItemsOrder.size
    }
    LaunchedEffect(selectedTabIndexOrder){
        pagerStateOrder.animateScrollToPage(selectedTabIndexOrder)
    }
    LaunchedEffect(pagerStateOrder.currentPage){
        selectedTabIndexOrder = pagerStateOrder.currentPage
    }
    LazyRow {
        items(tabItemsOrder.size) { index ->
            val item = tabItemsOrder[index]

            Tab(
                modifier = Modifier
                    .background(color = Color.White)
                    .height(63.dp),
                selected = index == selectedTabIndexOrder,
                onClick = { selectedTabIndexOrder = index },
                text = {
                    OutlinedButton(
                        onClick = { selectedTabIndexOrder = index },
                        contentPadding = PaddingValues(0.dp),
                        border = BorderStroke(1.dp, Color.Green),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (index == selectedTabIndexOrder) Color.Green else Color.Transparent,
                            contentColor = if (index == selectedTabIndexOrder) Color.White else Color.Green
                        )
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_star_rate_24),
                            contentDescription = "Rate",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(text = item.title)
                    }
                }
            )
        }
    }

    HorizontalPager(
        state = pagerStateOrder,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 12.dp)

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

                        1 -> Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row (
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row (
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.duc),
                                        contentDescription = "Avatar",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(CircleShape)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Anh Nguyet",
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Row (
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    /*ChooseButton(isChoosing = false)*/
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Icon(
                                        Icons.Filled.MoreVert,
                                        contentDescription = "More"
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = "Ok")
                            Spacer(modifier = Modifier.height(16.dp))
                            Row {
                                Icon(
                                    Icons.Outlined.FavoriteBorder,
                                    contentDescription = "Favorite"
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "370")
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "2 days ago", color = Color.Gray)
                            }
                        }

                        2 ->

                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row (
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row (
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.duc),
                                            contentDescription = "Avatar",
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .size(40.dp)
                                                .clip(CircleShape)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "Anh Nguyet",
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Row (
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        /*ChooseButton(isChoosing = false)*/
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Icon(
                                            Icons.Filled.MoreVert,
                                            contentDescription = "More"
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(text = "Ok")
                                Spacer(modifier = Modifier.height(16.dp))
                                Row {
                                    Icon(
                                        Icons.Outlined.FavoriteBorder,
                                        contentDescription = "Favorite"
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = "370")
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = "2 days ago", color = Color.Gray)
                                }
                            }



                        3 ->
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row (
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row (
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.duc),
                                            contentDescription = "Avatar",
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .size(40.dp)
                                                .clip(CircleShape)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "Anh Nguyet",
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Row (
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        /*  ChooseButton(isChoosing = false)*/
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Icon(
                                            Icons.Filled.MoreVert,
                                            contentDescription = "More"
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(text = "Ok")
                                Spacer(modifier = Modifier.height(16.dp))
                                Row {
                                    Icon(
                                        Icons.Outlined.FavoriteBorder,
                                        contentDescription = "Favorite"
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = "370")
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = "2 days ago", color = Color.Gray)
                                }
                            }

                        /*4 ->
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row (
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row (
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.studentid),
                                            contentDescription = "Avatar",
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .size(40.dp)
                                                .clip(CircleShape)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "Anh Nguyet",
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Row (
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                      *//*  ChooseButton(isChoosing = false)*//*
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Icon(
                                            Icons.Filled.MoreVert,
                                            contentDescription = "More"
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(text = "Ok")
                                Spacer(modifier = Modifier.height(16.dp))
                                Row {
                                    Icon(
                                        Icons.Outlined.FavoriteBorder,
                                        contentDescription = "Favorite"
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = "370")
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = "2 days ago", color = Color.Gray)
                                }
                            }*/
                    }
                }
            }
        }
    }

}
