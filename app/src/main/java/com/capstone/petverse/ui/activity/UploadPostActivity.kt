package com.capstone.petverse.ui.activity

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import com.capstone.petverse.R
import com.capstone.petverse.ui.viewmodel.UploadPostViewModel

@Composable
fun UploadPostScreen(navController: NavController, viewModel: UploadPostViewModel) {
    val context = LocalContext.current
    val imageUri by viewModel.imageUri
    val bitmap by viewModel.bitmap
    val selectedCategory by viewModel.selectedCategory
    val description by viewModel.description
    val phoneNumber by viewModel.phoneNumber
    val coroutineScope = rememberCoroutineScope()

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        viewModel.setImageUri(uri)
    }

    BackHandler {
        navController.popBackStack()
    }

    LaunchedEffect(Unit) {
        launcher.launch("image/*")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        imageUri?.let {
            bitmap?.let { btm ->
                Image(
                    bitmap = btm.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.size(150.dp)
                )
            }
            Text(
                text = stringResource(R.string.change_picture),
                color = Color(0xFFFFA726), // Orange color
                modifier = Modifier
                    .clickable { launcher.launch("image/*") }
            )
        } ?: run {
            Text(
                text = stringResource(R.string.upload_picture),
                color = Color(0xFFFFA726), // Orange color
                modifier = Modifier
                    .clickable { launcher.launch("image/*") }
            )
            Text(text = stringResource(R.string.no_image_selected), modifier = Modifier.padding(top = 16.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Category Dropdown
        var expanded by remember { mutableStateOf(false) }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.show_as),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.width(150.dp))
            Box(
                contentAlignment = Alignment.CenterStart
            ) {
                TextButton(
                    onClick = { expanded = true },
                    modifier = Modifier.background(Color.White)
                ) {
                    Text(
                        text = if (selectedCategory.isEmpty()) stringResource(R.string.select_category) else selectedCategory,
                        color = if (selectedCategory.isEmpty()) Color.Gray else Color.Black
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.post)) },
                        onClick = {
                            coroutineScope.launch {
                                viewModel.setSelectedCategory("post")
                            }
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.adoption)) },
                        onClick = {
                            coroutineScope.launch {
                                viewModel.setSelectedCategory("adoption")
                            }
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Description TextField
        TextField(
            value = description,
            onValueChange = { viewModel.setDescription(it) },
            label = { Text(stringResource(R.string.description)) },
            modifier = Modifier.fillMaxWidth()
        )

        // Phone Number TextField for adoption category
        if (selectedCategory == "adoption") {
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = phoneNumber,
                onValueChange = { viewModel.setPhoneNumber(it) },
                label = { Text(stringResource(R.string.phone_number)) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Submit Button
        Button(
            onClick = {
                viewModel.onSubmitPost(navController)
                Toast.makeText(context, "Submitting post...", Toast.LENGTH_SHORT).show()
            },
            Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(bottom = 5.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.colorPrimary)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = stringResource(R.string.post_button),
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
