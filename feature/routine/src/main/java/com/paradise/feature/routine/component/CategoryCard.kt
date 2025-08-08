package com.paradise.feature.routine.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paradise.core.designsystem.theme.PtPtTheme
import com.paradise.core.model.Category

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
