package com.firmino.geriodonto.companions

enum class MedClass(val text: String, val short_text: String = "") {
    MED_CLASS_DIURETICOS_TIAZIDICOS(text = "Diuréticos Tiazídicos"),
    MED_CLASS_INIBIDORES_ECA(text = "Inibidores da ECA"),
    MED_CLASS_BRA(text = "Bloqueadores do Receptor de Angiotensina (BRA)"),
    MED_CLASS_BLOQUEADORES_CALCIO(text = "Bloqueadores de Canais de Cálcio"),
    MED_CLASS_BETABLOQUEADORES(text = "Betabloqueadores"),
    MED_CLASS_DIURETICOS_ALCA(text = "Diuréticos de Alça"),
    MED_CLASS_ANTAGONISTAS_ALDOSTERONA(text = "Antagonistas da Aldosterona"),

    // Diabetes / Hipoglicemiantes
    MED_CLASS_BIGUANIDAS(text = "Biguanidas"),
    MED_CLASS_SULFONILUREIAS(text = "Sulfonilureias"),
    MED_CLASS_INSULINAS(text = "Insulinas"),
    MED_CLASS_INIBIDORES_SGLT2(text = "Inibidores de SGLT2"),
    MED_CLASS_INIBIDORES_DPP4(text = "Inibidores de DPP-4"),

    // Antiarreicos, Antiarrítmicos e Antitrombóticos
    MED_CLASS_ANTIARRITMICOS(text = "Antiarrítmicos"),
    MED_CLASS_ANTICOAGULANTES(text = "Anticoagulantes"),
    MED_CLASS_ANTIAGREGANTES_PLAQUETARIOS(text = "Antiagregantes Plaquetários"),

    // Sistema Nervoso Central (Cognição, Antidepressivos e Parkinson)
    MED_CLASS_INIBIDORES_ACETILCOLINESTERASE(text = "Inibidores da Acetilcolinesterase"),
    MED_CLASS_ANTAGONISTAS_NMDA(text = "Antagonistas do Receptor NMDA"),
    MED_CLASS_ANTIDEPRESSIVOS(text = "Antidepressivos"),
    MED_CLASS_ANTIPARKINSONIANOS(text = "Antiparkinsonianos"),

    // Saúde Óssea e Suplementação
    MED_CLASS_BIFOSFONATOS(text = "Bifosfonatos"),
    MED_CLASS_SUPLEMENTOS_MINERAIS(text = "Suplementos Minerais"),
    MED_CLASS_VITAMINAS(text = "Vitaminas"),

    // Analgésicos e Anti-inflamatórios
    MED_CLASS_ANALGESICOS_AGENTES(text = "Analgésicos Comuns"),
    MED_CLASS_AINE(text = "Anti-inflamatórios Não Esteroides (AINEs)"),
    MED_CLASS_ANALGESICOS_OPIOIDES(text = "Analgésicos Opioides"),

    // Sistema Respiratório
    MED_CLASS_BRONCODILATADORES(text = "Broncodilatadores"),
    MED_CLASS_CORTICOIDES_INALATORIOS(text = "Corticoides Inalatórios"),

    // Oncologia / Hormonioterapia (Próstata e Mama)
    MED_CLASS_ANTIANDROGENIOS(text = "Antiandrogênios (Próstata)"),
    MED_CLASS_ANALOGOS_GNRH(text = "Análogos de GnRH (Próstata)"),
    MED_CLASS_MODULADORES_ESTROGENIO(text = "Moduladores Seletivos do Receptor de Estrogênio (Mama)"),
    MED_CLASS_INIBIDORES_AROMATASE(text = "Inibidores da Aromatase (Mama)"),

    // Outros / Hematológicos e Renal
    MED_CLASS_ESTIMULANTES_ERITROPOESE(text = "Agentes Estimuladores da Eritropoese"),
    MED_CLASS_QUELANTES_FOSFATO(text = "Quelantes de Fosfato"),
    MED_CLASS_ESTIMULANTES_SALIVARES(text = "Estimulantes Salivares"),
    MED_CLASS_PRODUTOS_AUXILIARES_BUCAIS(text = "Produtos Auxiliares e Substitutos Salivares"),
    MED_CLASS_SUPLEMENTOS_ALIMENTARES(text = "Suplementos e Aminoácidos")
}

/**
 * Categorias principais que agrupam os sistemas anatômicos e tipos de riscos à saúde.
 * Usado para organizar a exibição e filtragem de dados clínicos.
 */
enum class RiskCategory(val description: String) {
    NEUROLOGICAL("Neurológico"),
    CARDIOVASCULAR("Cardiovascular"),
    RESPIRATORY("Respiratório"),
    METABOLIC_ENDOCRINE("Metabólico e Endócrino"),
    RENAL_UROLOGICAL("Renal e Urológico"),
    GASTROINTESTINAL("Gastrointestinal"),
    MUSCULOSKELETAL("Musculoesquelético"),
    SENSORY("Sensorial"),
    DERMATOLOGICAL("Dermatológico"),
    PSYCHIATRIC("Psiquiátrico e Psicológico"),
    INFECTIOUS("Infeccioso e Imunológico"),
    ORAL_AND_SPEECH("Saúde Bucal e Fonoaudiológica"),
    GENERAL("Geral e Sintomas"),
}

