package com.paradise.feature.routine.screen.category

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paradise.core.designsystem.component.icon.PtPtIconButton
import com.paradise.core.designsystem.component.topappbar.MainTopAppBar
import com.paradise.core.designsystem.theme.PtPtTheme
import com.paradise.core.model.Category
import com.paradise.core.model.Routine
import com.paradise.core.ui.RoutineItem
import com.paradise.core.ui.StepCount

@Composable
internal fun RoutineCategoryScreen(
    category: Category,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onAddClick: () -> Unit,
) {
    RoutineCategoryScreen(
        category = category,
        routines = emptyList(),
        modifier = modifier,
        onBackClick = onBackClick,
        onAddClick = onAddClick,
    )
}

@Composable
internal fun RoutineCategoryScreen(
    category: Category,
    routines: List<Routine>,
    onBackClick: () -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PtPtTheme.color.backgroundNormal),
    ) {
        MainTopAppBar(
            title = category.kor,
            leftContent = {
                PtPtIconButton(
                    imageVector = PtPtTheme.icon.back,
                    tint = PtPtTheme.color.textNormal,
                    onClick = onBackClick,
                )
            },
            rightContent = {
                PtPtIconButton(
                    imageVector = PtPtTheme.icon.addSmall,
                    onClick = onAddClick,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(PtPtTheme.shape.s)
                        .background(PtPtTheme.color.componentStrong),
                )
            },
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .padding(top = 39.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(
                items = routines,
                key = { it -> it.name },
            ) { movement ->
                RoutineItem(
                    routine = movement,
                ) {
                    StepCount(modifier = Modifier)
                }
            }
        }
    }
}

@Preview(
    name = "Category – Light",
    showBackground = true,
    backgroundColor = 0xFFF5F5F5,
)
@Preview(
    name = "Category – Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    backgroundColor = 0xFF000000,
)
@Composable
private fun RoutineCategoryScreenPreview() {
    val sampleRoutines = listOf(
        Routine("푸시업", 2),
        Routine("풀업", 3),
        Routine("버피", 4),
        Routine("스쿼트", 3),
    )

    PtPtTheme {
        RoutineCategoryScreen(
            category = Category.Full,
            routines = sampleRoutines,
            onBackClick = {},
            onAddClick = {},
            modifier = Modifier,
        )
    }
}
