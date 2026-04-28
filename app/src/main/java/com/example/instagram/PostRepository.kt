package com.example.instagram

class PostRepository {

    fun getPosts(page: Int): List<Post> {
        return List(20) { index ->
            val id = page * 20 + index
            Post(
                id = id,
                username = "User $id",
                imageUrl = "https://picsum.photos/200?random=$id",
                caption = "This is post $id",
                isLiked = false
            )
        }
    }
}