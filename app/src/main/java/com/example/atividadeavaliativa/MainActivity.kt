package com.example.atividadeavaliativa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext

// Classe principal que inicializa a Activity
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TelaDoJogo() // Chama a função que desenha a tela do jogo
        }
    }
}

// Função que desenha a tela do jogo
@Composable
fun TelaDoJogo() {
    // Variáveis de estado que controlam o jogo
    var contadorCliques by remember { mutableStateOf(0) } // Contador de cliques
    var cliquesNecessarios by remember { mutableStateOf((1..50).random()) } // Número de cliques necessários para vencer
    var imagemAtual by remember { mutableStateOf(R.drawable.imagem_1) } // Imagem atual
    var jogoTerminado by remember { mutableStateOf(false) } // Estado que indica se o jogo terminou

    // Obtém o contexto local
    val context = LocalContext.current

    // Layout da tela
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Exibe a imagem atual
        Image(
            painter = painterResource(id = imagemAtual),
            contentDescription = null,
            modifier = Modifier
                .size(250.dp)
                .clickable(enabled = !jogoTerminado) {
                    if (!jogoTerminado) { // Verifica se o jogo ainda está em andamento
                        contadorCliques++ // Incrementa o contador de cliques

                        // Atualiza a imagem conforme o progresso do jogo
                        imagemAtual = when {
                            contadorCliques >= cliquesNecessarios -> {
                                jogoTerminado = true
                                R.drawable.imagem_4 // Imagem de conquista ao atingir o alvo
                            }
                            contadorCliques > cliquesNecessarios * 2 / 3 -> R.drawable.imagem_3 // Imagem final
                            contadorCliques > cliquesNecessarios / 3 -> R.drawable.imagem_2 // Imagem intermediária
                            else -> R.drawable.imagem_1 // Imagem inicial
                        }
                    }
                }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Mensagem para o usuário
        if (jogoTerminado) {
            if (contadorCliques >= cliquesNecessarios) {
                Text("Parabéns pela conquista!") // Mensagem de conquista
            } else {
                Text("Você desistiu!") // Mensagem de desistência
            }
        } else {
            Text("Clique para avançar!") // Mensagem enquanto o jogo está em andamento
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Botão de desistir
        Button(onClick = {
            if (!jogoTerminado) {
                jogoTerminado = true
                imagemAtual = R.drawable.imagem_5 // Exibe a imagem de desistência
            }
        }) {
            Text("Desistir")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Opções após o jogo terminar
        if (jogoTerminado) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(onClick = {
                    contadorCliques = 0 // Reseta o contador de cliques
                    cliquesNecessarios = (1..50).random() // Gera um novo alvo de cliques
                    imagemAtual = R.drawable.imagem_1 // Reseta a imagem para a inicial
                    jogoTerminado = false // Reinicia o jogo
                }) {
                    Text("Novo Jogo")
                }
                Button(onClick = {
                    // Finaliza o aplicativo
                    (context as? ComponentActivity)?.finish()
                }) {
                    Text("Sair")
                }
            }
        }
    }
}

// Função para pré-visualização no Android Studio
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TelaDoJogo()
}
