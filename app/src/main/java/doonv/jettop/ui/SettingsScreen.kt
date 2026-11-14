package doonv.jettop.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import doonv.jettop.R
import doonv.jettop.data.DateType
import doonv.jettop.data.Settings
import doonv.jettop.ui.theme.Symbols

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SettingsScreen(vm: SettingsViewModel, onBack: () -> Unit, modifier: Modifier = Modifier) {
    val settings by vm.settings.collectAsStateWithLifecycle(
        initialValue = null,
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Symbols.Outlined.ArrowBack24,
                            contentDescription = stringResource(R.string.back),
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            val current = settings
            if (current == null) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    LoadingIndicator()
                }
            } else {
                SettingsList(
                    settings = current,
                    vm = vm,
                )
            }
        }
    }
}

@Composable
fun SettingsList(settings: Settings, vm: SettingsViewModel) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        SwitchSetting(
            stringResource(R.string.use_system_font),
            settings.useSystemFont,
            { enabled -> vm.updateSettings { it.copy(useSystemFont = enabled) } },
            description = stringResource(R.string.use_system_font_description)
        )
        ToggleGroupSetting(
            stringResource(R.string.dates),
            DateType.entries,
            settings.dateType,
            { value -> vm.updateSettings { it.copy(dateType = value) } },
            { stringResource(it.resource) }
        )
    }
}

@Composable
fun SwitchSetting(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    description: String = "",
) {
    ListItem(
        supportingContent = if (description.isNotEmpty()) {
            { Text(description) }
        } else {
            null
        },
        trailingContent = {
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                modifier = Modifier.clearAndSetSemantics {},
            )
        },
        modifier = modifier.toggleable(
            value = checked,
            role = Role.Switch,
            onValueChange = onCheckedChange,
        ),
    ) {
        Text(title)
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun <T> ToggleGroupSetting(
    title: String,
    options: List<T>,
    selected: T,
    onSelected: (T) -> Unit,
    optionLabel: @Composable (T) -> String,
    modifier: Modifier = Modifier,
    description: String = "",
) {
    ListItem(
        supportingContent = if (description.isNotEmpty()) {
            { Text(description) }
        } else {
            null
        },
        trailingContent = {
            Row(
                modifier = Modifier.selectableGroup(),
                horizontalArrangement = Arrangement.spacedBy(
                    ButtonGroupDefaults.ConnectedSpaceBetween
                ),
            ) {
                options.forEachIndexed { index, option ->
                    ToggleButton(
                        checked = option == selected,
                        onCheckedChange = { onSelected(option) },
                        shapes = when (index) {
                            0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                            options.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                            else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                        },
                        modifier = Modifier.semantics { role = Role.RadioButton },
                    ) {
                        Text(optionLabel(option))
                    }
                }
            }
        },
        modifier = modifier,
    ) {
        Text(title)
    }
}
