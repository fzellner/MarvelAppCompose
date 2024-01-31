package com.fzellner.marvelappcompose.designtoken.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fzellner.marvelappcompose.designtoken.colors.MarvelAppColor


@Composable
fun LoadingComponent(loadingComponentType: LoadingComponentType = LoadingComponentType.LINEAR) {

    when (loadingComponentType) {
        LoadingComponentType.LINEAR -> LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            strokeCap = StrokeCap.Butt,
            trackColor = MarvelAppColor.primaryBlack,
            color = MarvelAppColor.primaryRed
        )

        LoadingComponentType.CIRCULAR -> CircularProgressIndicator(
            modifier = Modifier
                .size(120.dp)
                .padding(20.dp),
            strokeCap = StrokeCap.Butt,
            trackColor = MarvelAppColor.primaryBlack,
            color = MarvelAppColor.primaryRed
        )
    }


}

@Preview
@Composable
fun LoadingComponentPreview() {
    LoadingComponent(LoadingComponentType.CIRCULAR)
}

enum class LoadingComponentType {
    LINEAR,
    CIRCULAR
}