/**
 * Mapeamento detalhado de riscos clínicos, complicações de saúde e sintomas.
 *
 * @property text O nome amigável e traduzido (PT-BR) para exibição na interface de usuário.
 * @property category A categoria de sistema corporal à qual este risco pertence.
 */
enum class Risk(val text: String, val category: RiskCategory) {
    // ==========================================
    // SAÚDE BUCAL E FONOAUDIOLÓGICA
    // ==========================================

    /**
     * Disfagia. Dificuldade grave de engolir alimentos ou líquidos.
     * Risco altíssimo de broncoaspiração (quando a comida vai para o pulmão).
     */
    DYSPHAGIA("Disfagia (Dificuldade de Deglutição)", RiskCategory.ORAL_AND_SPEECH),

    /**
     * Dificuldade ou dor ao mastigar, geralmente causada por perda dentária,
     * próteses mal adaptadas ou fraqueza muscular, podendo levar à desnutrição.
     */
    CHEWING_IMPAIRMENT("Comprometimento da Mastigação", RiskCategory.ORAL_AND_SPEECH),

    /**
     * Disartria, afasia ou disfonia. Dificuldade de articular as palavras e se comunicar,
     * frequentemente associada a sequelas de AVC ou doenças neurológicas.
     */
    SPEECH_IMPAIRMENT("Dificuldade de Fala", RiskCategory.ORAL_AND_SPEECH),

    /**
     * Redução na produção de saliva (Hipossalivação) e sensação de boca seca (Xerostomia).
     * Dificulta a formação do bolo alimentar e aumenta o risco de infecções e cáries.
     */
    XEROSTOMIA_HYPOSALIVATION("Xerostomia / Hipossalivação", RiskCategory.ORAL_AND_SPEECH),

    /**
     * Propensão ao desenvolvimento de lesões de cárie agressivas e perda de dentes,
     * muitas vezes agravada pela falta de saliva ou dificuldade de higienização motora.
     */
    TOOTH_DECAY("Risco Elevado de Cáries e Perda Dentária", RiskCategory.ORAL_AND_SPEECH),

    /**
     * Infecções oportunistas na cavidade bucal, sendo a Candidíase Oral ("sapinho")
     * a mais comum em pacientes imunodeprimidos ou que usam corticoides inalatórios.
     */
    ORAL_INFECTION("Infecção Oral (Ex: Candidíase)", RiskCategory.ORAL_AND_SPEECH),

    // ==========================================
    // NEUROLÓGICOS
    // ==========================================

    /** Acidente Vascular Cerebral. Interrupção do fluxo sanguíneo no cérebro (isquêmico ou hemorrágico). */
    STROKE("Acidente Vascular Cerebral (AVC)", RiskCategory.NEUROLOGICAL),

    /** Descargas elétricas anormais no cérebro que causam alterações de movimento, comportamento ou consciência. */
    SEIZURE("Convulsões / Epilepsia", RiskCategory.NEUROLOGICAL),

    /** Declínio progressivo das funções cognitivas e memória, comum em idosos. */
    DEMENTIA_ALZHEIMER("Demência / Alzheimer", RiskCategory.NEUROLOGICAL),

    /** Qualquer outra complicação ou risco associado ao sistema nervoso central ou periférico não listado. */
    GENERAL_NEUROLOGICAL("Outras Complicações Neurológicas", RiskCategory.NEUROLOGICAL),


    // ==========================================
    // CARDIOVASCULARES
    // ==========================================

    /** Bloqueio do fluxo sanguíneo para o músculo cardíaco, causando dano aos tecidos. */
    HEART_ATTACK("Infarto Agudo do Miocárdio", RiskCategory.CARDIOVASCULAR),

    /** Incapacidade do coração de bombear sangue de forma eficiente para atender às necessidades do corpo. */
    HEART_FAILURE("Insuficiência Cardíaca", RiskCategory.CARDIOVASCULAR),

    /** Frequência ou ritmo anormal dos batimentos cardíacos (muito rápido, muito devagar ou irregular). */
    ARRHYTHMIA("Arritmia Cardíaca", RiskCategory.CARDIOVASCULAR),

    /** Formação de coágulos sanguíneos em veias profundas, geralmente nas pernas, com risco de embolia. */
    THROMBOSIS("Trombose Venosa Profunda (TVP)", RiskCategory.CARDIOVASCULAR),

    /** Elevação súbita e severa da pressão arterial que pode causar danos a órgãos. */
    HYPERTENSION_CRISIS("Crise Hipertensiva", RiskCategory.CARDIOVASCULAR),

    /** Outros problemas relacionados ao coração e aos vasos sanguíneos. */
    GENERAL_CARDIOVASCULAR("Outras Complicações Cardiovasculares", RiskCategory.CARDIOVASCULAR),


    // ==========================================
    // RESPIRATÓRIOS
    // ==========================================

