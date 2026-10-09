package doonv.jettop.ui.studentcard

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import doonv.jettop.data.LoginData
import kotlinx.serialization.Serializable

fun NavGraphBuilder.studentCardGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    loginData: LoginData,
) {
    navigation<StudentCardGraph>(startDestination = StudentCardRoute.Menu) {
        composable<StudentCardRoute.Menu> {
            StudentCardScreen(
                modifier = modifier,
                loginData = loginData,
                onNavigate = { route -> navController.navigate(route) },
            )
        }
        composable<StudentCardRoute.ClassEvents> {
            val vm: ClassEventsViewModel = viewModel()
            LaunchedEffect(Unit) { vm.load() }
            ClassEventsScreen(
                modifier = modifier,
                vm = vm,
                onBack = { navController.popBackStack() },
            )
        }

        composable<StudentCardRoute.NonLessonEvents> { }
        composable<StudentCardRoute.Accommodations> { }
        composable<StudentCardRoute.PrivateLessons> { }
        composable<StudentCardRoute.SubmissionAndExamGrades> { }
        composable<StudentCardRoute.OngoingGrades> { }
    }
}


@Serializable
data object StudentCardGraph

sealed interface StudentCardRoute {
    @Serializable
    data object Menu : StudentCardRoute

    @Serializable
    data object ClassEvents : StudentCardRoute

    @Serializable
    data object NonLessonEvents : StudentCardRoute

    @Serializable
    data object Accommodations : StudentCardRoute

    @Serializable
    data object PrivateLessons : StudentCardRoute

    @Serializable
    data object SubmissionAndExamGrades : StudentCardRoute

    @Serializable
    data object OngoingGrades : StudentCardRoute
}
