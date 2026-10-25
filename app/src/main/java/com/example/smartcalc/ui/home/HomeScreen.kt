package com.example.smartcalc.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartcalc.R
import com.example.smartcalc.model.CalculatorType
import com.example.smartcalc.ui.components.CalculatorCard
import com.example.smartcalc.ui.theme.SmartCalcTheme
import com.example.smartcalc.ui.theme.spacing

/**
 * Stateful Home Screen composable that wires ViewModel to UI state.
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(),
    onCalculatorClick: (CalculatorType) -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.userMessage) {
        uiState.userMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.onUserMessageShown()
        }
    }

    HomeScreenContent(
        uiState = uiState,
        onCalculatorClick = { type ->
            viewModel.onCalculatorSelected(type)
            onCalculatorClick(type)
        },
        onSettingsClick = {
            viewModel.onSettingsClicked()
            onSettingsClick()
        },
        snackbarHostState = snackbarHostState,
        modifier = modifier
    )
}

/**
 * Stateless Home Screen Content composable for rendering UI and ease of previewing.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    uiState: HomeUiState,
    onCalculatorClick: (CalculatorType) -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = stringResource(R.string.app_name),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = stringResource(R.string.app_subtitle),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = stringResource(R.string.settings_content_description),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 140.dp),
            contentPadding = PaddingValues(
                horizontal = MaterialTheme.spacing.screenHorizontalPadding,
                vertical = MaterialTheme.spacing.standard
            ),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.standard),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.standard),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    text = stringResource(R.string.section_calculators),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = MaterialTheme.spacing.extraSmall)
                )
            }

            items(
                items = uiState.calculators,
                key = { it.type.name }
            ) { item ->
                val title = stringResource(item.titleResId)
                val description = stringResource(item.descriptionResId)

                CalculatorCard(
                    title = title,
                    description = description,
                    icon = item.icon,
                    iconColor = item.iconColor,
                    onClick = { onCalculatorClick(item.type) },
                    contentDescription = "$title, $description"
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Light Theme - Phone")
@Composable
fun HomeScreenPreviewLight() {
    SmartCalcTheme(darkTheme = false) {
        HomeScreenContent(
            uiState = HomeUiState(
                calculators = CalculatorType.entries.map {
                    CalculatorCardItem(it, it.titleResId, it.descriptionResId, it.icon, it.iconColor)
                }
            ),
            onCalculatorClick = {},
            onSettingsClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Dark Theme - Phone")
@Composable
fun HomeScreenPreviewDark() {
    SmartCalcTheme(darkTheme = true) {
        HomeScreenContent(
            uiState = HomeUiState(
                calculators = CalculatorType.entries.map {
                    CalculatorCardItem(it, it.titleResId, it.descriptionResId, it.icon, it.iconColor)
                }
            ),
            onCalculatorClick = {},
            onSettingsClick = {}
        )
    }
}
