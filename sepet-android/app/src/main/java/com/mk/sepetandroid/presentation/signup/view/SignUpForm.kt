package com.mk.sepetandroid.presentation.signup.view

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mk.sepetandroid.domain.model.auth.RegisterModel
import com.mk.sepetandroid.domain.model.enums.RouterEnum
import com.mk.sepetandroid.presentation.signup.SignUpEvent
import com.mk.sepetandroid.presentation.widget.SepetFieldWithLabel
import com.mk.sepetandroid.presentation.widget.SepetText
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SignUpForm(
    navHostController: NavHostController,
    onEvent : (SignUpEvent) -> Unit,
) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rePassword by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    val height = LocalConfiguration.current.screenHeightDp
    val width = LocalConfiguration.current.screenWidthDp

    val navScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = {3})

    HorizontalPager(
        state = pagerState,
        userScrollEnabled = true,
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(25.dp),
    ) {

        Column(
            modifier = Modifier.padding(5.dp)
        ){
            when(it){
                0 -> {
                    SepetFieldWithLabel(
                        "Name",
                        text = name,
                        textChanged = { nameText ->
                            name = nameText
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height((height * 0.09).dp)
                    )

                    Spacer(modifier = Modifier.padding(10.dp))

                    SepetFieldWithLabel(
                        "Email",
                        text = email,
                        textChanged = { emailText ->
                            email = emailText
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height((height * 0.09).dp)
                    )
                }
                1 -> {
                    SepetFieldWithLabel(
                        label = "Password" ,
                        text = password,
                        textChanged = {password = it} ,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height((height * 0.09).dp)
                    )
                    Spacer(modifier = Modifier.padding(10.dp))

                    SepetFieldWithLabel(
                        label = "Re-password" ,
                        text = rePassword,
                        textChanged = {rePassword = it} ,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height((height * 0.09).dp)
                    )
                }

                2 -> {
                    SepetFieldWithLabel(
                        label = "Phone",
                        text = phone,
                        textChanged = { phoneC ->
                            Regex("[^0-9]").replace(phoneC, "").let {
                                if(it.length < 11)
                                    phone = it
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height((height * 0.09).dp)
                    )
                }
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Button(
                onClick = {
                    navScope.launch {
                        if (pagerState.currentPage < pagerState.pageCount - 1) {
                            pagerState.animateScrollToPage(
                                page = pagerState.currentPage + 1,
                                animationSpec = spring(
                                    stiffness = Spring.StiffnessLow
                                )
                            )
                        } else {
                            onEvent(
                                SignUpEvent.SignUp(
                                    RegisterModel(
                                        username = name,
                                        email = email,
                                        password = password,
                                        passwordAgain = rePassword,
                                        phone = phone
                                    )
                                )
                            )
                        }
                    }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height((height * 0.09).dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                shape = RoundedCornerShape(15.dp)
            ) {
                SepetText(
                    text = if((pagerState.pageCount - 1) == pagerState.currentPage) "Sign up".uppercase() else "Next".uppercase()
                )
            }
        }

    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        SepetText(
            text = "Have a already account? ",
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.padding(2.dp))

        SepetText(
            text = "Sign in",
            color = MaterialTheme.colorScheme.primary,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable {
                navHostController.navigate(RouterEnum.SIGN_IN.name)
            }
        )
    }
}