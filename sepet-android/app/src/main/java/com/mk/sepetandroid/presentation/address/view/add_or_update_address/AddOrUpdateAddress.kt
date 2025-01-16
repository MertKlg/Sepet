package com.mk.sepetandroid.presentation.address.view.add_or_update_address

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mk.sepetandroid.domain.model.address.AddressModel
import com.mk.sepetandroid.presentation.address.AddressEvent
import com.mk.sepetandroid.presentation.address.AddressState
import com.mk.sepetandroid.presentation.widget.SepetButton
import com.mk.sepetandroid.presentation.widget.SepetFieldWithLabel
import com.mk.sepetandroid.presentation.widget.SepetText

@Composable
fun AddOrUpdateAddress(
    onEvent : (AddressEvent) -> Unit,
    state : AddressState,
    topPadding : Dp,
){
    val width = LocalConfiguration.current.screenWidthDp

    var name by remember { mutableStateOf("") }
    var town by remember { mutableStateOf( "") }
    var neighborhood by remember { mutableStateOf("") }
    var fullAddressText by remember { mutableStateOf("") }


    LaunchedEffect(key1 = state.willUpdateAddress != null) {
        name = state.willUpdateAddress?.name ?: ""
        town = state.willUpdateAddress?.town ?: ""
        neighborhood = state.willUpdateAddress?.neighbourhood ?: ""
        fullAddressText = state.willUpdateAddress?.fullAddressText ?: ""
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = topPadding,
                start = 10.dp,
                end = 10.dp
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SepetText(
            text = if(state.willUpdateAddress != null) "Update address" else "Add address"
        )


        SepetFieldWithLabel(
            label = "Full address" ,
            text = fullAddressText,
            textChanged = {fullAddressText = it},
            modifier = Modifier
                .fillMaxWidth(),
            leadingIcon = {
                Icon(
                    Icons.Default.LocationOn,
                    contentDescription = ""
                )
            }
        )

        Spacer(modifier = Modifier.padding(5.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            SepetFieldWithLabel(
                label = "Town",
                text = town,
                textChanged = { town = it },
                modifier = Modifier
                    .width((width * .45).dp)
            )

            Spacer(modifier = Modifier.padding(10.dp))

            SepetFieldWithLabel(
                label = "Neighbourhood",
                text = neighborhood,
                textChanged = {
                    neighborhood = it
                },
                modifier = Modifier,
            )
        }

        Spacer(modifier = Modifier.padding(5.dp))

        SepetFieldWithLabel(
            label = "Address title",
            text = name,
            textChanged = {
                name = it
            },
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(10.dp))
        SepetButton(
            text = "Save",
            onClick = {
                onEvent(
                    if(state.willUpdateAddress != null){
                        AddressEvent.UpdateAddress(
                            AddressModel(
                                name = name,
                                town = town,
                                neighbourhood = neighborhood,
                                id = state.willUpdateAddress.id,
                                fullAddressText = fullAddressText
                            )
                        )
                    }else{
                        AddressEvent.AddAddress(
                            AddressModel(
                                name = name,
                                town = town,
                                neighbourhood = neighborhood,
                                id = null,
                                fullAddressText = fullAddressText
                            )
                        )
                    }
                )

            },
            modifier = Modifier
                .fillMaxWidth()
                .height((60).dp)
        )
    }
}