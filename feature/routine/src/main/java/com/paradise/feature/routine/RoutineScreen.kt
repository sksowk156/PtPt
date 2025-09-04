package com.paradise.feature.routine

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paradise.core.designsystem.component.icon.PtPtIconButton
import com.paradise.core.designsystem.component.topappbar.MainTopAppBar
import com.paradise.core.designsystem.theme.PtPtTheme
import com.paradise.core.model.Category

@Composable
internal fun RoutineRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onCategoryClick: (Category) -> Unit,
) {
    RoutineScreen(
        onBackClick = onBackClick,
        onCategoryClick = onCategoryClick,
        modifier = modifier,
    )
}

@Composable
internal fun RoutineScreen(
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

@Composable
internal fun CategoryCard(
    category: Category,
    onClick: (Category) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .height(200.dp)
            .fillMaxWidth(),
        onClick = {
            onClick(category)
        },
        colors = CardDefaults.cardColors(
            containerColor = PtPtTheme.color.componentNormal,
        ),
        shape = PtPtTheme.shape.l,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(
                text = category.kor,
                style = PtPtTheme.typography.title01,
                color = PtPtTheme.color.textNormal,
            )
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
private fun RoutineScreenPreview() {
    PtPtTheme {
        RoutineScreen(
            onBackClick = {},
            onCategoryClick = {},
        )
    }
}
