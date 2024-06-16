package com.capstone.petverse.ui.activity

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.capstone.petverse.R

@Composable
fun ProfileScreen(navController: NavController) {
    Scaffold(
        topBar = { ProfileTopBar() },
        content = { paddingValues ->
            BodyContent(modifier = Modifier.padding(paddingValues), navController)
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
fun BodyContent(modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileSection()
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
        ProfileTab()
    }
}

@Composable
fun ProfileSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.account_circle_24),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Black, CircleShape),
            contentScale = ContentScale.Crop
        )
        Text("Name", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text("@username", fontSize = 15.sp, fontWeight = FontWeight.Medium)
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
        ProfileStatItem(value = "300", label = "Followers")
        VerticalDivider(
            color = Color.Gray,
            modifier = Modifier
                .height(40.dp)
                .width(1.dp)
                .align(Alignment.CenterVertically)
        )
        ProfileStatItem(value = "200", label = "Following")
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
fun ProfileTab() {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

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
            onClick = { selectedTabIndex = 0 },
            modifier = Modifier.height(42.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
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
            onClick = { selectedTabIndex = 1 },
            modifier = Modifier.height(42.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
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
            0 -> ContentPost()
            1 -> AdoptionPost()
        }
    }
}

@Composable
fun ContentPost() {
    val photos = remember { listOf(R.drawable.pet_adoption, R.drawable.pet_adoption, R.drawable.pet_adoption, R.drawable.pet_adoption, R.drawable.pet_adoption, R.drawable.pet_adoption, R.drawable.pet_adoption, R.drawable.pet_adoption) }
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
fun AdoptionPost() {
    val photos = remember { listOf(R.drawable.pet_adoption, R.drawable.pet_adoption, R.drawable.pet_adoption, R.drawable.pet_adoption, R.drawable.pet_adoption, R.drawable.pet_adoption, R.drawable.pet_adoption, R.drawable.pet_adoption) }
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

@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    val navController = rememberNavController()
    ProfileScreen(navController = navController)
}
