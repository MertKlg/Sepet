package com.mk.sepetandroid.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import com.mk.sepetandroid.presentation.route.RouteViewModel
import com.mk.sepetandroid.presentation.route.view.Router
import com.mk.sepetandroid.presentation.ui.theme.SepetAndroidTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SepetAndroidTheme{

                val viewmodel = hiltViewModel<RouteViewModel>()
                val state = viewmodel.state.value

                Router(
                    viewmodel::onEvent,
                    state
                )
            }
        }

    }
}