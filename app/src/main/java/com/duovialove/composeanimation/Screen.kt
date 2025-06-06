package com.duovialove.composeanimation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route:String, val title:String, val icon: ImageVector) {

    object Home : Screen(route = "home", title = "Home", icon = Icons.Default.Home)
    object Profile : Screen(route = "profile", title = "Profile", icon= Icons.Default.Person)
}

val bottomNavItemList= listOf(
    Screen.Home, Screen.Profile
)