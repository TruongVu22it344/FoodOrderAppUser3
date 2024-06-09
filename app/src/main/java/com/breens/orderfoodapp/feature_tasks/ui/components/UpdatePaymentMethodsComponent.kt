package com.breens.orderfoodapp.feature_tasks.ui.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.breens.orderfoodapp.R
import com.breens.orderfoodapp.data.model.DataX
import com.breens.orderfoodapp.data.model.Task
import com.breens.orderfoodapp.feature_tasks.state.OrderScreenUiState
import com.breens.orderfoodapp.feature_tasks.state.SignInScreenUiState
import com.breens.orderfoodapp.feature_tasks.state.optionsPayment
import com.breens.orderfoodapp.feature_tasks.viewmodel.StoreUser
import com.example.movieui.core.theme.Yellow
import kotlinx.coroutines.launch


@Composable
fun UpdatePaymentMethodsComponent(
    price : Int,
    foodCart: Task,
    savedEmail : String,
    productList : List<DataX>,
    navController: NavHostController,
    uiStateOrder: OrderScreenUiState,
    uiStateAccount: SignInScreenUiState,
    setUserID: (String) -> Unit,
    setFoodCode: (String) -> Unit,
    setOrderAddress:(String)->Unit,
    setOrderPayment: (String) -> Unit,
    setOrderQuantity: (Int) -> Unit,
    setOrderTitle: (String) -> Unit,
    setOrderImage: (String) -> Unit,
    setOrderPrice: (Int) -> Unit,
    setOrderTotal: (Int) -> Unit,
    saveOrder: () -> Unit,
) {
    var price_input by remember { mutableStateOf(0) }
    var maGD_input by remember { mutableStateOf(0) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = StoreUser(context)
    val check_price = dataStore.getPrice.collectAsState(initial = 0)
    var isShowDialog by remember { mutableStateOf(false) }
    var isShowDialog_Notice by remember { mutableStateOf(false) }
    var isPaymentSuccess by remember { mutableStateOf(false) }

    val uiStateValue = remember { mutableStateOf(OrderScreenUiState()) }
    uiStateOrder.selectedOptionPayment = uiStateValue.value.selectedOptionPayment
    val scrollState = rememberScrollState()

    LaunchedEffect(isPaymentSuccess) {
        if (isPaymentSuccess) {
            saveOrder()
            /*isShowDialog_Notice = true*/
            isPaymentSuccess = false
        }
    }

    if (isShowDialog) {
        Dialog(
            onDismissRequest = { isShowDialog = false }) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
            ) {
                LazyColumn {
                    items(uiStateAccount.accounts.size) { index ->
                        setUserID(uiStateAccount.accounts[index].userID)
                    }
                }
                setFoodCode(foodCart.taskId)
                setOrderImage(foodCart.image)
                setOrderTitle(foodCart.title)
                setOrderPrice(foodCart.price)
                setOrderAddress(uiStateOrder.selectedOptionAddress)
                setOrderQuantity(uiStateOrder.value)
                setOrderTotal(price)
                setOrderPayment(uiStateOrder.selectedOptionPayment)


                LazyColumn {
                    items(productList.lastIndex) { index ->
                        price_input = productList[index].giaTri
                        maGD_input = productList[index].maGD
                    }

                }

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = rememberImagePainter("https://img.vietqr.io/image/MB-0367462316-compact2.jpg?amount=${price}&addInfo=Thanh toán tiền ${foodCart.title}${savedEmail}&accountName=${foodCart.title}"),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(onClick = {
                            if((check_price.value!! == maGD_input) || (check_price.value!! != maGD_input)){
                                Toast.makeText(context,"Hệ thống xác nhận bạn chưa thanh toán!",Toast.LENGTH_SHORT).show()
                            }else if(check_price.value!! != maGD_input && price_input == price){
                                    scope.launch {
                                        dataStore.check_price(maGD_input)
                                    }
                                    isPaymentSuccess = true
                                isShowDialog = false
                                navController.popBackStack()
                                navController.popBackStack()
                            }

                        }) {
                            Text(text = "Xác nhận đã thanh toán")
                        }
                        Button(onClick = {
                            isShowDialog = false
                        }) {
                            Text(text = "Đóng")
                        }
                    }

                }
            }
        }
    }

    Scaffold(
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            androidx.compose.material.Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 12.dp),
                colors = androidx.compose.material.ButtonDefaults.buttonColors(
                    backgroundColor = Yellow
                ),
                shape = RoundedCornerShape(32.dp),
                onClick = {

                    navController.popBackStack()
                },
            ) {
                androidx.compose.material.Text(text = "Chọn")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            /*Text(text = "${check_price.value!!}")
            Text(text = "${maGD_input}")*/

            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back Button")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "Chọn phương thức thanh toán", style = MaterialTheme.typography.titleMedium)
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
            ) {
                optionsPayment.forEach { optionsPayment ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                            .selectable(
                                selected = uiStateValue.value.selectedOptionPayment == optionsPayment,
                                onClick = {
                                    uiStateValue.value =
                                        uiStateValue.value.copy(selectedOptionPayment = optionsPayment)
                                }
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "",
                                modifier = Modifier
                                    .background(color = Color.LightGray)
                                    .fillMaxWidth()
                                    .height(1.dp)
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(top = 15.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .border(
                                            width = 1.dp,
                                            color = Color.White,
                                            shape = RoundedCornerShape(50.dp)
                                        )
                                        .clip(RoundedCornerShape(50.dp))
                                        .background(Color.Green),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_featured_play_list_24),
                                        contentDescription = "Play Icon",
                                        modifier = Modifier.size(30.dp),
                                        tint = Color.White
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(
                                    modifier = Modifier.weight(0.7f)
                                ) {
                                    Text(
                                        text = "${optionsPayment} ",
                                        color = Color.Black,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                        RadioButton(
                            selected = uiStateValue.value.selectedOptionPayment == optionsPayment,
                            onClick = null
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .padding(horizontal = 16.dp)
                        .clickable {
                            isShowDialog = true
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "",
                            modifier = Modifier
                                .background(color = Color.LightGray)
                                .fillMaxWidth()
                                .height(1.dp)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = 15.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .border(
                                        width = 1.dp,
                                        color = Color.White,
                                        shape = RoundedCornerShape(50.dp)
                                    )
                                    .clip(RoundedCornerShape(50.dp))
                                    .background(Color.Green),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_featured_play_list_24),
                                    contentDescription = "Play Icon",
                                    modifier = Modifier.size(30.dp),
                                    tint = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(
                                modifier = Modifier.weight(0.7f)
                            ) {
                                Text(
                                    text = "Thanh toán qua VietQR ",
                                    color = Color.Black,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                    RadioButton(
                        selected = false,
                        onClick = null
                    )
                }
            }
        }
    }
}




