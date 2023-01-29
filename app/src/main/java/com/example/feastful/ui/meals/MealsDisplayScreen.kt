package com.example.feastful.ui.meals

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.example.feastful.R
import com.example.feastful.ui.mealsDetails.MealsDetailsScreen
import com.example.model.responses.MealsResponse
import java.util.*

object MealsDisplayScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: MealsCategoriesViewModel = viewModel()
        val navigator = LocalNavigator.currentOrThrow
        val responseMeals = viewModel.meals.value
        val searchText = remember {
            mutableStateOf("")
        }
        var filteredItems: List<MealsResponse>
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFDAF5FF))
        ) {
            Header(searchText)
            LazyColumn(contentPadding = PaddingValues(12.dp))
            {
                filteredItems =
                if (searchText.value == "") {
                    responseMeals
                } else {
                    val resultMeals = ArrayList<MealsResponse>()
                    for (item in responseMeals) {
                        if (item.name.lowercase(
                                Locale.getDefault()
                            ).contains(searchText.value.lowercase(Locale.getDefault()))
                        )
                            resultMeals.add(item)
                    }
                    resultMeals
                }
                items(filteredItems) { dish ->
                    FoodDetails(dish) { detail ->
                        navigator.push(MealsDetailsScreen(detail))
                    }
                }
            }
        }
    }
}

@Composable
fun Header(searchText: MutableState<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF44FFD2)),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.logo_no_background),
            contentDescription = "company logo",
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 2.dp)
                .size(70.dp)
        )
        TextField(
            value = searchText.value, onValueChange = { searchText.value = it },
            placeholder = { Text(text = "Search...", color = Color(0xFFEDEADE)) },
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 24.dp),
            //.shadow(4.dp, shape = RoundedCornerShape(25.dp), clip = true),
            trailingIcon = {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "Search Icon",
                    tint = Color.White,
                    //modifier = Modifier.background(Color(0xFF707fd9), shape = CircleShape)
                )
            },
            shape = RoundedCornerShape(25.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color(0xFF13279d),
                backgroundColor = Color.Black.copy(alpha = 0.1f),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            //label = { Text(text = "Search....") }
        )
    }
}

@Composable
fun FoodDetails(dish: MealsResponse, navigate: (MealsResponse) -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { navigate.invoke(dish) },
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color(0xFF5e72eb)
    ) {
        Row(Modifier.animateContentSize()) {
            AsyncImage(
                model = dish.imageUrl,
                contentDescription = "${dish.name} image",
                transform = AsyncImagePainter.Companion.DefaultTransform,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(16.dp)
                    .size(72.dp)
                    .clip(CircleShape),
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(.75f)
                    .padding(4.dp)
            ) {

                Text(
                    text = dish.name,
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFFffffff)
                )

                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = dish.description,
                        style = MaterialTheme.typography.subtitle2,
                        overflow = TextOverflow.Ellipsis,
                        color = Color(0xFFb9c1f6),
                        maxLines = if (isExpanded) Int.MAX_VALUE else 4
                    )
                }
            }

            IconButton(
                onClick = { isExpanded = !isExpanded },
                modifier = Modifier.align(
                    if (!isExpanded)
                        Alignment.CenterVertically
                    else
                        Alignment.Bottom
                )
            ) {
                Icon(
                    tint = Color(0xFFffffff),
                    imageVector = if (!isExpanded)
                        Icons.Filled.KeyboardArrowDown
                    else
                        Icons.Filled.KeyboardArrowUp,
                    contentDescription = "Expand Content",
                    modifier = Modifier
                        .padding(16.dp)
                )
            }
        }
    }
}