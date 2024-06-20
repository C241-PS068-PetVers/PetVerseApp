package com.capstone.petverse.ui.activity

import android.app.Application
import android.content.Intent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.capstone.petverse.R
import com.capstone.petverse.data.pref.UserPreference
import com.capstone.petverse.data.pref.dataStore
import com.capstone.petverse.data.response.UserProfile
import com.capstone.petverse.ui.viewmodel.ProfileViewModel
import com.capstone.petverse.ui.viewmodel.ViewModelFactory

@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val application = context.applicationContext as Application
    val profileViewModel: ProfileViewModel = viewModel(factory = ViewModelFactory.getInstance(application))

    val userPreference = UserPreference.getInstance(context.dataStore)
    val userProfile by profileViewModel.userProfile.collectAsState()

    LaunchedEffect(Unit) {
        userPreference.getSession().collect { user ->
            if (user.isLogin) {
                profileViewModel.fetchUserProfile("Bearer ${user.token}")
            }
        }
    }

    Scaffold(
        topBar = { ProfileTopBar() },
        content = { paddingValues ->
            BodyContent(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(Color.White),
                navController = navController,
                profileViewModel = profileViewModel,
                userProfile = userProfile
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopBar() {
    val context = LocalContext.current
    TopAppBar(
        title = {},
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
        actions = {
            IconButton(onClick = {
                context.startActivity(Intent(context, SettingsActivity::class.java))
            }) {
                Icon(Icons.Filled.Settings, contentDescription = "Settings")
            }
        }
    )
}

@Composable
fun BodyContent(
    modifier: Modifier = Modifier,
    navController: NavController,
    profileViewModel: ProfileViewModel,
    userProfile: UserProfile?
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileSection(userProfile)
        Spacer(modifier = Modifier.height(8.dp))
        ProfileStats()
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { navController.navigate("edit_profile") },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = colorResource(id = R.color.colorTextBlack)
            ),
            border = BorderStroke(1.dp, colorResource(id = R.color.colorTextBlack)),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = "Edit Profile",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        ProfileTab(profileViewModel)
    }
}

@Composable
fun ProfileSection(userProfile: UserProfile?) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        val painter = rememberImagePainter(userProfile?.profilePicture ?: R.drawable.account_circle_24)
        Image(
            painter = painter,
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Black, CircleShape),
            contentScale = ContentScale.Crop
        )
        Text(userProfile?.name ?: "Name", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text("@${userProfile?.username ?: "username"}", fontSize = 15.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun ProfileStats() {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        ProfileStatItem(value = "200", label = "Post")
        VerticalDivider(
            color = Color.Gray,
            modifier = Modifier
                .height(40.dp)
                .width(1.dp)
                .align(Alignment.CenterVertically)
        )
        ProfileStatItem(value = "60", label = "Likes")
    }
}

@Composable
fun ProfileStatItem(value: String, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(80.dp)
    ) {
        Text(text = value, fontWeight = FontWeight.Bold)
        Text(text = label)
    }
}

@Composable
fun ProfileTab(profileViewModel: ProfileViewModel) {
    val selectedTabIndex by profileViewModel.selectedTabIndex.collectAsState()

    TabRow(
        selectedTabIndex = selectedTabIndex,
        indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
                Modifier
                    .tabIndicatorOffset(tabPositions[selectedTabIndex])
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.colorPrimary))
            )
        }
    ) {
        Tab(
            selected = selectedTabIndex == 0,
            onClick = { profileViewModel.onTabSelected(0) },
            modifier = Modifier.height(42.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.home_outlined),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Tab(
            selected = selectedTabIndex == 1,
            onClick = { profileViewModel.onTabSelected(1) },
            modifier = Modifier.height(42.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.home_outlined),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    AnimatedContent(
        targetState = selectedTabIndex,
        label = "ProfileTabs",
        transitionSpec = {
            if (targetState > initialState) {
                slideInHorizontally { width -> width } + fadeIn(tween(300)) togetherWith
                        slideOutHorizontally { width -> -width } + fadeOut(tween(300))
            } else {
                slideInHorizontally { width -> -width } + fadeIn(tween(300)) togetherWith
                        slideOutHorizontally { width -> width } + fadeOut(tween(300))
            }.using(SizeTransform(false))
        }
    ) { index ->
        when (index) {
            0 -> ContentPost(profileViewModel.photos)
            1 -> AdoptionPost(profileViewModel.photos)
        }
    }
}

@Composable
fun ContentPost(photos: List<Int>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(4.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(photos.size) { index ->
            Image(
                painter = painterResource(id = photos[index]),
                contentDescription = null,
                modifier = Modifier
                    .padding(4.dp)
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun AdoptionPost(photos: List<Int>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(4.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(photos.size) { index ->
            Image(
                painter = painterResource(id = photos[index]),
                contentDescription = null,
                modifier = Modifier
                    .padding(4.dp)
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}