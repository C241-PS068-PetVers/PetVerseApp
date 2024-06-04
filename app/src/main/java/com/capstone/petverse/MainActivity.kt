package com.capstone.petverse

import ProfileActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.capstone.petverse.ui.activity.HomeScreen
import com.capstone.petverse.ui.activity.LikeHistoryActivity
import com.capstone.petverse.ui.activity.RequestContentPermission
import com.capstone.petverse.ui.components.Search
import com.capstone.petverse.ui.model.BottomBarItem
import com.capstone.petverse.ui.model.Screen
import com.capstone.petverse.ui.model.dummyMenu
import com.capstone.petverse.ui.theme.PetVerseTheme
import com.capstone.petverse.ui.viewmodel.SignupViewModel
import deleteAccount
import logout

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetVerseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PetVerseApp()
                }
            }
        }
    }
}

@Preview
@Composable
fun PetVerseApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ){ innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding).background(Color.White)
        ) {
            if (currentRoute != Screen.Upload.route) {
                Search()
            }
            NavHost(navController, startDestination = Screen.Home.route) {
                composable(Screen.Home.route) {
                    HomeScreen(dummyMenu)
                }
                composable(Screen.Favorite.route) {
                    LikeHistoryActivity()
                }
                composable(Screen.Upload.route) {
                    RequestContentPermission(navController)
//                    CreatePostScreen()
//                    UploadPosttActivity()
                }
//                composable(Screen.Profile.route) {
//                    ProfileActivity(viewModel, logoutAction, deleteAccountAction)
//                }
                composable(Screen.Profile.route) {
                    val viewModel: SignupViewModel = viewModel()
                    val context = LocalContext.current

                    CompositionLocalProvider(LocalContext provides LocalContext.current) {
                        ProfileActivity(
                            viewModel = viewModel,
                            logoutAction = { logout(context) },
                            deleteAccountAction = { deleteAccount(context) }
                        )
                    }
                }

            }
        }
    }

}

//@Composable
//fun Banner(modifier: Modifier = Modifier) {
//    Column(modifier = modifier) {
////        Text(
////            text = "PetVerse Search",
////            fontSize = 20.sp,
////            fontWeight = FontWeight.Bold,
////            color = Color.Black,
////            modifier = Modifier.padding(16.dp)
////        )
//        Search()
//    }
//}

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
                route = "home"
            ),
            BottomBarItem(
                title = stringResource(R.string.menu_favorite),
                icon = Icons.Default.Favorite,
                route = "favorite"
            ),
            BottomBarItem(
                title = stringResource(R.string.upload_post),
                icon = Icons.Default.Upload,
                route = "upload"
            ),
            BottomBarItem(
                title = stringResource(R.string.menu_profile),
                icon = Icons.Default.AccountCircle,
                route = "profile"
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
                        color = if (navController.currentDestination?.route == item.route) colorResource(id = R.color.colorPrimary) else Color.Gray
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
