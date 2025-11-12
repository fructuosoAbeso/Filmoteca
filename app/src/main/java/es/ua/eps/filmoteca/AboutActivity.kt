package es.ua.eps.filmoteca

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class AboutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AboutScreen(
                onWebsite = {
                    val url = "https://elpacmoneytransfer.es/"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                },
                onSupport = {
                    startActivity(Intent(this, SoporteActivity::class.java))
                },
                onBack = {
                    finish()
                },
                onNavigateHome = {
                    val intent = Intent(this, FlimListActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    onWebsite: () -> Unit,
    onSupport: () -> Unit,
    onBack: () -> Unit,
    onNavigateHome: () -> Unit
) {
    Scaffold(
        // 🟦 AppBar con icono de navegación
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "HOME",
                        color = Color.White,
                        fontSize = 20.sp,
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
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0D47A1),
                    titleContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFF121212) // Fondo oscuro uniforme
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 📷 Imagen superior
            Image(
                painter = painterResource(id = R.drawable.about_img),
                contentDescription = "Imagen de información",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .padding(bottom = 16.dp),
                contentScale = ContentScale.Crop
            )

            // 📝 Descripción
            Text(
                text = "Aplicación creada por El Pac Money Transfer.\n" +
                        "Administra y consulta tus películas de forma moderna.",
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 30.dp)
            )

            // 🌐 Botón Sitio Web
            Button(
                onClick = onWebsite,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("🌍 Ir al sitio web", color = Color.White, fontSize = 17.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 💬 Botón Soporte
            Button(
                onClick = onSupport,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("🛠 Obtener soporte", color = Color.White, fontSize = 17.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 🔙 Botón Volver (manteniendo tu diseño)
            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("⬅ Volver", color = Color.White, fontSize = 17.sp)
            }
        }
    }
}
