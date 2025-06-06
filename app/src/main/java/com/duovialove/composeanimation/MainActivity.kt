package com.duovialove.composeanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.duovialove.composeanimation.ui.theme.ComposeAnimationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()
            val viewModel : UserViewModel = viewModel()

            ComposeAnimationTheme {


                Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {


                    CustomNavigationBar(
                        navController
                    )
                }) { innerPadding ->
                    MainScreen(modifier=Modifier.padding(innerPadding), navController,viewModel)
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier, navController: NavHostController, viewModel: UserViewModel){

    NavHost(
        modifier=modifier,
        startDestination = "home",
        navController= navController
    ){

        composable(
            route = "home",
            enterTransition = {
                slideInHorizontally(initialOffsetX = {1000}, animationSpec = tween(700))
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = {-1000}, animationSpec = tween(700))
            }
        ) {
            HomeScreen(userViewModel = viewModel)
        }

        composable(
            route = "profile",
            enterTransition = {
                slideInHorizontally(initialOffsetX = {1000}, animationSpec = tween(700))
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = {-1000}, animationSpec = tween(700))
            }
        ) {
            ProfileScreen(userViewModel = viewModel)
        }

    }
}

@Composable
fun HomeScreen(userViewModel: UserViewModel){
    val name = userViewModel.userName

    Box(Modifier
        .fillMaxSize()
        .padding(16.dp), contentAlignment = Alignment.Center){
        Text("Welcome $name", style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
fun ProfileScreen(userViewModel: UserViewModel){
    val name = userViewModel.userName
    var enterYourName by remember {mutableStateOf("")}

    Column(Modifier
        .fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){

        Text("Your name: $name", style = MaterialTheme.typography.titleMedium)
        TextField(
            value = enterYourName,
            onValueChange = {
                enterYourName = it
            },
            placeholder = {
                Text("Enter your name")
            }
        )
        Button(
            onClick = {userViewModel.changeYourName(enterYourName)}
        ) {
            Text("Change Your Name")
        }
    }
}

@Composable
fun CustomNavigationBar(navController: NavController){

    val stateStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = stateStackEntry?.destination?.route

    NavigationBar {

        bottomNavItemList.forEach { screen->

            NavigationBarItem(
                icon = { Icon(screen.icon, screen.title) },
                label = {
                    Text(screen.title)
                },
                selected = currentRoute==screen.route,
                onClick = {
                    navController.navigate(screen.route){
                        popUpTo(navController.graph.startDestinationId){
                            saveState = true
                        }

                        launchSingleTop = true
                        restoreState = true

                    }
                }
            )

        }
    }

}