package com.capstone.petverse.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.capstone.petverse.R
import com.capstone.petverse.ui.viewmodel.WelcomeViewModel

class WelcomeActivity : ComponentActivity() {
    private val interFamily = FontFamily(
        Font(R.font.inter_bold, FontWeight.Bold),
        Font(R.font.inter_regular, FontWeight.Normal)
    )
    private val viewModel: WelcomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            PetverseAppWelcome(viewModel)
        }
    }

    @Composable
    fun PetverseAppWelcome(viewModel: WelcomeViewModel) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.onPrimary
        ) {
            WelcomeScreen(viewModel)
        }
    }

//    @Composable
//    fun CustomShapes() : Shapes {
//        return Shapes(
//            small = RoundedCornerShape(4.dp),
//            medium = RoundedCornerShape(8.dp),
//            large = RoundedCornerShape(16.dp)
//        )
//    }

    @Composable
    fun WelcomeScreen(viewModel: WelcomeViewModel) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.pet_adoption),
                        contentDescription = stringResource(id = R.string.welcome_screen_image),
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Column() {
                    Text(
                        text = stringResource(R.string.app_name),
                        fontSize = 40.sp,
                        fontFamily = interFamily,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.colorPrimary)
                    )

                    Text(
                        text = stringResource(R.string.intro_message),
                        fontSize = 15.sp,
                        fontFamily = interFamily,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.alpha(0.3f),
                        color = colorResource(id = R.color.colorPrimaryDark)
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(
                        onClick = {
                            viewModel.navigateToSignup(this@WelcomeActivity)
                        },
                        Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .absolutePadding(bottom = 5.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.colorPrimary)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = stringResource(R.string.signup))
                    }

                    OutlinedButton(
                        onClick = {
                            viewModel.navigateToLogin(this@WelcomeActivity)
                        },
                        Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .absolutePadding(top = 5.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.login),
                            color = colorResource(id = R.color.colorPrimaryDark)
                        )
                    }
                }
            }
        }
    }
}
