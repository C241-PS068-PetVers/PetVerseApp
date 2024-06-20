package com.capstone.petverse.ui.activity

import android.app.Application
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.capstone.petverse.R
import com.capstone.petverse.ui.theme.PetVerseTheme
import com.capstone.petverse.ui.viewmodel.EditProfileViewModel
import com.capstone.petverse.ui.viewmodel.ViewModelFactory
@Composable
fun EditProfileScreen(navController: NavController) {
    val context = LocalContext.current.applicationContext as Application
    val factory = ViewModelFactory.getInstance(context)
    val editProfileViewModel: EditProfileViewModel = viewModel(factory = factory)

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        editProfileViewModel.setImageUri(uri)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = stringResource(id = R.string.edit_profile),
                onBackClick = { navController.navigateUp() }
            )
        },
        content = { paddingValues ->
            EditProfileContent(
                modifier = Modifier.padding(paddingValues),
                editProfileViewModel = editProfileViewModel,
                onChangeProfilePictureClick = { launcher.launch("image/*") },
                navController = navController
            )
        }
    )
}

@Composable
fun EditProfileContent(
    modifier: Modifier = Modifier,
    editProfileViewModel: EditProfileViewModel,
    onChangeProfilePictureClick: () -> Unit,
    navController: NavController
) {
    val name by editProfileViewModel.name.collectAsState()
    val username by editProfileViewModel.username.collectAsState()
    val imageUri by editProfileViewModel.imageUri.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        AsyncImage(
            model = imageUri ?: "https://via.placeholder.com/150",
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .clickable { onChangeProfilePictureClick() },
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Change profile picture",
            color = colorResource(id = R.color.colorPrimary),
            modifier = Modifier.clickable { onChangeProfilePictureClick() }
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
                onValueChange = { editProfileViewModel.onNameChange(it) },
                singleLine = true,
                trailingIcon = {
                    if (name.isNotBlank())
                        IconButton(onClick = { editProfileViewModel.clearName() }) {
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
                onValueChange = { editProfileViewModel.onUsernameChange(it) },
                singleLine = true,
                trailingIcon = {
                    if (username.isNotBlank())
                        IconButton(onClick = { editProfileViewModel.clearUsername() }) {
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
            onClick = { editProfileViewModel.updateProfile(navController) },
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

