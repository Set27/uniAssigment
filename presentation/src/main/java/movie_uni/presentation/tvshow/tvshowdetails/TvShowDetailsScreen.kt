package movie_uni.presentation.tvshow.tvshowdetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.mutkuensert.androidphase.Phase
import com.mutkuensert.phasecomposeextension.Execute
import movie_uni.domain.tvshow.model.TvShowCast
import movie_uni.domain.tvshow.model.TvShowDetails
import movie_uni.presentation.components.Loading
import movie_uni.presentation.core.setStatusBarAppearanceByDrawable
import movie_uni.presentation.core.showToastIfNotNull
import movie_uni.presentation.theme.appTypography

@Composable
fun TvDetailsScreen(
    viewModel: TvShowDetailsViewModel = hiltViewModel()
) {
    val tvShowDetails by viewModel.tvShowDetails.collectAsStateWithLifecycle()
    val tvShowCast by viewModel.tvShowCast.collectAsStateWithLifecycle()

    LaunchedEffect(true) { viewModel.getTvShowDetails() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 25.dp)
    ) {
        TvShowDetails(
            phase = tvShowDetails,
            loadTvCastIfSuccessful = viewModel::getTvShowCast
        )

        Spacer(modifier = Modifier.height(15.dp))

        TvShowCast(
            phase = tvShowCast,
            navigateToPersonDetails = viewModel::navigateToPersonDetails
        )
    }
}

@Composable
private fun TvShowDetails(
    phase: Phase<TvShowDetails>,
    loadTvCastIfSuccessful: () -> Unit
) {
    phase.Execute(
        onLoading = { Loading() },
        onSuccess = {
            TvDetailsItem(it)
            LaunchedEffect(Unit) { loadTvCastIfSuccessful() }
        },
        onError = { LocalContext.current.showToastIfNotNull(it.message) })
}

@Composable
private fun TvDetailsItem(tvDetails: TvShowDetails) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(bottom = 30.dp)
    ) {
        if (tvDetails.imageUrl != null) {
            Card(
                elevation = 10.dp,
                shape = RectangleShape
            ) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(tvDetails.imageUrl)
                        .allowHardware(false)
                        .crossfade(true)
                        .build(),
                    loading = {
                        Loading()
                    },
                    onSuccess = { result ->
                        context.setStatusBarAppearanceByDrawable(drawable = result.result.drawable)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    contentDescription = "Tv Poster",
                    contentScale = ContentScale.FillWidth
                )
            }
        }

        Column(modifier = Modifier.padding(horizontal = 15.dp)) {
            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = tvDetails.name,
                style = MaterialTheme.appTypography.showDetailShowTitle
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = tvDetails.voteAverage.toString(),
                style = MaterialTheme.appTypography.showDetailVoteAverage
            )

            Spacer(modifier = Modifier.height(15.dp))

            Row {
                Text(
                    text = "Seasons: ",
                    style = MaterialTheme.appTypography.showDetailSeasonInfo
                )

                Text(
                    text = tvDetails.seasonCount.toString(),
                    style = MaterialTheme.appTypography.showDetailSeasonInfo
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            Row {
                Text(
                    text = "Total Episodes: ",
                    style = MaterialTheme.appTypography.showDetailEpisodeInfo
                )

                Text(
                    text = tvDetails.totalEpisodeNumber.toString(),
                    style = MaterialTheme.appTypography.showDetailEpisodeInfo
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            Column {
                Text(text = tvDetails.overview)
            }
        }
    }
}

@Composable
private fun TvShowCast(
    phase: Phase<List<TvShowCast>>,
    navigateToPersonDetails: (personId: Int) -> Unit
) {
    phase.Execute(
        onLoading = { Loading() },
        onSuccess = {
            LazyRow {
                items(it) { item ->
                    TvShowCastItem(
                        cast = item,
                        navigateToPersonDetails = { navigateToPersonDetails(item.id) })
                }
            }
        },
        onError = { LocalContext.current.showToastIfNotNull(it.message) })
}

@Composable
private fun TvShowCastItem(cast: TvShowCast, navigateToPersonDetails: () -> Unit) {
    Row(modifier = Modifier.padding(vertical = 10.dp, horizontal = 7.dp)) {
        Card(
            elevation = 10.dp, modifier = Modifier
                .clickable(onClick = navigateToPersonDetails)
        ) {
            Column(
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Card(elevation = 10.dp) {
                    SubcomposeAsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(cast.imageUrl)
                            .crossfade(true)
                            .build(),
                        loading = {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = Color.Gray)
                            }
                        },
                        modifier = Modifier
                            .clip(RoundedCornerShape(5.dp))
                            .height(150.dp),
                        contentDescription = "Tv Show Poster"
                    )
                }

                Spacer(Modifier.height(10.dp))

                Text(
                    text = cast.name,
                    style = MaterialTheme.appTypography.showDetailCastName
                )

                Spacer(Modifier.height(10.dp))

                Text(
                    text = cast.character,
                    style = MaterialTheme.appTypography.showDetailCharacterName
                )
            }
        }
    }
}