    /** Condição em que o sistema respiratório não consegue manter as trocas gasosas adequadas (oxigênio/gás carbônico). */
    RESPIRATORY_FAILURE("Insuficiência Respiratória", RiskCategory.RESPIRATORY),

    /** Doenças pulmonares inflamatórias crônicas que obstruem o fluxo de ar (ex: bronquite crônica, enfisema). */
    COPD("Doença Pulmonar Obstrutiva Crônica (DPOC)", RiskCategory.RESPIRATORY),

    /** Bloqueio de uma artéria nos pulmões, frequentemente causado por um coágulo que viajou de outra parte do corpo (TVP). */
    PULMONARY_EMBOLISM("Embolia Pulmonar", RiskCategory.RESPIRATORY),

    /** Outros riscos ligados aos pulmões e vias aéreas, como broncoaspiração. */
    GENERAL_RESPIRATORY("Outros Riscos Respiratórios", RiskCategory.RESPIRATORY),


    // ==========================================
    // METABÓLICOS E ENDÓCRINOS
    // ==========================================

    /** Riscos decorrentes da diabetes, como hipoglicemia (açúcar baixo), cetoacidose ou neuropatia diabética. */
    DIABETIC_COMPLICATION(
        "Complicações Diabéticas (Ex: Hipoglicemia)",
        RiskCategory.METABOLIC_ENDOCRINE,
    ),

    /** Problemas na glândula tireoide que afetam o metabolismo (hipotireoidismo ou hipertireoidismo). */
    THYROID_DYSFUNCTION("Disfunção Tireoidiana", RiskCategory.METABOLIC_ENDOCRINE),

    /** Desequilíbrios metabólicos em geral, como alterações de sódio, potássio ou cálcio no sangue. */
    GENERAL_METABOLIC("Outras Complicações Metabólicas", RiskCategory.METABOLIC_ENDOCRINE),


    // ==========================================
    // RENAIS E UROLÓGICOS
    // ==========================================

    /** Perda súbita ou crônica da capacidade dos rins de filtrar resíduos do sangue. */
    KIDNEY_FAILURE("Insuficiência Renal", RiskCategory.RENAL_UROLOGICAL),

    /** Infecção em qualquer parte do sistema urinário (rins, bexiga, uretra). */
    URINARY_INFECTION("Infecção do Trato Urinário (ITU)", RiskCategory.RENAL_UROLOGICAL),

    /** Problemas urológicos variados, como retenção urinária ou cálculos renais. */
    GENERAL_RENAL("Outras Complicações Renais/Urológicas", RiskCategory.RENAL_UROLOGICAL),


    // ==========================================
    // GASTROINTESTINAIS
    // ==========================================

    /** Sangramento em qualquer parte do trato digestivo (estômago, intestinos). */
    GASTROINTESTINAL_BLEEDING("Hemorragia Digestiva", RiskCategory.GASTROINTESTINAL),

    /** Feridas que se desenvolvem no revestimento do estômago, esôfago ou intestino delgado. */
    PEPTIC_ULCER("Úlcera Gástrica/Péptica", RiskCategory.GASTROINTESTINAL),

    /** Outros problemas digestivos, como constipação severa, refluxo ou obstrução intestinal. */
    GENERAL_GASTROINTESTINAL(
        "Outras Complicações Gastrointestinais",
        RiskCategory.GASTROINTESTINAL,
    ),


    // ==========================================
    // MUSCULOESQUELÉTICOS / MOTORES
    // ==========================================

    /** Quebra parcial ou total de um osso, um risco alto em pacientes com osteoporose ou perda de equilíbrio. */
    BONE_FRACTURE("Fratura Óssea", RiskCategory.MUSCULOSKELETAL),

    /** Limitação na capacidade de mover partes do corpo, necessitando muitas vezes de fisioterapia. */
    REDUCED_MOBILITY("Redução de Movimento", RiskCategory.MUSCULOSKELETAL),

    /** Instabilidade postural que aumenta substancialmente o risco de quedas do paciente. */
    LOSS_OF_BALANCE("Perda de Equilíbrio / Risco de Queda", RiskCategory.MUSCULOSKELETAL),

    /** Sarcopenia. Perda de massa e força muscular, comum em pacientes acamados ou idosos. */
    MUSCLE_WASTING("Perda de Massa Muscular (Sarcopenia)", RiskCategory.MUSCULOSKELETAL),


    // ==========================================
    // SENSORIAIS
    // ==========================================

    /** Diminuição acentuada ou perda total da visão, como glaucoma ou catarata não tratada. */
    VISION_LOSS("Complicações Visuais / Cegueira", RiskCategory.SENSORY),

    /** Diminuição acentuada ou perda total da audição, afetando a comunicação do paciente. */
    HEARING_LOSS("Perda Auditiva / Surdez", RiskCategory.SENSORY),

    /** Danos aos nervos periféricos causando dormência, formigamento ou falta de sensibilidade à dor/temperatura. */
    PERIPHERAL_NEUROPATHY("Perda de Sensibilidade (Neuropatia)", RiskCategory.SENSORY),


    // ==========================================
    // DERMATOLÓGICOS
    // ==========================================

