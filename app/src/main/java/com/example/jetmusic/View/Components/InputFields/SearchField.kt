package com.example.jetmusic.View.Components.InputFields

import android.view.KeyEvent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import androidx.compose.ui.unit.Dp
import com.example.jetmusic.BASE_BUTTON_HEIGHT
import com.example.jetmusic.BASE_BUTTON_WIDTH
import com.example.jetmusic.ui.theme.tidalGradient
import com.example.jetmusic.ui.theme.typography
import ir.kaaveh.sdpcompose.sdp

@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (value: String) -> Unit,
    textStyle: TextStyle = typography().bodyMedium,
    width: Dp = BASE_BUTTON_WIDTH.sdp,
    height: Dp = (BASE_BUTTON_HEIGHT - 4).sdp,
    shape: RoundedCornerShape = RoundedCornerShape(25.sdp),
    primaryColor: Color = colorScheme.background,
    inversePrimaryColor: Color = colorScheme.inversePrimary,
    unfocusedBorder: BorderStroke = BorderStroke(1.sdp, Color.White),
    focusedBorder: BorderStroke = BorderStroke(1.sdp, tidalGradient),
    placeHolder: String = "",
    onSearchClick: () -> Unit,
    onCancelClick: () -> Unit,
    searchIcon: @Composable () -> Unit,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val contentColor = if (isFocused) inversePrimaryColor else inversePrimaryColor.copy(0.85f)

    val border = if (isFocused) focusedBorder else unfocusedBorder

    Box(
        modifier = modifier
            .focusable(isFocused, interactionSource)
            .width(width)
            .height(height)
            .clip(shape)
            .background(primaryColor)
            .border(border, shape)
            .focusRequester(focusRequester)
            .clickable {
                focusRequester.requestFocus()
            },
        contentAlignment = Alignment.Center,
    ) {
        BasicTextField(
            modifier = Modifier
                .focusRequester(focusRequester)
                .onKeyEvent { event ->
                    if (event.key.nativeKeyCode == KeyEvent.KEYCODE_BACK) {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                        true
                    } else {
                        false
                    }
                },
            value = text,
            onValueChange = onTextChange,
            singleLine = true,
            readOnly = true,
            interactionSource = interactionSource,
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    onSearchClick()
                }
            ),
            cursorBrush = SolidColor(Color.White),
            textStyle = textStyle.copy(
                color = contentColor,
                fontSize = textStyle.fontSize,
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .height(20.sdp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    if(isFocused || leadingIcon == null) {
                        searchIcon()
                    } else {
                        leadingIcon()
                    }

                    Box(
                        Modifier
                            .padding(horizontal = 10.sdp)
                            .weight(1f)
                    ) {
                        if (text.isBlank()) {
                            Text(
                                text = text.ifEmpty { placeHolder },
                                style = textStyle,
                                color = contentColor,
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                            )
                        }

                        innerTextField()
                    }

                    if(text.isNotBlank()) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "clear",
                            tint = contentColor,
                            modifier = Modifier
                                .size(24.sdp)
                                .clickable { onCancelClick() }
                        )
                    }
                }
            }
        )
    }
}