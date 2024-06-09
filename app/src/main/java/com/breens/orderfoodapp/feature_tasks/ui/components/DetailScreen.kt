package com.breens.orderfoodapp.feature_tasks.ui.components

import android.text.Html
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.breens.orderfoodapp.R
import com.breens.orderfoodapp.data.model.Task
import com.breens.orderfoodapp.feature_tasks.state.OrderScreenUiState
import com.breens.orderfoodapp.feature_tasks.state.SignInScreenUiState
import com.example.movieui.core.theme.FoodColor
import com.example.movieui.core.theme.Gray
import java.text.DecimalFormat


@Composable
fun DetailScreen(
    navController: NavHostController,
    food: Task,
    uiStateOrder: OrderScreenUiState,
    uiStateAccount: SignInScreenUiState,
    openDialog: () -> Unit,
    closeDialog: () -> Unit
) {
    val scrollState = rememberScrollState()
    val uiStateValue = remember { mutableStateOf(OrderScreenUiState()) }
    if (uiStateOrder.isShowDialog) {
        AlertDialog(
            onDismissRequest = closeDialog,
            title = {
                Text(text = "Thông báo")
            },
            text = {
                Text(text = "Vui lòng đăng nhập để tiếp tục!.")
            },
            confirmButton = {
                Button(
                    onClick = closeDialog
                ) {
                    Text("OK")
                }
            }
        )
    }
    Scaffold(
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            Button(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = FoodColor
                ),
                shape = RoundedCornerShape(32.dp),
                onClick = {
                    if (uiStateAccount.accounts.isEmpty()) {
                        openDialog()
                    } else {
                            navController.navigate("CartComponent/${food.taskId}")


                    }


                },
            ) {
                Text(text = "Mua ngay", color = Color.White)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Row(
                modifier = Modifier.padding(
                    horizontal = 16.dp, vertical = 8.dp
                ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back Button")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "Chi tiết món ăn", style = MaterialTheme.typography.h6)
            }
            Row(
                modifier = Modifier
                    .height(320.dp)
                    .padding(horizontal = 16.dp)
            ) {
                Image(
                    painter = rememberImagePainter(food.image),
                    contentDescription = "Food Image",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .weight(0.7f)
                        .height(320.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    modifier = Modifier
                        .height(320.dp)
                        .weight(0.3f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    MovieInfo(
                        painterResourceId = R.drawable.baseline_fastfood_24,
                        title = "Tên món",
                        value = food.title,
                        button = {}
                    )
                    MovieInfo(
                        painterResourceId = R.drawable.baseline_access_time_filled,
                        title = "Ngày đăng",
                        value = "20/04",
                        button = {}
                    )
                    MovieInfo(
                        painterResourceId = R.drawable.baseline_stars,
                        title = "Đánh giá",
                        value = "5.0",
                        button =  { navController.navigate("RatingScreen/${food.taskId}") }

                    )


                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            Column(
               horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)



            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .background(FoodColor)
                        .padding(start = 5.dp, end = 5.dp)


                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_minus),
                        contentDescription = "Minus",
                        modifier = Modifier
                            .clickable {
                                uiStateValue.value =
                                    uiStateValue.value.copy(valueChange = uiStateValue.value.valueChange - 1)
                            }
                            .size(25.dp),
                        tint = Color.White
                    )
                    uiStateOrder.value = uiStateValue.value.valueChange
                    Text(

                        text = "${uiStateValue.value.valueChange}",
                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 16.dp),
                        fontFamily = FontFamily(Font(R.font.poppins_bold)),
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_plus),
                        contentDescription = "Add",
                        modifier = Modifier
                            .clickable {
                                uiStateValue.value =
                                    uiStateValue.value.copy(valueChange = uiStateValue.value.valueChange + 1)
                            }
                            .size(25.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }

            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(0.7f)
                    ) {
                        Text(
                            text = food.title,
                            fontFamily = FontFamily(Font(R.font.poppins_bold)),
                            color = Color.Black,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Xem giới thiệu bên dưới!",
                                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Image(
                                painter = painterResource(id = R.drawable.baseline_arrow_circle_down_24),
                                contentDescription = "Category Image",
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Column(
                        modifier = Modifier.weight(0.3f)
                    ) {
                        Text(text = "${DecimalFormat("#,###").format(food.price)}đ", color = Color.Red)
                    }
                }
                Spacer(modifier = Modifier.height(25.dp))
                AndroidView(
                    factory = { context ->
                        TextView(context).apply {
                            text = Html.fromHtml("<string><span style = \"color:#424242\">${food.body}</span><span style = \"color:#F54748\"></span></string>")
                        }
                    }
                )
                Spacer(modifier = Modifier.height(25.dp))
                /*Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(FoodColor),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(30.dp))
                        .height(65.dp)
                ) {
                    Text(
                        text = "Add to Cart",
                        fontFamily = FontFamily(Font(R.font.poppins_bold)),
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }*/
            }
        }
    }
}





@Composable
fun MovieInfo(
    @DrawableRes painterResourceId: Int,
    title: String,
    value: String,
    button: () -> Unit
) {
    Column(
        modifier = Modifier
            .border(width = 1.dp, color = Gray, shape = RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .clickable { button()}
            .padding(12.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            painter = painterResource(id = painterResourceId),
            contentDescription = title,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = title)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = value)
    }
}

