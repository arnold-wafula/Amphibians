package com.lemonboy.amphibians.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.lemonboy.amphibians.R
import com.lemonboy.amphibians.model.Amphibian
import com.lemonboy.amphibians.ui.theme.AmphibiansTheme

@Composable
fun HomeScreen(
    amphibianUiState: AmphibianUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when(amphibianUiState) {
        is AmphibianUiState.Loading ->
            LoadingScreen(modifier = modifier.fillMaxSize())

        is AmphibianUiState.Success ->
            AmphibianListScreen(amphibianUiState.amphibians, modifier)

        else ->
            ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun AmphibianCard(amphibian: Amphibian, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = modifier) {
            Text(
                text = amphibian.name + (amphibian.type),
                style = MaterialTheme.typography.headlineSmall,
                //modifier = modifier.fillMaxWidth()
            )

            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(amphibian.imgSrc)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(R.string.amphibian_photo),
                //modifier = modifier.fillMaxWidth()
            )

            Text(
                text = amphibian.description,
                style = MaterialTheme.typography.bodySmall,
                //modifier = modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun AmphibianListScreen(amphibians: List<Amphibian>, modifier: Modifier = Modifier) {
    LazyColumn(
        contentPadding = PaddingValues(4.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        items(items = amphibians) { amphibian ->
            AmphibianCard(
                amphibian,
                modifier = modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .aspectRatio(1.5f)
            )
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_connection_error),
            contentDescription = null
        )
        Text(
            text = stringResource(R.string.loading_failed),
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = retryAction) {
            Text(text = stringResource(R.string.retry))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    AmphibiansTheme {
        ErrorScreen(retryAction = {})
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    AmphibiansTheme {
        LoadingScreen()
    }
}


@Preview(showBackground = true)
@Composable
fun AmphibiansListScreen() {
    AmphibiansTheme {
        //val mockData = List(5) { Amphibian("", "", "", "")}
        AmphibiansListScreen()
    }
}