package com.mk.sepetandroid.presentation.mydetails.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mk.sepetandroid.R
import com.mk.sepetandroid.domain.model.profile.ProfileModel
import com.mk.sepetandroid.presentation.mydetails.MyDetailsEvent
import com.mk.sepetandroid.presentation.mydetails.MyDetailsState
import com.mk.sepetandroid.presentation.widget.SepetButton
import com.mk.sepetandroid.presentation.widget.SepetFieldWithLabel
import com.mk.sepetandroid.presentation.widget.SepetText
import com.mk.sepetandroid.presentation.widget.SepetTopBar

@Composable
fun MyDetailsView(
    navHostController: NavHostController,
    state : MyDetailsState,
    onEvent : (MyDetailsEvent) -> Unit
) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    val snackbarHost = SnackbarHostState()

    if(state.profileModel.isNotEmpty()){
        val profile = state.profileModel.first()
        name = profile.username ?: ""
        email = profile.email ?: ""
        phone = profile.phone ?: ""
    }


    val height = LocalConfiguration.current.screenHeightDp

    val pageWidgetsModifier = Modifier
        .fillMaxWidth()
        .height((height * .07).dp)

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHost)
        },
        topBar = {
            SepetTopBar(
                title = { SepetText(text = "My details") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navHostController.navigateUp()
                        }
                    ) {
                        Icon(
                            painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                            contentDescription = ""
                        )
                    }
                }
            )
        }

    ) { it ->
        Column(
            modifier = Modifier
                .padding(
                    top = it.calculateTopPadding() + 10.dp,
                    start = 10.dp,
                    end = 10.dp
                )
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            SepetFieldWithLabel(
                label = "Name" ,
                text = name,
                textChanged = {name = it},
                modifier = pageWidgetsModifier,
            )

            Spacer(modifier = Modifier.padding(10.dp))

            SepetFieldWithLabel(
                label = "Email" ,
                text = email,
                textChanged = {email = it},
                modifier = pageWidgetsModifier,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                )
            )

            Spacer(modifier = Modifier.padding(10.dp))


            SepetFieldWithLabel(
                label = "Phone" ,
                text = phone,
                textChanged = {
                    Regex("[^0-9]").replace(it, "").let {
                        if(it.length < 11)
                            phone = it
                    }
                },
                modifier = pageWidgetsModifier,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                )
            )

            Spacer(modifier = Modifier.padding(10.dp))

            SepetButton(
                text = "Save",
                onClick = {
                    onEvent(
                        MyDetailsEvent.UpdateProfile(
                            ProfileModel(
                                username = name,
                                email = email,
                                image = "",
                                phone = phone
                            )
                        )
                    )
                },
                modifier = pageWidgetsModifier
            )
        }
    }

    LaunchedEffect(key1 = Unit) {
        onEvent(MyDetailsEvent.GetProfile)
    }

    LaunchedEffect(key1 = state.messageAvailable) {
        if(state.messageAvailable){
            snackbarHost.showSnackbar(state.message ?: "", withDismissAction = true).let {
                when(it){
                    SnackbarResult.Dismissed -> {
                        onEvent(MyDetailsEvent.CloseMessage)
                    }
                    SnackbarResult.ActionPerformed -> {}
                }
            }
        }

    }

}