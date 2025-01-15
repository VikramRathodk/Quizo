package org.devvikram.quizo.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import org.devvikram.quizo.R
import org.devvikram.quizo.models.QuizCategories
import org.devvikram.quizo.navigations.Destination
import org.devvikram.quizo.utils.SharedPreference


@Composable
fun HomeScreen(
    navHostController: NavHostController,
    appViewModel: AppViewModel
) {
    val sharedPreference = SharedPreference(LocalContext.current)
    var isMenuShow by remember {
        mutableStateOf(false)

    }
    var isLogoutDialogOpen by remember {
        mutableStateOf(false)
    }
    val username = sharedPreference.getUsername()

    val quizCategoriesList by remember {
        mutableStateOf(
            listOf(
                QuizCategories(
                    id = "1",
                    title = "General Knowledge",
                    description = "Test your general knowledge across a variety of topics!",
                    questions = listOf(),
                    color = Color.Red
                ),
                QuizCategories(
                    id = "2",
                    title = "Science",
                    description = "Explore the fascinating world of science!",
                    questions = listOf(),
                    color = Color.Green
                ),
                QuizCategories(
                    id = "3",
                    title = "History",
                    description = "Delve into the events that shaped our world.",
                    questions = listOf(),
                    color = Color.Blue
                ),
                QuizCategories(
                    id = "4",
                    title = "Technology",
                    description = "Dive into the advancements shaping our future.",
                    questions = listOf(),
                    color = Color.Yellow
                ),
                QuizCategories(
                    id = "5",
                    title = "Sports",
                    description = "Challenge yourself with questions about sports and athletes.",
                    questions = listOf(),
                    color = Color.Magenta
                ),
                QuizCategories(
                    id = "6",
                    title = "Entertainment",
                    description = "Test your knowledge of movies, music, and pop culture.",
                    questions = listOf(),
                    color = Color.LightGray
                ),
                QuizCategories(
                    id = "7",
                    title = "Geography",
                    description = "Explore questions about countries, capitals, and landscapes.",
                    questions = listOf(),
                    color = Color.Gray
                ),
                QuizCategories(
                    id = "8",
                    title = "Literature",
                    description = "Dive into the world of classic and modern literature.",
                    questions = listOf(),
                    color = Color.Cyan
                )
            )
        )
    }

    Scaffold(
        topBar = {
            UserInfoCard(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                userName = username.toString(),
                avatarUrl = "",
                rank = 200,
                score = 120,
                onLogoutClick = {
                    isLogoutDialogOpen = true
                }
            )
        },
//        TODO bottom navigation
        bottomBar = {
            BottomBar(navHostController = navHostController)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            // TODO Quiz Categories i.e menus
            QuizCategories(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                categories = quizCategoriesList
            )





        }
//         logout dialog

        if (isLogoutDialogOpen) {
            LogoutDialog(
                onDismissRequest = { isLogoutDialogOpen = false },
                onConfirmLogout = {
                    appViewModel.logoutUser()
                    navHostController.navigate(Destination.Login.route) {
                        popUpTo(Destination.Home.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

    }
}

@Composable
fun BottomBar(navHostController: NavHostController) {
    BottomAppBar {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                selected = navHostController.currentDestination?.route == Destination.Home.route,
                onClick = { navHostController.navigate(Destination.Home.route) }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Filled.FavoriteBorder, contentDescription = "Favorite") },
                selected = false,
                onClick = {  }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
                selected = false,
                onClick = {  }
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Profile"
                    )
                },
                selected = false,
                onClick = {  }
            )
        }


    }
}

@Composable
fun QuizCategories(modifier: Modifier, categories: List<QuizCategories>) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(categories) { category ->
            QuizCategoryCard(category = category)
        }
    }
}

@Composable
fun QuizCategoryCard(category: QuizCategories) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = category.color.copy(
                alpha = 0.1f
            ),
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "${category.title} icon",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(48.dp)
                    .padding(bottom = 8.dp)
            )
            Text(
                text = category.title,
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = category.description,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


@Composable
fun RecentQuizesCategories(modifier: Modifier) {

}

@Composable
fun UserInfoCard(
    modifier: Modifier = Modifier,
    userName: String,
    avatarUrl: String? = null,
    rank: Int,
    score: Int,
    onLogoutClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // User Avatar
            if (avatarUrl != null) {
                Image(
                    painter = rememberAsyncImagePainter(avatarUrl),
                    contentDescription = "$userName's avatar",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .border(3.dp, MaterialTheme.colorScheme.primary, CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = userName.firstOrNull()?.uppercase() ?: "?",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // User Info
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = userName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Rank: $rank",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = "Score: $score",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Logout Icon
            Icon(
                imageVector = Icons.Default.ExitToApp,
                contentDescription = "Logout",
                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { onLogoutClick() }
            )
        }
    }
}



@Composable
fun LogoutDialog(onDismissRequest: () -> Unit, onConfirmLogout: () -> Unit) {

    AlertDialog(
        onDismissRequest = {
            onDismissRequest()
        },
        title = {
            Text(text = "Logout")
        },
        text = {
            Text("Leaving so soon?.")
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirmLogout()
                onDismissRequest()
            }) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismissRequest()
            }) {
                Text("Dismiss")
            }
        }
    )
}


