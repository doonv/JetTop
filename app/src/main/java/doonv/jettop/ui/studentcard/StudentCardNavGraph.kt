package doonv.jettop.ui.studentcard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
    eventsVm: DisciplineEventsViewModel,
    ongoingGradesVm: OngoingGradesViewModel,
    pupilCardSettingsVm: PupilCardSettingsViewModel
) {
    val onBack: () -> Unit = { navController.popBackStack() }
    navigation<StudentCardGraph>(startDestination = StudentCardRoute.Menu) {
        composable<StudentCardRoute.Menu> {
            StudentCardScreen(
                modifier = modifier,
                loginData = loginData,
                onNavigate = { route -> navController.navigate(route) },
            )
        }
        composable<StudentCardRoute.DisciplineEvents> {
            DisciplineEventsScreen(
                modifier = modifier,
                vm = eventsVm,
                onBack = onBack,
            )
        }

        composable<StudentCardRoute.NonLessonEvents> { }
        composable<StudentCardRoute.Accommodations> { }
        composable<StudentCardRoute.PrivateLessons> { }
        composable<StudentCardRoute.SubmissionAndExamGrades> { }
        composable<StudentCardRoute.OngoingGrades> {
            OngoingGradesScreen(
                modifier = modifier,
                vm = ongoingGradesVm,
                settingsVm = pupilCardSettingsVm,
                onBack = onBack
            )
        }
    }
}


@Serializable
data object StudentCardGraph

sealed interface StudentCardRoute {
    @Serializable
    data object Menu : StudentCardRoute

    @Serializable
    data object DisciplineEvents : StudentCardRoute

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
