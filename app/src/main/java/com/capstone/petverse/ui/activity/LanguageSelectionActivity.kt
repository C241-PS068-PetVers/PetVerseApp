package com.capstone.petverse.ui.activity

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.petverse.MainActivity
import com.capstone.petverse.R
import com.capstone.petverse.ui.activity.ui.theme.PetVerseTheme
import java.util.*

class LanguageSelectionActivity : ComponentActivity() {
    private val interFamily = FontFamily(
        Font(R.font.inter_medium, FontWeight.Medium),
        Font(R.font.inter_semibold, FontWeight.SemiBold)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetVerseTheme {
                LanguageSelectionScreen(interFamily = interFamily) {
                    finish() // Finish activity on back click
                }
            }
        }
    }
}

@Composable
fun LanguageSelectionScreen(interFamily: FontFamily = FontFamily.Default, onBackClick: () -> Unit) {
    Scaffold(
        topBar = { TopAppBar(title = stringResource(id = R.string.select_language), onBackClick = onBackClick) },
        content = { innerPadding ->
            LanguageSelectionContent(interFamily = interFamily, modifier = Modifier.padding(innerPadding))
        }
    )
}

@Composable
fun LanguageSelectionContent(interFamily: FontFamily, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LanguageItem(text = "English", onClick = { setLocale(context, "en") }, interFamily = interFamily)
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        LanguageItem(text = "Indonesian", onClick = { setLocale(context, "id") }, interFamily = interFamily)
    }
}

@Composable
fun LanguageItem(text: String, onClick: () -> Unit, interFamily: FontFamily) {
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
    }
}

fun setLocale(context: Context, languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    val config = Configuration()
    config.setLocale(locale)
    context.resources.updateConfiguration(config, context.resources.displayMetrics)

    // Restart the activity for changes to take effect
    val refreshIntent = Intent(context, MainActivity::class.java)
    context.startActivity(refreshIntent)
    (context as ComponentActivity).finish()
}

@Preview(showBackground = true)
@Composable
fun PreviewLanguageSelectionScreen() {
    PetVerseTheme {
        LanguageSelectionScreen(onBackClick = {})
    }
}
