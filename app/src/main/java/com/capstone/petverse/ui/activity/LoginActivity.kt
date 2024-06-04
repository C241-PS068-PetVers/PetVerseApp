package com.capstone.petverse.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.capstone.petverse.R
import com.capstone.petverse.ui.viewmodel.LoginFormState
import com.capstone.petverse.ui.viewmodel.LoginViewModel

class LoginActivity : ComponentActivity() {
    private val interFamily = FontFamily(
        Font(R.font.inter_bold, FontWeight.Bold),
        Font(R.font.inter_medium, FontWeight.Medium),
        Font(R.font.inter_regular, FontWeight.Normal)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            PetverseAppLogin()
        }
    }

    @Composable
    fun PetverseAppLogin() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.onPrimary
        ) {
            LoginScreen()
        }
    }

    @Composable
    fun rememberArrowForward(): ImageVector {
        return remember {
            ImageVector.Builder(
                name = "arrow_forward",
                defaultWidth = 40.0.dp,
                defaultHeight = 40.0.dp,
                viewportWidth = 40.0f,
                viewportHeight = 40.0f
            ).apply {
                path(
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1f,
                    stroke = null,
                    strokeAlpha = 1f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(19.083f, 32.167f)
                    quadToRelative(-0.375f, -0.375f, -0.375f, -0.938f)
                    quadToRelative(0f, -0.562f, 0.375f, -0.937f)
                    lineToRelative(9f, -9f)
                    horizontalLineTo(8.208f)
                    quadToRelative(-0.583f, 0f, -0.958f, -0.375f)
                    reflectiveQuadTo(6.875f, 20f)
                    quadToRelative(0f, -0.542f, 0.375f, -0.938f)
                    quadToRelative(0.375f, -0.395f, 0.958f, -0.395f)
                    horizontalLineToRelative(19.875f)
                    lineToRelative(-9f, -8.959f)
                    quadToRelative(-0.375f, -0.375f, -0.375f, -0.958f)
                    reflectiveQuadToRelative(0.375f, -0.958f)
                    quadToRelative(0.375f, -0.375f, 0.917f, -0.375f)
                    reflectiveQuadToRelative(0.917f, 0.375f)
                    lineToRelative(11.291f, 11.291f)
                    quadToRelative(0.209f, 0.209f, 0.292f, 0.438f)
                    quadToRelative(0.083f, 0.229f, 0.083f, 0.479f)
                    quadToRelative(0f, 0.25f, -0.083f, 0.479f)
                    quadToRelative(-0.083f, 0.229f, -0.292f, 0.438f)
                    lineTo(20.917f, 32.208f)
                    quadToRelative(-0.375f, 0.375f, -0.917f, 0.354f)
                    quadToRelative(-0.542f, -0.02f, -0.917f, -0.395f)
                    close()
                }
            }.build()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview
    @Composable
    fun LoginScreen(viewModel: LoginViewModel = viewModel()) {
        val loginFormState by viewModel.loginFormState.observeAsState()
        val context = LocalContext.current

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(R.string.login),
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = interFamily,
                        color = colorResource(id = R.color.colorPrimary)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Column(horizontalAlignment = Alignment.Start){
                        Text(
                            text = stringResource(R.string.email_label),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = interFamily,
                            color = colorResource(id = R.color.colorPrimaryDark)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = loginFormState?.email ?: "",
                            onValueChange = { viewModel.onEmailChange(it) },
                            singleLine = true,
                            trailingIcon = {
                                if (loginFormState?.email?.isNotBlank() == true)
                                    IconButton(onClick = { viewModel.onEmailChange("") }) {
                                        Icon(imageVector = Icons.Filled.Clear, contentDescription = "Clear email")
                                    }
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = colorResource(id = R.color.colorPrimary),
                                unfocusedBorderColor = colorResource(id = R.color.colorPrimaryDark)
                            )
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = stringResource(R.string.password_label),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = interFamily,
                            color = colorResource(id = R.color.colorPrimaryDark)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = loginFormState?.password ?: "",
                            onValueChange = { viewModel.onPasswordChange(it) },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                            visualTransformation = if (loginFormState?.isPasswordVisible == true) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                                    Icon(
                                        imageVector = if (loginFormState?.isPasswordVisible == true) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                        contentDescription = "Password Toggle"
                                    )
                                }
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = colorResource(id = R.color.colorPrimary),
                                unfocusedBorderColor = colorResource(id = R.color.colorPrimaryDark)
                            )
                        )

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
                                        fontFamily = interFamily
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Icon(
                                        imageVector = rememberArrowForward(),
                                        contentDescription = "Arrow Forward",
                                        tint = Color.White
                                    )
                                }

                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = stringResource(R.string.forgot_password),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = interFamily,
                                color = colorResource(id = R.color.colorPrimaryDark),
                                modifier = Modifier.clickable {
                                    viewModel.navigateToResetPassword(context)
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
                                fontFamily = interFamily,
                                color = colorResource(id = R.color.colorPrimaryDark)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = stringResource(R.string.signup),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = interFamily,
                                color = colorResource(id = R.color.colorPrimary),
                                modifier = Modifier.clickable {
                                    viewModel.navigateToSignup(context)
                                }
                            )
                        }

                    }
                }
            }
        }
    }
}
