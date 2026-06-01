package com.firmino.geriodonto.data

data class MedicalCondition(
    val name: String,
    val description: String,
    val commonMeds: List<String>,
    val commonRisks: List<Risk>,
)

val HipertensaoArterial = MedicalCondition(
    name = "Hipertensão Arterial",
    description = "Considerada uma das doenças mais prevalentes em idosos, aumentando o risco de AVC, infarto e insuficiência cardíaca.",
    commonMeds = listOf(
        "hidroclorotiazida",
        "clortalidona",
        "enalapril",
        "captopril",
        "losartana",
        "valsartana",
        "anlodipino",
        "atenolol",
        "carvedilol",
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
        "metformina",
        "gliclazida",
        "glibenclamida",
        "insulinaGlargina",
        "insulinaRegular",
        "empagliflozina",
        "sitagliptina",
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
        "furosemida",
        "espironolactona",
        "carvedilol",
        "enalapril",
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
        "amiodarona",
        "varfarina",
        "rivaroxabana",
        "acidoAcetilsalicilico",
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
        "donepezila",
        "rivastigmina",
        "galantamina",
        "memantina",
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
        "donepezila",
        "rivastigmina",
        "galantamina",
        "memantina",
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
        "alendronato",
        "risedronato",
        "carbonatoDeCalcio",
        "colecalciferol",
    ),
    commonRisks = listOf(
        Risk.BONE_FRACTURE,
    ),
)

val Artrite = MedicalCondition(
    name = "Artrite",
    description = "Inflamação aguda ou crônica das articulações (como Artrite Reumatoide), causando dor, inchaço e rigidez matinal.",
    commonMeds = listOf(
        "paracetamol",
        "dipirona",
        "ibuprofeno",
        "diclofenaco",
        "tramadol",
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
        "paracetamol",
        "dipirona",
        "ibuprofeno",
        "diclofenaco",
        "tramadol",
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
        "sertralina",
        "escitalopram",
        "fluoxetina",
        "mirtazapina",
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
        "levodopa",
        "carbidopa",
        "pramipexol",
        "selegilina",
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
        "salbutamol",
        "brometoDeIpratropio",
        "budesonida",
        "formoterol",
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
        "salbutamol",
        "brometoDeIpratropio",
        "budesonida",
        "formoterol",
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
        "salbutamol",
        "brometoDeIpratropio",
        "budesonida",
        "formoterol",
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
        "bicalutamida",
        "leuprorrelina",
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
        "tamoxifeno",
        "anastrozol",
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
        "losartana",
        "furosemida",
        "eritropoetina",
        "sevelamer",
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
        "pilocarpina",
        "cevimelina",
        "xilitol",
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
        "colecalciferol",
        "carbonatoDeCalcio",
        "suplementosProteicos",
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
        "colecalciferol",
        "carbonatoDeCalcio",
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
