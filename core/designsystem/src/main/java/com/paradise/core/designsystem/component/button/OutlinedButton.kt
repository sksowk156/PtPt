package com.paradise.core.designsystem.component.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paradise.core.designsystem.component.button.base.BaseButton
import com.paradise.core.designsystem.component.button.base.BaseButton.IconConfig
import com.paradise.core.designsystem.component.button.base.BaseButton.Size
import com.paradise.core.designsystem.theme.PtPtTheme

@Composable
fun OutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Size = Size.Large,
    enabled: Boolean = true,
    isSelected: Boolean = false,
    iconConfig: IconConfig = IconConfig.None,
    icon: (@Composable () -> Unit)? = { Icon(PtPtTheme.icon.none, contentDescription = "none") },
) {
    BaseButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        style = BaseButton.OutlineStyle,
        size = size,
        enabled = enabled,
        isSelected = isSelected,
        iconConfig = iconConfig,
        icon = icon,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Preview(showBackground = true, backgroundColor = 0xFFF0F0F0)
@Composable
private fun OutlinedButtonPreview() {
    PtPtTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Text(
                "icon",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(bottom = 8.dp),
            )

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                // 1-1. 아이콘 없는 기본 버튼
                OutlinedButton(
                    text = "Label",
                    onClick = { /* */ },
                )

                // 1-2. 아이콘이 앞에 오는 버튼
                OutlinedButton(
                    text = "Label",
                    onClick = { /* */ },
                    iconConfig = IconConfig.Start,
                )

                // 1-3. 아이콘이 뒤에 오는 버튼
                OutlinedButton(
                    text = "Label",
                    onClick = { /* */ },
                    iconConfig = IconConfig.End,
                )

                // 1-4. 아이콘만 있는 빈 텍스트 없이 버튼 (text="" 로 처리)
                OutlinedButton(
                    text = "Label",
                    onClick = { /* */ },
                    iconConfig = IconConfig.Both,
                )
            }

            // 2. Size 섹션
            Text(
                "size",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(bottom = 8.dp),
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                OutlinedButton(
                    text = "Large",
                    onClick = { /* */ },
                    size = Size.Large,
                )
                OutlinedButton(
                    text = "Medium",
                    onClick = { /* */ },
                    size = Size.Medium,
                )
                OutlinedButton(
                    text = "Small",
                    onClick = { /* */ },
                    size = Size.Small,
                )
            }

            // 3. State 섹션
            Text(
                "state",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(bottom = 8.dp),
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                // 3-1. 활성 상태: 기본 색
                OutlinedButton(
                    text = "Active",
                    onClick = { /* */ },
                )
                // 3-2. 활성 상태: 다크 그린 배경 (임시로 래핑하지 않으므로 기본 색 사용)
                // 실제로 컬러를 직접 지정하는 API가 없으므로,
                // 배경 색을 바꾸고 싶다면 CustomizableButton.colors(...) 함수를 수정해야 합니다.
                // 여기서는 예시로 두 번째 버튼을 Disabled 상태와 구별하기 위해 enabled=true 그대로 둡니다.
                OutlinedButton(
                    text = "Dark Green",
                    onClick = { /* */ },
                )
                // 3-3. 비활성화 상태
                OutlinedButton(
                    text = "Disabled",
                    onClick = { /* */ },
                    enabled = false,
                )
            }
        }
    }
}
