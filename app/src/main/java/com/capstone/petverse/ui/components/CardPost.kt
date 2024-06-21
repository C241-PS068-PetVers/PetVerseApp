package com.capstone.petverse.ui.components

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
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
import coil.transform.RoundedCornersTransformation
import com.capstone.petverse.R
import com.capstone.petverse.ui.model.PostUser
import com.capstone.petverse.ui.viewmodel.UploadPostViewModel

@Composable
fun CardPost(
    post: PostUser,
    viewModel: UploadPostViewModel,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val isInPreview = LocalInspectionMode.current
    var isLiked by remember { mutableStateOf(post.likes.contains(viewModel.userSession.value?.email)) }
    var likesCount by remember { mutableStateOf(post.likes.size) }

    val painterProfile: Painter = if (isInPreview) {
        painterResource(id = R.drawable.account_circle_24)
    } else {
        rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(post.profilePicture ?: R.drawable.account_circle_24)
                .size(Size.ORIGINAL)
                .crossfade(true)
                .transformations(RoundedCornersTransformation(8f))
                .build()
        )
    }

    val painterPost: Painter = if (isInPreview) {
        painterResource(id = R.drawable.account_circle_24)
    } else {
        rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(post.imageUrl)
                .size(Size.ORIGINAL)
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
                    modifier = Modifier.size(40.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = post.username,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Image(
                painter = painterPost,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
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
                    Text(text = likesCount.toString())
                }

                // Display phone icon for adoption category
                if (post.category == "adoption") {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Phone Number",
                        modifier = Modifier
                            .clickable {
                                val phoneNumber = post.phoneNumber
                                Log.e("CardPost", "Phone number: $phoneNumber")
                                val intent = Intent(Intent.ACTION_DIAL).apply {
                                    data = Uri.parse("tel:$phoneNumber")
                                }
                                context.startActivity(intent)
                            }

                    )
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
