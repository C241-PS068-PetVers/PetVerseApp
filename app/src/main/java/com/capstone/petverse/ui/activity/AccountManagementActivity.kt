package com.capstone.petverse.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.petverse.R
import com.capstone.petverse.ui.activity.ui.theme.PetVerseTheme

class AccountManagementActivity : ComponentActivity() {
    private val interFamily = FontFamily(
        Font(R.font.inter_medium, FontWeight.Medium),
        Font(R.font.inter_semibold, FontWeight.SemiBold)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetVerseTheme {
                AccountManagementScreen(interFamily = interFamily) {
                    finish()  // Finish activity on back click
                }
            }
        }
    }
}

@Composable
fun AccountManagementScreen(interFamily: FontFamily = FontFamily.Default, onBackClick: () -> Unit) {
    Scaffold(
        topBar = { TopAppBar(title = stringResource(id = R.string.account_management), onBackClick = onBackClick) },
        content = { innerPadding ->
            AccountManagementContent(interFamily = interFamily, modifier = Modifier.padding(innerPadding))
        }
    )
}

@Composable
fun AccountManagementContent(interFamily: FontFamily, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        AccountManagementItem(text = stringResource(R.string.change_email), onClick = { /* TODO */ }, interFamily = interFamily)
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        AccountManagementItem(text = stringResource(R.string.change_password), onClick = { /* TODO */ }, interFamily = interFamily)
    }
}

@Composable
fun AccountManagementItem(text: String, onClick: () -> Unit, interFamily: FontFamily) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            fontFamily = interFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 17.sp
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = null
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAccountManagementScreen() {
    PetVerseTheme {
        AccountManagementScreen(onBackClick = {})
    }
}
