package com.capstone.petverse.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.petverse.R
import com.capstone.petverse.ui.theme.PetVerseTheme

class OtpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetVerseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    OtpScreen()
                }
            }
        }
    }
}

@Composable
fun OtpScreen() {
    var otpValue by remember { mutableStateOf("") }
    val focusRequesters = List(4) { FocusRequester() }
    val localFocusManager = LocalFocusManager.current
    val context = LocalContext.current

    val interFamily = FontFamily(
        Font(R.font.inter_bold, FontWeight.Bold),
        Font(R.font.inter_medium, FontWeight.Medium),
        Font(R.font.inter_regular, FontWeight.Normal)
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
        IconButton(
            onClick = {
                val intent = Intent(context, ResetPassUsingEmailActivity::class.java)
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
        ) {
            Text(
                text = "Password recovery code",
                color = colorResource(id = R.color.colorPrimary),
                fontFamily = interFamily,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp),
                fontSize = 40.sp,
                lineHeight = 45.sp

            )

            Text(
                text = "We sent a 4-digit password recovery code to your email. Enter the code to proceed.",
                color = colorResource(id = R.color.colorTextBlack),
                fontFamily = interFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(4) { index ->
                    val char = otpValue.getOrNull(index)?.toString() ?: ""

                    BasicTextField(
                        value = char,
                        onValueChange = { newValue ->
                            if (newValue.length <= 1 && newValue.all { it.isDigit() }) {
                                val newOtpValue = StringBuilder(otpValue)
                                if (newValue.isEmpty()) {
                                    if (index < otpValue.length) {
                                        newOtpValue.deleteCharAt(index)
                                    }
                                } else {
                                    if (index < otpValue.length) {
                                        newOtpValue.setCharAt(index, newValue[0])
                                    } else {
                                        newOtpValue.append(newValue)
                                    }
                                }
                                otpValue = newOtpValue.toString().take(4)
                                if (newValue.isNotEmpty() && index < 3) {
                                    focusRequesters[index + 1].requestFocus()
                                }
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(60.dp)
                            .border(
                                1.dp,
                                Color.Gray,
                                RoundedCornerShape(8.dp)
                            )
                            .padding(15.dp)
                            .focusRequester(focusRequesters[index]),
                        textStyle = LocalTextStyle.current.copy(
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp,
                            color = Color.Black
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = if (index == 3) ImeAction.Done else ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                if (index < 3) {
                                    focusRequesters[index + 1].requestFocus()
                                } else {
                                    localFocusManager.clearFocus()
                                }
                            }
                        )
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                }
            }
            Spacer(modifier = Modifier.height(38.dp))

            Button(
                onClick = {
                    val intent = Intent(context, NewPasswordWlActivity::class.java)
                    context.startActivity(intent)
                },
                Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.colorPrimary)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Submit",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = interFamily
                )
            }
        }
    }
}
