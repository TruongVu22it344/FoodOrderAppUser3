package com.breens.orderfoodapp.feature_tasks.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.breens.orderfoodapp.common.SIDE_EFFECTS_KEY
import com.breens.orderfoodapp.data.model.TabItems
import com.breens.orderfoodapp.feature_tasks.events.OrderScreenUiEvent
import com.breens.orderfoodapp.feature_tasks.side_effects.CateScreenSideEffects
import com.breens.orderfoodapp.feature_tasks.side_effects.OrderScreenSideEffects
import com.breens.orderfoodapp.feature_tasks.side_effects.TaskScreenSideEffects
import com.breens.orderfoodapp.feature_tasks.ui.components.BannerComponent
import com.breens.orderfoodapp.feature_tasks.ui.components.CardComponent
import com.breens.orderfoodapp.feature_tasks.ui.components.CartComponent
import com.breens.orderfoodapp.feature_tasks.ui.components.CategoriesComponent
import com.breens.orderfoodapp.feature_tasks.ui.components.DetailScreen
import com.breens.orderfoodapp.feature_tasks.ui.components.EmptyComponent
import com.breens.orderfoodapp.feature_tasks.ui.components.LoadingComponent
import com.breens.orderfoodapp.feature_tasks.ui.components.TaskCardComponent
import com.breens.orderfoodapp.feature_tasks.ui.components.UpdateAddressComponent
import com.breens.orderfoodapp.feature_tasks.ui.components.UpdateDiscountComponent
import com.breens.orderfoodapp.feature_tasks.viewmodel.BannerViewModel
import com.breens.orderfoodapp.feature_tasks.viewmodel.CateViewModel
import com.breens.orderfoodapp.feature_tasks.viewmodel.OrderViewModel
import com.breens.orderfoodapp.feature_tasks.viewmodel.TasksViewModel
import com.breens.orderfoodapp.theme.TodoChampTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.breens.orderfoodapp.data.ProductsRepositoryImpl
import com.breens.orderfoodapp.data.model.DataX
import com.breens.orderfoodapp.data.repositories.RetrofitInstance
import com.breens.orderfoodapp.feature_tasks.events.CardsScreenUiEvent
import com.breens.orderfoodapp.feature_tasks.events.CartsScreenUiEvent
import com.breens.orderfoodapp.feature_tasks.events.ChatScreenUiEvent
import com.breens.orderfoodapp.feature_tasks.events.SignInScreenUiEvent
import com.breens.orderfoodapp.feature_tasks.events.TasksScreenUiEvent
import com.breens.orderfoodapp.feature_tasks.side_effects.CardScreenSideEffects
import com.breens.orderfoodapp.feature_tasks.side_effects.SignInScreenSideEffects
import com.breens.orderfoodapp.feature_tasks.ui.components.Card2Component
import com.breens.orderfoodapp.feature_tasks.ui.components.CartGeneralComponent
import com.breens.orderfoodapp.feature_tasks.ui.components.ChatComponent
import com.breens.orderfoodapp.feature_tasks.ui.components.DetailCateComponent
import com.breens.orderfoodapp.feature_tasks.ui.components.FavoriteFoodComponent
import com.breens.orderfoodapp.feature_tasks.ui.components.ForgetPassword
import com.breens.orderfoodapp.feature_tasks.ui.components.Profile
import com.breens.orderfoodapp.feature_tasks.ui.components.RatingScreen
import com.breens.orderfoodapp.feature_tasks.ui.components.SignInComponent
import com.breens.orderfoodapp.feature_tasks.ui.components.SignUpComponent
import com.breens.orderfoodapp.feature_tasks.ui.components.TabOrderComponent
import com.breens.orderfoodapp.feature_tasks.ui.components.UpdatePaymentMethodsComponent
import com.breens.orderfoodapp.feature_tasks.viewmodel.AccountViewModel
import com.breens.orderfoodapp.feature_tasks.viewmodel.CardsViewModel
import com.breens.orderfoodapp.feature_tasks.viewmodel.CartViewModel
import com.breens.orderfoodapp.feature_tasks.viewmodel.ChatViewModel
import com.breens.orderfoodapp.feature_tasks.viewmodel.PayViewModel
import com.breens.orderfoodapp.feature_tasks.viewmodel.StoreUser
import com.breens.orderfoodapp.theme.colorWhite
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class NotesScreenActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            TodoChampTheme {

                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Gray)
                        .background(if (isSystemInDarkTheme()) Color.Black else colorWhite)
                ) {
                        val viewModel by viewModels<PayViewModel>(factoryProducer = {
                            object : ViewModelProvider.Factory {
                                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                    return PayViewModel(ProductsRepositoryImpl(RetrofitInstance.api)) as T
                                }
                            }
                        })
                        val productList by viewModel.products.collectAsState()
                        val context = LocalContext.current

                        LaunchedEffect(key1 = viewModel.showErrorToastChannel) {
                            viewModel.showErrorToastChannel.collectLatest { show ->
                                if (show) {
                                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }

                        if (productList.isEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        } else {

                            FoodScreenActivity(productList)
                        }



                }


            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class,
)
@Composable
fun FoodScreenActivity(productList : List<DataX>) {
    val tasksViewModel: TasksViewModel = viewModel()
    val bannerViewModel: BannerViewModel = viewModel()
    val catesViewModel: CateViewModel = viewModel()
    val orderViewModel: OrderViewModel = viewModel()
    val accountViewModel: AccountViewModel = viewModel()
    val cardsViewModel: CardsViewModel = viewModel()
    val chatViewModel: ChatViewModel = viewModel()
    val cartViewModel: CartViewModel = viewModel()


    val effectFlow = tasksViewModel.effect
    val effectFlowCate = catesViewModel.effectCate
    val effectFlowBanner = bannerViewModel.effect1
    val effectFlowOrder = orderViewModel.effectOrder
    val effectFlowAccount = accountViewModel.effectAccount
    val effectFlowCard = cardsViewModel.effect
    val effectMessage= chatViewModel.effectMessage
    val snackBarHostState = remember {
        SnackbarHostState()
    }

    val uiState = tasksViewModel.state.collectAsState().value
    val uiStateBanner = bannerViewModel.state1.collectAsState().value
    val uiStateCate = catesViewModel.stateCate.collectAsState().value
    val uiStateOrder = orderViewModel.stateOrder.collectAsState().value
    val uiStateAccount = accountViewModel.stateAccount.collectAsState().value
    val uiStateCard = cardsViewModel.state.collectAsState().value
    val uiStateMessage = chatViewModel.stateMessage.collectAsState().value
    val uiStateCart = cartViewModel.state.collectAsState().value


    val context = LocalContext.current

    val tabItems = listOf(
        TabItems(
            id = 1,
            title = "Home",
            unselectedIcon = Icons.Outlined.Home,
            selectedIcon = Icons.Filled.Home
        ),
        TabItems(
            id = 2,
            title = "Đơn hàng",
            unselectedIcon = Icons.Outlined.ShoppingCart,
            selectedIcon = Icons.Filled.ShoppingCart
        ),
        TabItems(
            id = 3,
            title = "Đã thích",
            unselectedIcon = Icons.Outlined.FavoriteBorder,
            selectedIcon = Icons.Filled.Favorite
        ),
        TabItems(
            id = 4,
            title = "ChatBox",
            unselectedIcon = Icons.Outlined.Create,
            selectedIcon = Icons.Filled.Create
        ),
        TabItems(
            id = 5,
            title = "Tôi",
            unselectedIcon = Icons.Outlined.AccountCircle,
            selectedIcon = Icons.Filled.AccountCircle
        )
    )

    var selectedTabIndex by remember {
        mutableStateOf(0)
    }
    val pagerState = rememberPagerState {
        tabItems.size
    }
    val navController = rememberNavController()

    var hasLoggedIn by remember { mutableStateOf(false) }
    val dataStore = StoreUser(context)
    val savedEmail = dataStore.getEmail.collectAsState(initial = "")
    val savedPassword = dataStore.getPassword.collectAsState(initial = "")
    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }
    LaunchedEffect(pagerState.currentPage) {
        selectedTabIndex = pagerState.currentPage
    }
    LaunchedEffect(key1 = SIDE_EFFECTS_KEY) {
        effectFlowAccount.onEach { effectAccount ->
            when (effectAccount) {
                is SignInScreenSideEffects.NavigateToHome -> navController.navigate("TabComponent")
                is SignInScreenSideEffects.ShowSnackBarMessage -> {
                    snackBarHostState.showSnackbar(
                        message = effectAccount.messageAccount,
                        duration = SnackbarDuration.Short,
                        actionLabel = "DISMISS",
                    )
                }

            }
        }.collect()
    }

    LaunchedEffect(key1 = SIDE_EFFECTS_KEY) {
        effectFlow.onEach { effect ->
            when (effect) {
                is TaskScreenSideEffects.ShowSnackBarMessage -> {
                    snackBarHostState.showSnackbar(
                        message = effect.message,
                        duration = SnackbarDuration.Short,
                        actionLabel = "DISMISS",
                    )
                }

            }
        }.collect()
    }
    LaunchedEffect(key1 = SIDE_EFFECTS_KEY) {

    }
    LaunchedEffect(key1 = SIDE_EFFECTS_KEY) {
        effectFlowCard.onEach { effect ->
            when (effect) {
                is CardScreenSideEffects.ShowSnackBarMessage -> {
                    snackBarHostState.showSnackbar(
                        message = effect.messageCard,
                        duration = SnackbarDuration.Short,
                        actionLabel = "DISMISS",
                    )
                }

            }
        }.collect()
    }
    LaunchedEffect(key1 = SIDE_EFFECTS_KEY) {
        effectFlowCate.onEach { effectCate ->
            when (effectCate) {
                is CateScreenSideEffects.ShowSnackBarMessage -> {
                    snackBarHostState.showSnackbar(
                        message = effectCate.messageCate,
                        duration = SnackbarDuration.Short,
                        actionLabel = "DISMISS",
                    )
                }

            }
        }.collect()
    }
    LaunchedEffect(key1 = SIDE_EFFECTS_KEY) {
        effectFlowOrder.onEach { effectOrder ->
            when (effectOrder) {
                is OrderScreenSideEffects.ShowSnackBarMessage -> {
                    snackBarHostState.showSnackbar(
                        message = effectOrder.messageOrder,
                        duration = SnackbarDuration.Short,
                        actionLabel = "DISMISS",
                    )
                }

            }
        }.collect()
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)


        ) { index ->
            var searchText by remember { mutableStateOf("") }
            var isSearching by remember { mutableStateOf(false) }
            val scrollState = rememberScrollState()
            Scaffold(

                snackbarHost = {
                    SnackbarHost(snackBarHostState)
                },
                containerColor = Color(0XFFFAFAFA),
            ) {
                Box(modifier = Modifier.padding(it)) {


                    NavHost(
                        navController = navController,
                        startDestination = "Home",

                        ) {


                        composable("Home"){

                            LaunchedEffect(savedEmail, savedPassword) {
                                if (savedEmail.value!!.isNotEmpty() && savedPassword.value!!.isNotEmpty() && !hasLoggedIn) {
                                    hasLoggedIn = true // Đánh dấu đã thực hiện đăng nhập
                                    accountViewModel.sendEvent(
                                        event = SignInScreenUiEvent.OnChangeEmail(email = savedEmail.value!!)
                                    )
                                    accountViewModel.sendEvent(
                                        event = SignInScreenUiEvent.OnChangePassword(password = savedPassword.value!!)
                                    )
                                    accountViewModel.sendEvent(
                                        event = SignInScreenUiEvent.LoginUser
                                    )
                                }
                            }
                            when(tabItems[index].id){
                                1 ->
                                    Column(
                                        modifier = Modifier
                                            .verticalScroll(scrollState)
                                    ) {
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Row{


                                            OutlinedTextField(
                                                value = searchText,
                                                onValueChange = {
                                                    searchText = it
                                                    isSearching = searchText.isNotEmpty()
                                                    tasksViewModel.sendEvent(TasksScreenUiEvent.GetTasks(searchText))
                                                },
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                colors = OutlinedTextFieldDefaults.colors(
                                                    focusedBorderColor = Color.Transparent,
                                                    unfocusedBorderColor = Color.Transparent,
                                                    focusedContainerColor = Color.LightGray,
                                                    unfocusedContainerColor = Color.LightGray
                                                ),
                                                placeholder = { Text("Search", fontSize = 15.sp) },
                                                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search")
                                                }
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(2.dp))
                                        if (!isSearching) {
                                            when {

                                                /* uiStateBanner.isLoadingBanner -> {
                                                LoadingComponent()
                                            }*/

                                                !uiStateBanner.isLoadingBanner && uiStateBanner.banners.isNotEmpty() -> {

                                                    /*WelcomeMessageComponent(navController = navController)

                                                Spacer(
                                                    modifier = Modifier.height(
                                                        8.dp,
                                                    ),
                                                )*/



                                                    BannerComponent(
                                                        uiStateBanner = uiStateBanner,
                                                        navController = navController,
                                                    )


                                                }

                                                !uiStateBanner.isLoadingBanner && uiStateBanner.banners.isEmpty() -> {
                                                    EmptyComponent()
                                                }


                                            }
                                            when {

                                                !uiStateCate.isLoadingCate && uiStateCate.cates.isNotEmpty() -> {


                                                    CategoriesComponent(
                                                        navController = navController,
                                                        uiStateCate = uiStateCate
                                                    )
                                                }


                                                !uiStateCate.isLoadingCate && uiStateCate.cates.isEmpty() -> {
                                                    EmptyComponent()
                                                }

                                            }
                                            when {
                                                !uiState.isLoading && uiState.tasks.isNotEmpty() -> {


                                                    CardComponent(
                                                        navController = navController,
                                                        uiState = uiState,

                                                        )
                                                }


                                                !uiState.isLoading && uiState.tasks.isEmpty() -> {
                                                    EmptyComponent()
                                                }

                                            }

                                            when {
                                                !uiStateCard.isLoading && uiStateCard.cards.isNotEmpty() -> {


                                                    Card2Component(
                                                        navController = navController,
                                                        uiStateCard = uiStateCard,

                                                        setCardFavorite = { favorite ->
                                                            cardsViewModel.sendEvent(
                                                                event = CardsScreenUiEvent.OnChangeCardFavorite(
                                                                    favorite = favorite
                                                                ),
                                                            )
                                                        },
                                                        /*setViews = { views ->
                                                            cardsViewModel.sendEvent(
                                                                event = CardsScreenUiEvent.OnChangeCardViews(
                                                                    views = views
                                                                ),
                                                            )
                                                        },*/
                                                        updateFavorite = { cardsToBeUpdated ->
                                                            cardsViewModel.sendEvent(
                                                                event = CardsScreenUiEvent.SetCardToBeUpdated(
                                                                    cardToBeUpdated = cardsToBeUpdated,
                                                                ),
                                                            )
                                                        },

                                                        saveFavorite = {
                                                            cardsViewModel.sendEvent(event = CardsScreenUiEvent.UpdateNote)
                                                        },
                                                        /*setImage = { image ->
                                                        cartViewModel.sendEvent(
                                                            event = CartsScreenUiEvent.OnChangeTaskImage(image = image),
                                                        )
                                                    },
                                                    setTitle = { title ->
                                                        cartViewModel.sendEvent(
                                                            event = CartsScreenUiEvent.OnChangeTaskTitle(title = title),
                                                        )
                                                    },
                                                    setBody = { body ->
                                                        cartViewModel.sendEvent(
                                                            event = CartsScreenUiEvent.OnChangeTaskBody(body = body),
                                                        )
                                                    },
                                                    setPrice = { price ->
                                                        cartViewModel.sendEvent(
                                                            event = CartsScreenUiEvent.OnChangeTaskPrice(price = price),
                                                        )
                                                    },*/
                                                        addCart = { cardsToBeUpdated ->
                                                            cardsViewModel.sendEvent(
                                                                event = CardsScreenUiEvent.SetCardToBeUpdated(
                                                                    cardToBeUpdated = cardsToBeUpdated,
                                                                ),
                                                            )
                                                        },
                                                        saveCart = {
                                                            cardsViewModel.sendEvent(
                                                                event = CardsScreenUiEvent.AddCart
                                                            )
                                                        }


                                                        )
                                                }


                                                !uiStateCard.isLoading && uiStateCard.cards.isEmpty() -> {
                                                    EmptyComponent()
                                                }

                                            }
                                            when {
                                                uiState.isLoading -> {
                                                    LoadingComponent()
                                                }

                                                !uiState.isLoading && uiState.tasks.isNotEmpty() -> {


                                                    TaskCardComponent(
                                                        navController = navController,
                                                        uiState = uiState,

                                                        )
                                                }


                                                !uiState.isLoading && uiState.tasks.isEmpty() -> {
                                                    EmptyComponent()
                                                }

                                            }
                                        }else{
                                            when {
                                                uiState.isLoading -> {
                                                    LoadingComponent()
                                                }

                                                !uiState.isLoading && uiState.tasks.isNotEmpty() -> {


                                                    TaskCardComponent(
                                                        navController = navController,
                                                        uiState = uiState,

                                                        )
                                                }


                                                !uiState.isLoading && uiState.tasks.isEmpty() -> {
                                                    EmptyComponent()
                                                }

                                            }
                                        }

                                    }




                                5 -> when {
                                    uiStateAccount.isLoading -> {
                                        LoadingComponent()
                                    }

                                    !uiStateAccount.isLoading  -> {

                                        Profile(
                                            navController = navController,
                                            uiStateAccount = uiStateAccount,
                                            logoutUser = {
                                                accountViewModel.sendEvent(
                                                    event = SignInScreenUiEvent.LogoutUser
                                                )
                                            })

                                    }



                                }


                            }
                        }

                        composable("SignInComponent"){
                            SignInComponent(
                                uiStateAccount = uiStateAccount,
                                navController = navController,
                                setEmail = { email->
                                    accountViewModel.sendEvent(
                                        event = SignInScreenUiEvent.OnChangeEmail(email = email),
                                    )
                                },
                                setPassword = { password ->
                                    accountViewModel.sendEvent(
                                        event = SignInScreenUiEvent.OnChangePassword(password = password),
                                    )
                                },
                                saveAccount = {
                                    accountViewModel.sendEvent(
                                        event = SignInScreenUiEvent.LoginUser
                                    )
                                },
                            )
                        }
                        composable("SignUpComponent"){
                            SignUpComponent(
                                uiStateAccount = uiStateAccount,
                                navController = navController,
                                setFirstname = { firstname->
                                    accountViewModel.sendEvent(
                                        event = SignInScreenUiEvent.OnChangeFirstname(firstname = firstname),
                                    )
                                },
                                setLastname = { lastname->
                                    accountViewModel.sendEvent(
                                        event = SignInScreenUiEvent.OnChangeLastname(lastname = lastname),
                                    )
                                },
                                setEmail = { email->
                                    accountViewModel.sendEvent(
                                        event = SignInScreenUiEvent.OnChangeEmail(email = email),
                                    )
                                },
                                setPassword = { password ->
                                    accountViewModel.sendEvent(
                                        event = SignInScreenUiEvent.OnChangePassword(password = password),
                                    )
                                },
                                saveAccount = {
                                    accountViewModel.sendEvent(
                                        event = SignInScreenUiEvent.RegisterUser(
                                            firstname = uiStateAccount.currentFirstname,
                                            lastname = uiStateAccount.currentLastName,
                                            email = uiStateAccount.currentEmail,
                                            password = uiStateAccount.currentPassword,
                                        ),
                                    )
                                },
                            )
                        }
                        composable("TabComponent") {
                            when (tabItems[index].id) {
                                1 ->
                                    Column(
                                        modifier = Modifier
                                            .verticalScroll(scrollState)
                                    ) {
                                        when {

                                            /* uiStateBanner.isLoadingBanner -> {
                                                LoadingComponent()
                                            }*/

                                            !uiStateBanner.isLoadingBanner && uiStateBanner.banners.isNotEmpty() -> {

                                                /* WelcomeMessageComponent(navController=navController)

                                                 Spacer(
                                                     modifier = Modifier.height(
                                                         8.dp,
                                                     ),
                                                 )*/



                                                BannerComponent(
                                                    uiStateBanner = uiStateBanner,
                                                    navController = navController,
                                                )


                                            }

                                            !uiStateBanner.isLoadingBanner && uiStateBanner.banners.isEmpty() -> {
                                                EmptyComponent()
                                            }


                                        }
                                        when {

                                            !uiStateCate.isLoadingCate && uiStateCate.cates.isNotEmpty() -> {


                                                CategoriesComponent(
                                                    navController = navController,
                                                    uiStateCate = uiStateCate
                                                )
                                            }


                                            !uiStateCate.isLoadingCate && uiStateCate.cates.isEmpty() -> {
                                                EmptyComponent()
                                            }

                                        }
                                        when {
                                            !uiState.isLoading && uiState.tasks.isNotEmpty() -> {


                                                CardComponent(
                                                    navController = navController,
                                                    uiState = uiState,

                                                    )
                                            }


                                            !uiState.isLoading && uiState.tasks.isEmpty() -> {
                                                EmptyComponent()
                                            }

                                        }
                                        when {
                                            !uiStateCard.isLoading && uiStateCard.cards.isNotEmpty() -> {


                                                Card2Component(
                                                    navController = navController,
                                                    uiStateCard = uiStateCard,

                                                    setCardFavorite = { favorite ->
                                                        cardsViewModel.sendEvent(
                                                            event = CardsScreenUiEvent.OnChangeCardFavorite(favorite = favorite),
                                                        )
                                                    },
                                                    /*setViews = { views ->
                                                        cardsViewModel.sendEvent(
                                                            event = CardsScreenUiEvent.OnChangeCardViews(views = views),
                                                        )
                                                    },*/
                                                    updateFavorite = { cardsToBeUpdated ->
                                                        cardsViewModel.sendEvent(
                                                            event = CardsScreenUiEvent.SetCardToBeUpdated(
                                                                cardToBeUpdated = cardsToBeUpdated,
                                                            ),
                                                        )
                                                    },
                                                    saveFavorite = {

                                                        cardsViewModel.sendEvent(event = CardsScreenUiEvent.UpdateNote)
                                                    },
                                                    /*setImage = { image ->
                                                        cartViewModel.sendEvent(
                                                            event = CartsScreenUiEvent.OnChangeTaskImage(image = image),
                                                        )
                                                    },
                                                    setTitle = { title ->
                                                        cartViewModel.sendEvent(
                                                            event = CartsScreenUiEvent.OnChangeTaskTitle(title = title),
                                                        )
                                                    },
                                                    setBody = { body ->
                                                        cartViewModel.sendEvent(
                                                            event = CartsScreenUiEvent.OnChangeTaskBody(body = body),
                                                        )
                                                    },
                                                    setPrice = { price ->
                                                        cartViewModel.sendEvent(
                                                            event = CartsScreenUiEvent.OnChangeTaskPrice(price = price),
                                                        )
                                                    },*/
                                                    addCart = { cardsToBeUpdated ->
                                                        cardsViewModel.sendEvent(
                                                            event = CardsScreenUiEvent.SetCardToBeUpdated(
                                                                cardToBeUpdated = cardsToBeUpdated,
                                                            ),
                                                        )
                                                    },
                                                    saveCart = {
                                                        cardsViewModel.sendEvent(
                                                            event = CardsScreenUiEvent.AddCart
                                                        )
                                                    }



                                                    )
                                            }


                                            !uiStateCard.isLoading && uiStateCard.cards.isEmpty() -> {
                                                EmptyComponent()
                                            }

                                        }
                                        when {
                                            uiState.isLoading -> {
                                                LoadingComponent()
                                            }

                                            !uiState.isLoading && uiState.tasks.isNotEmpty() -> {


                                                TaskCardComponent(
                                                    navController = navController,
                                                    uiState = uiState,

                                                    )
                                            }


                                            !uiState.isLoading && uiState.tasks.isEmpty() -> {
                                                EmptyComponent()
                                            }

                                        }


                                    }
                                2 ->
                                    when {
                                        uiStateOrder.isLoading -> {
                                            LoadingComponent()
                                        }

                                        !uiStateOrder.isLoading && uiStateOrder.orders.isNotEmpty() -> {
                                            TabOrderComponent(
                                                navControllerNotes = navController,
                                                uiStateAccount = uiStateAccount,
                                                uiStateOrder = uiStateOrder,

                                                setOrderStatus = { status ->
                                                    orderViewModel.sendEvent(
                                                        event = OrderScreenUiEvent.OnChangeOrderStatus(status = status),
                                                    )
                                                },
                                                updateStatus = { statusToBeUpdated ->
                                                    orderViewModel.sendEvent(
                                                        event = OrderScreenUiEvent.SetStatusToBeUpdated(
                                                            statusToBeUpdated = statusToBeUpdated,
                                                        ),
                                                    )
                                                },

                                                saveStatus = {
                                                    orderViewModel.sendEvent(event = OrderScreenUiEvent.UpdateNote)
                                                },

                                                )

                                        }


                                        !uiStateOrder.isLoading && uiStateOrder.orders.isEmpty() -> {
                                            EmptyComponent()
                                        }

                                    }

                                3->
                                    when {
                                        uiStateCard.isLoading -> {
                                            LoadingComponent()
                                        }
                                        !uiStateCard.isLoading && uiStateCard.cards.isNotEmpty() -> {
                                            FavoriteFoodComponent(uiStateCard = uiStateCard)


                                        }


                                        !uiStateCard.isLoading && uiStateCard.cards.isEmpty() -> {
                                            EmptyComponent()
                                        }

                                    }
                                4 ->
                                    when {
                                        uiStateMessage.isLoading -> {
                                            LoadingComponent()
                                        }
                                        !uiStateMessage.isLoading -> {
                                            ChatComponent(navController = navController,
                                                uiState = uiStateMessage,
                                                uiStateAccount = uiStateAccount,
                                                setMessage = {message ->
                                                    chatViewModel.sendEvent(
                                                        event = ChatScreenUiEvent.OnChangeMessage(message = message),
                                                    )
                                                },
                                                direction = {direction ->
                                                    chatViewModel.sendEvent(
                                                        event = ChatScreenUiEvent.OnChangeDirection(direction = direction),
                                                    )
                                                },
                                                senderID = {senderID ->
                                                    chatViewModel.sendEvent(
                                                        event = ChatScreenUiEvent.OnChangeSenderID(senderID = senderID),
                                                    )
                                                },
                                                senderMessage = {
                                                    chatViewModel.sendEvent(
                                                        event = ChatScreenUiEvent.AddMessage(
                                                            message = uiStateMessage.currentMessage,
                                                            senderID = uiStateMessage.currentSenderID,
                                                            direction = uiStateMessage.direction

                                                        ),
                                                    )
                                                }
                                            )


                                        }


                                    }

                                5 ->
                                    when {
                                        uiStateAccount.isLoading -> {
                                            LoadingComponent()
                                        }

                                        !uiStateAccount.isLoading  -> {

                                            Profile(
                                                navController = navController,
                                                uiStateAccount = uiStateAccount,
                                                logoutUser = {
                                                    accountViewModel.sendEvent(
                                                        event = SignInScreenUiEvent.LogoutUser
                                                    )
                                                }
                                            )

                                        }



                                    }
                            }

                        }
                        composable("DetailCateComponent/{titleCate}") { backStackEntry ->

                            val titleCate = backStackEntry.arguments?.getString("titleCate")
                            val card = uiStateCard.cards.first { it.cate == titleCate }

                                DetailCateComponent(
                                    card = card,
                                    navController = navController
                                )
                        }
                        composable("DetailScreen/{id}") { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id")
                            val food = uiState.tasks.first { it.taskId == id }
                            DetailScreen(
                                navController = navController,
                                food, uiStateOrder = uiStateOrder,
                                uiStateAccount = uiStateAccount,
                                openDialog = {
                                    orderViewModel.sendEvent(
                                        event = OrderScreenUiEvent.OnChangeDialogState(show = true),
                                    )
                                },
                                closeDialog = {
                                    orderViewModel.sendEvent(
                                        event = OrderScreenUiEvent.OnChangeDialogState(show = false),
                                    )
                                },
                            )
                        }
                        composable("RatingScreen/{id}") { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id")
                            val food = uiState.tasks.first { it.taskId == id }
                            RatingScreen(
                                navController = navController,
                                food,
                                uiStateOrder = uiStateOrder,
                                uiStateAccount = uiStateAccount,
                            )
                        }
                        composable("CartComponent/{foodId}") { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("foodId")
                            val foodCart = (uiState.tasks.first { it.taskId == id })

                            CartComponent(
                                navController = navController,
                                uiStateAccount = uiStateAccount,
                                foodCart,
                                uiStateOrder = uiStateOrder,
                                setUserID = {userID ->
                                    orderViewModel.sendEvent(
                                        event = OrderScreenUiEvent.OnChangeUserID(userID = userID),
                                    )
                                },
                                setFoodCode = {code ->
                                    orderViewModel.sendEvent(
                                        event = OrderScreenUiEvent.OnChangeFoodCode(code = code),
                                    )
                                },
                                setOrderAddress = { address ->
                                    orderViewModel.sendEvent(
                                        event = OrderScreenUiEvent.OnChangeOrderAddress(address = address),
                                    )
                                },
                                setOrderPayment = { payment ->
                                    orderViewModel.sendEvent(
                                        event = OrderScreenUiEvent.OnChangeOrderPayment(
                                            paymentMethods = payment
                                        ),
                                    )
                                },
                                setOrderQuantity = { quantity ->
                                    orderViewModel.sendEvent(
                                        event = OrderScreenUiEvent.OnChangeOrderQuantity(quantity = quantity),
                                    )
                                },
                                setOrderTitle = {title ->
                                    orderViewModel.sendEvent(
                                        event = OrderScreenUiEvent.OnChangeOrderTitle(title = title),
                                    )
                                },
                                setOrderPrice = {price ->
                                    orderViewModel.sendEvent(
                                        event = OrderScreenUiEvent.OnChangeOrderPrice(price = price),
                                    )
                                },
                                setOrderImage = {image ->
                                    orderViewModel.sendEvent(
                                        event = OrderScreenUiEvent.OnChangeOrderImageOrder(imageOrder = image),
                                    )
                                },
                                setOrderTotal = {total ->
                                    orderViewModel.sendEvent(
                                        event = OrderScreenUiEvent.OnChangeOrderTotal(total = total),
                                    )
                                },
                                saveOrder = {
                                    orderViewModel.sendEvent(
                                        event = OrderScreenUiEvent.AddOrder(
                                            userID = uiStateOrder.currentUserID,
                                            code = uiStateOrder.currentCode,
                                            address = uiStateOrder.currentAddressOrder,
                                            imageOrder = uiStateOrder.imgUrlOrder,
                                            titleOrder = uiStateOrder.currentTitle,
                                            price = uiStateOrder.currentPriceOrder,
                                            quantity = uiStateOrder.currentQuantityOrder,
                                            paymentMethods = uiStateOrder.currentPaymentOrder,
                                            total = uiStateOrder.total

                                        ),
                                    )
                                },

                            )
                        }
                        composable("CartGeneralComponent") {

                            CartGeneralComponent(
                                navController = navController,
                                uiStateAccount = uiStateAccount,
                                uiState = uiStateCart,
                                uiStateOrder = uiStateOrder,
                                setUserID = {userID ->
                                    orderViewModel.sendEvent(
                                        event = OrderScreenUiEvent.OnChangeUserID(userID = userID),
                                    )
                                },
                                setFoodCode = {code ->
                                    orderViewModel.sendEvent(
                                        event = OrderScreenUiEvent.OnChangeFoodCode(code = code),
                                    )
                                },
                                setOrderAddress = { address ->
                                    orderViewModel.sendEvent(
                                        event = OrderScreenUiEvent.OnChangeOrderAddress(address = address),
                                    )
                                },
                                setOrderPayment = { payment ->
                                    orderViewModel.sendEvent(
                                        event = OrderScreenUiEvent.OnChangeOrderPayment(
                                            paymentMethods = payment
                                        ),
                                    )
                                },
                                setOrderQuantity = { quantity ->
                                    orderViewModel.sendEvent(
                                        event = OrderScreenUiEvent.OnChangeOrderQuantity(quantity = quantity),
                                    )
                                },
                                setOrderTitle = {title ->
                                    orderViewModel.sendEvent(
                                        event = OrderScreenUiEvent.OnChangeOrderTitle(title = title),
                                    )
                                },
                                setOrderPrice = {price ->
                                    orderViewModel.sendEvent(
                                        event = OrderScreenUiEvent.OnChangeOrderPrice(price = price),
                                    )
                                },
                                setOrderImage = {image ->
                                    orderViewModel.sendEvent(
                                        event = OrderScreenUiEvent.OnChangeOrderImageOrder(imageOrder = image),
                                    )
                                },
                                setOrderTotal = {total ->
                                    orderViewModel.sendEvent(
                                        event = OrderScreenUiEvent.OnChangeOrderTotal(total = total),
                                    )
                                },
                                saveOrder = {
                                    orderViewModel.sendEvent(
                                        event = OrderScreenUiEvent.AddOrder(
                                            userID = uiStateOrder.currentUserID,
                                            code = uiStateOrder.currentCode,
                                            address = uiStateOrder.currentAddressOrder,
                                            imageOrder = uiStateOrder.imgUrlOrder,
                                            titleOrder = uiStateOrder.currentTitle,
                                            price = uiStateOrder.currentPriceOrder,
                                            quantity = uiStateOrder.currentQuantityOrder,
                                            paymentMethods = uiStateOrder.currentPaymentOrder,
                                            total = uiStateOrder.total

                                        ),
                                    )
                                },
                                deleteCart = { foodId ->
                                    cartViewModel.sendEvent(
                                        event = CartsScreenUiEvent.DeleteNote(
                                            foodId = foodId
                                        ),
                                    )
                                },
                            )
                        }
                        composable("UpdateAddressComponent") {

                            UpdateAddressComponent(
                                navController = navController,
                                uiStateOrder = uiStateOrder
                            )
                        }
                        composable("UpdatePaymentMethodsComponent/{foodId}/{price}") {backStackEntry->
                            val id = backStackEntry.arguments?.getString("foodId")
                            val foodCart = (uiState.tasks.first { it.taskId == id })
                            val priceString = backStackEntry.arguments?.getString("price")
                            val price = priceString?.toIntOrNull()

                            if (price != null) {
                                UpdatePaymentMethodsComponent(
                                    foodCart=foodCart,
                                    price = price,
                                    savedEmail = savedEmail.value!!,
                                    productList = productList,
                                    uiStateAccount = uiStateAccount,
                                    navController = navController,
                                    uiStateOrder = uiStateOrder,
                                    setUserID = {userID ->
                                        orderViewModel.sendEvent(
                                            event = OrderScreenUiEvent.OnChangeUserID(userID = userID),
                                        )
                                    },
                                    setFoodCode = {code ->
                                        orderViewModel.sendEvent(
                                            event = OrderScreenUiEvent.OnChangeFoodCode(code = code),
                                        )
                                    },
                                    setOrderAddress = { address ->
                                        orderViewModel.sendEvent(
                                            event = OrderScreenUiEvent.OnChangeOrderAddress(address = address),
                                        )
                                    },
                                    setOrderPayment = { payment ->
                                        orderViewModel.sendEvent(
                                            event = OrderScreenUiEvent.OnChangeOrderPayment(
                                                paymentMethods = payment
                                            ),
                                        )
                                    },
                                    setOrderQuantity = { quantity ->
                                        orderViewModel.sendEvent(
                                            event = OrderScreenUiEvent.OnChangeOrderQuantity(quantity = quantity),
                                        )
                                    },
                                    setOrderTitle = {title ->
                                        orderViewModel.sendEvent(
                                            event = OrderScreenUiEvent.OnChangeOrderTitle(title = title),
                                        )
                                    },
                                    setOrderPrice = {price ->
                                        orderViewModel.sendEvent(
                                            event = OrderScreenUiEvent.OnChangeOrderPrice(price = price),
                                        )
                                    },
                                    setOrderImage = {image ->
                                        orderViewModel.sendEvent(
                                            event = OrderScreenUiEvent.OnChangeOrderImageOrder(imageOrder = image),
                                        )
                                    },
                                    setOrderTotal = {total ->
                                        orderViewModel.sendEvent(
                                            event = OrderScreenUiEvent.OnChangeOrderTotal(total = total),
                                        )
                                    },
                                    saveOrder = {
                                        orderViewModel.sendEvent(
                                            event = OrderScreenUiEvent.AddOrder(
                                                userID = uiStateOrder.currentUserID,
                                                code = uiStateOrder.currentCode,
                                                address = uiStateOrder.currentAddressOrder,
                                                imageOrder = uiStateOrder.imgUrlOrder,
                                                titleOrder = uiStateOrder.currentTitle,
                                                price = uiStateOrder.currentPriceOrder,
                                                quantity = uiStateOrder.currentQuantityOrder,
                                                paymentMethods = uiStateOrder.currentPaymentOrder,
                                                total = uiStateOrder.total

                                            ),
                                        )
                                    }


                                )
                            } else {
                                // Xử lý trường hợp giá trị price không hợp lệ
                            }
                        }
                        composable("UpdateDiscountComponent") {

                            UpdateDiscountComponent(
                                navController = navController,
                                uiState = uiStateOrder
                            )
                        }
                        composable("ForgetPassword") {

                            ForgetPassword(
                                navController = navController,
                                uiStateAccount = uiStateAccount,
                                setEmail = { email->
                                    accountViewModel.sendEvent(
                                        event = SignInScreenUiEvent.OnChangeEmail(email = email),
                                    )
                                },
                                resetPassword = {
                                    accountViewModel.sendEvent(
                                        event = SignInScreenUiEvent.ForgetPassword(
                                            email = uiStateAccount.currentEmail
                                        ),
                                    )
                                },
                            )
                        }


                    }
                }
            }
        }
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabItems.forEachIndexed { index, item ->
                androidx.compose.material3.Tab(
                    modifier = Modifier
                        .background(color = Color.White)
                        .height(63.dp),
                    selected = index == selectedTabIndex,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = item.title,
                            fontSize = 9.sp,
                            color = if (index == selectedTabIndex) {
                                Color.Red
                            } else {
                                Color.Gray
                            }
                        )
                    },

                    icon = {
                        androidx.compose.material3.Icon(
                            imageVector = if (index == selectedTabIndex) {
                                item.selectedIcon
                            } else {
                                item.unselectedIcon
                            },
                            contentDescription = item.title,
                            tint = if (index == selectedTabIndex) {
                                Color.Red
                            } else {
                                Color.Gray
                            }

                        )
                    }
                )
            }
        }

    }
}