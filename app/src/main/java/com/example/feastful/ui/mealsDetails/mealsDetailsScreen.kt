package com.example.feastful.ui.mealsDetails

import android.util.Log
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import coil.compose.AsyncImage
import com.example.model.responses.Details
import com.example.model.responses.MealsResponse

class MealsDetailsScreen(private val detail: MealsResponse) : Screen {
    @Composable
    override fun Content() {
        val viewModel: DetailsScreenViewModel = viewModel()
        viewModel.getInstruction(detail.id.toInt())
        Surface(color = Color(0xFFDAF5FF)) {
            MealsDetails(detail, viewModel)
        }
    }
}

@Composable
fun MealsDetails(detail: MealsResponse, viewModel: DetailsScreenViewModel) {
    var animationState by remember { mutableStateOf(MealsAnimations.Normal) }
    val stateTransition = updateTransition(targetState = animationState, label = "")
    val picSize by stateTransition.animateDp(targetValueByState = { it.size }, label = "")
    val instruction = if (viewModel.instruction.value.isEmpty()) {
        ""
    } else {
        viewModel.instruction.value[0].strInstructions
    }
    Surface(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxWidth()
                .background(Color(0xFFDAF5FF))
        ) {
            Card(
                elevation = 2.dp, modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(Modifier.background(Color(0xFF44FFD2))) {
                    Card(
                        shape = CircleShape,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        AsyncImage(
                            model = detail.imageUrl,
                            contentDescription = "Meal Image",
                            modifier = Modifier
                                .size(picSize)
                                .padding(1.dp)
                                .clickable {
                                    animationState = if (animationState == MealsAnimations.Normal)
                                        MealsAnimations.Expanded
                                    else
                                        MealsAnimations.Normal
                                }
                        )
                    }
                    Text(
                        text = detail.name,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                            .padding(4.dp)
                            .align(Alignment.CenterVertically),
                        color = Color(0xFF000000)
                    )

                }
            }
            Box(Modifier.padding(4.dp)) {

                Text(
                    text = buildAnnotatedString {
                        for (i in instruction){
                            append(i)
                            if('\n' == i){
                                append("\n")
                            }
                        }
                    },
                    color = Color(0xFF13279d)
                )
            }
        }
    }
}

enum class MealsAnimations(val size: Dp) {
    Normal(100.dp),
    Expanded(250.dp)
}