package com.blazek10.racelog.ui.athletelist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.blazek10.racelog.R

@Composable
fun AthleteListScreen(
    modifier: Modifier = Modifier,
    athleteListViewModel: AthleteListViewModel = viewModel()
) {
    val athleteListUiState by athleteListViewModel.uiState.collectAsState()
    val athletes = listOf(
        "John Doe", "Jane Smith", "Mary Johnson", "James Williams",
        "Patricia Brown", "Robert Jones", "Linda Garcia", "Michael Miller",
        "Elizabeth Davis", "David Martinez", "Barbara Rodriguez", "Charles Wilson",
        "Thomas Moore", "Christopher Taylor", "Daniel Anderson Anderson Anderson", "Paul Thomas"
    )
    Surface(
        modifier,
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.padding(top=16.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ButtonWithIcon(
                    icon = Icons.Filled.ArrowBack,
                    onClick = { /*TODO*/ })
                SearchBar(
                    athleteListUiState.searchText,
                    onSearchTextChange = { athleteListViewModel.updateSearchText(it) })
            }
            Spacer(modifier = Modifier.height(16.dp))
            AthleteList(athletes, athleteListUiState.searchText)
        }
    }
}

@Composable
fun SearchBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit
) {
    TextField(
        value = searchText,
        onValueChange = onSearchTextChange,
        label = { Text(stringResource(id = R.string.search_athlete))},
        leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null)},
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        trailingIcon = {
            if (searchText.isNotEmpty())
                ButtonWithIcon(
                    icon= Icons.Filled.Clear,
                    onClick = { onSearchTextChange("") }) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 16.dp),
    )
}

@Composable
fun ButtonWithIcon(icon: ImageVector, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.height(24.dp)
    ) {
        Icon(icon, contentDescription = null)
    }
}

@Composable
fun AthleteList(
    athletes: List<String>,
    searchText: String
) {
    val filteredAthletes = if (searchText.isEmpty())
        athletes else
            athletes.filter { it.contains(searchText, ignoreCase = true) }

    LazyColumn (
//        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        items(filteredAthletes) {athlete ->
            AthleteItem(athlete, athletes.indexOf(athlete) + 1)
        }
    }
}

@Composable
fun AthleteItem(
    athlete: String,
    index: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$index.",
                modifier = Modifier.padding(end = 8.dp),
            )
            Text(
                text = "#34",
                modifier = Modifier.padding(end = 8.dp),
            )
            Text(
                text = athlete,
                modifier = Modifier.weight(1f),
            )
            Button(onClick = { /*TODO*/ }) {
                Text(text = stringResource(id = R.string.view))
            }
        }

    }
}