    /** Feridas na pele e tecidos subjacentes resultantes de pressão prolongada. Muito crítico em pacientes imobilizados. */
    PRESSURE_ULCER("Lesão por Pressão (Escara)", RiskCategory.DERMATOLOGICAL),

    /** Alergias cutâneas, dermatites, infecções fúngicas ou outros problemas de pele. */
    GENERAL_DERMATOLOGICAL("Outras Complicações Dermatológicas", RiskCategory.DERMATOLOGICAL),


    // ==========================================
    // PSIQUIÁTRICOS / SAÚDE MENTAL
    // ==========================================

    /** Transtornos de humor persistentes que afetam o bem-estar psicológico e a adesão a tratamentos. */
    DEPRESSION_ANXIETY("Depressão e/ou Ansiedade", RiskCategory.PSYCHIATRIC),

    /** Estado súbito de confusão mental severa e desorientação, comum em idosos internados ou com infecções. */
    DELIRIUM("Delirium / Confusão Mental", RiskCategory.PSYCHIATRIC),


    // ==========================================
    // INFECCIOSOS E IMUNOLÓGICOS
    // ==========================================

    /** Resposta extrema e desregulada do corpo a uma infecção, gerando risco iminente de morte. */
    SEPSIS("Sepse / Infecção Generalizada", RiskCategory.INFECTIOUS),

    /** Risco de contrair infecções durante a estadia em ambientes hospitalares ou clínicas (ex: superbactérias). */
    HOSPITAL_INFECTION("Risco de Infecção Hospitalar", RiskCategory.INFECTIOUS),


    // ==========================================
    // GERAIS / SINTOMAS ISOLADOS
    // ==========================================

    /** Relato de dor que precisa ser monitorada, avaliada e controlada clinicamente. */
    CHRONIC_PAIN("Dor Aguda/Crônica", RiskCategory.GENERAL),

    /** Cansaço extremo persistente que não melhora significativamente com repouso. */
    CHRONIC_FATIGUE("Fadiga Crônica", RiskCategory.GENERAL),

    /** Deficiência de nutrientes essenciais por má alimentação ou problemas de absorção. */
    MALNUTRITION("Desnutrição", RiskCategory.GENERAL)
}

data class Med(
    val name: String,
    val principleActive: String = "",
    val medClass: MedClass,
)

data class MedicalCondition(
    val name: String,
    val description: String,
    val commonMeds: List<Med>,
    val commonRisks: List<Risk>,
)

