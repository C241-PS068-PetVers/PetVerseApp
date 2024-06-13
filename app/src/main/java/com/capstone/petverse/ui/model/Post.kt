package com.capstone.petverse.ui.model

//data class Post(
////    val username: String,
////    val imageProfil: String,
////    val image: String,
////    val description: String,
////    val commentsCount: Int,
////    val likesCount: Int
//
//    val authorName: String,
//    val imageUrl: String,
//    val description: String,
//    val id: String,
//    val category: String,
//)

data class PostUser(
    val id: String,
    val authorName: String,
    val imageUrl: String,
    val description: String,
    val category: String,
    val likes: List<String> = emptyList(),
    val commentsCount: Int = 0
)

//val dummyMenu = listOf(
//    Post(
//        username = "user1",
//        imageProfil = "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcR9onSdUyUOy-XXNQdn7rkQ3yXuMCOJPl2Iu_AkoQx_CglfO4fq",
//        image = "https://www.akc.org/wp-content/uploads/2017/11/Samoyed-standing-in-the-forest.jpg",
//        description = "Beautiful Samoyed enjoying nature!",
//        commentsCount = 25,
//        likesCount = 150
//    ),
//    Post(
//        username = "user2",
//        imageProfil = "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcR9onSdUyUOy-XXNQdn7rkQ3yXuMCOJPl2Iu_AkoQx_CglfO4fq",
//        image = "https://www.akc.org/wp-content/uploads/2017/11/Samoyed-standing-in-the-forest.jpg",
//        description = "Samoyed love ❤️",
//        commentsCount = 30,
//        likesCount = 180
//    ),
//    Post(
//        username = "user3",
//        imageProfil = "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcR9onSdUyUOy-XXNQdn7rkQ3yXuMCOJPl2Iu_AkoQx_CglfO4fq",
//        image = "https://www.akc.org/wp-content/uploads/2017/11/Samoyed-standing-in-the-forest.jpg",
//        description = "Chilling with my fluffy buddy!",
//        commentsCount = 20,
//        likesCount = 120
//    ),
//    Post(
//        username = "user4",
//        imageProfil = "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcR9onSdUyUOy-XXNQdn7rkQ3yXuMCOJPl2Iu_AkoQx_CglfO4fq",
//        image = "https://www.akc.org/wp-content/uploads/2017/11/Samoyed-standing-in-the-forest.jpg",
//        description = "Exploring the wilderness with my Samoyed!",
//        commentsCount = 35,
//        likesCount = 200
//    ),
//    Post(
//        username = "user5",
//        imageProfil = "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcR9onSdUyUOy-XXNQdn7rkQ3yXuMCOJPl2Iu_AkoQx_CglfO4fq",
//        image = "https://www.akc.org/wp-content/uploads/2017/11/Samoyed-standing-in-the-forest.jpg",
//        description = "Samoyed adventures 🐾",
//        commentsCount = 40,
//        likesCount = 220
//    )
//)
//
//val dumyPostMenu = dummyMenu.shuffled()