package com.breens.orderfoodapp.feature_tasks.ui.components

import com.breens.orderfoodapp.R


import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.breens.orderfoodapp.data.model.Chat
import com.breens.orderfoodapp.feature_tasks.state.ChatScreenUiState
import com.breens.orderfoodapp.feature_tasks.state.SignInScreenUiState
import com.breens.orderfoodapp.theme.BlueMess
import com.breens.orderfoodapp.theme.Gray
import com.breens.orderfoodapp.theme.LightRed
import com.breens.orderfoodapp.theme.LightYellow
import com.breens.orderfoodapp.theme.Yellow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatComponent(
    navController: NavHostController,
    uiStateAccount : SignInScreenUiState,
    uiState: ChatScreenUiState,
    senderID: (String) -> Unit,
    setMessage: (String) -> Unit,
    direction: (Boolean) -> Unit,
    senderMessage: () -> Unit
) {
    val listState = rememberLazyListState()
    LaunchedEffect(uiState.messages.size) {
        listState.scrollToItem(uiState.messages.size - 1)
    }
    var userID by remember {
        mutableStateOf("")
    }
    var receiveID by remember {
        mutableStateOf("")
    }
    LazyColumn(contentPadding = PaddingValues(12.dp)){
        items(uiStateAccount.accounts.size) { indexAccount ->
            if(uiStateAccount.accounts[indexAccount].role == 0) {
                userID = uiStateAccount.accounts[indexAccount].firstName+ uiStateAccount.accounts[indexAccount].lastName
            }
        }
    }
    LazyColumn(contentPadding = PaddingValues(12.dp)){
        items(uiStateAccount.accounts.size) { indexAccount ->
            if(uiStateAccount.accounts[indexAccount].role == 1) {
                receiveID = uiStateAccount.accounts[indexAccount].firstName+ uiStateAccount.accounts[indexAccount].lastName
            }



        }
    }
    senderID(userID)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueMess)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {

                    Icon(
                        painter = painterResource(id = R.drawable.baseline_account_circle_24),
                        contentDescription = "",
                        modifier = Modifier.size(40.dp),
                        tint = Color.White
                    )
                    SpacerWidth()
                    Column {
                        Text(
                            text = receiveID, style = TextStyle(
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        )
                        Text(
                            text = stringResource(R.string.online), style = TextStyle(
                                color = Color.White,
                                fontSize = 14.sp
                            )
                        )
                    }
                }
                IconComponentImageVector(icon = Icons.Default.MoreVert, size = 24.dp, tint = Color.White)
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Color.White, RoundedCornerShape(
                            topStart = 30.dp, topEnd = 30.dp
                        )
                    )
                    .padding(top = 25.dp)


            ) {
                LazyColumn(
                    modifier = Modifier.padding(
                        start = 15.dp,
                        top = 25.dp,
                        end = 15.dp,
                        bottom = 75.dp
                    ),
                    state = listState
                ) {
                    items(uiState.messages.size) { index ->
                        if(userID == uiState.messages[index].senderID){
                            ChatRow(uiState.messages[index])
                        }

                    }
                }

            }

        }




        Column() {
            Spacer(modifier = Modifier.weight(1f))
            TextField(
                value =  uiState.currentMessage, onValueChange = { message ->
                    setMessage(message) },
                placeholder = {
                    Text(
                        text = stringResource(R.string.type_message),
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color.Black
                        ),
                        textAlign = TextAlign.Center
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,
                    focusedLabelColor = Color.Transparent,
                    unfocusedLabelColor = Color.Transparent,
                    cursorColor = Color.Black,
                ),
                leadingIcon = { Icons.Default.Add },
                trailingIcon = {
                    Box(
                        modifier = Modifier
                            .background(Yellow, CircleShape)
                            .size(33.dp), contentAlignment = Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_send_24), contentDescription = "",
                            tint = Color.Black,
                            modifier = Modifier
                                .size(15.dp)
                                .clickable {
                                    direction(false)
                                    senderMessage()
                                    setMessage("")
                                }
                        )
                    }
                },
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 20.dp).fillMaxWidth() ,
                shape = CircleShape
            )

        }


    }

}

@Composable
fun ChatRow(
    chat: Chat
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (chat.direction) Alignment.Start else Alignment.End
    ) {
        Box(
            modifier = Modifier
                .background(
                    if (chat.direction) LightRed else LightYellow,
                    RoundedCornerShape(100.dp)
                ),
            contentAlignment = Center
        ) {
            Text(
                text = chat.message, style = TextStyle(
                    color = Color.Black,
                    fontSize = 15.sp
                ),
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 15.dp),
                textAlign = TextAlign.End
            )
        }
        Text(
            text = chat.createdAt,
            style = TextStyle(
                color = Gray,
                fontSize = 12.sp
            ),
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 15.dp),
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(

    text: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {


}

/*@Composable
fun CommonIconButton(
    imageVector: ImageVector
) {

    Box(
        modifier = Modifier
            .background(Yellow, CircleShape)
            .size(33.dp), contentAlignment = Center
    ) {
        IconComponentImageVector(icon = imageVector, size = 15.dp, tint = Color.Black)
    }

}*/

@Composable
fun CommonIconButtonDrawable(
    @DrawableRes icon: Int
) {



}

@Composable
fun IconComponentImageVector(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified,
    size: Dp
) {
    Icon(imageVector = icon, contentDescription = "", modifier = modifier.size(size), tint = tint)
}

@Composable
fun IconComponentDrawable(
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified,
    size: Dp
) {
    Icon(
        painter = painterResource(id = icon),
        contentDescription = "",
        modifier = modifier.size(size),
        tint = tint
    )
}
@Composable
fun SpacerWidth(
    width: Dp = 10.dp
) {
    Spacer(modifier = Modifier.width(width))
}

@Composable
fun SpacerHeight(
    height: Dp = 10.dp
) {
    Spacer(modifier = Modifier.height(height))
}
