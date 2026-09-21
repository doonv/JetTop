package doonv.jettop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import doonv.jettop.ui.AuthState
import doonv.jettop.ui.AuthViewModel
import doonv.jettop.ui.LoginScreen
import doonv.jettop.ui.MenuCountersViewModel
import doonv.jettop.ui.ScheduleScreen
import doonv.jettop.ui.ScheduleViewModel
import doonv.jettop.ui.theme.JetTopTheme

enum class Destination(
    val route: String,
    val label: Int,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
) {
    MAIN("main", R.string.main, Icons.Outlined.Home, Icons.Filled.Home),
    MESSAGES("messages", R.string.messages, Icons.Outlined.Email, Icons.Filled.Email),
    STUDENT_CARD(
        "student_card",
        R.string.student_card,
        Icons.Outlined.AccountBox,
        Icons.Filled.AccountBox
    )
}

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetTopTheme {
                val auth: AuthViewModel = viewModel()
                val authState by auth.state.collectAsStateWithLifecycle()
                when (val s = authState) {
                    AuthState.Checking -> Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        Column(
                            Modifier
                                .padding(innerPadding)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            LoadingIndicator()
                        }
                    }

                    is AuthState.LoggedOut -> {
                        LoginScreen(
                            onLogin = { username, password -> auth.login(username, password) },
                            s.isLoading,
                            s.error
                        )
                    }

                    is AuthState.LoggedIn -> HomeScaffold()
                }
            }
        }
    }
}

@Composable
fun HomeScaffold() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { HomeBottomBar(navController) }
    ) { innerPadding ->
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

@Composable
fun HomeBottomBar(navController: NavHostController) {
    val startDestination = Destination.MAIN
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }
    val countersVm: MenuCountersViewModel = viewModel()
    val count = countersVm.unreadMessages.collectAsStateWithLifecycle().value
    NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
        Destination.entries.forEachIndexed { index, destination ->
            val label = stringResource(destination.label)
            NavigationBarItem(selected = selectedDestination == index, onClick = {
                navController.navigate(route = destination.route)
                selectedDestination = index
            }, icon = {
                val icon = @Composable {
                    Icon(
                        if (selectedDestination == index) destination.selectedIcon else destination.icon,
                        contentDescription = label
                    )
                }
                if (destination == Destination.MESSAGES && count != null && count > 0) {
                    BadgedBox(badge = {
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                            Badge {
                                Text(if (count > 99) "99+" else count.toString())
                            }
                        }
                    }) {
                        icon()
                    }
                } else {
                    icon()
                }
            }, label = { Text(label) })
        }
    }
}
