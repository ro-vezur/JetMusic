package com.example.jetmusic.View.Screens.StartScreen.Screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.jetmusic.data.DTOs.UserDTOs.User
import com.example.jetmusic.Helpers.Validation.Result.ValidationResults
import com.example.jetmusic.View.Components.Buttons.TextButton
import com.example.jetmusic.View.Components.Buttons.TurnBackButton
import com.example.jetmusic.View.Components.InputFields.ValidationTextInputField
import com.example.jetmusic.View.ScreenRoutes.ScreensRoutes
import com.example.jetmusic.ViewModels.StartScreenViewModels.SignUpViewModel
import com.example.jetmusic.ui.theme.tidalGradient
import com.example.jetmusic.ui.theme.typography
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    navController: NavController,
    setUser: (newUser: User) -> Unit,
    signUpViewModel: SignUpViewModel = hiltViewModel(),
) {

    val nameValidationResult by signUpViewModel.nameValidation.collectAsStateWithLifecycle()
    var fullName by remember { mutableStateOf("") }

    val emailValidationResult by signUpViewModel.emailValidation.collectAsStateWithLifecycle()
    var email by remember { mutableStateOf("") }

    val passwordValidationResult by signUpViewModel.passwordValidation.collectAsStateWithLifecycle()
    var password by remember { mutableStateOf("") }

    val passwordConfirmValidationResult by signUpViewModel.passwordValidationConfirm.collectAsStateWithLifecycle()
    var passwordConfirm by remember { mutableStateOf("") }

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
                turnBack = {navController.navigate(ScreensRoutes.StartScreens.WelcomeRoute)}
            )
        }

        Text(
            modifier = Modifier
                .padding(top = 32.sdp),
            text = "Sign Up",
            style = typography().headlineMedium
        )

        Text(
            modifier = Modifier
                .padding(top = 28.sdp),
            text = "First Create Your Account",
            style = typography().titleSmall,
            color = Color.LightGray.copy(0.85f)
        )

        Column(
            modifier = Modifier
                .padding(top = 50.sdp),
            verticalArrangement = Arrangement.spacedBy(13.sdp)
        ){
            ValidationTextInputField(
                text = fullName,
                onTextChange = { value ->
                    fullName = value
                    signUpViewModel.setNameResult(ValidationResults.NONE)
                },
                placeHolder = "Full Name",
                validationResults = nameValidationResult,
                leadingIcon = Icons.Filled.Person,
                )

            ValidationTextInputField(
                text = email,
                onTextChange = { value ->
                    email = value
                    signUpViewModel.setEmailResult(ValidationResults.NONE)
                },
                placeHolder = "Email",
                validationResults = emailValidationResult,
                leadingIcon = Icons.Filled.Email,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            )

            ValidationTextInputField(
                text = password,
                onTextChange = { value ->
                    password = value
                    signUpViewModel.setPasswordResult(ValidationResults.NONE)
                },
                placeHolder = "Password",
                validationResults = passwordValidationResult,
                leadingIcon = Icons.Filled.Lock,
                trailingIcon = {
                   Icon(
                       imageVector = if(showPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                       contentDescription = "visibility",
                       modifier = Modifier
                           .clip(RoundedCornerShape(10.sdp))
                           .size(24.sdp)
                           .clickable { showPassword = !showPassword }
                   )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if(showPassword) VisualTransformation.None else PasswordVisualTransformation()
            )

            ValidationTextInputField(
                text = passwordConfirm,
                onTextChange = { value ->
                    passwordConfirm = value
                    signUpViewModel.setPasswordConfirmResult(ValidationResults.NONE)
                },
                placeHolder = "Password Confirm",
                validationResults = passwordConfirmValidationResult,
                leadingIcon = Icons.Filled.Lock,
                trailingIcon = {
                    Icon(
                        imageVector = if(showPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "visibility",
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.sdp))
                            .size(24.sdp)
                            .clickable { showPassword = !showPassword }
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if(showPassword) VisualTransformation.None else PasswordVisualTransformation()
            )
        }

        TextButton(
            modifier = Modifier
                .padding(top = 40.sdp),
            text = "Sign Up",
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    val isValid = signUpViewModel.isValid(
                        name = fullName,
                        email = email,
                        password = password,
                        passwordConfirm = passwordConfirm,
                    )

                    if (isValid) {
                        signUpViewModel.signUp(
                            name = fullName,
                            email = email,
                            password = password,
                            onSuccess = { newUser ->
                                setUser(newUser)
                                //  navController.navigate(ScreensRoutes.HomeRoute)
                            }
                        )
                    }
                }
            }
        )

        Row(
            modifier = Modifier
                .padding(top = 12.sdp),
            horizontalArrangement = Arrangement.spacedBy(5.sdp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Already Have Account?",
                style = typography().bodyMedium,
                fontWeight = FontWeight.Normal
            )

            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(2.sdp))
                    .clickable { navController.navigate(ScreensRoutes.StartScreens.LogInRoute) },
                text = "Log In",
                style = typography().bodyLarge.copy(
                    brush = tidalGradient
                ),
                textDecoration = TextDecoration.Underline
            )
        }
    }
}