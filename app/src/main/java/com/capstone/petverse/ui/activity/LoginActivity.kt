package com.capstone.petverse.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.capstone.petverse.R
import com.capstone.petverse.ui.viewmodel.LoginViewModel
import com.capstone.petverse.ui.viewmodel.ViewModelFactory

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            PetverseAppLogin()
        }
    }

    @Composable
    fun PetverseAppLogin() {
        val factory = ViewModelFactory.getInstance(application)
        val viewModel: LoginViewModel = viewModel(factory = factory)
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars),
            color = MaterialTheme.colorScheme.onPrimary
        ) {
            LoginScreen(viewModel)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun LoginScreen(viewModel: LoginViewModel) {
        val loginFormState by viewModel.loginFormState.observeAsState()
        val isLoading by viewModel.isLoading.observeAsState(false)
        val emailError by viewModel.emailError.observeAsState()
        val passwordError by viewModel.passwordError.observeAsState()
        val scrollState = rememberScrollState()
        val context = LocalContext.current

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(40.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(R.string.login),
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Default,
                        color = colorResource(id = R.color.colorPrimary)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = stringResource(R.string.email_label),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily.Default,
                            color = colorResource(id = R.color.colorPrimaryDark)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = loginFormState?.email ?: "",
                            onValueChange = { viewModel.onEmailChange(it) },
                            isError = emailError != null,
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = if (emailError != null) Color.Red else colorResource(id = R.color.colorPrimary),
                                unfocusedBorderColor = if (emailError != null) Color.Red else colorResource(id = R.color.colorPrimaryDark)
                            ),
                            singleLine = true
                        )

                        if (emailError != null) {
                            Text(text = emailError ?: "", color = Color.Red, style = TextStyle(fontSize = 12.sp))
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = stringResource(R.string.password_label),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily.Default,
                            color = colorResource(id = R.color.colorPrimaryDark)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = loginFormState?.password ?: "",
                            onValueChange = { viewModel.onPasswordChange(it) },
                            isError = passwordError != null,
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = if (passwordError != null) Color.Red else colorResource(id = R.color.colorPrimary),
                                unfocusedBorderColor = if (passwordError != null) Color.Red else colorResource(id = R.color.colorPrimaryDark)
                            ),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                            visualTransformation = if (loginFormState?.isPasswordVisible == true) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                                    Icon(
                                        imageVector = if (loginFormState?.isPasswordVisible == true) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                        contentDescription = if (loginFormState?.isPasswordVisible == true) "Hide password" else "Show password"
                                    )
                                }
                            }
                        )

                        if (passwordError != null) {
                            Text(text = passwordError ?: "", color = Color.Red, style = TextStyle(fontSize = 12.sp))
                        }

                        Spacer(modifier = Modifier.height(38.dp))

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(
                                onClick = { viewModel.login(context) },
                                Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .absolutePadding(bottom = 5.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.colorPrimary)),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                if (isLoading) {
                                    CircularProgressIndicator(
                                        color = Color.White,
                                        modifier = Modifier.size(24.dp)
                                    )
                                } else {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = stringResource(R.string.login),
                                            color = Color.White,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            fontFamily = FontFamily.Default
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = stringResource(R.string.forgot_password),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = FontFamily.Default,
                                color = colorResource(id = R.color.colorPrimaryDark),
                                modifier = Modifier.clickable {
                                    viewModel.navigateToSignup(context)
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(70.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = stringResource(R.string.dont_have_account),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = FontFamily.Default,
                                color = colorResource(id = R.color.colorPrimaryDark)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                    }
                }
            }
        }
    }

}
