package com.mindera.rocketscience.spacex_presentation.screen.home

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.mindera.rocketscience.core.R
import com.mindera.rocketscience.core_ui.ui.LocalSpacing
import com.mindera.rocketscience.core_ui.ui.navigation.Route
import com.mindera.rocketscience.core_ui.ui.ui.theme.MyApplicationTheme
import com.mindera.rocketscience.spacex_domain.model.SpaceXLaunchItem
import com.mindera.rocketscience.spacex_presentation.components.FilterDialog
import com.mindera.rocketscience.spacex_presentation.components.HeaderText
import com.mindera.rocketscience.spacex_presentation.components.LoadImage
import com.mindera.rocketscience.spacex_presentation.components.ScrollToTopButton
import com.mindera.rocketscience.spacex_presentation.components.SpaceXTopAppBar
import com.mindera.rocketscience.util.UiText
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch


@Composable
fun SpaceXHomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onItemClicked: (spaceXLaunchItem: SpaceXLaunchItem) -> Unit,
    onNavigateUp: () -> Unit = {}
) {
    viewModel.getCompanyInfo()
    viewModel.fetchAllSpaceXLaunchItems()

    val snackHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lazyPagingItems by viewModel.allLaunches.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        allLaunchesLazyPagingItems = lazyPagingItems.collectAsLazyPagingItems(),
        onItemClicked = onItemClicked,
        snackBarHostState = snackHostState,
        onNavigateUp = onNavigateUp,
        onFilterEventClicked = { year: Int?, success: Boolean ->
            viewModel.onEvent(
                HomeScreenEvent.FilterSpaceXItems(
                    year = year,
                    onlySuccessfulLaunch = success,
                )
            )
        },
        persistScrollPosition = {
            viewModel.persistScrollPosition(it)
        },
        scrollPosition = viewModel.scrollPosition
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeScreenUiState,
    allLaunchesLazyPagingItems: LazyPagingItems<SpaceXLaunchItem>,
    onItemClicked: (spaceXLaunchItem: SpaceXLaunchItem) -> Unit,
    snackBarHostState: SnackbarHostState,
    onNavigateUp: () -> Unit,
    onFilterEventClicked: (year: Int?, successful: Boolean) -> Unit,
    persistScrollPosition: (Int) -> Unit,
    scrollPosition: Int
) {
    val scrollBarBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var isFilterDialogOpen by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        modifier = Modifier.nestedScroll(scrollBarBehavior.nestedScrollConnection),
        topBar = {
            SpaceXTopAppBar(
                currentRoute = Route.Home,
                scrollBehavior = scrollBarBehavior,
                onNavigateUp = onNavigateUp
            ) {
                isFilterDialogOpen = !isFilterDialogOpen
            }
        }
    ) { paddingValues ->

        SpaceXSnackBar(uiState, snackBarHostState, LocalContext.current)

        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(
                    horizontal = LocalSpacing.current.spaceMedium,
                    vertical = LocalSpacing.current.spaceMedium
                ),
        ) {
            val companyDetails = String.format(
                stringResource(R.string.company_info),
                uiState.companyName,
                uiState.founderName,
                uiState.founded,
                uiState.employees,
                uiState.launchSites,
                uiState.valuation
            )
            HomeScreenHeader(companyInfoDetails = companyDetails)
            HomeScreenContent(
                spaceXLaunchesLazyItems = allLaunchesLazyPagingItems,
                onItemClicked = onItemClicked,
                persistScrollPosition = persistScrollPosition,
                scrollPosition = scrollPosition
            )
        }

        FilterDialog(
            isOpen = isFilterDialogOpen, onClose = {
                isFilterDialogOpen = false
            },
            onApplyFilters = onFilterEventClicked
        )
    }


}

@Composable
fun HomeScreenHeader(
    companyInfoDetails: String
) {
    val localSpacing = LocalSpacing.current
    HeaderText(text = stringResource(R.string.company))
    Spacer(modifier = Modifier.height(localSpacing.spaceSmall))
    Text(text = companyInfoDetails, textAlign = TextAlign.Start)
    Spacer(modifier = Modifier.height(localSpacing.spaceSmall))
    HeaderText(text = stringResource(R.string.launches))
    Spacer(modifier = Modifier.height(localSpacing.spaceSmall))
}

