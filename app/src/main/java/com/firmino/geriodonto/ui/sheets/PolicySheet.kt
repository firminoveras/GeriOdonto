package com.firmino.geriodonto.ui.sheets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.firmino.geriodonto.ui.widgets.SheetHeader
import com.firmino.geriodonto.ui.widgets.textBox
import com.firmino.geriodonto.ui.widgets.textParagraph
import com.firmino.geriodonto.ui.widgets.textTitle

@Composable
fun PolicySheet() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SheetHeader(
            iconName = "policy",
            title = "Política de Privacidade",
            subtitle = "Aplicativo GeriOdonto",
        )
        LazyColumn(
            modifier = Modifier.padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item { Spacer(Modifier.fillMaxWidth()) }
            item {
                Column {
                    Text(text = "23 de Junho de 2026", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Última atualização", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                }
            }
            textParagraph("O GeriOdonto é uma plataforma digital de suporte à decisão clínica e pedagógica voltada para a Odontogeriatria. O aplicativo foi projetado para auxiliar cirurgiões-dentistas, acadêmicos e professores na avaliação de riscos sistêmicos, análise de exames laboratoriais, manejo de condições crônicas e identificação de interações medicamentosas em pacientes idosos.")
            textParagraph("Esta Política de Privacidade esclarece, de forma transparente e em total conformidade com a Lei Geral de Proteção de Dados (LGPD - Lei nº 13.709/2018), como as informações são tratadas pelo aplicativo.")

            textTitle("1. Princípio do Tratamento Local")
            textParagraph("O GeriOdonto foi desenvolvido sob o princípio da privacidade por padrão (Privacy by Design). Isso significa que:")
            textBox(
                title = "Armazenamento Local",
                text = "Todas as informações clínicas, anamneses, valores de exames laboratoriais e dados de identificação do paciente inseridos no aplicativo são salvos única e exclusivamente no armazenamento interno do dispositivo móvel do usuário, utilizando o banco de dados nativo estruturado do ecossistema Android (Room SQLite).",
            )
            textBox(
                title = "Ausência de Servidores e Nuvem",
                text = "O aplicativo não possui servidores próprios, não realiza o upload de dados para a nuvem, não coleta telemetria de dados de saúde e não possui acesso remoto aos prontuários criados.",
            )
            textBox(
                title = "Compartilhamento Controlado",
                text = "O aplicativo permite a exportação de relatórios clínicos em formato PDF. Este arquivo é gerado localmente e qualquer compartilhamento (via e-mail, WhatsApp ou outros meios) é de responsabilidade exclusiva e consciente do usuário profissional.",
            )

            textTitle("2. Categorias de Dados Tratados")
            textParagraph("Para cumprir suas funções de triagem e cálculo de riscos odontológicos, o aplicativo processa localmente as seguintes categorias de informações:")
            textBox(
                title = "Dados de Identificação Básica",
                text = "Nome ou iniciais do paciente, idade e sexo (utilizados estritamente para a contextualização do relatório clínico e cálculo de taxas biológicas).",
            )
            textBox(
                title = "Dados de Saúde Sensíveis",
                text = "Presença de condições médicas sistêmicas como Hipertensão Arterial, Diabetes Mellitus, Insuficiência Renal, Doenças Cardiovasculares, Alterações Hematológicas e histórico de eventos agudos (como Infarto ou AVC).",
            )
            textBox(
                title = "Dados Laboratoriais e Farmacológicos",
                text = "Valores numéricos de taxas como Creatinina, Ureia, TAP/RNI, TGO/TGP e a relação de medicamentos de uso contínuo (anticoagulantes, estatinas, etc.) para o cruzamento automatizado de riscos e interações prejudiciais ao manejo odontológico.",
            )

            textTitle("3. Permissões Solicitadas ao Sistema")
            textParagraph("Para executar suas funções técnicas básicas, o GeriOdonto requer apenas permissões operacionais do ecossistema Android:")
            textBox(
                title = "Acesso ao Armazenamento / Mídia",
                text = "Necessária exclusivamente para salvar localmente os relatórios e receitas gerados em formato PDF no diretório escolhido pelo usuário.",
            )
            textBox(
                title = "Notificações do Sistema",
                text = "Utilizada para alertas internos ou comunicações operacionais do próprio aplicativo, sem qualquer coleta de dados ou rastreamento de comportamento do usuário.",
            )

            textTitle("4. Segurança das Informações Clínicas")
            textParagraph("Visto que o GeriOdonto delega todo o armazenamento ao ambiente local do aparelho do usuário profissional, a integridade e o sigilo dos dados de saúde sensíveis dependem diretamente da segurança do próprio dispositivo móvel.")
            textBox(
                title = "Mecanismos de Proteção Obrigatórios",
                text = "O usuário profissional compromete-se a manter o seu dispositivo digital protegido por mecanismos de autenticação biométrica ou senhas fortes, impedindo o acesso físico de terceiros não autorizados aos prontuários e anamneses exibidos na tela.",
            )
            textBox(
                title = "Boas Práticas de Exportação",
                text = "Ao gerar e exportar relatórios ou receitas clínicas em formato PDF, o usuário torna-se o controlador exclusivo daquele arquivo físico, devendo zelar pelo sigilo profissional odontológico e pela destinação segura do documento compartilhado.",
            )

            textTitle("5. Direitos Legais do Titular (LGPD)")
            textParagraph("Em total conformidade com o artigo 18 da Lei Geral de Proteção de Dados (Lei nº 13.709/2018), todos os direitos de acesso, retificação, oposição e exclusão de dados pessoais são plenamente garantidos e exercidos de forma imediata pelo operador:")
            textBox(
                title = "Retificação e Alteração Instantânea",
                text = "Qualquer dado clínico, valor de exame laboratorial ou dado demográfico inserido incorretamente pode ser modificado ou updated em tempo real pelo cirurgião-dentista ou acadêmico através das telas de edição nativas do aplicativo.",
            )
            textBox(
                title = "Eliminação Definitiva de Dados",
                text = "A exclusão permanente de todas as informações armazenadas de um paciente ocorre de três formas diretas: deletando o perfil do idoso dentro do próprio app, limpando o armazenamento do GeriOdonto nas configurações do Android ou efetuando a desinstalação completa do aplicativo móvel.",
            )

            textTitle("6. Isenção de Responsabilidade Clínica")
            textParagraph("O GeriOdonto atua como uma plataforma de suporte e ferramenta pedagógica, de modo que o uso dos dados processados em ambiente de triagem possui limitações normativas:")
            textBox(
                title = "Soberania do Julgamento Profissional",
                text = "Os cálculos de risco sistêmico, clearance renal e alertas de interações medicamentosas fornecidos localmente pelo software servem como guias informativos e não substituem, em hipótese alguma, o diagnóstico, a conduta e o julgamento clínico soberano do cirurgião-dentista responsável.",
            )

            textTitle("7. Alterações, Responsabilidade e Contato")
            textParagraph("Esta Política de Privacidade poderá ser atualizada periodicamente para refletir novas implementações de recursos técnicos, otimizações de segurança ou mudanças normativas na legislação de saúde e proteção de dados.")
            textBox(
                title = "Canal de Atendimento e Suporte",
                text = "Para esclarecimento de dúvidas sobre a privacidade de dados locais, reportar problemas técnicos ou enviar sugestões de melhoria arquitetural, o usuário pode acionar diretamente o desenvolvedor responsável através do canal de suporte disponibilizado na página oficial de distribuição do aplicativo na Google Play Store.",
            )
            item { Spacer(Modifier.height(32.dp)) }
        }
    }
}