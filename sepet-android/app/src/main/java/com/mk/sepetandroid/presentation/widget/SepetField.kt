package com.mk.sepetandroid.presentation.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mk.sepetandroid.R


@Composable
private fun fieldColors(
    materialTheme : MaterialTheme = MaterialTheme
): TextFieldColors {
    return TextFieldDefaults.colors(
        disabledContainerColor = materialTheme.colorScheme.primary,
        focusedContainerColor = materialTheme.colorScheme.primary,
        unfocusedContainerColor = materialTheme.colorScheme.primary,
        errorContainerColor = materialTheme.colorScheme.error,

        focusedTextColor = MaterialTheme.colorScheme.onPrimary,
        unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
        errorTextColor = MaterialTheme.colorScheme.error,
        disabledTextColor = MaterialTheme.colorScheme.onPrimary,

        focusedIndicatorColor = Color.Transparent,
        errorIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
    )
}

@Composable
private fun fieldStyle(): TextStyle {
    return TextStyle(
        color = MaterialTheme.colorScheme.onPrimary,
        fontFamily = fontFamily
    )
}

@Composable
fun SepetTextField(
    value : String,
    valueChanged : (String) -> Unit,
    colors : TextFieldColors = fieldColors(),
    style : TextStyle = fieldStyle(),
    modifier: Modifier,
    singleLine : Boolean =  true,
    shape : RoundedCornerShape = RoundedCornerShape(10.dp),
    enabled : Boolean = true,
    readOnly : Boolean = false,
    label : @Composable() (() -> Unit)? = null,
    placeholder : @Composable() (() -> Unit)? = null,
    leadingIcon : @Composable() (() -> Unit)? = null,
    trailingIcon : @Composable() (() -> Unit)? = null,
    prefix : @Composable() (() -> Unit)? = null,
    suffix : @Composable() (() -> Unit)? = null,
    supportingText : @Composable() (() -> Unit)? = null,
    isError : Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    minLines : Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }

) {
    TextField(
        value = value,
        onValueChange = valueChanged,
        shape = shape,
        colors = colors,
        textStyle = style,
        singleLine = singleLine,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        label= label,
        placeholder= placeholder,
        leadingIcon= leadingIcon,
        trailingIcon= trailingIcon,
        prefix= prefix,
        suffix= suffix,
        supportingText= supportingText,
        isError = isError ,
        visualTransformation= visualTransformation,
        keyboardOptions= keyboardOptions,
        keyboardActions= keyboardActions,
        maxLines = if (singleLine) 1 else Int. MAX_VALUE,
        minLines = minLines,
        interactionSource = interactionSource,
    )
}


@Composable
fun SepetPasswordTextField(
    value : String,
    valueChanged : (String) -> Unit,
    modifier: Modifier,
    singleLine : Boolean =  true,
    colors : TextFieldColors = fieldColors()
) {

    var showPassword by remember { mutableStateOf(false) }
    SepetTextField(
        value = value,
        valueChanged = valueChanged,
        modifier = modifier,
        singleLine = singleLine,
        trailingIcon = {
            val changeIcon = if (showPassword) R.drawable.baseline_lock_open_24 else R.drawable.baseline_lock_outline_24
            val description = if (showPassword) "Show password" else "Hide password"
            Icon(
                painter = painterResource(id = changeIcon),
                contentDescription = description,
                modifier = Modifier
                    .clickable {
                        showPassword = !showPassword
                    }
            )
        },
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        colors = colors
    )
}


@Composable
fun SepetFieldWithLabel(
    label : String,
    labelFontSize : TextUnit = TextUnit.Unspecified,
    labelColor : Color = Color.Unspecified,
    text : String,
    textChanged : (String) -> Unit,
    colors: TextFieldColors = fieldColors(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    leadingIcon: @Composable() (() -> Unit)? = null,
    trailingIcon: @Composable() (() -> Unit)? = null,
    modifier: Modifier,
) {

    Column{
        SepetText(
            text = label.uppercase(),
            fontWeight = FontWeight.Medium,
            fontSize = labelFontSize,
            color = labelColor
        )

        //Spacer(modifier = Modifier.padding(5.dp))

        SepetTextField(
            value = text,
            valueChanged = textChanged ,
            modifier = modifier,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            colors = colors,
            keyboardOptions = keyboardOptions,
        )
    }
}
