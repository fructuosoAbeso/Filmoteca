package es.ua.eps.filmoteca

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class FlimDataActivity : ComponentActivity() {

    companion object {
        const val EXTRA_FILM_TITLE = "EXTRA_FILM_TITLE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val filmTitle = intent.getStringExtra(EXTRA_FILM_TITLE) ?: "Película desconocida"

        // Buscar la película
        val film = FilmDataSource.films.find { it.title == filmTitle }

        setContent {
            film?.let {
                FilmDataScreen(
                    film = it,
                    onEdit = {
                        val intent = Intent(this, FlimEditActivity::class.java)
                        intent.putExtra("title", it.title)
                        intent.putExtra("director", it.director)
                        intent.putExtra("year", it.year)
                        intent.putExtra("genre", it.genre)
                        intent.putExtra("format", it.format)
                        intent.putExtra("imdb", it.imdbUrl)
                        intent.putExtra("notes", it.comments)
                        intent.putExtra("imageResId", it.imageResId) // 🔹 PASAMOS LA IMAGEN
                        startActivity(intent)
                    },
                    onBack = { finish() },
                    onNavigateHome = {
                        val intent = Intent(this, FlimListActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }
                )
            } ?: run {
                Text("Película no encontrada", color = Color.White, fontSize = 22.sp)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmDataScreen(
    film: Film,
    onEdit: () -> Unit,
    onBack: () -> Unit,
    onNavigateHome: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "HOME",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateHome) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver al inicio",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFF1E88E5)
                )
            )
        },
        containerColor = Color(0xFF121212)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Imagen
            Image(
                painter = painterResource(id = film.imageResId),
                contentDescription = film.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(film.title ?: "Sin título", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Director: ${film.director}", color = Color.White, fontSize = 20.sp)
                Text("Año: ${film.year}", color = Color.White, fontSize = 20.sp)
                Text("Género: ${film.genre}", color = Color.White, fontSize = 20.sp)
                Text("Formato: ${film.format}", color = Color.White, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(12.dp))
                Text("Notas: ${film.comments ?: "Sin comentarios"}", color = Color.White, fontSize = 18.sp)

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        film.imdbUrl?.let {
                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03A9F4)),
                    modifier = Modifier.fillMaxWidth().height(55.dp)
                ) {
                    Text("Ver en IMDB", color = Color.White, fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onEdit,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B5E20)),
                    modifier = Modifier.fillMaxWidth().height(55.dp)
                ) {
                    Text("Editar película", color = Color.White, fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onBack,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                    modifier = Modifier.fillMaxWidth().height(55.dp)
                ) {
                    Text("Volver", color = Color.White, fontSize = 18.sp)
                }
            }
        }
    }
}

