package com.pdm0126.taller1_00002024

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pdm0126.taller1_00002024.ui.theme.AndroidPediaByIrahetaGarciaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidPediaByIrahetaGarciaTheme {
                PantallaPreguntas()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Inicio() {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                modifier = Modifier,
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp, 20.dp)
                    )
                },
            )
        },

        ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box() {
                Text(
                    modifier = Modifier.padding(10.dp, 20.dp),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    text = stringResource(R.string.AndroidP)
                )
            }
            Box(modifier = Modifier) {
                Text(
                    fontSize = 20.sp,
                    text = stringResource(R.string.nombre),
                )
            }
            Box(modifier = Modifier.padding(10.dp)) {
                Text(
                    fontSize = 20.sp,
                    text = stringResource(R.string.carnet)
                )
            }

            Button(
                onClick = {
//                    listaUsuario.add(usuario.value)
//                    usuario.value = ""
                },
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                //colors = ButtonColors(contentColor = colorResource(R.color.purple_700))
            ) {
                Text(
                    text = stringResource(R.string.ComQuiz),
                    fontSize = 20.sp,
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPreguntas(): Int {
    var puntaje by rememberSaveable { mutableIntStateOf(0) }
    var numeroPregunta by rememberSaveable { mutableIntStateOf(0) }
    val preguntaActual = PreguntasQuiz[numeroPregunta]
    var respondido by rememberSaveable { mutableStateOf(false) }
    var opcionSeleccionada by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp, 20.dp)
                    )
                },
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = "Pregunta ${numeroPregunta + 1} de 3")

            Text(text = preguntaActual.pregunta)

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                preguntaActual.opciones.forEach { opcion ->

                    val colorBoton = when {
                        !respondido -> MaterialTheme.colorScheme.primary
                        opcion == preguntaActual.respuestaCorrecta -> Color.Green
                        opcion != preguntaActual.respuestaCorrecta && opcionSeleccionada == opcion -> Color.Red
                        else -> MaterialTheme.colorScheme.primary
                    }

                    Button(
                        onClick = {
                            if (!respondido) {
                                respondido = true
                                opcionSeleccionada = opcion

                                if (opcion == preguntaActual.respuestaCorrecta) {
                                    puntaje += 1
                                }
                            }
                        },
                        enabled = !respondido,
                        colors = ButtonDefaults.buttonColors(containerColor = colorBoton),
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = opcion,
                            fontSize = 20.sp
                        )
                    }
                }

                if (respondido) {
                    Text(
                        text = preguntaActual.funFact,
                        fontSize = 22.sp,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    Button(
                        onClick = {
                            if (numeroPregunta < PreguntasQuiz.size - 1) {
                                numeroPregunta += 1
                                respondido = false
                                opcionSeleccionada = ""
                            }
                        },
                        modifier = Modifier.padding(top = 10.dp)
                    ) {
                        Text("Siguiente")
                    }
                }
            }
        }
    }

    return puntaje
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaFinal() {

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                modifier = Modifier,
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp, 20.dp)
                    )
                },
            )
        },

        ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box() {
                Text(
                    modifier = Modifier.padding(10.dp, 20.dp),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    text = "Obtuviste X de 3"
                )
            }
            Box(modifier = Modifier) {
                //When
                //Text(
                //fontSize = 20.sp,
                //text = stringResource(R.string.RESPUESTA PERSONALIZADA),
                //)
            }

            Button(
                onClick = {
//                    reiniciar
                    //restablece todos los estados al valor inicial y regresa a la primera
//            pregunta.
                },
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                //colors = ButtonColors(contentColor = colorResource(R.color.purple_700))
            ) {
                Text(
                    text = stringResource(R.string.Reiniciar),
                    fontSize = 20.sp,
                )
            }
        }
    }
}

data class Pregunta(
    val id: Int,
    val pregunta: String,
    val opciones: List<String>,
    val respuestaCorrecta: String,
    val funFact: String
)

val PreguntasQuiz = listOf(
    Pregunta(
        id = 1,
        pregunta = "¿En qué se inspiró la diseñadora Irina Blok para crear el robot de Android?",
        opciones = listOf(
            "Animales",
            "Videojuegos",
            "Señales de baños y robots",
            "Plantas"
        ),
        respuestaCorrecta = "Señales de baños y robots",
        funFact = "Al terminar el diseño Google libero el diseño al publico para mostrar la filosofia de Android"
    ),
    Pregunta(
        id = 2,
        pregunta = "¿Que le sucedio a la estatua de Android en 2012?",
        opciones = listOf(
            "Nada",
            "Se la robaron",
            "La pintaron",
            "Se le cayo la cabeza"
        ),
        respuestaCorrecta = "Se le cayo la cabeza",
        funFact = "Cada vez que hay una nueva version de Android se construye una nueva estatua, a 'Jelly Bean' le robaron un jelly bean que estaba dentro del robot"
    ),
    Pregunta(
        id = 3,
        pregunta = "¿Que Easter Egg estaba escondido en la version de Android 2.3?",
        opciones = listOf(
            "Gingerbread",
            "Honeycomb",
            "Jelly Bean",
            "Ice Cream Sandwich"
        ),
        respuestaCorrecta = "Gingerbread",
        funFact = "El primer Easter Egg fue en la version 2.3 y era una galleta de gengibre zombie junto al robot de Android"
    ),
)

@Preview(showBackground = true)
@Composable
fun Preview() {
    AndroidPediaByIrahetaGarciaTheme {
        PantallaPreguntas()
    }
}