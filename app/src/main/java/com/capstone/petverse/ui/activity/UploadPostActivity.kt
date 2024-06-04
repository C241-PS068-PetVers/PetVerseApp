package com.capstone.petverse.ui.activity

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.capstone.petverse.R
import com.capstone.petverse.ui.activity.ui.theme.PetVerseTheme

//class UploadPostActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            PetVerseTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Greeting("Android")
//                }
//            }
//        }
//    }
//}

@Composable
fun UploadPostActivity() {


//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(text = "Upload Screen")
//    }



}


//@Composable
//fun RequestContentPermission() {
//    var imageUri by remember {
//        mutableStateOf<Uri?>(null)
//    }
//    val context = LocalContext.current
//    val bitmap =  remember {
//        mutableStateOf<Bitmap?>(null)
//    }
//
//    val launcher = rememberLauncherForActivityResult(contract =
//    ActivityResultContracts.GetContent()) { uri: Uri? ->
//        imageUri = uri
//    }
//    Column() {
//        Button(onClick = {
//            launcher.launch("image/*")
//        }) {
//            Text(text = "Pick image")
//        }
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        imageUri?.let {
//            if (Build.VERSION.SDK_INT < 28) {
//                bitmap.value = MediaStore.Images
//                    .Media.getBitmap(context.contentResolver,it)
//
//            } else {
//                val source = ImageDecoder
//                    .createSource(context.contentResolver,it)
//                bitmap.value = ImageDecoder.decodeBitmap(source)
//            }
//
//            bitmap.value?.let {  btm ->
//                Image(bitmap = btm.asImageBitmap(),
//                    contentDescription =null,
//                    modifier = Modifier.size(400.dp))
//            }
//        }
//
//    }
//}

//with Button
//@Composable
//fun RequestContentPermission() {
//    var imageUri by remember {
//        mutableStateOf<Uri?>(null)
//    }
//    val context = LocalContext.current
//    val bitmap = remember {
//        mutableStateOf<Bitmap?>(null)
//    }
//
//    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
//        imageUri = uri
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Button(onClick = {
//            launcher.launch("image/*")
//        }) {
//            Text(text = "Pick image")
//        }
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        imageUri?.let {
//            if (Build.VERSION.SDK_INT < 28) {
//                bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
//            } else {
//                val source = ImageDecoder.createSource(context.contentResolver, it)
//                bitmap.value = ImageDecoder.decodeBitmap(source)
//            }
//
//            bitmap.value?.let { btm ->
//                Image(
//                    bitmap = btm.asImageBitmap(),
//                    contentDescription = null,
//                    modifier = Modifier.size(400.dp)
//                )
//            }
//        }
//    }
//}

//without button
//@Composable
//fun RequestContentPermission() {
//    var imageUri by remember {
//        mutableStateOf<Uri?>(null)
//    }
//    val context = LocalContext.current
//    val bitmap = remember {
//        mutableStateOf<Bitmap?>(null)
//    }
//
//    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
//        imageUri = uri
//    }
//
//    // Launch the image picker as soon as the composable is entered
//    LaunchedEffect(Unit) {
//        launcher.launch("image/*")
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        imageUri?.let {
//            if (Build.VERSION.SDK_INT < 28) {
//                bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
//            } else {
//                val source = ImageDecoder.createSource(context.contentResolver, it)
//                bitmap.value = ImageDecoder.decodeBitmap(source)
//            }
//
//            bitmap.value?.let { btm ->
//                Image(
//                    bitmap = btm.asImageBitmap(),
//                    contentDescription = null,
//                    modifier = Modifier.size(400.dp)
//                )
//            }
//        } ?: run {
//            Text(text = "No image selected", modifier = Modifier.padding(top = 16.dp))
//        }
//    }
//}

@Composable
fun RequestContentPermission(navController: NavController) {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    val bitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
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
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }

            bitmap.value?.let { btm ->
                Image(
                    bitmap = btm.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.size(150.dp)
                )
            }
            Text(
                text = "Change picture",
                color = Color(0xFFFFA726), // Orange color
                modifier = Modifier
                    .clickable { launcher.launch("image/*") }
            )
        } ?: run {
            Text(
                text = "Upload picture",
                color = Color(0xFFFFA726), // Orange color
                modifier = Modifier
                    .clickable { launcher.launch("image/*") }
            )
            Text(text = "No image selected", modifier = Modifier.padding(top = 16.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))


        // Category Dropdown
        val selectedCategory by rememberSaveable { mutableStateOf("") }
        var expanded by remember { mutableStateOf(false) }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Show as",
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
                        text = if (selectedCategory.isEmpty()) "Select Category" else selectedCategory,
                        color = if (selectedCategory.isEmpty()) Color.Gray else Color.Black
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Load") },
                        onClick = { Toast.makeText(context, "Load", Toast.LENGTH_SHORT).show() }
                    )
                    DropdownMenuItem(
                        text = { Text("Save") },
                        onClick = { Toast.makeText(context, "Save", Toast.LENGTH_SHORT).show() }
                    )
                }

            }



        }
        Spacer(modifier = Modifier.height(16.dp))

        // Description TextField
        var description by rememberSaveable { mutableStateOf("") }
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(16.dp))

        // Image Replacement Button
//        Button(
//            onClick = {
//                launcher.launch("image/*")
//            },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Replace Image")
//        }

        Spacer(modifier = Modifier.height(16.dp))

        // Submit Button
//        Button(
//            onClick = {
                // Handle post or adoption upload here
//                navController.navigate("your_destination_route")
//            },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Kirim")
//        }

        Button(
            onClick = {
                // Handle post or adoption upload here
                navController.navigate("your_destination_route")
            },
            Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(bottom = 5.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.colorPrimary)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Post",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = interFamily
            )
        }
    }
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PetVerseTheme {
        RequestContentPermission(navController = NavController(LocalContext.current))
    }
}