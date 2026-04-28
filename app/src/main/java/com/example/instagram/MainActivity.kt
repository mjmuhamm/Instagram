package com.example.instagram

import android.R.attr.contentDescription
import android.graphics.fonts.Font
import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.instagram.ui.theme.InstagramTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel : PostViewModel by viewModels()
            InstagramTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Home(Modifier.padding(innerPadding), viewModel)
                }
            }
        }
    }
}

data class NavItem(
    val location: String,
    val icon: ImageVector,
    val route: String
)

@Composable
fun Home(modifier: Modifier, viewModel: PostViewModel) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(navController)
        }
    ) { innerPadding ->
        NavHostContainer(navController, Modifier.padding(innerPadding), viewModel)
    }
}

@Composable
fun NavHostContainer(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: PostViewModel
) {

    NavHost(
        navController = navController,
        startDestination = "Home",
        modifier = modifier
    ) {
        composable("Home") { InstagramScreen(viewModel) }
        composable("Reels") { ReelsScreen() }
        composable("Messages") { MessagesScreen() }
        composable("Profile") { ProfileScreen() }
    }
}

@Composable
fun BottomNavBar(navController: NavController) {

    val items = listOf(
        NavItem("Home", Icons.Default.Home, "Home"),
        NavItem("Reels", Icons.Default.Star, "Reels"),
        NavItem("Messages", Icons.Default.Email, "Messages"),
        NavItem("Profile", Icons.Default.Person, "Profile")
    )
    var selectedItem by remember { mutableIntStateOf(0) }

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(item.icon, contentDescription = item.location)
                },
                label = {
                    Text(item.location)
                }
            )
        }
    }
}

data class HomeScreen(
    val title: String,
    val profileImage: Image,
    val itemImage: Image,
    val contentDescription: String,

)

@Composable
fun InstagramScreen(viewModel: PostViewModel) {


    val reels = listOf(Color.Red, Color.Blue, Color.Gray, Color.Magenta, Color.Yellow, Color.Cyan, Color.Red, Color.Blue, Color.Gray, Color.Magenta, Color.Yellow, Color.Cyan)
    val state = viewModel.state.collectAsStateWithLifecycle()
    val value = state.value
    Column(modifier = Modifier.fillMaxSize().background(Color.White).padding(top = 15.dp)) {
        Row(horizontalArrangement = Arrangement.SpaceAround) {
            Text(text = "+", modifier = Modifier.padding(end = 114.dp, start = 19.dp), fontSize = 43.sp, fontWeight = FontWeight.Thin)
            Text("Instagram", modifier = Modifier.padding(top = 13.dp, end = 105.dp), fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic, fontSize = 20.sp)
            Image(painter = painterResource(id = R.drawable.heart), contentDescription = "heart", modifier = Modifier.size(44.dp).padding(top = 11.dp))
        }

        LazyRow(modifier = Modifier.padding(top = 12.dp, start = 5.dp, end = 5.dp)) {
            items(value) { item ->
                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = item.caption,
                    modifier = Modifier.padding(horizontal = 3.dp)
                        .size(70.dp)
                        .clip(CircleShape)
                )
            }
        }

        LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 16.dp)) {
            items(value) { item ->
                val x = value.indexOf(item)
                FeedItem(item, x, viewModel)
            }
        }
    }

}

@Composable
fun ReelsScreen() {
    Text("Reels Screen")
}

@Composable
fun MessagesScreen() {
    Text("Messages Screen")
}

@Composable
fun ProfileScreen() {
    Text("Profile Screen")
}

@Composable

fun FeedItem(value: Post, index: Int, viewModel: PostViewModel) {

    val randomColor = Color(
        red = Random.nextInt(256),
        green = Random.nextInt(256),
        blue = Random.nextInt(256)
    )

    val randomColor2 = Color(
        red = Random.nextInt(256),
        green = Random.nextInt(256),
        blue = Random.nextInt(256)
    )

    val state = viewModel.state.collectAsStateWithLifecycle()




    Card(modifier = Modifier.fillMaxWidth().height(height = 550.dp)) {
        Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
            Row() {
                AsyncImage(
                    model = value.imageUrl,
                    contentDescription = value.caption,
                    modifier = Modifier.padding(start = 7.dp, top = 7.dp)
                        .size(30.dp)
                        .clip(CircleShape)
                )
                Text(value.username, modifier = Modifier.padding(start = 9.dp, top = 12.dp), fontWeight = FontWeight.Bold)
            }

            AsyncImage(
                model = value.imageUrl,
                contentDescription = value.caption,
                modifier = Modifier.padding(top = 7.dp)
                    .fillMaxWidth()
                    .height(412.dp)
            )

            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                val painter = painterResource(
                    id = if (state.value[index].isLiked) R.drawable.heart_liked else R.drawable.heart
                )
                Image(painter = painter, contentDescription = "heart", modifier = Modifier.size(34.dp).padding(top = 9.dp).clickable {
                    viewModel.updateLike(index)
                })






                Image(painter = painterResource(id = R.drawable.comment), contentDescription = "comment", modifier = Modifier.size(34.dp).padding(top = 11.dp))
                Image(painter = painterResource(id = R.drawable.share), contentDescription = "share", modifier = Modifier.size(34.dp).padding(top = 12.dp))
                Spacer(modifier = Modifier.weight(1f))
                Image(painter = painterResource(id = R.drawable.save), contentDescription = "save", modifier = Modifier.size(34.dp).padding(top = 11.dp, end = 15.dp))
            }
            Row() {
                Text(value.username, modifier = Modifier.padding(top = 5.dp, start = 7.dp, end = 3.dp), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                Text(value.caption, Modifier.padding(top = 5.dp), fontSize = 13.sp)
            }

            Text("4 hours ago", modifier = Modifier.padding(top = 2.dp, start = 7.dp), fontWeight = FontWeight.Light, fontSize = 12.sp)

        }
    }
}