package es.ua.eps.filmoteca

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class FlimEditActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Lanza la cámara
        val takePhotoLauncher =
            registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
                if (bitmap != null) {
                    Toast.makeText(this, "Foto tomada", Toast.LENGTH_SHORT).show()
                }
            }

        // Selección de imagen desde galería
        val pickImageLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                if (uri != null) {
                    Toast.makeText(this, "Imagen seleccionada", Toast.LENGTH_SHORT).show()
                }
            }

        setContent {
            EditFilmScreen(
                onTakePhoto = { takePhotoLauncher.launch(null) },
                onSelectImage = { pickImageLauncher.launch("image/*") },
                onSave = { title, director, year, genre, format, imdb, notes ->
                    Toast.makeText(this, "Película guardada", Toast.LENGTH_SHORT).show()
                    finish()
                },
                onBack = { finish() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditFilmScreen(
    onTakePhoto: () -> Unit,
    onSelectImage: () -> Unit,
    onSave: (String, String, String, String, String, String, String) -> Unit,
    onBack: () -> Unit
) {
    val configuration = LocalConfiguration.current
    if (configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
        EditFilmScreenLandscape(onTakePhoto, onSelectImage, onSave, onBack)
    } else {
        EditFilmScreenPortrait(onTakePhoto, onSelectImage, onSave, onBack)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditFilmScreenPortrait(
    onTakePhoto: () -> Unit,
    onSelectImage: () -> Unit,
    onSave: (String, String, String, String, String, String, String) -> Unit,
    onBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var director by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("Acción") }
    var format by remember { mutableStateOf("DVD") }
    var imdb by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    val genres = listOf("Acción", "Drama", "Comedia", "Terror", "Sci-Fi")
    val formats = listOf("DVD", "Blu-ray", "Online")

    var expandedGenre by remember { mutableStateOf(false) }
    var expandedFormat by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar película", color = Color.White) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color(0xFF1E88E5))
            )
        },
        containerColor = Color.Black
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Imagen + botones
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sample_movie),
                    contentDescription = "Póster",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(100.dp)
                        .height(150.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Button(
                        onClick = onTakePhoto,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                    ) {
                        Text("Tomar fotografía", color = Color.White)
                    }
                    Button(
                        onClick = onSelectImage,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Seleccionar imagen", color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Campos de texto
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título", color = Color.LightGray) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(color = Color.White)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = director,
                onValueChange = { director = it },
                label = { Text("Director", color = Color.LightGray) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(color = Color.White)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = year,
                onValueChange = { year = it },
                label = { Text("Año de estreno", color = Color.LightGray) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = LocalTextStyle.current.copy(color = Color.White)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Dropdown género
            ExposedDropdownMenuBox(
                expanded = expandedGenre,
                onExpandedChange = { expandedGenre = !expandedGenre }
            ) {
                OutlinedTextField(
                    value = genre,
                    onValueChange = {},
                    label = { Text("Género", color = Color.LightGray) },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedGenre) },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = LocalTextStyle.current.copy(color = Color.White)
                )
                DropdownMenu(
                    expanded = expandedGenre,
                    onDismissRequest = { expandedGenre = false },
                    modifier = Modifier.background(Color(0xFF1E1E1E))
                ) {
                    genres.forEach { g ->
                        DropdownMenuItem(
                            text = { Text(g, color = Color.White) },
                            onClick = { genre = g; expandedGenre = false }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Dropdown formato
            ExposedDropdownMenuBox(
                expanded = expandedFormat,
                onExpandedChange = { expandedFormat = !expandedFormat }
            ) {
                OutlinedTextField(
                    value = format,
                    onValueChange = {},
                    label = { Text("Formato", color = Color.LightGray) },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedFormat) },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = LocalTextStyle.current.copy(color = Color.White)
                )
                DropdownMenu(
                    expanded = expandedFormat,
                    onDismissRequest = { expandedFormat = false },
                    modifier = Modifier.background(Color(0xFF1E1E1E))
                ) {
                    formats.forEach { f ->
                        DropdownMenuItem(
                            text = { Text(f, color = Color.White) },
                            onClick = { format = f; expandedFormat = false }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = imdb,
                onValueChange = { imdb = it },
                label = { Text("Enlace a IMDB", color = Color.LightGray) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(color = Color.White)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notas", color = Color.LightGray) },
                modifier = Modifier.fillMaxWidth().height(120.dp),
                textStyle = LocalTextStyle.current.copy(color = Color.White)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botones
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Button(
                    onClick = onBack,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                ) { Text("Volver", color = Color.White) }

                Button(
                    onClick = { onSave(title, director, year, genre, format, imdb, notes) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B5E20)),
                    modifier = Modifier.weight(1f).padding(start = 8.dp)
                ) { Text("Guardar", color = Color.White) }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditFilmScreenLandscape(
    onTakePhoto: () -> Unit,
    onSelectImage: () -> Unit,
    onSave: (String, String, String, String, String, String, String) -> Unit,
    onBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var director by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("Acción") }
    var format by remember { mutableStateOf("DVD") }
    var imdb by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    val genres = listOf("Acción", "Drama", "Comedia", "Terror", "Sci-Fi")
    val formats = listOf("DVD", "Blu-ray", "Online")

    var expandedGenre by remember { mutableStateOf(false) }
    var expandedFormat by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Imagen a la izquierda
        Image(
            painter = painterResource(id = R.drawable.sample_movie),
            contentDescription = "Póster",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(150.dp)
                .height(220.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Datos a la derecha
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título", color = Color.LightGray) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(color = Color.White)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = director,
                onValueChange = { director = it },
                label = { Text("Director", color = Color.LightGray) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(color = Color.White)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = year,
                onValueChange = { year = it },
                label = { Text("Año de estreno", color = Color.LightGray) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = LocalTextStyle.current.copy(color = Color.White)
            )

            // Dropdown género
            Spacer(modifier = Modifier.height(8.dp))
            ExposedDropdownMenuBox(
                expanded = expandedGenre,
                onExpandedChange = { expandedGenre = !expandedGenre }
            ) {
                OutlinedTextField(
                    value = genre,
                    onValueChange = {},
                    label = { Text("Género", color = Color.LightGray) },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedGenre) },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = LocalTextStyle.current.copy(color = Color.White)
                )
                DropdownMenu(
                    expanded = expandedGenre,
                    onDismissRequest = { expandedGenre = false },
                    modifier = Modifier.background(Color(0xFF1E1E1E))
                ) {
                    genres.forEach { g ->
                        DropdownMenuItem(
                            text = { Text(g, color = Color.White) },
                            onClick = { genre = g; expandedGenre = false }
                        )
                    }
                }
            }

            // Dropdown formato
            Spacer(modifier = Modifier.height(8.dp))
            ExposedDropdownMenuBox(
                expanded = expandedFormat,
                onExpandedChange = { expandedFormat = !expandedFormat }
            ) {
                OutlinedTextField(
                    value = format,
                    onValueChange = {},
                    label = { Text("Formato", color = Color.LightGray) },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedFormat) },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = LocalTextStyle.current.copy(color = Color.White)
                )
                DropdownMenu(
                    expanded = expandedFormat,
                    onDismissRequest = { expandedFormat = false },
                    modifier = Modifier.background(Color(0xFF1E1E1E))
                ) {
                    formats.forEach { f ->
                        DropdownMenuItem(
                            text = { Text(f, color = Color.White) },
                            onClick = { format = f; expandedFormat = false }
                        )
                    }
                }
            }

            // Enlace a IMDB
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = imdb,
                onValueChange = { imdb = it },
                label = { Text("Enlace a IMDB", color = Color.LightGray) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(color = Color.White)
            )

            // Notas
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notas", color = Color.LightGray) },
                modifier = Modifier.fillMaxWidth().height(120.dp),
                textStyle = LocalTextStyle.current.copy(color = Color.White)
            )

            // Botones
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Button(
                    onClick = onBack,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                ) { Text("Volver", color = Color.White) }

                Button(
                    onClick = { onSave(title, director, year, genre, format, imdb, notes) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B5E20)),
                    modifier = Modifier.weight(1f).padding(start = 8.dp)
                ) { Text("Guardar", color = Color.White) }
            }
        }
    }
}
