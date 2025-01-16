package com.mk.sepetandroid.presentation.profile.view

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mk.sepetandroid.domain.model.enums.RouterEnum
import com.mk.sepetandroid.domain.model.view.ProfileItemModel
import com.mk.sepetandroid.presentation.profile.ProfileEvent
import com.mk.sepetandroid.presentation.profile.ProfileState
import com.mk.sepetandroid.presentation.widget.NotFound
import com.mk.sepetandroid.presentation.widget.SepetButton
import com.mk.sepetandroid.presentation.widget.SepetLoading
import com.mk.sepetandroid.presentation.widget.SepetText
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ProfileView(
    navHostController : NavHostController,
    state : ProfileState,
    onEvent : (ProfileEvent) -> Unit
) {
    val height = LocalConfiguration.current.screenHeightDp
    val width = LocalConfiguration.current.screenWidthDp
    val profileActions = ProfileItemModel().getProfileItems()
    val snackbarHostState = SnackbarHostState()
    val scope = rememberCoroutineScope()


    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
    ){
        it.calculateTopPadding()
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ){
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Profile image",
                    modifier = Modifier
                        .width((width * .15).dp)
                        .height((height * .1).dp)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.primary,
                            RoundedCornerShape(10.dp)
                        )
                )

                Spacer(
                    modifier = Modifier
                        .padding(7.dp)
                )

                LazyColumn{
                    items(state.profile){
                        SepetText(
                            text = it.username ?: "",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier
                                .padding(4.dp)
                        )

                        SepetText(
                            text = it.email ?: "",
                            fontSize = 15.sp,
                        )
                    }
                }
            }
            Spacer(
                modifier = Modifier
                    .padding(7.dp)
            )
            HorizontalDivider()

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ){
                items(profileActions){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(
                                15.dp
                            )
                            .clickable {
                                try{
                                    it.route?.let {
                                        navHostController.navigate(it.name)
                                    }
                                }catch (e : Exception){
                                    e.printStackTrace()
                                }
                            },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = it.icon!!),
                            contentDescription = "${it.name} icon",
                            modifier = Modifier
                                .width((width * .07).dp)
                                .height((height * .07).dp),
                        )

                        SepetText(
                            text = it.name!!,
                            fontSize = 20.sp,
                        )

                        Icon(
                            Icons.AutoMirrored.Default.KeyboardArrowRight,
                            contentDescription = "${it.name} route",
                            modifier = Modifier
                                .width((width * .07).dp)
                                .height((height * .07).dp),
                        )
                    }
                    HorizontalDivider()
                }
            }
        }
    }


    LaunchedEffect(key1 = Unit) {
        onEvent(ProfileEvent.GetProfile)
    }

    LaunchedEffect(key1 = state.routerEnum) {
        if (state.routerEnum.isNotEmpty())
            navHostController.navigate(state.routerEnum)
    }

    LaunchedEffect(key1 = state.isMessageAvailable) {
        if (state.isMessageAvailable){
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = state.message,
                    withDismissAction = true
                ).let {
                    if(it == SnackbarResult.ActionPerformed){
                        onEvent(ProfileEvent.CloseMessage)
                    }
                }
            }
        }
    }

}