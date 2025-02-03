package com.example.jetmusic.View.Screens.StartScreen.Screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetmusic.View.Components.Buttons.TurnBackButton
import com.example.jetmusic.ui.theme.JetMusicTheme
import ir.kaaveh.sdpcompose.sdp
import androidx.compose.runtime.setValue
import com.example.jetmusic.BASE_BUTTON_WIDTH
import com.example.jetmusic.View.Components.InputFields.PhoneTextField
import com.example.jetmusic.ui.theme.typography
import java.util.Locale
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetmusic.BASE_BUTTON_HEIGHT
import com.example.jetmusic.Helpers.Context.findActivity
import com.example.jetmusic.Helpers.CountryCodePicker.getPhoneCodeByCountryCode
import com.example.jetmusic.View.Components.Buttons.TextButton
import com.example.jetmusic.View.ScreensRoutes
import com.example.jetmusic.ViewModels.StartScreenViewModels.PhoneNumberViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

@Composable
fun PhoneNumberLogInScreen(
    navController: NavController,
    phoneNumberViewModel: PhoneNumberViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    var selectedCountryCode by remember { mutableStateOf(Locale.getDefault().country) }
    var phoneNumber by remember { mutableStateOf("") }

    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(p0: PhoneAuthCredential) {
            Toast.makeText(context, "Verification successful..", Toast.LENGTH_SHORT).show()
        }
        override fun onVerificationFailed(p0: FirebaseException) {
            Toast.makeText(context, "Verification failed..", Toast.LENGTH_SHORT).show()
        }

        override fun onCodeAutoRetrievalTimeOut(p0: String) {
            Toast.makeText(context,"TIME OUT",Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background),
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
                turnBack = {
                    navController.navigate(ScreensRoutes.StartScreens.WelcomeRoute)
                }
            )
        }

        Text(
            modifier = Modifier
                .padding(top = screenHeight / 7),
            text = "Enter Your Phone Number",
            style = typography().titleMedium
        )

        Text(
            modifier = Modifier
                .padding(top = 18.sdp),
            text = "We will send you a 4 digit verification code",
            style = typography().bodyMedium,
            color = Color.LightGray.copy(0.85f)
        )

        PhoneTextField(
            modifier = Modifier
                .padding(top = 75.sdp)
                .width(BASE_BUTTON_WIDTH.sdp)
                .height(BASE_BUTTON_HEIGHT.sdp),
            phone = phoneNumber,
            countryCode = selectedCountryCode,
            textStyle = typography().bodyLarge.copy(
                color = colorScheme.inversePrimary
            ),
            onTextChange = { value ->
                phoneNumber = value
            },
            selectCountryCode = { code ->
                selectedCountryCode = code
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        TextButton(
            modifier = Modifier
                .padding(bottom = 150.sdp),
            text = "Generate OTP",
            onClick = {
                phoneNumberViewModel.sendVerificationCode(
                    phoneNumber = getPhoneCodeByCountryCode(selectedCountryCode) +
                            phoneNumber,
                    activity = context.findActivity(),
                    callbacks = callbacks,
                )
            }
        )

    }
}