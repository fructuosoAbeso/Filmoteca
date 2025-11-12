package es.ua.eps.filmoteca

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class FlimListActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val films = remember { mutableStateListOf<Film>().apply { addAll(FilmDataSource.films) } }

                FilmListScreen(
                    films = films,
                    onFilmSelected = { film ->
                        val intent = Intent(this, FlimDataActivity::class.java)
                        intent.putExtra(FlimDataActivity.EXTRA_FILM_TITLE, film.title)
                        startActivity(intent)
                    },
                    onAddFilm = {
                        val newFilm = Film().apply {
                            title = "Nueva película"
                            director = "Desconocido"
                            comments = "Comentarios..."
                            imageResId = R.drawable.avatar
                            year = 2025
                            genre = Film.GENRE_DRAMA
                            format = Film.FORMAT_DIGITAL
                        }
                        films.add(newFilm)
                    },
                    onAbout = {
                        startActivity(Intent(this, AboutActivity::class.java))
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmListScreen(
    films: MutableList<Film>,
    onFilmSelected: (Film) -> Unit,
    onAddFilm: () -> Unit,
    onAbout: () -> Unit
) {
    var isSelectionMode by remember { mutableStateOf(false) }
    val selectedFilms = remember { mutableStateListOf<Film>() }
    val backgroundColor = Color.Black

    Scaffold(
        topBar = {
            if (isSelectionMode) {
                TopAppBar(
                    title = { Text("${selectedFilms.size} seleccionadas") },
                    navigationIcon = {
                        IconButton(onClick = {
                            isSelectionMode = false
                            selectedFilms.clear()
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Cancelar selección",
                                tint = Color.White
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            films.removeAll(selectedFilms)
                            selectedFilms.clear()
                            isSelectionMode = false
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Borrar seleccionadas",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.Red)
                )
            } else {
                TopAppBar(
                    title = {
                        Text(
                            "🎥 Filmoteca",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    actions = {
                        var expanded by remember { mutableStateOf(false) }
                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Menú",
                                tint = Color.White
                            )
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.background(Color.White)
                        ) {
                            DropdownMenuItem(
                                text = { Text("➕ Añadir película") },
                                onClick = {
                                    expanded = false
                                    onAddFilm()
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("ℹ️ Acerca de") },
                                onClick = {
                                    expanded = false
                                    onAbout()
                                }
                            )
                        }
                    },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color(0xFF1E88E5))
                )
            }
        },
        containerColor = backgroundColor
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(backgroundColor),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(films) { film ->
                FilmCard(
                    film = film,
                    isSelected = selectedFilms.contains(film),
                    onClick = {
                        if (isSelectionMode) {
                            if (selectedFilms.contains(film)) {
                                selectedFilms.remove(film)
                                if (selectedFilms.isEmpty()) isSelectionMode = false
                            } else {
                                selectedFilms.add(film)
                            }
                        } else {
                            onFilmSelected(film)
                        }
                    },
                    onLongClick = {
                        if (!isSelectionMode) {
                            isSelectionMode = true
                            selectedFilms.add(film)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun FilmCard(
    film: Film,
    isSelected: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Color(0xFF1976D2) else Color(0xFF1E1E1E)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onClick() },
                    onLongPress = { onLongClick() }
                )
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = film.imageResId),
                contentDescription = film.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            film.title?.let {
                Text(
                    text = it,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color = Color.White
                )
            }
            Text(
                text = "🎬 Director: ${film.director}",
                fontSize = 14.sp,
                color = Color.LightGray,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Text(
                text = film.comments ?: "",
                fontSize = 13.sp,
                color = Color.Gray,
                modifier = Modifier
                    .padding(8.dp)
                    .padding(bottom = 8.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
