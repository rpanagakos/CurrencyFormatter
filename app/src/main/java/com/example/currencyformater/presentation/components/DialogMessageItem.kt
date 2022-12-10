package com.example.currencyformater.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.currencyformater.R
import com.example.currencyformater.theme.LocalTheme
import kotlinx.coroutines.NonDisposableHandle.parent

@Composable
fun DialogMessageItem(
    modifier: Modifier = Modifier,
    message: String,
    onDismissRequest: () -> Unit
) {

    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Surface(
            modifier = modifier,
            color = LocalTheme.colors.White,
            shape = RoundedCornerShape(LocalTheme.radius.radius_8dp),
            elevation = 8.dp
        ) {
            ConstraintLayout(
                modifier = modifier
                    .wrapContentSize()
            ) {
                val (buttonRef, messageRef) = createRefs()
                IconButton(
                    modifier = Modifier
                        .size(20.dp)
                        .constrainAs(buttonRef) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                        },
                    onClick = onDismissRequest
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_baseline_close_24), contentDescription = null)
                }
                Text(
                    modifier = Modifier
                        .padding(start = LocalTheme.spacing.padding_8dp, bottom = LocalTheme.spacing.padding_8dp, end = LocalTheme.spacing.padding_8dp)
                        .constrainAs(messageRef) {
                            top.linkTo(buttonRef.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    text = message,
                    style = LocalTheme.typography.REGULAR_16_MONT,
                    color = LocalTheme.colors.Black
                )
            }
        }
    }
}