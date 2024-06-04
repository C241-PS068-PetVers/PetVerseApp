package com.capstone.petverse.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.petverse.R
import com.capstone.petverse.ui.theme.PetVerseTheme

class NewPasswordWlActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetVerseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NewPassword()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewPassword() {
    var newPassword by remember { mutableStateOf("") }
    var confirmNewPassword by remember { mutableStateOf("") }
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        IconButton(
            onClick = {
                val intent = Intent(context, OtpActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.padding(15.dp).align(Alignment.TopStart),
            content = {
                Icon(
                    imageVector = rememberArrowBack(),
                    contentDescription = "Back",
                )
            }
        )

        Column(
            modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ){
            Text(
                text = "Create a new password",
                color = colorResource(id = R.color.colorPrimary),
                fontFamily = interFamily,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp),
                fontSize = 40.sp,
                lineHeight = 45.sp

            )

            Text (
                text = "Your new password must be different from the previous password you used.",
                color = colorResource(id = R.color.colorTextBlack),
                fontFamily = interFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Column(horizontalAlignment = Alignment.Start){
                Text(
                    text = "New password",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = interFamily,
                    color = colorResource(id = R.color.colorPrimaryDark)
                )
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    singleLine = true,
                    trailingIcon = {
                        if (newPassword.isNotBlank())
                            IconButton(onClick = { newPassword = "" }) {
                                Icon(imageVector = Icons.Filled.Clear, contentDescription = "Clear name")
                            }
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = colorResource(id = R.color.colorPrimary),
                        unfocusedBorderColor = colorResource(id = R.color.colorPrimaryDark)
                    )
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = "Confirm new password",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = interFamily,
                    color = colorResource(id = R.color.colorPrimaryDark)
                )
                Spacer(modifier = Modifier.height(15.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = confirmNewPassword,
                    onValueChange = { confirmNewPassword = it },
                    singleLine = true,
                    trailingIcon = {
                        if (confirmNewPassword.isNotBlank())
                            IconButton(onClick = { confirmNewPassword = "" }) {
                                Icon(imageVector = Icons.Filled.Clear, contentDescription = "Clear username")
                            }
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = colorResource(id = R.color.colorPrimary),
                        unfocusedBorderColor = colorResource(id = R.color.colorPrimaryDark)
                    )
                )

                Spacer(modifier = Modifier.height(38.dp))

                Button(
                    onClick = {
                        val intent = Intent(context, ResetPassCompleteActivity::class.java)
                        context.startActivity(intent)
                    },
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.colorPrimary)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Create new password",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = interFamily
                    )

                }
            }
        }
    }
}