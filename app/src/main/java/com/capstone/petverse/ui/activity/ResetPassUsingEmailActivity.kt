package com.capstone.petverse.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.*
import com.capstone.petverse.R
import com.capstone.petverse.ui.theme.PetVerseTheme

class ResetPassUsingEmailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetVerseTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    ResetPassUsingEmail()
                }
            }
        }
    }
}

val interFamily = FontFamily(
    Font(R.font.inter_bold, FontWeight.Bold),
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_regular, FontWeight.Normal)
)

@Composable
fun rememberArrowBack(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "arrow_back",
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
                moveTo(18.542f, 32.208f)
                lineTo(7.25f, 20.917f)
                quadToRelative(-0.208f, -0.209f, -0.292f, -0.438f)
                quadToRelative(-0.083f, -0.229f, -0.083f, -0.479f)
                quadToRelative(0f, -0.25f, 0.083f, -0.479f)
                quadToRelative(0.084f, -0.229f, 0.292f, -0.438f)
                lineTo(18.583f, 7.75f)
                quadToRelative(0.375f, -0.333f, 0.896f, -0.333f)
                reflectiveQuadToRelative(0.938f, 0.375f)
                quadToRelative(0.375f, 0.416f, 0.375f, 0.937f)
                quadToRelative(0f, 0.521f, -0.375f, 0.938f)
                lineToRelative(-9.042f, 9f)
                horizontalLineToRelative(19.917f)
                quadToRelative(0.541f, 0f, 0.916f, 0.395f)
                quadToRelative(0.375f, 0.396f, 0.375f, 0.938f)
                quadToRelative(0f, 0.542f, -0.375f, 0.917f)
                reflectiveQuadToRelative(-0.916f, 0.375f)
                horizontalLineTo(11.375f)
                lineToRelative(9.083f, 9.083f)
                quadToRelative(0.334f, 0.375f, 0.334f, 0.896f)
                reflectiveQuadToRelative(-0.375f, 0.937f)
                quadToRelative(-0.417f, 0.375f, -0.938f, 0.375f)
                quadToRelative(-0.521f, 0f, -0.937f, -0.375f)
                close()
            }
        }.build()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPassUsingEmail() {
    var email by remember { mutableStateOf("") }
    val context = LocalContext.current
    Box(
        modifier = Modifier,
        contentAlignment = Alignment.TopStart,
    ) {
        IconButton(
            onClick = {
                val intent = Intent(context, LoginActivity::class.java)
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Reset Password",
                    color = colorResource(id = R.color.colorPrimary),
                    fontFamily = interFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Please enter your email address and we’ll send you a otp code to reset your password",
                    color = colorResource(id = R.color.colorTextBlack),
                    fontFamily = interFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                )

                Spacer(modifier = Modifier.height(13.dp))

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
                        value = email,
                        onValueChange = { email = it },
                        singleLine = true,
                        trailingIcon = {
                            if (email.isNotBlank())
                                IconButton(onClick = { email = "" }) {
                                    Icon(imageVector = Icons.Filled.Clear, contentDescription = "Clear username")
                                }
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = colorResource(id = R.color.colorPrimary),
                            unfocusedBorderColor = colorResource(id = R.color.colorPrimaryDark)
                        )
                    )
                }

                Spacer(modifier = Modifier.height(38.dp))

                Button(
                    onClick = {
                        val intent = Intent(context, OtpActivity::class.java)
                        context.startActivity(intent)
                    },
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.colorPrimary)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Send OTP",
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