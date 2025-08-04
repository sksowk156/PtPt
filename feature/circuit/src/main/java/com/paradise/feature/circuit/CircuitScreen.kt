package com.paradise.feature.circuit

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paradise.core.designsystem.component.icon.PtPtIconButton
import com.paradise.core.designsystem.component.topappbar.MainTopAppBar
import com.paradise.core.designsystem.theme.PtPtTheme
import com.paradise.core.model.Category
import com.paradise.feature.circuit.component.CategoryCard

@Composable
internal fun CircuitRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onCategoryClick: (Category) -> Unit,
) {
    CircuitScreen(
        onBackClick = onBackClick,
        onCategoryClick = onCategoryClick,
        modifier = modifier,
    )
}

@Composable
internal fun CircuitScreen(
    onBackClick: () -> Unit,
    onCategoryClick: (Category) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PtPtTheme.color.backgroundNormal),
    ) {
        MainTopAppBar(
            title = "무산소 운동",
            leftContent = {
                PtPtIconButton(
                    imageVector = PtPtTheme.icon.back,
                    tint = PtPtTheme.color.textNormal,
                    onClick = onBackClick,
                )
            },
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                start = 20.dp,
                end = 20.dp,
                top = 40.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(
                items = Category.entries,
                key = { category -> category.name },
            ) { category ->
                CategoryCard(
                    category = category,
                    onClick = onCategoryClick,
                )
            }
        }
    }
}

@Preview(
    name = "Circuit – Light",
    showBackground = true,
    backgroundColor = 0xFFF5F5F5,
)
@Preview(
    name = "Circuit – Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF000000,
)
@Composable
private fun CircuitScreenPreview() {
    PtPtTheme {
        CircuitScreen(
            onBackClick = {},
            onCategoryClick = {},
        )
    }
}
