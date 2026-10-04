package doonv.jettop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import doonv.jettop.data.LoginData
import doonv.jettop.ui.AuthState
import doonv.jettop.ui.AuthViewModel
import doonv.jettop.ui.DashboardScreen
import doonv.jettop.ui.LoginScreen
import doonv.jettop.ui.MenuCountersViewModel
import doonv.jettop.ui.MessageDetailsScreen
import doonv.jettop.ui.MessageDetailsViewModel
import doonv.jettop.ui.MessagesScreen
import doonv.jettop.ui.MessagesViewModel
import doonv.jettop.ui.ScheduleViewModel
import doonv.jettop.ui.StudentCardScreen
import doonv.jettop.ui.theme.Symbols
import doonv.jettop.ui.theme.JetTopTheme
import kotlinx.serialization.Serializable

enum class Destination(
    val route: String,
    val label: Int,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
) {
    MAIN("main", R.string.main, Symbols.Outlined.Home24, Symbols.Filled.Home24),
    MESSAGES("messages", R.string.messages, Symbols.Outlined.Mail24, Symbols.Filled.Mail24),
    STUDENT_CARD(
        "student_card",
        R.string.student_card,
        Symbols.Outlined.AccountBox24,
        Symbols.Filled.AccountBox24
    )
}

@Serializable
data class MessageDetailsPage(val id: String)


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
                    is AuthState.LoggedOut -> {
                        LoginScreen(
                            onLogin = { username, password -> auth.login(username, password) },
                            s.isLoading,
                            s.error
                        )
                    }

                    is AuthState.LoggedIn -> HomeScaffold(s.login)
                }
            }
        }
    }
}

@Composable
fun HomeScaffold(loginData: LoginData) {
    val navController = rememberNavController()
    val countersVm: MenuCountersViewModel = viewModel()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        // Don't add any window insets here so we don't have to deal with them when
        // we add a top bar in MessagesScreen
        contentWindowInsets = WindowInsets(),
        bottomBar = { HomeBottomBar(navController, countersVm) },
    ) { innerPadding ->
        val modifier = Modifier
            .padding(innerPadding)
            .consumeWindowInsets(innerPadding)
        val scheduleVm: ScheduleViewModel = viewModel()
        val messagesVm: MessagesViewModel = viewModel()
        NavHost(
            navController = navController,
            startDestination = Destination.MAIN.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None }) {
            composable(Destination.MAIN.route) {
                DashboardScreen(
                    modifier = modifier,
                    vm = scheduleVm
                )
            }
            composable(Destination.MESSAGES.route) {
                MessagesScreen(
                    modifier = modifier,
                    vm = messagesVm,
                    onRefresh = {
                        countersVm.refresh()
                    },
                    onMessageClick = { message ->
                        if (!message.isRead) countersVm.markRead()
                        messagesVm.markRead(message.messageId)
                        navController.navigate(MessageDetailsPage(id = message.messageId))
                    }
                )
            }
            composable<MessageDetailsPage> { entry ->
                val route = entry.toRoute<MessageDetailsPage>()
                val vm: MessageDetailsViewModel = viewModel()
                LaunchedEffect(route.id) { vm.load(route.id) }
                MessageDetailsScreen(
                    modifier = modifier,
                    vm = vm,
                    onBack = { navController.popBackStack() },
                    messageId = route.id
                )
            }
            composable(Destination.STUDENT_CARD.route) {
                StudentCardScreen(
                    modifier = modifier,
                    loginData
                )
            }
        }

    }
}

@Composable
fun HomeBottomBar(navController: NavHostController, countersVm: MenuCountersViewModel) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route


    val count = countersVm.unreadMessages.collectAsStateWithLifecycle().value

    NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
        Destination.entries.forEach { destination ->
            val label = stringResource(destination.label)
            NavigationBarItem(selected = currentRoute == destination.route, onClick = {
                navController.navigate(route = destination.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }, icon = {
                val icon = @Composable {
                    Icon(
                        if (currentRoute == destination.route) destination.selectedIcon else destination.icon,
                        contentDescription = label
                    )
                }
                if (destination == Destination.MESSAGES && count != null && count > 0) {
                    BadgedBox(badge = {
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
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