val Hidroclorotiazida = Med(
    name = "Hidroclorotiazida",
    principleActive = "Hidroclorotiazida",
    medClass = MedClass.MED_CLASS_DIURETICOS_TIAZIDICOS,
)
val Clortalidona = Med(
    name = "Clortalidona",
    principleActive = "Clortalidona",
    medClass = MedClass.MED_CLASS_DIURETICOS_TIAZIDICOS,
)
val Enalapril = Med(
    name = "Enalapril",
    principleActive = "Maleato de Enalapril",
    medClass = MedClass.MED_CLASS_INIBIDORES_ECA,
)
val Captopril = Med(
    name = "Captopril",
    principleActive = "Captopril",
    medClass = MedClass.MED_CLASS_INIBIDORES_ECA,
)
val Losartana = Med(
    name = "Losartana",
    principleActive = "Losartana Potássica",
    medClass = MedClass.MED_CLASS_BRA,
)
val Valsartana = Med(
    name = "Valsartana",
    principleActive = "Valsartana",
    medClass = MedClass.MED_CLASS_BRA,
)
val Anlodipino = Med(
    name = "Anlodipino",
    principleActive = "Besilato de Anlodipino",
    medClass = MedClass.MED_CLASS_BLOQUEADORES_CALCIO,
)
val Atenolol = Med(
    name = "Atenolol",
    principleActive = "Atenolol",
    medClass = MedClass.MED_CLASS_BETABLOQUEADORES,
)
val Carvedilol = Med(
    name = "Carvedilol",
    principleActive = "Carvedilol",
    medClass = MedClass.MED_CLASS_BETABLOQUEADORES,
)
val Furosemida = Med(
    name = "Furosemida",
    principleActive = "Furosemida",
    medClass = MedClass.MED_CLASS_DIURETICOS_ALCA,
)
val Espironolactona = Med(
    name = "Espironolactona",
    principleActive = "Espironolactona",
    medClass = MedClass.MED_CLASS_ANTAGONISTAS_ALDOSTERONA,
)
val Metformina = Med(
    name = "Metformina",
    principleActive = "Cloridrato de Metformina",
    medClass = MedClass.MED_CLASS_BIGUANIDAS,
)
val Gliclazida = Med(
    name = "Gliclazida",
    principleActive = "Gliclazida",
    medClass = MedClass.MED_CLASS_SULFONILUREIAS,
)
val Glibenclamida = Med(
    name = "Glibenclamida",
    principleActive = "Glibenclamida",
    medClass = MedClass.MED_CLASS_SULFONILUREIAS,
)
val InsulinaGlargina = Med(
    name = "Insulina Glargina",
    principleActive = "Insulina Glargina",
    medClass = MedClass.MED_CLASS_INSULINAS,
)
val InsulinaRegular = Med(
    name = "Insulina Regular",
    principleActive = "Insulina Humana Regular",
    medClass = MedClass.MED_CLASS_INSULINAS,
)
val Empagliflozina = Med(
    name = "Empagliflozina",
    principleActive = "Empagliflozina",
    medClass = MedClass.MED_CLASS_INIBIDORES_SGLT2,
)
val Sitagliptina = Med(
    name = "Sitagliptina",
    principleActive = "Fosfato de Sitagliptina",
    medClass = MedClass.MED_CLASS_INIBIDORES_DPP4,
)
val Amiodarona = Med(
    name = "Amiodarona",
    principleActive = "Cloridrato de Amiodarona",
    medClass = MedClass.MED_CLASS_ANTIARRITMICOS,
)
val Varfarina = Med(
    name = "Varfarina",
    principleActive = "Varfarina Sódica",
    medClass = MedClass.MED_CLASS_ANTICOAGULANTES,
)
val Rivaroxabana = Med(
    name = "Rivaroxabana",
    principleActive = "Rivaroxabana",
    medClass = MedClass.MED_CLASS_ANTICOAGULANTES,
)
val AcidoAcetilsalicilico = Med(
    name = "Ácido Acetilsalicílico",
    principleActive = "Ácido Acetilsalicílico",
    medClass = MedClass.MED_CLASS_ANTIAGREGANTES_PLAQUETARIOS,
)
val Donepezila = Med(
    name = "Donepezila",
    principleActive = "Cloridrato de Donepezila",
    medClass = MedClass.MED_CLASS_INIBIDORES_ACETILCOLINESTERASE,
)
val Rivastigmina = Med(
    name = "Rivastigmina",
    principleActive = "Tartarato de Rivastigmina",
    medClass = MedClass.MED_CLASS_INIBIDORES_ACETILCOLINESTERASE,
)
val Galantamina = Med(
    name = "Galantamina",
    principleActive = "Bromidrato de Galantamina",
    medClass = MedClass.MED_CLASS_INIBIDORES_ACETILCOLINESTERASE,
)
val Memantina = Med(
    name = "Memantina",
    principleActive = "Cloridrato de Memantina",
    medClass = MedClass.MED_CLASS_ANTAGONISTAS_NMDA,
)
val Alendronato = Med(
    name = "Alendronato",
    principleActive = "Alendronato de Sódio",
    medClass = MedClass.MED_CLASS_BIFOSFONATOS,
)
val Risedronato = Med(
    name = "Risedronato",
    principleActive = "Risedronato Sódico",
    medClass = MedClass.MED_CLASS_BIFOSFONATOS,
)
val CarbonatoDeCalcio = Med(
    name = "Carbonato de Cálcio",
    principleActive = "Carbonato de Cálcio",
    medClass = MedClass.MED_CLASS_SUPLEMENTOS_MINERAIS,
)
val Colecalciferol = Med(
    name = "Colecalciferol",
    principleActive = "Colecalciferol (Vitamina D3)",
    medClass = MedClass.MED_CLASS_VITAMINAS,
)
val Paracetamol = Med(
    name = "Paracetamol",
    principleActive = "Paracetamol",
    medClass = MedClass.MED_CLASS_ANALGESICOS_AGENTES,
)
val Dipirona = Med(
    name = "Dipirona",
    principleActive = "Dipirona Monoidratada",
    medClass = MedClass.MED_CLASS_ANALGESICOS_AGENTES,
)
val Ibuprofeno = Med(
    name = "Ibuprofeno",
    principleActive = "Ibuprofeno",
    medClass = MedClass.MED_CLASS_AINE,
)
val Diclofenaco = Med(
    name = "Diclofenaco",
    principleActive = "Diclofenaco Sódico/Potássico",
    medClass = MedClass.MED_CLASS_AINE,
)
val Tramadol = Med(
    name = "Tramadol",
    principleActive = "Cloridrato de Tramadol",
    medClass = MedClass.MED_CLASS_ANALGESICOS_OPIOIDES,
)
val Sertralina = Med(
    name = "Sertralina",
    principleActive = "Cloridrato de Sertralina",
    medClass = MedClass.MED_CLASS_ANTIDEPRESSIVOS,
)
val Escitalopram = Med(
    name = "Escitalopram",
    principleActive = "Oxalato de Escitalopram",
    medClass = MedClass.MED_CLASS_ANTIDEPRESSIVOS,
)
val Fluoxetina = Med(
    name = "Fluoxetina",
    principleActive = "Cloridrato de Fluoxetina",
    medClass = MedClass.MED_CLASS_ANTIDEPRESSIVOS,
)
val Mirtazapina = Med(
    name = "Mirtazapina",
    principleActive = "Mirtazapina",
    medClass = MedClass.MED_CLASS_ANTIDEPRESSIVOS,
)
val Levodopa = Med(
    name = "Levodopa",
    principleActive = "Levodopa",
    medClass = MedClass.MED_CLASS_ANTIPARKINSONIANOS,
)
val Carbidopa = Med(
    name = "Carbidopa",
    principleActive = "Carbidopa",
    medClass = MedClass.MED_CLASS_ANTIPARKINSONIANOS,
)
val Pramipexol = Med(
    name = "Pramipexol",
    principleActive = "Dicloridrato de Pramipexol",
    medClass = MedClass.MED_CLASS_ANTIPARKINSONIANOS,
)
val Selegilina = Med(
    name = "Selegilina",
    principleActive = "Cloridrato de Selegilina",
    medClass = MedClass.MED_CLASS_ANTIPARKINSONIANOS,
)
val Salbutamol = Med(
    name = "Salbutamol",
    principleActive = "Sulfato de Salbutamol",
    medClass = MedClass.MED_CLASS_BRONCODILATADORES,
)
val BrometoDeIpratropio = Med(
    name = "Brometo de Ipratrópio",
    principleActive = "Brometo de Ipratrópio",
    medClass = MedClass.MED_CLASS_BRONCODILATADORES,
)
val Budesonida = Med(
    name = "Budesonida",
    principleActive = "Budesonida",
    medClass = MedClass.MED_CLASS_CORTICOIDES_INALATORIOS,
)
val Formoterol = Med(
    name = "Formoterol",
    principleActive = "Fumarato de Formoterol Dihidratado",
    medClass = MedClass.MED_CLASS_BRONCODILATADORES,
)
val Bicalutamida = Med(
    name = "Bicalutamida",
    principleActive = "Bicalutamida",
    medClass = MedClass.MED_CLASS_ANTIANDROGENIOS,
)
val Leuprorrelina = Med(
    name = "Leuprorrelina",
    principleActive = "Acetato de Leuprorrelina",
    medClass = MedClass.MED_CLASS_ANALOGOS_GNRH,
)
val Tamoxifeno = Med(
    name = "Tamoxifeno",
    principleActive = "Citrato de Tamoxifeno",
    medClass = MedClass.MED_CLASS_MODULADORES_ESTROGENIO,
)
val Anastrozol = Med(
    name = "Anastrozol",
    principleActive = "Anastrozol",
    medClass = MedClass.MED_CLASS_INIBIDORES_AROMATASE,
)
val Eritropoetina = Med(
    name = "Eritropoetina",
    principleActive = "Eritropoetina Humana Recombinante",
    medClass = MedClass.MED_CLASS_ESTIMULANTES_ERITROPOESE,
)
val Sevelamer = Med(
    name = "Sevelâmer",
    principleActive = "Cloridrato/Carbonato de Sevelâmer",
    medClass = MedClass.MED_CLASS_QUELANTES_FOSFATO,
)
val Pilocarpina = Med(
    name = "Pilocarpina",
    principleActive = "Cloridrato de Pilocarpina",
    medClass = MedClass.MED_CLASS_ESTIMULANTES_SALIVARES,
)
val Cevimelina = Med(
    name = "Cevimelina",
    principleActive = "Cloridrato de Cevimelina",
    medClass = MedClass.MED_CLASS_ESTIMULANTES_SALIVARES,
)
val Xilitol = Med(
    name = "Substitutos Salivares com Xilitol",
    principleActive = "Substitutos Salivares com Xilitol",
    medClass = MedClass.MED_CLASS_PRODUTOS_AUXILIARES_BUCAIS,
)
val SuplementosProteicos = Med(
    name = "Suplementos Proteicos",
    principleActive = "Proteínas isoladas/concentradas",
    medClass = MedClass.MED_CLASS_SUPLEMENTOS_ALIMENTARES,
)
val AminoacidosEssenciais = Med(
    name = "Aminoácidos Essenciais",
    principleActive = "Mix de Aminoácidos Essenciais",
    medClass = MedClass.MED_CLASS_SUPLEMENTOS_ALIMENTARES,
)
val Testosterona = Med(
    name = "Testosterona",
    principleActive = "Testosterona",
    medClass = MedClass.MED_CLASS_SUPLEMENTOS_ALIMENTARES,
)


