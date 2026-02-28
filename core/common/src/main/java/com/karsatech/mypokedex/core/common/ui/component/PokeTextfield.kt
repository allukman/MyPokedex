package com.karsatech.mypokedex.core.common.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.karsatech.mypokedex.core.common.R
import com.karsatech.mypokedex.core.common.ui.theme.AppTheme.typography
import com.karsatech.mypokedex.core.common.ui.theme.Dimens.Dp8

@Composable
fun PokeTextfield(
    label: String,
    placeholder: String,
    isPassword: Boolean = false,
    value: String,
    onValueChange: (String) -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dp8)
    ) {
        Text(
            text = label,
            style = typography.bodyBold3,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(Dp8))

        TextField(
            value = value,
            onValueChange = onValueChange,
            maxLines = 1,
            placeholder = { Text(placeholder, style = typography.body3, color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),

            visualTransformation =
                if (isPassword && !passwordVisible)
                    PasswordVisualTransformation()
                else
                    VisualTransformation.None,

            trailingIcon = {
                if (isPassword) {
                    IconButton(
                        onClick = { passwordVisible = !passwordVisible }
                    ) {
                        Icon(
                            painter = painterResource(
                                id = if (passwordVisible)
                                    R.drawable.ic_eye_off
                                else
                                    R.drawable.ic_eye
                            ),
                            contentDescription =
                                if (passwordVisible) "Hide password" else "Show password"
                        )
                    }
                }
            },

            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }
}
//@Composable
//fun PokeTextfield(
//    label: String,
//    placeholder: String,
//    isPassword: Boolean = false,
//    value: String,
//    onValueChange: (String) -> Unit
//) {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = Dp8)
//    ) {
//        Text(
//            text = label,
//            style = typography.bodyBold3,
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(Dp8))
//        TextField(
//            value = value,
//            onValueChange = onValueChange,
//            placeholder = { Text(placeholder, color = Color.Gray) },
//            modifier = Modifier.fillMaxWidth(),
//            shape = RoundedCornerShape(12.dp),
//            visualTransformation = if (isPassword)
//                PasswordVisualTransformation()
//            else
//                VisualTransformation.None,
//            trailingIcon = {
//                if (isPassword) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.ic_eye),
//                        contentDescription = null
//                    )
//                }
//            },
//            colors = TextFieldDefaults.colors(
//                focusedIndicatorColor = Color.Transparent,
//                unfocusedIndicatorColor = Color.Transparent,
//                disabledIndicatorColor = Color.Transparent
//            )
//        )
//    }
//}