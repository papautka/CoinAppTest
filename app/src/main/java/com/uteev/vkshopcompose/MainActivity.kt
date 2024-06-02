package com.uteev.vkshopcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.uteev.vkshopcompose.ui.theme.MainScreen
import com.uteev.vkshopcompose.ui.theme.VkShopComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VkShopComposeTheme {
                MainScreen()
            }
        }
    }
}