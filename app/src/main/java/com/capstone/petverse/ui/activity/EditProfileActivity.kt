package com.capstone.petverse.ui.activity

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.capstone.petverse.R
import com.capstone.petverse.ui.theme.PetVerseTheme

@Composable
fun EditProfileScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = stringResource(id = R.string.edit_profile),
                onBackClick = { navController.navigateUp() }
            )
        },
        content = { paddingValues ->
            EditProfileContent(modifier = Modifier.padding(paddingValues))
        }
    )
}

@Composable
fun EditProfileContent(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        AsyncImage(
            model = "https://via.placeholder.com/150",
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .clickable { /* TODO: Handle profile picture change */ },
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Change profile picture",
            color = colorResource(id = R.color.colorPrimary),
            modifier = Modifier.clickable { /* TODO: Handle profile picture change */ }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = stringResource(R.string.name),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily(Font(R.font.inter_bold, FontWeight.Bold)),
                color = colorResource(id = R.color.colorPrimaryDark)
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                onValueChange = { name = it },
                singleLine = true,
                trailingIcon = {
                    if (name.isNotBlank())
                        IconButton(onClick = { name = "" }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear name")
                        }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.colorPrimary),
                    unfocusedBorderColor = colorResource(id = R.color.colorPrimaryDark),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(R.string.username),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily(Font(R.font.inter_bold, FontWeight.Bold)),
                color = colorResource(id = R.color.colorPrimaryDark)
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = username,
                onValueChange = { username = it },
                singleLine = true,
                trailingIcon = {
                    if (username.isNotBlank())
                        IconButton(onClick = { username = "" }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear username")
                        }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.colorPrimary),
                    unfocusedBorderColor = colorResource(id = R.color.colorPrimaryDark),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* TODO: Handle update profile */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.colorPrimary)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Update",
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEditProfileScreen() {
    PetVerseTheme {
        val navController = rememberNavController()
        EditProfileScreen(navController = navController)
    }
}
