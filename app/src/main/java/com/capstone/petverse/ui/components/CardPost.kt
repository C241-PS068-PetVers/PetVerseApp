package com.capstone.petverse.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.transform.RoundedCornersTransformation
import com.capstone.petverse.ui.activity.interFamily
import com.capstone.petverse.ui.model.Post
import com.capstone.petverse.ui.components.ui.theme.PetVerseTheme

@Composable
fun CardPost(
    post: Post,
    modifier: Modifier = Modifier,
){
    val painterProfil: Painter = rememberImagePainter(
        data = post.imageProfil,
        builder = {
            transformations(RoundedCornersTransformation(8f))
        }
    )
    val painterDescription: Painter = rememberImagePainter(
        data = post.image,
        builder = {
            transformations(RoundedCornersTransformation(8f))
        }
    )
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(2.dp, Color.Gray, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
    ){
        Column() {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(10.dp)
            ){
                Image(
                    painter = painterProfil,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = post.username,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = interFamily
                )
            }

            Image(
                painter = painterDescription,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    IconWithText(icon = Icons.Default.FavoriteBorder, text = post.likesCount.toString())
                    Spacer(modifier = Modifier.width(16.dp))
                    IconWithText(icon = Icons.Default.Comment, text = post.commentsCount.toString())
                }
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share",
                    modifier = Modifier.size(24.dp)
                )
            }

            // Description
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
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
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

@Preview(showBackground = true)
@Composable
fun CardPostPreview() {
    PetVerseTheme {
        CardPost(
            post = Post("USer1", "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcR9onSdUyUOy-XXNQdn7rkQ3yXuMCOJPl2Iu_AkoQx_CglfO4fq", "https://www.akc.org/wp-content/uploads/2017/11/Samoyed-standing-in-the-forest.jpg", "Samoyed adventures", 10, 20),
            modifier = Modifier.padding(8.dp)
        )
    }
}