val HipertensaoArterial = MedicalCondition(
    name = "Hipertensão Arterial",
    description = "Considerada uma das doenças mais prevalentes em idosos, aumentando o risco de AVC, infarto e insuficiência cardíaca.",
    commonMeds = listOf(
        Hidroclorotiazida,
        Clortalidona,
        Enalapril,
        Captopril,
        Losartana,
        Valsartana,
        Anlodipino,
        Atenolol,
        Carvedilol,
    ),
    commonRisks = listOf(
        Risk.STROKE,
        Risk.HEART_FAILURE,
        Risk.HEART_ATTACK,
        Risk.GENERAL_RENAL,
        Risk.HYPERTENSION_CRISIS,
    ),
)

val DiabetesMellitusTipo2 = MedicalCondition(
    name = "Diabetes Mellitus Tipo 2",
    description = "Muito comum após os 60 anos, podendo causar complicações cardiovasculares, renais, visuais e neurológicas.",
    commonMeds = listOf(
        Metformina,
        Gliclazida,
        Glibenclamida,
        InsulinaGlargina,
        InsulinaRegular,
        Empagliflozina,
        Sitagliptina,
    ),
    commonRisks = listOf(
        Risk.DIABETIC_COMPLICATION,
        Risk.KIDNEY_FAILURE,
        Risk.VISION_LOSS,
        Risk.PERIPHERAL_NEUROPATHY,
    ),
)

