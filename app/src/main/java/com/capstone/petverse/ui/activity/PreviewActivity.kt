package com.capstone.petverse.ui.activity

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.capstone.petverse.ui.activity.ui.theme.PetVerseTheme

class PreviewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val imageUri = intent.getStringExtra("imageUri")
        setContent {
            PetVerseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PreviewScreen(imageUri)
                }
            }
        }
    }
}

//@Composable
//fun PreviewScreen(imageUri: String?) {
//    val context = LocalContext.current
//    val uri = remember { mutableStateOf(Uri.parse(imageUri)) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        uri.value?.let {
//            Image(
//                painter = rememberImagePainter(data = it),
//                contentDescription = null,
//                modifier = Modifier.size(200.dp)
//            )
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // TODO: Add fields for description, category selection, and image replacement
//
//        Button(
//            onClick = {
//                // TODO: Handle post or adoption upload
//            }
//        ) {
//            Text("Kirim")
//        }
//    }
//}

@Composable
fun PreviewScreen(imageUri: String?) {
    val context = LocalContext.current
    val uri = remember { mutableStateOf(Uri.parse(imageUri)) }
    var description by rememberSaveable { mutableStateOf("") }
    var selectedCategory by rememberSaveable { mutableStateOf("") }
    val categories = listOf("Category 1", "Category 2", "Category 3")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        uri.value?.let {
            // Use rememberImagePainter to load the image from the URI
            Image(
                painter = rememberImagePainter(data = it),
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Description TextField
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Category Dropdown
        var expanded by remember { mutableStateOf(false) }
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            TextButton(
                onClick = { expanded = true },
                modifier = Modifier.background(Color.White)
            ) {
                Text(
                    text = if (selectedCategory.isEmpty()) "Select Category" else selectedCategory,
                    color = if (selectedCategory.isEmpty()) Color.Gray else Color.Black
                )
            }
//            DropdownMenu(
//                expanded = expanded,
//                onDismissRequest = { expanded = false },
//                modifier = Modifier.background(Color.White)
//            ) {
//                categories.forEach { category ->
//                    DropdownMenuItem(onClick = {
//                        selectedCategory = category
//                        expanded = false
//                    }) {
//                        Text(text = category)
//                    }
//                }
//            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Image Replacement Button
        Button(
            onClick = {
                // TODO: Handle image replacement
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Replace Image")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Submit Button
        Button(
            onClick = {
                // TODO: Handle post or adoption upload
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Kirim")
        }
    }
}






@Preview(showBackground = true)
@Composable
fun PreviewActivitys() {
    PetVerseTheme {
        PreviewScreen("content://media/external/images/media/1")
    }
}