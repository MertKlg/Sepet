package com.mk.sepetandroid.presentation.signin.view

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mk.sepetandroid.domain.model.enums.RouterEnum
import com.mk.sepetandroid.domain.model.auth.SignInModel
import com.mk.sepetandroid.presentation.signin.SignInEvent
import com.mk.sepetandroid.presentation.signin.SignInState
import com.mk.sepetandroid.presentation.widget.SepetFieldWithLabel
import com.mk.sepetandroid.presentation.widget.SepetPasswordTextField
import com.mk.sepetandroid.presentation.widget.SepetText

@Composable
fun SignInView(
    navHostController : NavHostController,
    state : SignInState,
    onEvent : (SignInEvent) -> Unit
) {
    val height = LocalConfiguration.current.screenHeightDp
    val width = LocalConfiguration.current.screenWidthDp
    val inputHeight = (height * .09).dp

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val snackbarHostState = SnackbarHostState()

    LaunchedEffect(key1 = state.successSignIn) {
        if (state.successSignIn)
            navHostController.navigate(RouterEnum.PROFILE_SCREEN.name)
    }

    LaunchedEffect(key1 = state.isMessageAvailable){
        if(state.isMessageAvailable)
            snackbarHostState.showSnackbar(state.message, withDismissAction = true)
    }


    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ){
        it.calculateTopPadding()
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ){
            Column(
                modifier = Modifier
                    .height((height * .4).dp)
                    .width((width * .2).dp)
                    .rotate(30F)
                    .offset(
                        x = (-90).dp,
                        y = 0.dp
                    )
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.secondary,
                        RoundedCornerShape(
                            topEnd = 100.dp,
                            bottomEnd = 100.dp
                        )
                    )
            ) {}

            Column(
                modifier = Modifier
                    .align(
                        Alignment.TopEnd
                    )
                    .height((height * .4).dp)
                    .width((width * .7).dp)
                    .rotate(-10F)
                    .offset(
                        x = (0).dp,
                        y = (height - 200).dp
                    )

                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.primary,
                        CircleShape
                    )
            ) {}

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                SepetText(
                    text = "Sign in",
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold
                )

                SepetText(
                    text = "Please sign in to your existing account"
                )

                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(25.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Spacer(modifier = Modifier.padding(10.dp))

                    Column{

                        SepetFieldWithLabel(
                            label = "Email",
                            text = email,
                            textChanged = {email = it} ,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(inputHeight),
                            labelColor = MaterialTheme.colorScheme.onPrimary,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                unfocusedContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )

                    }

                    Spacer(modifier = Modifier.padding(10.dp))

                    Column{
                        SepetText(
                            text = "Password".uppercase(),
                            fontWeight = FontWeight.Medium,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        Spacer(modifier = Modifier.padding(5.dp))

                        SepetPasswordTextField(
                            value = password,
                            valueChanged = {
                                password = it
                            } ,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(inputHeight),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                unfocusedContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                                errorIndicatorColor = Color.Unspecified,
                                unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                    }

                    Spacer(modifier = Modifier.padding(10.dp))

                    Button(
                        onClick = {
                            onEvent(SignInEvent.SignIn(SignInModel(email = email, password = password)))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height((height * 0.09).dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        shape = RoundedCornerShape(15.dp)
                    ) {
                        SepetText(
                            text = "Sign in".uppercase()
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))

                    Row {
                        SepetText(
                            text = "You don't have a account? ",
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onPrimary,
                        )

                        SepetText(
                            text = "Sign up",
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onPrimary.copy(0.8F),
                            modifier = Modifier.clickable {
                                navHostController.navigate(RouterEnum.SIGN_UP.name)
                            }
                        )
                    }
                }
            }
        }
    }


}