val InsuficienciaCardiaca = MedicalCondition(
    name = "Insuficiência Cardíaca",
    description = "Incapacidade do coração de bombear sangue em volume adequado, causando acúmulo de líquidos, falta de ar e fadiga crônica.",
    commonMeds = listOf(
        Furosemida,
        Espironolactona,
        Carvedilol,
        Enalapril,
    ),
    commonRisks = listOf(
        Risk.HEART_FAILURE,
        Risk.RESPIRATORY_FAILURE,
        Risk.CHRONIC_FATIGUE,
    ),
)

val Arritmia = MedicalCondition(
    name = "Arritmia",
    description = "Alteração no ritmo ou frequência dos batimentos cardíacos, aumentando substancialmente o risco de formação de coágulos e AVC.",
    commonMeds = listOf(
        Amiodarona,
        Varfarina,
        Rivaroxabana,
        AcidoAcetilsalicilico,
    ),
    commonRisks = listOf(
        Risk.ARRHYTHMIA,
        Risk.STROKE,
    ),
)

val Alzheimer = MedicalCondition(
    name = "Alzheimer",
    description = "Tipo mais comum de demência, caracterizada pelo declínio progressivo da memória, cognição e capacidade de realizar tarefas diárias.",
    commonMeds = listOf(
        Donepezila,
        Rivastigmina,
        Galantamina,
        Memantina,
    ),
    commonRisks = listOf(
        Risk.DEMENTIA_ALZHEIMER,
        Risk.LOSS_OF_BALANCE,
        Risk.DELIRIUM,
    ),
)

val Demencia = MedicalCondition(
    name = "Demência",
    description = "Termo abrangente para o declínio cognitivo grave (incluindo demência vascular ou por corpos de Lewy) que interfere na independência do indivíduo.",
    commonMeds = listOf(
        Donepezila,
        Rivastigmina,
        Galantamina,
        Memantina,
    ),
    commonRisks = listOf(
        Risk.DEMENTIA_ALZHEIMER,
        Risk.DELIRIUM,
    ),
)

val Osteoporose = MedicalCondition(
    name = "Osteoporose",
    description = "Redução acentuada da densidade e qualidade óssea, tornando os ossos porosos e extremamente suscetíveis a fraturas de baixo impacto.",
    commonMeds = listOf(
        Alendronato,
        Risedronato,
        CarbonatoDeCalcio,
        Colecalciferol,
    ),
    commonRisks = listOf(
        Risk.BONE_FRACTURE,
    ),
)

val Artrite = MedicalCondition(
    name = "Artrite",
    description = "Inflamação aguda ou crônica das articulações (como Artrite Reumatoide), causando dor, inchaço e rigidez matinal.",
    commonMeds = listOf(
        Paracetamol,
        Dipirona,
        Ibuprofeno,
        Diclofenaco,
        Tramadol,
    ),
    commonRisks = listOf(
        Risk.REDUCED_MOBILITY,
        Risk.CHRONIC_PAIN,
    ),
)

val Artrose = MedicalCondition(
    name = "Artrose (Osteoartrite)",
    description = "Desgaste mecânico e progressivo da cartilagem articular, muito comum em joelhos, quadris e coluna com o envelhecimento.", // Alterado: Substituída a palavra "Desgaste" por explicação fisiopatológica.
    commonMeds = listOf(
        Paracetamol,
        Dipirona,
        Ibuprofeno,
        Diclofenaco,
        Tramadol,
    ),
    commonRisks = listOf(
        Risk.REDUCED_MOBILITY,
        Risk.CHRONIC_PAIN,
        Risk.LOSS_OF_BALANCE,
    ),
)

val Depressao = MedicalCondition(
    name = "Depressão",
    description = "Transtorno de humor frequentemente subdiagnosticado em idosos, podendo estar associado ao isolamento social, perdas e doenças crônicas.",
    commonMeds = listOf(
        Sertralina,
        Escitalopram,
        Fluoxetina,
        Mirtazapina,
    ),
    commonRisks = listOf(
        Risk.DEPRESSION_ANXIETY,
        Risk.MALNUTRITION,
        Risk.CHRONIC_FATIGUE,
    ),
)

val Parkinson = MedicalCondition(
    name = "Doença de Parkinson",
    description = "Doença neurodegenerativa que afeta principalmente movimentos, causando tremores, rigidez e lentidão (bradicinesia).",
    commonMeds = listOf(
        Levodopa,
        Carbidopa,
        Pramipexol,
        Selegilina,
    ),
    commonRisks = listOf(
        Risk.REDUCED_MOBILITY,
        Risk.LOSS_OF_BALANCE,
        Risk.DYSPHAGIA,
    ),
)

