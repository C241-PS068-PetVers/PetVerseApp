package com.capstone.petverse

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.capstone.petverse.data.pref.UserPreference
import com.capstone.petverse.data.pref.dataStore
import com.capstone.petverse.ui.activity.CameraPreviewScreen
import com.capstone.petverse.ui.activity.DetectionScreen
import com.capstone.petverse.ui.activity.EditProfileScreen
import com.capstone.petverse.ui.activity.HomeScreen
import com.capstone.petverse.ui.activity.LikeHistoryActivity
import com.capstone.petverse.ui.activity.ProfileScreen
import com.capstone.petverse.ui.activity.SearchResultScreen
import com.capstone.petverse.ui.activity.UploadPostScreen
import com.capstone.petverse.ui.activity.WelcomeActivity
import com.capstone.petverse.ui.activity.PostListScreen
import com.capstone.petverse.ui.activity.ResultActivity
import com.capstone.petverse.ui.components.Search
import com.capstone.petverse.ui.model.BottomBarItem
import com.capstone.petverse.ui.model.Screen
import com.capstone.petverse.ui.theme.PetVerseTheme
import com.capstone.petverse.ui.viewmodel.ProfileViewModel
import com.capstone.petverse.ui.viewmodel.ResultViewModel
import com.capstone.petverse.ui.viewmodel.UploadPostViewModel
import com.capstone.petverse.ui.viewmodel.ViewModelFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private var isCameraPermissionGranted by mutableStateOf(false)

    private val cameraPermissionRequest = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        isCameraPermissionGranted = isGranted
        if (!isGranted) {
            Toast.makeText(this, "Camera permission is required to use this feature", Toast.LENGTH_LONG).show()
        }
    }

    private fun requestCameraPermission() {
        cameraPermissionRequest.launch(android.Manifest.permission.CAMERA)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val userPreference = UserPreference.getInstance(dataStore)
            val session = userPreference.getSession().first()
            if (session.isLogin) {
                setContent {
                    requestCameraPermission()
                    PetVerseTheme {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            PetVerseApp(isCameraPermissionGranted, session.token)
                        }
                    }
                }
            } else {
                val intent = Intent(this@MainActivity, WelcomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}

@Composable
fun PetVerseApp(isCameraPermissionGranted: Boolean, token: String, modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val context = LocalContext.current
    val factory = ViewModelFactory.getInstance(context.applicationContext as Application)
    val profileViewModel: ProfileViewModel = viewModel(factory = factory)
    val resultViewModel: ResultViewModel = viewModel(factory = factory)

    Scaffold(
        bottomBar = { if (currentRoute != Screen.EditProfile.route) BottomBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .background(Color.White)
        ) {
            if (currentRoute != Screen.Profile.route && currentRoute != Screen.Upload.route && currentRoute != Screen.EditProfile.route && currentRoute != Screen.Favorite.route && currentRoute != Screen.Detection.route && currentRoute != "result") {
                Search(navController = navController, modifier = Modifier.padding(5.dp))
            }

            NavHost(navController, startDestination = Screen.Home.route) {
                composable(Screen.Home.route) {
                    val uploadPostViewModel: UploadPostViewModel = viewModel(factory = factory)
                    HomeScreen(viewModel = uploadPostViewModel)
                }
                composable(Screen.Favorite.route) {
                    LikeHistoryActivity()
                }
                composable(Screen.Detection.route) {
                    if (isCameraPermissionGranted) {
                        CameraPreviewScreen(navController)
                    } else {
                        Toast.makeText(context, "Camera permission is required to use this feature", Toast.LENGTH_LONG).show()
                    }
                }
                composable("cameraPreview") {
                    CameraPreviewScreen(navController)
                }
                composable("detection") { DetectionScreen(navController = navController, resultViewModel = resultViewModel) }
                composable("result") { ResultActivity(navController = navController, viewModel = resultViewModel) }

                composable(Screen.Upload.route) {
                    val uploadPostViewModel: UploadPostViewModel = viewModel(factory = factory)
                    UploadPostScreen(navController, uploadPostViewModel)
                }
                composable(Screen.Profile.route) {
                    ProfileScreen(navController)
                }
                composable(Screen.EditProfile.route) {
                    EditProfileScreen(navController)
                }
                composable("search") {
                    Search(navController)
                }
                composable("search_result/{query}") { backStackEntry ->
                    val query = backStackEntry.arguments?.getString("query") ?: ""
                    SearchResultScreen(navController = navController, query = query)
                }
                composable(
                    "post_list_screen/{category}/{postId}?token={token}",
                    arguments = listOf(
                        navArgument("category") { type = NavType.StringType },
                        navArgument("postId") { type = NavType.StringType },
                        navArgument("token") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val category = backStackEntry.arguments?.getString("category")
                    val postId = backStackEntry.arguments?.getString("postId")
                    val token = backStackEntry.arguments?.getString("token")
                    if (category != null && postId != null && token != null) {
                        PostListScreen(navController, category, postId, profileViewModel, token)
                    }
                }
            }
        }
    }
}

@Composable
fun BottomBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        val navigationItems = listOf(
            BottomBarItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                route = Screen.Home.route
            ),
            BottomBarItem(
                title = stringResource(R.string.menu_favorite),
                icon = Icons.Default.Favorite,
                route = Screen.Favorite.route
            ),
            BottomBarItem(
                title = stringResource(R.string.detection),
                icon = Icons.Default.PhotoCamera,
                route = Screen.Detection.route
            ),
            BottomBarItem(
                title = stringResource(R.string.upload_post),
                icon = Icons.Default.Upload,
                route = Screen.Upload.route
            ),
            BottomBarItem(
                title = stringResource(R.string.menu_profile),
                icon = Icons.Default.AccountCircle,
                route = Screen.Profile.route
            ),
        )
        navigationItems.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = if (navController.currentDestination?.route == item.route) colorResource(id = R.color.colorPrimary) else Color.Gray
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        color = if (navController.currentDestination?.route == item.route) colorResource(id = R.color.colorPrimary) else Color.Gray,
                        fontWeight = if (navController.currentDestination?.route == item.route) FontWeight.Bold else FontWeight.Normal
                    )
                },
                selected = navController.currentDestination?.route == item.route,
                onClick = {
                    navController.navigate(item.route)
                },
            )
        }
    }
}
