package doonv.jettop

import android.app.Application
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import doonv.jettop.data.CachedDisciplineEvents
import doonv.jettop.data.CachedSchedule
import doonv.jettop.data.DisciplineEventsRepository
import doonv.jettop.data.FileCache
import doonv.jettop.data.LoginData
import doonv.jettop.data.ScheduleRepository
import doonv.jettop.ui.AuthState
import doonv.jettop.ui.AuthViewModel
import doonv.jettop.ui.DashboardScreen
import doonv.jettop.ui.DashboardViewModel
import doonv.jettop.ui.LoginScreen
import doonv.jettop.ui.MenuCountersViewModel
import doonv.jettop.ui.MessageDetailsScreen
import doonv.jettop.ui.MessageDetailsViewModel
import doonv.jettop.ui.MessagesScreen
import doonv.jettop.ui.MessagesViewModel
import doonv.jettop.ui.studentcard.DisciplineEventsViewModel
import doonv.jettop.ui.studentcard.StudentCardGraph
import doonv.jettop.ui.studentcard.studentCardGraph
import doonv.jettop.ui.theme.JetTopTheme
import doonv.jettop.ui.theme.Symbols
import kotlinx.serialization.Serializable

@Serializable
data object MainRoute

@Serializable
data object MessagesRoute

@Serializable
data object MessageInbox

@Serializable
data class MessageDetailsPage(val id: String)

enum class Destination(
    val route: Any,
    val label: Int,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
) {
    MAIN(MainRoute, R.string.main, Symbols.Outlined.Home24, Symbols.Filled.Home24),
    MESSAGES(MessagesRoute, R.string.messages, Symbols.Outlined.Mail24, Symbols.Filled.Mail24),
    STUDENT_CARD(
        StudentCardGraph,
        R.string.student_card,
        Symbols.Outlined.AccountBox24,
        Symbols.Filled.AccountBox24
    )
}


class MainActivity : ComponentActivity() {
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
        val messagesVm: MessagesViewModel = viewModel()
        val app = LocalContext.current.applicationContext as Application
        val scheduleRepo = remember(app) {
            ScheduleRepository(
                cache = FileCache(
                    file = app.filesDir.resolve("schedules.json"),
                    serializer = CachedSchedule.serializer()
                )
            )
        }
        val disciplineRepo = remember(app) {
            DisciplineEventsRepository(
                cache = FileCache(
                    file = app.filesDir.resolve("discipline_events.json"),
                    serializer = CachedDisciplineEvents.serializer()
                )
            )
        }
        val dashboardFactory = remember(app, scheduleRepo, disciplineRepo) {
            viewModelFactory {
                initializer { DashboardViewModel(app, scheduleRepo, disciplineRepo) }
                initializer { DisciplineEventsViewModel(app, disciplineRepo) }
            }
        }
        val dashboardVm: DashboardViewModel = viewModel(factory = dashboardFactory)
        val eventsVm: DisciplineEventsViewModel = viewModel(factory = dashboardFactory)
        NavHost(
            navController = navController,
            startDestination = MainRoute,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None }) {
            composable<MainRoute> {
                DashboardScreen(
                    modifier = modifier,
                    vm = dashboardVm,
                    firstName = loginData.firstName,
                )
            }
            messagesGraph(modifier, messagesVm, countersVm, navController)
            studentCardGraph(navController, modifier, loginData, eventsVm)
        }

    }
}

private fun NavGraphBuilder.messagesGraph(
    modifier: Modifier,
    messagesVm: MessagesViewModel,
    countersVm: MenuCountersViewModel,
    navController: NavHostController,
) {
    navigation<MessagesRoute>(startDestination = MessageInbox) {
        composable<MessageInbox> {
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
    }
}

@Composable
fun HomeBottomBar(navController: NavHostController, countersVm: MenuCountersViewModel) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val hierarchy = backStackEntry?.destination?.hierarchy.orEmpty()
    val count = countersVm.unreadMessages.collectAsStateWithLifecycle().value

    NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
        Destination.entries.forEach { destination ->
            val label = stringResource(destination.label)
            val selected = hierarchy.any { it.hasRoute(destination.route::class) }
            NavigationBarItem(selected = selected, onClick = {
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
                        if (selected) destination.selectedIcon else destination.icon,
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
