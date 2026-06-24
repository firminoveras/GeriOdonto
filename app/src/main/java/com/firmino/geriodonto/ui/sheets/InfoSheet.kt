package com.firmino.geriodonto.ui.sheets

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.firmino.geriodonto.companions.MaterialSymbol
import com.firmino.geriodonto.ui.widgets.SheetHeader
import com.firmino.geriodonto.ui.widgets.textBoxTiple
import com.firmino.geriodonto.ui.widgets.textTitle

@Composable
fun InfoSheet() {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SheetHeader(
            iconName = "license",
            title = "Informações e Atribuições",
            subtitle = "Aplicativo GeriOdonto",
        )
        LazyColumn(
            modifier = Modifier.padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item { Spacer(Modifier.fillMaxWidth()) }

            textTitle("Atribuições")
            textBoxTiple(
                title = "Room",
                text = "developer.android.com/training/data-storage/room",
                text2 = "A biblioteca de persistência Room oferece uma camada de abstração sobre o SQLite para permitir um acesso mais robusto ao banco de dados, aproveitando toda a capacidade do SQLite.",
                context = context,
            )

            textBoxTiple(
                title = "Hilt",
                text = "github.com/google/dagger",
                text2 = "Hilt é um framework de injeção de dependências em tempo de compilação. Ele não utiliza reflexão nem geração de bytecode em tempo de execução, realiza toda a análise em tempo de compilação e gera código-fonte puro.",
                context = context,
            )

            textBoxTiple(
                title = "Material Kolor",
                text = "github.com/jordond/materialkolor",
                text2 = "Uma biblioteca Compose Multiplatforma para criar paletas de cores dinâmicas do Material Design 3 a partir de qualquer cor.",
                context = context,
            )

          //TODO: Revisar essas informações
          //textTitle("Informações")
          //textParagraph("O aplicativo GeriOdonto foi desenvolvido por Firmino Veras e servirá como base operacional e ferramenta de suporte para o trabalho de pós-graduação de Eugênio. Esta pesquisa científica e plataforma digital estão sendo conduzidas e vinculadas institucionalmente junto à XXXX, unindo a inovação tecnológica aplicada à Odontogeriatria com o rigor acadêmico.")

            item {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        context.startActivity(Intent(Intent.ACTION_VIEW, "https://github.com/firminoveras".toUri()))
                    },
                    content = {
                        MaterialSymbol(iconName = "deployed_code", color = MaterialTheme.colorScheme.onPrimary)
                        Spacer(Modifier.width(8.dp))
                        Text("Página do Desenvolvedor")
                    },
                )
            }

            item { Spacer(Modifier.height(32.dp)) }
        }
    }
}
