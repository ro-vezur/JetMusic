package com.example.jetmusic.View.Screens.StartScreen.Screens.LogInScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.jetmusic.Extensions.NavigateExtensions.singleTapNavigate
import com.example.jetmusic.Helpers.Validation.ValidationResults
import com.example.jetmusic.View.Components.Buttons.TextButton
import com.example.jetmusic.View.Components.Buttons.TurnBackButton
import com.example.jetmusic.View.Components.InputFields.ValidationTextField.DefaultValidationLeadingIcon
import com.example.jetmusic.View.Components.InputFields.ValidationTextField.DefaultValidationTrailingIcon
import com.example.jetmusic.View.Components.InputFields.ValidationTextField.ValidationTextInputField
import com.example.jetmusic.View.ScreensRoutes
import com.example.jetmusic.data.DTOs.UserDTOs.User
import com.example.jetmusic.ui.theme.tidalGradient
import com.example.jetmusic.ui.theme.typography
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LogInScreen(
    navController: NavController,
    setUser: (newUser: User) -> Unit,
    logInViewModel: LogInViewModel = hiltViewModel(),
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp

    val emailValidationResult by logInViewModel.emailValidation.collectAsStateWithLifecycle()
    var email by remember { mutableStateOf("") }

    val passwordValidationResult by logInViewModel.passwordValidation.collectAsStateWithLifecycle()
    var password by remember { mutableStateOf("") }

    var showPassword by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.sdp, top = 18.sdp),
            contentAlignment = Alignment.CenterStart,
        ) {
            TurnBackButton(
                size = 21.sdp,
                border = BorderStroke(1.sdp, Color.White),
                background = Color.Transparent,
                iconColor = Color.White,
                turnBack = {navController.singleTapNavigate(ScreensRoutes.StartScreens.WelcomeRoute)}
            )
        }

        Text(
            modifier = Modifier
                .padding(top = screenHeight.dp / 8),
            text = "Log In",
            style = typography().headlineMedium
        )

        Text(
            modifier = Modifier
                .padding(top = 20.sdp),
            text = "Enter Your Email and Password",
            style = typography().titleSmall,
            color = Color.LightGray.copy(0.85f)
        )

        Column(
            modifier = Modifier
                .padding(top = screenHeight.dp / 10),
            verticalArrangement = Arrangement.spacedBy(11.sdp)
        ){
            ValidationTextInputField(
                text = email,
                onTextChange = { value ->
                    email = value
                    logInViewModel.setEmailResult(ValidationResults.NONE)
                },
                placeHolder = "Email",
                validationResults = emailValidationResult,
                leadingIcon = { tint ->
                    DefaultValidationLeadingIcon(
                        icon = Icons.Filled.Email,
                        tint = tint,
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    showKeyboardOnFocus = true
                )
            )

            ValidationTextInputField(
                text = password,
                onTextChange = { value ->
                    password = value
                    logInViewModel.setPasswordResult(ValidationResults.NONE)
                },
                placeHolder = "Password",
                validationResults = passwordValidationResult,
                leadingIcon = { tint ->
                    DefaultValidationLeadingIcon(
                        icon = Icons.Filled.Lock,
                        tint = tint,
                    )
                },
                trailingIcon = { tint ->
                    DefaultValidationTrailingIcon(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.sdp))
                            .clickable { showPassword = !showPassword },
                        icon = if(showPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        tint = tint,
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if(showPassword) VisualTransformation.None else PasswordVisualTransformation()
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        TextButton(
            modifier = Modifier,
            text = "Log In",
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    val isValid = logInViewModel.isValid(
                        email = email,
                        password = password,
                    )

                    if(isValid) {
                        logInViewModel.logIn(
                            email = email,
                            password = password,
                            onSuccess = { newUser ->
                                setUser(newUser)
                            }
                        )
                    }
                }
            }
        )

        Row(
            modifier = Modifier
                .padding(top = 8.sdp, bottom = screenHeight.dp / 22),
            horizontalArrangement = Arrangement.spacedBy(5.sdp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Don't Have Account?",
                style = typography().bodyMedium,
                fontWeight = FontWeight.Normal
            )

            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(2.sdp))
                    .clickable {
                        navController.navigate(ScreensRoutes.StartScreens.SignUpRoute) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(ScreensRoutes.StartScreens.LogInRoute) {
                                inclusive = true
                            }
                        }

                    },
                text = "Sign up",
                style = typography().bodyLarge.copy(brush = tidalGradient),
                textDecoration = TextDecoration.Underline
            )
        }
    }
}