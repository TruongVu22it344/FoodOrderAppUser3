package com.breens.orderfoodapp.feature_tasks.ui.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.breens.orderfoodapp.feature_tasks.state.BannerScreenUiState
import com.example.movieui.core.theme.Gray
import com.example.movieui.core.theme.Yellow

import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BannerComponent(uiStateBanner: BannerScreenUiState, navController: NavHostController,) {



        val pagerState = rememberPagerState()
        val bannerIndex = remember { mutableStateOf(0) }

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                bannerIndex.value = page
            }
        }

        /// auto scroll
        LaunchedEffect(Unit) {
            while (true) {
                delay(10_000)
                tween<Float>(1500)
                pagerState.animateScrollToPage(
                    page = (pagerState.currentPage + 1) % pagerState.pageCount
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
                .padding(horizontal = 0.dp)
        ) {
            HorizontalPager(
                state = pagerState,
                count = (uiStateBanner.banners).size,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
            ) { index ->

                Image(
                    painter = rememberImagePainter(uiStateBanner.banners[index].imageBanner),
                    contentDescription = "Banners",
                    contentScale = ContentScale.FillBounds,
                )
            }
            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                repeat((uiStateBanner.banners).size) { index ->
                    val height = 12.dp
                    val width = if (index == bannerIndex.value) 28.dp else 12.dp
                    val color = if (index == bannerIndex.value) Yellow else Gray

                    Surface(
                        modifier = Modifier
                            .padding(end = 6.dp)
                            .size(width, height)
                            .clip(RoundedCornerShape(20.dp)),
                        color = color,
                    ) {
                    }
                }
            }
        }
    Spacer(modifier = Modifier.height(20.dp))

}