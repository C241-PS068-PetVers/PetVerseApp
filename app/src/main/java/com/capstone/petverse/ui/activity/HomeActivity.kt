package com.capstone.petverse.ui.activity

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.capstone.petverse.ui.model.Post
import com.capstone.petverse.ui.components.CardPost


@Composable
fun HomeScreen(
    listMenu: List<Post>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    ) {
        items(listMenu, key = { it.username }) { menu ->
            CardPost(menu, modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}