@OptIn(FlowPreview::class)
@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    spaceXLaunchesLazyItems: LazyPagingItems<SpaceXLaunchItem>,
    onItemClicked: (SpaceXLaunchItem) -> Unit,
    persistScrollPosition: (Int) -> Unit,
    scrollPosition: Int,
) {
    val lazyListState = rememberLazyListState(
        initialFirstVisibleItemIndex = scrollPosition
    )
    val coroutineScope = rememberCoroutineScope()

    val showButton by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex > 0
        }
    }

    LaunchedEffect(lazyListState) {
        snapshotFlow {
            lazyListState.firstVisibleItemIndex
        }
            .debounce(500L)
            .collectLatest { index ->
                persistScrollPosition(index)
            }
    }

    Box(modifier = modifier.fillMaxWidth()) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.spaceSmall),
            state = lazyListState,
        ) {

            items(
                count = spaceXLaunchesLazyItems.itemCount,
                key = spaceXLaunchesLazyItems.itemKey(SpaceXLaunchItem::id),
                contentType = spaceXLaunchesLazyItems.itemContentType { SpaceXLaunchItem::class }
            ) { index: Int ->
                val spaceXLaunchItem: SpaceXLaunchItem =
                    spaceXLaunchesLazyItems[index] ?: return@items
                LaunchItem(spaceXLaunchItem = spaceXLaunchItem, onItemClicked = onItemClicked)
            }

        }

        if (showButton) {
            ScrollToTopButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(LocalSpacing.current.spaceMedium)
            ) {
                coroutineScope.launch {
                    lazyListState.animateScrollToItem(index = 0)
                }
            }
        }
    }
}


@Composable
fun LaunchItem(
    modifier: Modifier = Modifier,
    spaceXLaunchItem: SpaceXLaunchItem,
    onItemClicked: (SpaceXLaunchItem) -> Unit
) {
    Row(
        modifier = modifier
            .padding(LocalSpacing.current.spaceSmall)
            .fillMaxWidth()
            .clickable { onItemClicked(spaceXLaunchItem) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        LoadImage(
            imageLink = spaceXLaunchItem.imageLink,
            size = LocalSpacing.current.space100
        )
        Spacer(modifier = Modifier.width(LocalSpacing.current.spaceExtraSmall))
        Column(modifier = Modifier.weight(2f)) {
            Text(
                text = String.format(
                    stringResource(R.string.mission),
                    spaceXLaunchItem.missionName
                )
            )
            Text(
                text = String.format(
                    stringResource(R.string.date_time), spaceXLaunchItem.date.asString(
                        LocalContext.current
                    ), spaceXLaunchItem.time.asString(LocalContext.current)
                )
            )
            Text(text = String.format(stringResource(R.string.Rocket), spaceXLaunchItem.rocket))
            Text(
                text = String.format(
                    stringResource(R.string.days_since_launch),
                    spaceXLaunchItem.daysSinceLaunch
                )
            )
        }
        Checkbox(
            checked = spaceXLaunchItem.success,
            onCheckedChange = {}, // this is empty because we don't need it to be checked
        )
    }
}

@Composable
private fun SpaceXSnackBar(
    uiState: HomeScreenUiState,
    snackHostState: SnackbarHostState,
    context: Context
) {
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(uiState) {
        snapshotFlow {
            uiState.error
        }.distinctUntilChanged()
            .collectLatest {
                uiState.error?.asString(
                    context
                )?.let {
                    coroutineScope.launch {
                        snackHostState.showSnackbar(
                            message = it
                        )
                    }
                }
            }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MyApplicationTheme {
        LaunchItem(
            spaceXLaunchItem = SpaceXLaunchItem(
                id = 3968,
                missionName = "Imogene York",
                date = UiText.StringResource(R.string.unable_to_get_time),
                rocket = "autem",
                imageLink = "laoreet",
                success = false,
                daysSinceLaunch = 6578,
                wikipediaLink = "gravida",
                articleLink = "ridens",
                time = UiText.StringResource(R.string.unable_to_get_time),
                year = "quaestio",
            ),
            onItemClicked = {},
        )
    }
}
