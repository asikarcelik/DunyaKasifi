package com.asikarcelik.dnyakaifi.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.asikarcelik.dnyakaifi.ui.screens.academy.AcademyWelcomeScreen
import com.asikarcelik.dnyakaifi.ui.screens.academy.AvatarSelectionScreen
import com.asikarcelik.dnyakaifi.ui.screens.academy.EquipmentSelectionScreen
import com.asikarcelik.dnyakaifi.ui.screens.academy.VehicleSelectionScreen
import com.asikarcelik.dnyakaifi.ui.screens.academy.ExplorerOathScreen
import com.asikarcelik.dnyakaifi.ui.screens.academy.CertificateScreen
import com.asikarcelik.dnyakaifi.ui.screens.worldmap.WorldMapScreen

// Uygulama içindeki ekranları tanımlama
enum class AppScreen(val route: String) {
    ACADEMY_WELCOME("academy_welcome"),
    AVATAR_SELECTION("avatar_selection"),
    EQUIPMENT_SELECTION("equipment_selection"),
    VEHICLE_SELECTION("vehicle_selection"),
    EXPLORER_OATH("explorer_oath"),
    CERTIFICATE("certificate"),
    WORLD_MAP("world_map")
}

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = AppScreen.ACADEMY_WELCOME.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = AppScreen.ACADEMY_WELCOME.route) {
            AcademyWelcomeScreen(
                onStartClick = {
                    navController.navigate(AppScreen.AVATAR_SELECTION.route)
                }
            )
        }
        
        composable(route = AppScreen.AVATAR_SELECTION.route) {
            AvatarSelectionScreen(
                onNextClick = {
                    navController.navigate(AppScreen.EQUIPMENT_SELECTION.route)
                }
            )
        }
        
        composable(route = AppScreen.EQUIPMENT_SELECTION.route) {
            EquipmentSelectionScreen(
                onNextClick = {
                    navController.navigate(AppScreen.VEHICLE_SELECTION.route)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(route = AppScreen.VEHICLE_SELECTION.route) {
            VehicleSelectionScreen(
                onNextClick = {
                    navController.navigate(AppScreen.EXPLORER_OATH.route)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(route = AppScreen.EXPLORER_OATH.route) {
            ExplorerOathScreen(
                onFinishClick = {
                    navController.navigate(AppScreen.CERTIFICATE.route) {
                        // Önceki tüm ekranları stack'ten kaldır
                        popUpTo(AppScreen.ACADEMY_WELCOME.route) {
                            inclusive = true
                        }
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(route = AppScreen.CERTIFICATE.route) {
            CertificateScreen(
                onRestartClick = {
                    // Sertifika ekranından sonra Dünya Haritası ekranına geçiş yap
                    navController.navigate(AppScreen.WORLD_MAP.route) {
                        // Önceki tüm ekranları stack'ten kaldır
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        
        composable(route = AppScreen.WORLD_MAP.route) {
            WorldMapScreen(
                onBackClick = {
                    navController.navigate(AppScreen.ACADEMY_WELCOME.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
} 