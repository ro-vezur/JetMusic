package com.example.jetmusic.View.Components.InputFields

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import com.example.jetmusic.ui.theme.typography
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.example.jetmusic.BASE_BUTTON_HEIGHT
import com.example.jetmusic.BASE_BUTTON_WIDTH
import com.example.jetmusic.Helpers.Validation.Result.ValidationResults
import com.example.jetmusic.ui.theme.tidalGradient

@Composable
fun ValidationTextInputField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (value: String) -> Unit,
    textStyle: TextStyle = typography().bodyMedium,
    textAlignment: Alignment = Alignment.Center,
    width: Dp = BASE_BUTTON_WIDTH.sdp,
    height: Dp = (BASE_BUTTON_HEIGHT - 4).sdp,
    maxHeight: Dp = height,
    shape: RoundedCornerShape = RoundedCornerShape(25.sdp),
    background: Color = colorScheme.background,
    errorBorder: BorderStroke = BorderStroke(1.sdp, colorScheme.error),
    unfocusedBorder: BorderStroke = BorderStroke(1.sdp,Color.White),
    focusedBorder: BorderStroke = BorderStroke(1.sdp, tidalGradient),
    textPadding: PaddingValues = PaddingValues(),
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    validationResults: ValidationResults = ValidationResults.NONE,
    placeHolder: String = "",
    leadingIcon: ImageVector? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions()
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val isError = validationResults == ValidationResults.ERROR
    val isCorrect = validationResults == ValidationResults.CORRECT

    val contentColor = if(!readOnly) {
        if (isError) colorScheme.error else {
            if (isFocused) Color.White else Color.White.copy(0.85f)
        }
    } else Color.White

    val border = if (isError) errorBorder
    else { if (isFocused && !readOnly) focusedBorder else unfocusedBorder }

    LaunchedEffect(keyboardController) {

    }

    Box(
        modifier = modifier
            .focusable(isFocused, interactionSource)
            .width(width)
            .heightIn(height, maxHeight)
            .clip(shape)
            .background(background)
            .border(border, shape)
            .focusRequester(focusRequester)
            .clickable {
                focusRequester.requestFocus()
            },
        contentAlignment = textAlignment,
    ) {
        BasicTextField(
            modifier = Modifier
                .padding(textPadding)
                .focusRequester(focusRequester)
                .onKeyEvent { event ->
                    if (event.key.nativeKeyCode == android.view.KeyEvent.KEYCODE_BACK) {
                        focusManager.clearFocus()
                        true
                    } else {
                        false
                    }
                },
            value = text,
            onValueChange = onTextChange,
            singleLine = singleLine,
            readOnly = readOnly,
            interactionSource = interactionSource,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            ),
            cursorBrush = SolidColor(Color.White),
            textStyle = textStyle.copy(
                color = contentColor,
                fontSize = textStyle.fontSize * if (visualTransformation == PasswordVisualTransformation()) (1.18f) else (1f),
                letterSpacing = if (visualTransformation == PasswordVisualTransformation()) 1.ssp else 0.ssp
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .heightIn(20.sdp, maxHeight),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (leadingIcon != null) {
                        Icon(
                            imageVector = leadingIcon,
                            contentDescription = placeHolder,
                            tint = contentColor,
                            modifier = Modifier
                                .padding(start = 12.sdp)
                                .size(24.sdp)
                        )
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

                    Row(
                        modifier = Modifier
                            .padding(end = 14.sdp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(7.sdp)
                    ) {
                        if (trailingIcon != null) {
                            trailingIcon()
                        }

                        if(isCorrect) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "check",
                                tint = Color.Green,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.sdp))
                                    .size(24.sdp)
                            )
                        }
                    }
                }
            }
        )
    }
}