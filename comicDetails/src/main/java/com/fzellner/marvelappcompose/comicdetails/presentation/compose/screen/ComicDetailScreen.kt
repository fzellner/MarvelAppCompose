package com.fzellner.marvelappcompose.comicdetails.presentation.compose.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.fzellner.marvelappcompose.comicdetails.presentation.ComicDetailViewModel
import com.fzellner.marvelappcompose.comicdetails.presentation.model.ComicDetailUiModel
import com.fzellner.marvelappcompose.comicdetails.presentation.state.ComicDetailViewState
import com.fzellner.marvelappcompose.designtoken.MarvelAppTheme
import com.fzellner.marvelappcompose.designtoken.colors.MarvelAppColor
import com.fzellner.marvelappcompose.designtoken.colors.MarvelAppGradients
import com.fzellner.marvelappcompose.designtoken.components.GradientPriceIndicatorComponent
import com.fzellner.marvelappcompose.designtoken.components.LoadingComponent
import com.fzellner.marvelappcompose.designtoken.components.LoadingComponentType


@Composable
fun ComicDetailScreen() {
    MarvelAppTheme {
        val comicDetailViewModel: ComicDetailViewModel = hiltViewModel()
        val viewState = comicDetailViewModel.viewState.collectAsState().value

        Scaffold {
            when (viewState) {
                is ComicDetailViewState.Error -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = viewState.message)
                }

                ComicDetailViewState.Loading -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingComponent(
                        LoadingComponentType.CIRCULAR
                    )
                }

                is ComicDetailViewState.ShowDetails -> ShowComicDetails(
                    it,
                    viewState.comicDetailUiModel
                )
            }

        }

    }

}

@Composable
private fun ShowComicDetails(
    paddingValues: PaddingValues,
    comicDetailUiModel: ComicDetailUiModel
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        val (details, price) = createRefs()

        AsyncImage(
            model = comicDetailUiModel.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .drawWithCache {
                    val gradient = Brush.verticalGradient(
                        colors = MarvelAppGradients.gradientDark,
                        startY = 2f,
                        endY = size.height
                    )
                    onDrawWithContent {
                        drawContent()
                        drawRect(gradient, blendMode = BlendMode.Multiply)
                    }
                }
        )

        GradientPriceIndicatorComponent(
            price = comicDetailUiModel.price,
            colors = MarvelAppGradients.gradientRed,
            modifier = Modifier.constrainAs(price) {
                top.linkTo(parent.top, margin = 24.dp)
                end.linkTo(parent.end, margin = 24.dp)
                width = Dimension.wrapContent
                height = Dimension.wrapContent
            })

        Column(
            modifier = Modifier
                .constrainAs(details) {
                    bottom.linkTo(parent.bottom, margin = 24.dp)
                    linkTo(
                        parent.start,
                        parent.end,
                        startMargin = 24.dp,
                        endMargin = 24.dp,
                        bias = 0f
                    )
                    width = Dimension.preferredWrapContent

                },
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp),


            ) {
            Text(
                text = "#${comicDetailUiModel.id}",
                color = MarvelAppColor.primaryWhite,
                fontSize = 19.sp
            )
            Text(
                text = comicDetailUiModel.publishDate,
                color = MarvelAppColor.primaryWhite,
                fontSize = 19.sp
            )
            Text(
                text = comicDetailUiModel.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = MarvelAppColor.primaryWhite,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = comicDetailUiModel.description, color = MarvelAppColor.primaryWhite)
            Text(
                text = "Written by: ${comicDetailUiModel.authors}",
                color = MarvelAppColor.primaryWhite
            )
        }

    }
}
