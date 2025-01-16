package com.mk.sepetandroid.presentation.password.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mk.sepetandroid.R
import com.mk.sepetandroid.domain.model.auth.UpdatePasswordModel
import com.mk.sepetandroid.presentation.password.PasswordEvent
import com.mk.sepetandroid.presentation.password.PasswordState
import com.mk.sepetandroid.presentation.widget.SepetButton
import com.mk.sepetandroid.presentation.widget.SepetPasswordTextField
import com.mk.sepetandroid.presentation.widget.SepetText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordView(
    navHostController : NavHostController,
    state: PasswordState,
    onEvent : (PasswordEvent) -> Unit
) {
    var password by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = state.isError, key2 = state.isSuccess) {
        if(state.isError || state.isSuccess){
            val result = snackbarHostState.showSnackbar(
                state.message ?: "",
                withDismissAction = true
            )

            when(result){
                SnackbarResult.Dismissed -> {
                    onEvent(PasswordEvent.CloseDialog)
                }
                SnackbarResult.ActionPerformed -> {}
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        topBar = {
            TopAppBar(
                title = {
                    SepetText(
                        text = "Refresh password"
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navHostController.navigateUp()
                        },
                        content = {
                            Icon(
                                painterResource(R.drawable.baseline_arrow_back_ios_new_24),
                                contentDescription = "Back to the before page"
                            )
                        }
                    )
                }
            )
        },
        content = { it ->
            Column(
                modifier = Modifier
                    .padding(
                        top = it.calculateTopPadding(),
                        start = 10.dp,
                        end = 10.dp
                    )
            ) {
                SepetText(
                    text = "Your current password"
                )
                SepetPasswordTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = password,
                    valueChanged = { password = it },
                )

                SepetText(
                    modifier = Modifier
                        .padding(top = 10.dp),
                    text = "New password"
                )

                SepetPasswordTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = newPassword,
                    valueChanged = { newPassword = it },
                )

                SepetButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp)
                        .height(50.dp),
                    text = "Change password",
                    onClick = {
                        onEvent(PasswordEvent.SetNewPassword(UpdatePasswordModel(password, newPassword)))
                    }
                )
            }
        }
    )
}