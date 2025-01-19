package com.example.jetmusic.View.Components.InputFields

import android.view.KeyEvent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.jetmusic.Helpers.CountryCodePicker.countryCodeToPhoneCode
import com.example.jetmusic.Helpers.CountryCodePicker.flagEmoji
import com.example.jetmusic.Helpers.CountryCodePicker.getPhoneCodeByCountryCode
import com.example.jetmusic.Helpers.PhoneVisualTransformations.PhoneVisualTransformation
import com.example.jetmusic.PHONE_FIELD_MASK
import com.example.jetmusic.ui.theme.typography
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import java.util.Locale
import androidx.compose.material3.MaterialTheme.colorScheme
import com.example.jetmusic.ui.theme.tidalGradient

@Composable
fun PhoneTextField(
    modifier: Modifier = Modifier,
    countryCode: String,
    phone: String,
    mask: String = PHONE_FIELD_MASK,
    maskNumber: Char = '0',
    onTextChange: (String) -> Unit,
    selectCountryCode: (String) -> Unit,
    dropDownMenuState: LazyListState = rememberLazyListState(),
    textStyle: TextStyle = typography().bodyMedium,
    shape: RoundedCornerShape = RoundedCornerShape(10.sdp),
    errorBorder: BorderStroke = BorderStroke(1.sdp, colorScheme.error),
    unfocusedBorder: BorderStroke = BorderStroke(1.sdp,Color.White),
    focusedBorder: BorderStroke = BorderStroke(1.sdp, tidalGradient),
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }

    val isFocused by interactionSource.collectIsFocusedAsState()

    var isExpanded by remember { mutableStateOf(false) }

    val selectedPhoneCode by remember(countryCode) {
        mutableStateOf(getPhoneCodeByCountryCode(countryCode))
    }
    val selectedCountryFlagEmoji by remember(countryCode) {
        mutableStateOf(Locale("",countryCode).flagEmoji)
    }

    val countryCodes by remember { mutableStateOf(countryCodeToPhoneCode.keys.sorted()) }

    val border = if (isFocused) focusedBorder else unfocusedBorder

    Row(
        modifier = modifier
            .border(border,shape),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(shape)
                    .clickable {
                        isExpanded = true
                        focusManager.clearFocus()
                    },
                contentAlignment = Alignment.CenterStart,
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 10.sdp),
                    text = "$selectedCountryFlagEmoji $selectedPhoneCode",
                    fontSize = textStyle.fontSize * 1.1f
                )
            }

            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = {isExpanded = false }
            ) {
                Box(
                    modifier = Modifier
                        .width(80.sdp)
                        .height(220.sdp)
                ){
                    LazyColumn(
                        state = dropDownMenuState
                    ) {
                        items(countryCodes) { countryCode ->
                            val phoneCode = getPhoneCodeByCountryCode(countryCode)
                            val countryFlagEmoji = Locale("", countryCode).flagEmoji

                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = "$countryFlagEmoji  $phoneCode",
                                    )
                                },
                                onClick = {
                                    isExpanded = false
                                    selectCountryCode(countryCode)
                                }
                            )
                        }
                    }
                }
            }
        }

        BasicTextField(
            modifier = Modifier
                .focusRequester(focusRequester)
                .onKeyEvent { event ->
                    if (event.key.nativeKeyCode == KeyEvent.KEYCODE_BACK) {
                        focusManager.clearFocus()
                        true
                    } else {
                        false
                    }
                },
            value = phone,
            onValueChange = { value ->
                onTextChange(value.take(mask.count { it == maskNumber }))
            },
            textStyle = textStyle,
            singleLine = true,
            interactionSource = interactionSource,
            visualTransformation = PhoneVisualTransformation(mask,maskNumber),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            ),
            cursorBrush = SolidColor(Color.White),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        Modifier
                            .padding(horizontal = 7.sdp)
                    ) {
                        if (phone.isBlank()) {
                            Text(
                                text = PHONE_FIELD_MASK,
                                style = textStyle,
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                            )
                        }

                        innerTextField()
                    }
                }
            }
        )
    }
}