package es.ua.eps.filmoteca

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class SoporteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SoporteScreen(
                onSend = { mensaje ->
                    if (mensaje.isBlank()) {
                        Toast.makeText(this, "Por favor escribe un mensaje antes de enviar.", Toast.LENGTH_SHORT).show()
                    } else {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:fabesongui@gmail.com")
                            putExtra(Intent.EXTRA_SUBJECT, "Consulta de soporte")
                            putExtra(Intent.EXTRA_TEXT, mensaje)
                        }
                        try {
                            startActivity(Intent.createChooser(intent, "Enviar correo con:"))
                        } catch (e: Exception) {
                            Toast.makeText(this, "No hay aplicaciones de correo instaladas", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                onBack = { finish() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoporteScreen(
    onSend: (String) -> Unit,
    onBack: () -> Unit
) {
    var mensaje by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "🛠 Soporte",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0D47A1),
                    titleContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFF121212)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Descripción
            Text(
                text = "Si tienes alguna duda o problema, puedes enviarnos un mensaje a nuestro soporte.",
                color = Color.White,
                fontSize = 18.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 24.dp)
            )

            // Información de contacto
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1E1E1E), shape = RoundedCornerShape(12.dp))
                    .padding(16.dp)
                    .padding(bottom = 24.dp)
            ) {
                Text("📧 Correo: fabesongui@gmail.com", color = Color.White, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text("📞 Teléfono: +34 600 000 000", color = Color.White, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text("⏰ Horario: Lunes a Viernes, 9:00 - 18:00", color = Color.White, fontSize = 16.sp)
            }

            // Campo de mensaje simple
            OutlinedTextField(
                value = mensaje,
                onValueChange = { mensaje = it },
                label = { Text("Escribe tu mensaje aquí", color = Color.White) },
                placeholder = { Text("Mensaje...", color = Color.White.copy(alpha = 0.5f)) },
                textStyle = LocalTextStyle.current.copy(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(bottom = 24.dp)
            )

            // Botón Enviar
            Button(
                onClick = { onSend(mensaje.text) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("✉ Enviar mensaje", color = Color.White, fontSize = 17.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón Volver
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
