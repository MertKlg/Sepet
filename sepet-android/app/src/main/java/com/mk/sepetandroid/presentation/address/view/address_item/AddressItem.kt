package com.mk.sepetandroid.presentation.address.view.address_item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mk.sepetandroid.presentation.address.AddressEvent
import com.mk.sepetandroid.presentation.address.AddressState
import com.mk.sepetandroid.presentation.widget.SepetText
import kotlinx.coroutines.launch

@Composable
fun AddressItem(
    state : AddressState,
    topPadding : Dp,
    onEvent: (AddressEvent) -> Unit,
    nextPage : () -> Unit
){
    when(state.addressList.isEmpty()){
        true -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SepetText(
                    text = "No address found",
                    fontSize = 25.sp
                )
            }
        }

        false -> {
            LazyColumn(
                modifier = Modifier
                    .padding(top = topPadding)
                    .fillMaxSize()
            ) {
                items(state.addressList){
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .clip(RoundedCornerShape(10.dp)),
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ){
                            Column(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .clip(RoundedCornerShape(30.dp))
                                    .size(50.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ){
                                Icon(
                                    Icons.Default.LocationOn,
                                    contentDescription = "",
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.Start
                            ){
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ){
                                    SepetText(
                                        it.name.toString()
                                    )

                                    Row {
                                        Icon(
                                            Icons.Default.Edit,
                                            contentDescription = "",
                                            modifier = Modifier
                                                .padding(end = 10.dp)
                                                .clickable {
                                                    onEvent(AddressEvent.WillUpdateAddress(it))
                                                    nextPage()
                                                }
                                        )

                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = "",
                                            modifier = Modifier
                                                .clickable {
                                                    onEvent(
                                                        AddressEvent.DeleteAddress(
                                                            it
                                                        )
                                                    )
                                                }
                                        )
                                    }
                                }

                                SepetText(
                                    text = it.fullAddressText.toString()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}