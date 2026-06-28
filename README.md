# GeriOdonto

![Logo](/media/icon.png)

![Android](https://img.shields.io/badge/Platform-Android-3DDC84?style=flat-square&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Language-Kotlin-7F52FF?style=flat-square&logo=kotlin&logoColor=white)
![Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4?style=flat-square&logo=jetpackcompose&logoColor=white)
![LGPD](https://img.shields.io/badge/Compliance-LGPD-blue?style=flat-square)

O **GeriOdonto** é uma plataforma digital de suporte à decisão clínica e pedagógica voltada para a **Odontogeriatria**. O aplicativo foi projetado para auxiliar cirurgiões-dentistas, acadêmicos e professores na avaliação de riscos sistêmicos, análise de exames laboratoriais, manejo de condições crônicas e identificação de interações medicamentosas em pacientes idosos.

---

## 📌 Finalidade Acadêmica e Científica

O GeriOdonto atua como a base operacional e ferramenta de suporte prático para a pesquisa de pós-graduação intitulada:
> **"Impactos das prescrições inadequadas de medicamentos em Odontogeriatria"**

* **Desenvolvimento Técnico:** Firmino Veras
* **Autores do Projeto Científico:** Eugênio Pacelli Ferreira Passos, Marina Nogueira Brasileiro Veras e Islania Giselia Albuquerque Araújo.
* **Instituição de Vínculo:** XXXX

---

## 🚀 Funcionalidades Principais

* **Triagem de Condições Médicas:** Avaliação e mapeamento automatizado de mais de 30 condições sistêmicas frequentes em idosos (como Hipertensão, Diabetes Tipo 2, Sarcopenia, Doença Renal Crônica, Histórico de Quedas, entre outras).
* **Análise de Exames Laboratoriais:** Interface dedicada para inserção e interpretação de taxas biológicas cruciais (Creatinina, Ureia, RNI, TGO/TGP), oferecendo parâmetros integrados de segurança.
* **Identificação de Interações Medicamentosas:** Cruzamento inteligente de dados farmacológicos para alertar sobre efeitos sinérgicos ou antagônicos nocivos ao manejo odontológico.
* **Emissão de Prescrição em PDF:** Módulo para seleção de terapêutica odontológica adequada com exportação local imediata do relatório e receita clínica em formato PDF.
* **Interface Customizável (Clean Tech):** Sistema de temas dinâmicos alimentado pela biblioteca *Material Kolor* (Material Design 3), contando com Modo Claro, Modo Escuro e Modo OLED.

---

## 🔒 Privacidade e Segurança (LGPD Compliance)

O GeriOdonto foi arquitetado sob o princípio estrito de **Privacy by Design** (privacidade por padrão):
* **Tratamento 100% Local:** Todas as anamneses, dados de exames e históricos clínicos processados são salvos exclusivamente no armazenamento interno do dispositivo móvel do profissional através do banco de dados **Room SQLite**.
* **Funcionamento Offline:** O aplicativo não possui servidores próprios e não realiza upload de dados para a nuvem, mitigando riscos de vazamentos cibernéticos.
* **Controle do Usuário:** A exclusão de perfis e dados é definitiva e controlada diretamente no aparelho. A distribuição dos relatórios exportados em PDF é de responsabilidade consciente do cirurgião-dentista operador.

---

## 🛠️ Stack Tecnológica

* **Linguagem:** [Kotlin](https://kotlinlang.org/)
* **Interface de Usuário:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (Declaração reativa e moderna)
* **Arquitetura:** MVVM (Model-View-ViewModel) com State Flow e Clean Architecture UI principles.
* **Banco de Dados Local:** [Room Database](https://developer.android.com/training/data-storage/room) (SQLite ORM nativo do Android)
* **Injeção de Dependências:** [Hilt](https://developer.android.com/chemical/hilt)
* **Estilização Dinâmica:** [Material Kolor](https://github.com/jordond/MaterialKolor) (Geração de paletas a partir de *seed color*)
* **Carga Inicial:** `DatabaseSeeder` automatizado via parsing de arquivos JSON estruturados em `assets`.

---

## 📂 Estrutura do Banco de Dados Local

O banco de dados é populado no primeiro ciclo de execução a partir do arquivo estático `medications.json`, mapeando as seguintes estruturas relacionais chaves:
1. **Medicamentos de Uso Contínuo:** Catálogo farmacológico base do paciente idoso.
2. **Classes Terapêuticas:** Categorização e agrupamento farmacológico (`MedClass`).
3. **Mapeamento de Riscos e Interações:** Vinculação polimórfica estruturada pelas categorias biológicas do `RiskCategory` (Neurológico, Cardiovascular, Musculoesquelético, etc.).

---

## ⚖️ Licença e Atribuições

Este software utiliza fontes, ícones (Material Symbols) e bibliotecas de terceiros sob suas respectivas licenças de código aberto e termos éticos de uso de software livre, detalhados na tela de **Informações e Atribuições** interna do aplicativo.

---
*GeriOdonto — Tecnologia e inovação aplicada à segurança clínica na Odontogeriatria.*
