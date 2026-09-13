package doonv.smarterschool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import doonv.smarterschool.ui.ScheduleScreen
import doonv.smarterschool.ui.ScheduleViewModel
import doonv.smarterschool.ui.theme.SmarterSchoolTheme

enum class Destination(
    val route: String,
    val label: Int,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
) {
    MAIN("main", R.string.main, Icons.Outlined.Home, Icons.Filled.Home),
    MESSAGES("messages", R.string.messages, Icons.Outlined.Email, Icons.Filled.Email),
    STUDENT_CARD("student_card", R.string.student_card, Icons.Outlined.AccountBox, Icons.Filled.AccountBox)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmarterSchoolTheme {
                val navController = rememberNavController()
                val startDestination = Destination.MAIN
                var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }
                Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
                    NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                        Destination.entries.forEachIndexed { index, destination ->
                            val label = stringResource(destination.label)
                            NavigationBarItem(selected = selectedDestination == index, onClick = {
                                navController.navigate(route = destination.route)
                                selectedDestination = index
                            }, icon = {
                                Icon(
                                    if (selectedDestination == index) destination.selectedIcon else destination.icon,
                                    contentDescription = label
                                )
                            }, label = { Text(label) })
                        }
                    }
                }) { innerPadding ->
                    val scheduleVm: ScheduleViewModel = viewModel()
                    NavHost(
                        navController = navController,
                        startDestination = Destination.MAIN.route,
                        enterTransition = { EnterTransition.None },
                        exitTransition = { ExitTransition.None },
                        popEnterTransition = { EnterTransition.None },
                        popExitTransition = { ExitTransition.None }) {
                        composable(Destination.MAIN.route) {
                            ScheduleScreen(
                                modifier = Modifier.padding(innerPadding),
                                vm = scheduleVm
                            )
                        }
                        composable(Destination.MESSAGES.route) { }
                        composable(Destination.STUDENT_CARD.route) { }
                    }

                }
            }
        }
    }
}