val Dpoc = MedicalCondition(
    name = "Doença Pulmonar Obstrutiva Crônica (DPOC)",
    description = "Grupo de doenças pulmonares crônicas (incluindo bronquite e enfisema) que obstruem as vias aéreas, tornando a respiração difícil.",
    commonMeds = listOf(
        Salbutamol,
        BrometoDeIpratropio,
        Budesonida,
        Formoterol,
    ),
    commonRisks = listOf(
        Risk.COPD,
        Risk.RESPIRATORY_FAILURE,
        Risk.ORAL_INFECTION,
    ),
)

val Bronquite = MedicalCondition(
    name = "Bronquite Crônica",
    description = "Inflamação crônica dos brônquios com produção excessiva de muco, tosse persistente e componente formador da DPOC.",
    commonMeds = listOf(
        Salbutamol,
        BrometoDeIpratropio,
        Budesonida,
        Formoterol,
    ),
    commonRisks = listOf(
        Risk.COPD,
        Risk.RESPIRATORY_FAILURE,
    ),
)

val Enfisema = MedicalCondition(
    name = "Enfisema Pulmonar",
    description = "Destruição progressiva dos alvéolos pulmonares, diminuindo a capacidade dos pulmões de realizar as trocas de oxigênio.",
    commonMeds = listOf(
        Salbutamol,
        BrometoDeIpratropio,
        Budesonida,
        Formoterol,
    ),
    commonRisks = listOf(
        Risk.COPD,
        Risk.RESPIRATORY_FAILURE,
    ),
)

val CancerProstata = MedicalCondition(
    name = "Câncer de Próstata",
    description = "Neoplasia maligna na glândula prostática, podendo causar obstrução do fluxo urinário e dor pélvica.",
    commonMeds = listOf(
        Bicalutamida,
        Leuprorrelina,
    ),
    commonRisks = listOf(
        Risk.URINARY_INFECTION,
        Risk.GENERAL_RENAL,
    ),
)

val CancerMama = MedicalCondition(
    name = "Câncer de Mama", // Alterado: Adicionado acento em "Câncer".
    description = "Neoplasia maligna no tecido mamário. Os tratamentos hormonais podem impactar a saúde óssea e o humor.",
    commonMeds = listOf(
        Tamoxifeno,
        Anastrozol,
    ),
    commonRisks = listOf(
        Risk.DEPRESSION_ANXIETY,
        Risk.BONE_FRACTURE,
    ),
)

val DoencaRenal = MedicalCondition(
    name = "Doença Renal Crônica",
    description = "Perda lenta e progressiva da função dos rins, exigindo controle rigoroso de pressão arterial e, em fases avançadas, diálise.",
    commonMeds = listOf(
        Losartana,
        Furosemida,
        Eritropoetina,
        Sevelamer,
    ),
    commonRisks = listOf(
        Risk.KIDNEY_FAILURE,
        Risk.HYPERTENSION_CRISIS,
        Risk.GENERAL_METABOLIC,
    ),
)

val Xerostomia = MedicalCondition(
    name = "Xerostomia / Hipossalivação",
    description = "Sensação subjetiva de boca seca e redução real do fluxo salivar, impactando fortemente a qualidade de vida e digestão.",
    commonMeds = listOf(
        Pilocarpina,
        Cevimelina,
        Xilitol,
    ),
    commonRisks = listOf(
        Risk.DYSPHAGIA,
        Risk.CHEWING_IMPAIRMENT,
        Risk.SPEECH_IMPAIRMENT,
        Risk.XEROSTOMIA_HYPOSALIVATION,
        Risk.TOOTH_DECAY,
        Risk.ORAL_INFECTION,
        Risk.MALNUTRITION,
    ),
)

val Fragilidade = MedicalCondition(
    name = "Síndrome de Fragilidade",
    description = "Síndrome clínica de vulnerabilidade sistêmica a estressores, caracterizada por fraqueza, lentidão e exaustão.",
    commonMeds = listOf(
        Colecalciferol,
        CarbonatoDeCalcio,
        SuplementosProteicos,
    ),
    commonRisks = listOf(
        Risk.MUSCLE_WASTING,
        Risk.CHRONIC_FATIGUE,
        Risk.LOSS_OF_BALANCE,
        Risk.BONE_FRACTURE,
    ),
)

val Sarcopenia = MedicalCondition(
    name = "Sarcopenia",
    description = "Doença muscular caracterizada pela perda acelerada de massa, força e desempenho físico muscular.",
    commonMeds = listOf(
        Colecalciferol,
        CarbonatoDeCalcio,
    ),
    commonRisks = listOf(
        Risk.MUSCLE_WASTING,
        Risk.REDUCED_MOBILITY,
        Risk.LOSS_OF_BALANCE,
    ),
)
val medicalConditionsList = listOf(
    HipertensaoArterial,
    DiabetesMellitusTipo2,
    InsuficienciaCardiaca,
    Arritmia,
    Alzheimer,
    Demencia,
    Osteoporose,
    Artrite,
    Artrose,
    Depressao,
    Parkinson,
    Dpoc,
    Bronquite,
    Enfisema,
    CancerProstata,
    CancerMama,
    DoencaRenal,
    Xerostomia,
    Fragilidade,
    Sarcopenia
)