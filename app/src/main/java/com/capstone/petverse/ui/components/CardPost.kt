package com.capstone.petverse.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.capstone.petverse.R
import coil.transform.RoundedCornersTransformation
import com.capstone.petverse.ui.model.PostUser
import com.capstone.petverse.ui.viewmodel.UploadPostViewModel

@Composable
fun CardPost(
    post: PostUser,
    viewModel: UploadPostViewModel,
    modifier: Modifier = Modifier,
) {
    val isInPreview = LocalInspectionMode.current
    var isLiked by remember { mutableStateOf(post.likes.contains(viewModel.userSession.value?.email)) }
    var likesCount by remember { mutableStateOf(post.likes.size) }
    val painterProfile: Painter = if (isInPreview) {
        // Use a placeholder image during preview mode
        painterResource(id = R.drawable.account_circle_24) // Make sure to add a placeholder image in your resources
    } else {
        rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(post.imageUrl)
                .size(Size.ORIGINAL) // Load original size
                .crossfade(true)
                .transformations(RoundedCornersTransformation(8f))
                .build()
        )
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(2.dp, Color.Gray, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(10.dp)
            ) {
                Image(
                    painter = painterProfile,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp).clip(CircleShape), // Adjust size as needed
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = post.authorName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Image(
                painter = painterProfile,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f), // Ensures the image is a square
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
//                Row {
//                    IconWithText(icon = Icons.Default.FavoriteBorder, text = post.likesCount.toString())
//                    Spacer(modifier = Modifier.width(16.dp))
//                    IconWithText(icon = Icons.Default.Comment, text = post.commentsCount.toString())
//                }
//                Icon(
//                    imageVector = Icons.Default.Share,
//                    contentDescription = "Share",
//                    modifier = Modifier.size(24.dp)
//                )
                Row(
                    modifier = Modifier.clickable {
                        if (isLiked) {
                            viewModel.unlikePost(post.id)
                            isLiked = false
                            likesCount -= 1
                        } else {
                            viewModel.likePost(post.id)
                            isLiked = true
                            likesCount += 1
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = if (isLiked) "Unlike" else "Like",
                        tint = if (isLiked) Color.Red else Color.Gray
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = likesCount.toString())
                }
            }

            Text(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                text = post.description,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun IconWithText(
    icon: ImageVector,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = text,
            fontSize = 12.sp
        )
    }
}
