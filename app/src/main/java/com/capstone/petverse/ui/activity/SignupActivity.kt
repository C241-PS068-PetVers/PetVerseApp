package com.capstone.petverse.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
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
import com.capstone.petverse.ui.viewmodel.SignupViewModel
import com.capstone.petverse.ui.viewmodel.ViewModelFactory

class SignupActivity : ComponentActivity() {
    private val interFamily = FontFamily(
        Font(R.font.inter_bold, FontWeight.Bold),
        Font(R.font.inter_medium, FontWeight.Medium),
        Font(R.font.inter_regular, FontWeight.Normal)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            PetverseAppSignup()
        }
    }

    @Composable
    fun PetverseAppSignup() {
//        val context = LocalContext.current
        val factory = ViewModelFactory.getInstance(application, LocalContext.current)
        val viewModel: SignupViewModel = viewModel(factory = factory)
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars),
            color = MaterialTheme.colorScheme.onPrimary
        ) {
            SignupScreen(viewModel)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SignupScreen(viewModel: SignupViewModel) {
        val name by viewModel.name.observeAsState("")
        val username by viewModel.username.observeAsState("")
        val email by viewModel.email.observeAsState("")
        val password by viewModel.password.observeAsState("")
        val isPasswordVisible by viewModel.isPasswordVisible.observeAsState(false)
        val context = LocalContext.current
        val scrollState = rememberScrollState()
        val configuration = LocalConfiguration.current
        val isLandscape = configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE


        val addIcon = rememberAdd()

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
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
                        text = stringResource(R.string.signup),
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = interFamily,
                        color = colorResource(id = R.color.colorPrimary)
                    )

                    Spacer(modifier = Modifier.height(if (isLandscape) 20.dp else 10.dp))

                    Column(horizontalAlignment = Alignment.Start) {
                        Text(
                            text = stringResource(R.string.name_label),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = interFamily,
                            color = colorResource(id = R.color.colorPrimaryDark)
                        )
                        Spacer(modifier = Modifier.height(if (isLandscape) 20.dp else 10.dp))

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = name,
                            onValueChange = { viewModel.onNameChange(it) },
                            singleLine = true,
                            trailingIcon = {
                                if (name.isNotBlank())
                                    IconButton(onClick = { viewModel.clearName() }) {
                                        Icon(imageVector = Icons.Filled.Clear, contentDescription = "Clear name")
                                    }
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = colorResource(id = R.color.colorPrimary),
                                unfocusedBorderColor = colorResource(id = R.color.colorPrimaryDark)
                            )
                        )

                        Spacer(modifier = Modifier.height(if (isLandscape) 20.dp else 10.dp))

                        Text(
                            text = stringResource(R.string.username_label),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = interFamily,
                            color = colorResource(id = R.color.colorPrimaryDark)
                        )
                        Spacer(modifier = Modifier.height(if (isLandscape) 20.dp else 10.dp))

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = username,
                            onValueChange = { viewModel.onUsernameChange(it) },
                            singleLine = true,
                            trailingIcon = {
                                if (username.isNotBlank())
                                    IconButton(onClick = { viewModel.clearUsername() }) {
                                        Icon(imageVector = Icons.Filled.Clear, contentDescription = "Clear username")
                                    }
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = colorResource(id = R.color.colorPrimary),
                                unfocusedBorderColor = colorResource(id = R.color.colorPrimaryDark)
                            )
                        )

                        Spacer(modifier = Modifier.height(if (isLandscape) 20.dp else 10.dp))

                        Text(
                            text = stringResource(R.string.email_label),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = interFamily,
                            color = colorResource(id = R.color.colorPrimaryDark)
                        )

                        Spacer(modifier = Modifier.height(if (isLandscape) 20.dp else 10.dp))

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = email,
                            onValueChange = { viewModel.onEmailChange(it) },
                            singleLine = true,
                            trailingIcon = {
                                if (email.isNotBlank())
                                    IconButton(onClick = { viewModel.clearEmail() }) {
                                        Icon(imageVector = Icons.Filled.Clear, contentDescription = "Clear email")
                                    }
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = colorResource(id = R.color.colorPrimary),
                                unfocusedBorderColor = colorResource(id = R.color.colorPrimaryDark)
                            )
                        )

                        Spacer(modifier = Modifier.height(if (isLandscape) 20.dp else 10.dp))

                        Text(
                            text = stringResource(R.string.password_label),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = interFamily,
                            color = colorResource(id = R.color.colorPrimaryDark)
                        )

                        Spacer(modifier = Modifier.height(if (isLandscape) 20.dp else 10.dp))

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = password,
                            onValueChange = { viewModel.onPasswordChange(it) },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                                    Icon(
                                        imageVector = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                        contentDescription = "Password Toggle"
                                    )
                                }
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = colorResource(id = R.color.colorPrimary),
                                unfocusedBorderColor = colorResource(id = R.color.colorPrimaryDark)
                            )
                        )

                        Spacer(modifier = Modifier.height(if (isLandscape) 30.dp else 38.dp))

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(
                                onClick = {
                                    viewModel.onSignupClicked(context)
                                },
                                Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .padding(bottom = 5.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.colorPrimary)),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = addIcon,
                                        contentDescription = "Add",
                                        tint = Color.White
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = stringResource(R.string.signup),
                                        color = Color.White,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        fontFamily = interFamily
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(if (isLandscape) 50.dp else 70.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = stringResource(R.string.have_account),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    fontFamily = interFamily,
                                    color = colorResource(id = R.color.colorPrimaryDark)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = stringResource(R.string.login),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = interFamily,
                                    color = colorResource(id = R.color.colorPrimary),
                                    modifier = Modifier.clickable {
                                        viewModel.navigateToLogin(context)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun rememberAdd(): ImageVector {
        return remember {
            ImageVector.Builder(
                name = "add",
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
                    moveTo(20f, 31.458f)
                    quadToRelative(-0.542f, 0f, -0.917f, -0.396f)
                    quadToRelative(-0.375f, -0.395f, -0.375f, -0.937f)
                    verticalLineToRelative(-8.833f)
                    horizontalLineTo(9.875f)
                    quadToRelative(-0.583f, 0f, -0.958f, -0.375f)
                    reflectiveQuadTo(8.542f, 20f)
                    quadToRelative(0f, -0.583f, 0.375f, -0.958f)
                    reflectiveQuadToRelative(0.958f, -0.375f)
                    horizontalLineToRelative(8.833f)
                    verticalLineTo(9.833f)
                    quadToRelative(0f, -0.541f, 0.375f, -0.916f)
                    reflectiveQuadTo(20f, 8.542f)
                    quadToRelative(0.542f, 0f, 0.938f, 0.375f)
                    quadToRelative(0.395f, 0.375f, 0.395f, 0.916f)
                    verticalLineToRelative(8.834f)
                    horizontalLineToRelative(8.792f)
                    quadToRelative(0.583f, 0f, 0.958f, 0.395f)
                    quadToRelative(0.375f, 0.396f, 0.375f, 0.938f)
                    quadToRelative(0f, 0.542f, -0.375f, 0.917f)
                    reflectiveQuadToRelative(-0.958f, 0.375f)
                    horizontalLineToRelative(-8.792f)
                    verticalLineToRelative(8.833f)
                    quadToRelative(0f, 0.542f, -0.395f, 0.937f)
                    quadToRelative(-0.396f, 0.396f, -0.938f, 0.396f)
                    close()
                }
            }.build()
        }
    }
}
