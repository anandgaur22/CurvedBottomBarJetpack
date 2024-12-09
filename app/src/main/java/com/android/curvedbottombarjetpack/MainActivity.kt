package com.android.curvedbottombarjetpack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {

            CurvedBottomNavigationApp()

        }
    }
}


// Define the Screen data class
data class Screen(
    val route: String,
    @DrawableRes val icon: Int,
    @DrawableRes val selectedIcon: Int
)


// Main Composable
@Composable
fun CurvedBottomNavigationApp() {
    var currentScreen by remember { mutableStateOf<Screen?>(null) }

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize()
    ) {
        BottomMenuBar(
            screens = listOf(
                Screen(
                    route = "home",
                    icon = R.drawable.outline_home_24,
                    selectedIcon = R.drawable.baseline_home_24
                ),
                Screen(
                    route = "products",
                    icon = R.drawable.outline_collections_24,
                    selectedIcon = R.drawable.baseline_collections_24
                ),
                Screen(
                    route = "cart",
                    icon = R.drawable.outline_shopping_cart_24,
                    selectedIcon = R.drawable.baseline_shopping_cart_24
                ),
                Screen(
                    route = "profile",
                    icon = R.drawable.outline_person_24,
                    selectedIcon = R.drawable.baseline_person_24
                ),
                Screen(
                    route = "chat",
                    icon = R.drawable.outline_chat_24,
                    selectedIcon = R.drawable.baseline_chat_24
                )
            ),
            currentScreen = currentScreen,
            onNavigateTo = { currentScreen = it }
        )
    }
}

// Menu Bar Shape
private fun menuBarShape() = GenericShape { size, _ ->
    reset()

    moveTo(0f, 0f)

    val width = 150f
    val height = 90f

    val point1 = 75f
    val point2 = 85f

    lineTo(size.width / 2 - width, 0f)

    cubicTo(
        size.width / 2 - point1, 0f,
        size.width / 2 - point2, height,
        size.width / 2, height
    )

    cubicTo(
        size.width / 2 + point2, height,
        size.width / 2 + point1, 0f,
        size.width / 2 + width, 0f
    )

    lineTo(size.width / 2 + width, 0f)

    lineTo(size.width, 0f)
    lineTo(size.width, size.height)
    lineTo(0f, size.height)

    close()
}

// Bottom Menu Bar
@Composable
fun BottomMenuBar(
    screens: List<Screen>,
    currentScreen: Screen?,
    onNavigateTo: (Screen) -> Unit
) {
    val backgroundShape = remember { menuBarShape() }

    Box {
        // Background Shape
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color.White, backgroundShape)
                .align(Alignment.BottomCenter)
        )

        // Floating Action Button in the Center
        Column(
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            FloatingActionButton(
                shape = RoundedCornerShape(50),
                containerColor = Color.White,
                contentColor = Color.Gray,
                onClick = {},
                modifier = Modifier.clip(RoundedCornerShape(50))
            ) {
                Row(
                    modifier = Modifier.size(64.dp)
                ) {
                    BottomBarItem(screens[2], currentScreen, onNavigateTo) // Cart Icon
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
        }

        // Bottom Bar Items
        Row(
            modifier = Modifier
                .height(56.dp)
                .align(Alignment.BottomCenter)
        ) {
            BottomBarItem(screens[0], currentScreen, onNavigateTo) // Home
            BottomBarItem(screens[1], currentScreen, onNavigateTo) // Products

            Spacer(modifier = Modifier.width(72.dp)) // Space for the Floating Action Button

            BottomBarItem(screens[3], currentScreen, onNavigateTo) // Profile
            BottomBarItem(screens[4], currentScreen, onNavigateTo) // Chat
        }
    }
}





// Individual Bottom Bar Item
@Composable
private fun RowScope.BottomBarItem(
    screen: Screen,
    currentScreen: Screen?,
    onNavigateTo: (Screen) -> Unit
) {
    val selected = currentScreen?.route == screen.route

    Box(
        Modifier
            .clickable( // Use clickable as an alternative to selectable
                onClick = { onNavigateTo(screen) },
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(radius = 32.dp) // Ripple effect
            )
            .fillMaxHeight()
            .weight(1f),
        contentAlignment = Alignment.Center
    ) {
        BadgedBox(
            badge = {},
            content = {
                Icon(
                    painter = painterResource(
                        id = if (selected) screen.selectedIcon else screen.icon
                    ),
                    contentDescription = null,
                    tint = if (selected) Color.Black else Color.Gray
                )
            }
        )
    }
}

// Preview Function
@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    CurvedBottomNavigationApp()
}