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
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
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
                    Box(modifier = Modifier.padding(innerPadding)) {
                        when (pantallaActual) {
                            1 -> Inicio()
                            2 -> PantallaPreguntas()
                            3 -> PantallaFinal()
                            else -> {}
                        }
                    }
                }
            }
        }
    }


var pantallaActual by mutableIntStateOf(1)
var puntajeGlobal by mutableIntStateOf(0)
//var opcionSeleccionada by mutableStateOf("")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Inicio() {
        Column(
            modifier = Modifier
                .padding(16.dp)
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
                    pantallaActual = 2
                },
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            ) {
                Text(
                    text = stringResource(R.string.ComQuiz),
                    fontSize = 20.sp,
                )
            }
        }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPreguntas(){
    var numeroPregunta by rememberSaveable { mutableIntStateOf(0) }
    val preguntaActual = PreguntasQuiz[numeroPregunta]
    var isRespondido by rememberSaveable { mutableStateOf(false) }
    var opcionSeleccionada by rememberSaveable { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = "Pregunta ${numeroPregunta + 1} de 3", fontSize = 20.sp)
            Text(text = "Puntaje $puntajeGlobal de 3", fontSize = 20.sp)
            Text(text = preguntaActual.pregunta, fontSize = 17.sp)

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                preguntaActual.opciones.forEach { opcion ->
                    val colorBoton = when {
                        !isRespondido -> MaterialTheme.colorScheme.primary
                        opcion == preguntaActual.respuestaCorrecta -> Color.Green
                        opcion == opcionSeleccionada && opcion != preguntaActual.respuestaCorrecta -> Color.Red
                        else -> MaterialTheme.colorScheme.primary
                    }
                    Button(
                        onClick = {
                                isRespondido = true
                                opcionSeleccionada = opcion
                                BotonOpcionesPregunta(opcion, numeroPregunta)
                        },
                        enabled = !isRespondido,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorBoton,
                            disabledContainerColor = colorBoton
                        ),
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

                if (isRespondido) {
                    Text(
                        text = preguntaActual.funFact,
                        fontSize = 22.sp,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    if(numeroPregunta < PreguntasQuiz.size -1) {
                    Button(
                        onClick = {
                            numeroPregunta += 1
                            isRespondido = false
                            opcionSeleccionada = ""
                        }, modifier = Modifier.padding(top = 10.dp)
                    ) {Text(
                        "Siguiente"
                    )}
                    }
                    else{
                        Button(
                            onClick = {
                                pantallaActual = 3
                            }, modifier = Modifier.padding(top = 10.dp)
                        ) {Text(
                            "Ver Resultado"
                        )}
                    }
                    }
                }
            }
        }



fun BotonOpcionesPregunta(opcion: String, numeroPregunta: Int){
    val preguntaActual = PreguntasQuiz[numeroPregunta]
      if (opcion == preguntaActual.respuestaCorrecta) {
           puntajeGlobal += 1
      }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaFinal() {

    val mensaje = when(puntajeGlobal){
        0 -> stringResource(R.string.cerode3)
        1 -> stringResource(R.string.unaDe3)
        2 -> stringResource(R.string.dosDe3)
        3 -> stringResource(R.string.tresDe3)
            else ->{}
    }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box() {
                Text(
                    modifier = Modifier.padding(10.dp, 20.dp),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    text = "Obtuviste $puntajeGlobal de 3"
                )
            }
            Box(modifier = Modifier) {
                Text(
                fontSize = 20.sp,
                text = "$mensaje"
                )
            }

            Button(
                onClick = {
                    pantallaActual = 2
                    puntajeGlobal = 0
                },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = stringResource(R.string.Reiniciar),
                    fontSize = 20.sp,
                )
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