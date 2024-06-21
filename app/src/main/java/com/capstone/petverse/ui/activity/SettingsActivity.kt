package com.capstone.petverse.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.petverse.R
import com.capstone.petverse.data.pref.UserPreference
import com.capstone.petverse.data.pref.dataStore
import com.capstone.petverse.ui.activity.ui.theme.PetVerseTheme
import kotlinx.coroutines.launch
import java.util.*

class SettingsActivity : ComponentActivity() {
    private val interFamily = FontFamily(
        Font(R.font.inter_medium, FontWeight.Medium),
        Font(R.font.inter_semibold, FontWeight.SemiBold)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetVerseTheme {
                SettingsScreen(interFamily = interFamily) {
                    finish()
                }
            }
        }
    }
}

@Composable
fun SettingsScreen(interFamily: FontFamily = FontFamily.Default, onBackClick: () -> Unit) {
    Scaffold(
        topBar = { TopAppBar(title = stringResource(id = R.string.settings), onBackClick = onBackClick) },
        content = { innerPadding ->
            SettingsContent(interFamily = interFamily, modifier = Modifier.padding(innerPadding))
        }
    )
}

@Composable
fun SettingsContent(interFamily: FontFamily, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val userPreference = UserPreference.getInstance(context.dataStore)
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SettingsSection(title = stringResource(R.string.account), interFamily = interFamily) {
            SettingsItem(text = stringResource(R.string.account_management), onClick = {
                val intent = Intent(context, AccountManagementActivity::class.java)
                context.startActivity(intent)
            }, interFamily = interFamily)
            SettingsItem(text = stringResource(R.string.select_language), onClick = {
                val intent = Intent(context, LanguageSelectionActivity::class.java)
                context.startActivity(intent)
            }, interFamily = interFamily)
            DarkModeToggle(interFamily = interFamily)
        }

        SettingsSection(title = stringResource(R.string.deletion), interFamily = interFamily) {
            SettingsItem(text = stringResource(R.string.delete_account), onClick = { /* TODO */ }, interFamily = interFamily)
        }

        SettingsSection(title = stringResource(R.string.support), interFamily = interFamily) {
            SettingsItem(text = stringResource(R.string.help_center), onClick = { /* TODO */ }, isExternalLink = true, interFamily = interFamily)
            SettingsItem(text = stringResource(R.string.terms_of_service), onClick = { /* TODO */ }, isExternalLink = true, interFamily = interFamily)
            SettingsItem(text = stringResource(R.string.privacy_policy), onClick = { /* TODO */ }, isExternalLink = true, interFamily = interFamily)
        }

        LogOutButton(
            text = stringResource(R.string.log_out),
            onClick = {
                coroutineScope.launch {
                    userPreference.logout()
                    val intent = Intent(context, WelcomeActivity::class.java)
                    context.startActivity(intent)
                    (context as ComponentActivity).finish()
                }
            },
            interFamily = interFamily
        )
    }
}

@Composable
fun SettingsSection(title: String, interFamily: FontFamily, content: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            fontFamily = interFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        content()
        Divider(modifier = Modifier.padding(vertical = 8.dp))
    }
}

@Composable
fun SettingsItem(text: String, onClick: () -> Unit, interFamily: FontFamily, isExternalLink: Boolean = false) {
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
            imageVector = if (isExternalLink) Icons.Filled.ArrowOutward else Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = null
        )
    }
}

@Composable
fun LogOutButton(text: String, onClick: () -> Unit, interFamily: FontFamily) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            text = text,
            fontFamily = interFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 17.sp,
            color = Color.White
        )
    }
}

@Composable
fun DarkModeToggle(interFamily: FontFamily) {
    var isDarkModeEnabled by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.enable_dark_mode),
            fontFamily = interFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp
        )
        Switch(
            checked = isDarkModeEnabled,
            onCheckedChange = { isDarkModeEnabled = it }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsScreen() {
    PetVerseTheme {
        SettingsScreen(onBackClick = {})
    }
}
