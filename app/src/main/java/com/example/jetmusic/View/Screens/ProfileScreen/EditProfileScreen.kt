package com.example.jetmusic.View.Screens.ProfileScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetmusic.View.Components.Buttons.TextButton
import com.example.jetmusic.View.Components.InputFields.ValidationTextField.DefaultValidationLeadingIcon
import com.example.jetmusic.View.Components.InputFields.ValidationTextField.ValidationTextInputField
import com.example.jetmusic.View.Components.TopBars.TopBarWithNavigateBack
import com.example.jetmusic.data.DTOs.UserDTOs.User
import com.example.jetmusic.ui.theme.JetMusicTheme
import com.example.jetmusic.ui.theme.tidalGradient
import com.example.jetmusic.ui.theme.typography
import ir.kaaveh.sdpcompose.sdp

@Composable
fun EditProfileScreen(
    user: User,
    updateUser: (User) -> Unit,
) {
    var usernameFieldText by remember { mutableStateOf(user.fullName) }
    
    Scaffold(
        topBar = {
            TopBarWithNavigateBack(
                title = "Edit Profile"
            ) {
                
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 16.sdp)
                    .size(82.sdp)
                    .border(BorderStroke(1.sdp, tidalGradient), CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    modifier = Modifier
                        .size(66.sdp),
                    imageVector = Icons.Filled.Person,
                    contentDescription = "user icon"
                )
            }

            TextButton(
                modifier = Modifier
                    .padding(top = 15.sdp),
                text = "Change Picture",
                style = typography().bodyMedium,
                width = 115.sdp,
                height = 26.sdp,
                corners = RoundedCornerShape(8.sdp),
                onClick = {

                }
            )

            Column(
                modifier = Modifier
                    .padding(top = 35.sdp),
                verticalArrangement = Arrangement.spacedBy(12.sdp)
            ) {
                ValidationTextInputField(
                    text = usernameFieldText,
                    placeHolder = "Username",
                    leadingIcon = {
                        DefaultValidationLeadingIcon(
                            icon = Icons.Filled.Person
                        )
                    },
                    onTextChange = {  value ->
                        usernameFieldText = value
                    }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            TextButton(
                text = "Save Changes",
                onClick = {
                    val updatedUser = user.copy(
                        fullName = usernameFieldText
                    )

                    updateUser(updatedUser)
                }
            )
        }
    }
}

@Preview
@Composable
private fun editProfilePrev() {
    JetMusicTheme {
        EditProfileScreen(user = User()) {

        }
    }
}