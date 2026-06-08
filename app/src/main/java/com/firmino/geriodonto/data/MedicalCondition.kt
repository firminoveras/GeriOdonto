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
        "insulina_glargina",
        "insulina",
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
        "acido_acetilsalicilico",
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
        "carbonato_calcio",
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
        "diclofenaco_sodico",
        "tramadol",
    ),
    commonRisks = listOf(
        Risk.REDUCED_MOBILITY,
        Risk.CHRONIC_PAIN,
    ),
)

val Artrose = MedicalCondition(
    name = "Artrose (Osteoartrite)",
    description = "Desgaste mecânico e progressivo da cartilagem articular, muito comum em joelhos, quadris e coluna com o envelhecimento.",
    commonMeds = listOf(
        "paracetamol",
        "dipirona",
        "ibuprofeno",
        "diclofenaco_sodico",
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

val Sarcopenia = MedicalCondition(
    name = "Sarcopenia",
    description = "Doença muscular caracterizada pela perda acelerada de massa, força e desempenho físico muscular.",
    commonMeds = listOf(
        "colecalciferol",
        "carbonato_calcio",
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
    Sarcopenia
)

val Fragilidade = MedicalCondition(
    name = "Fragilidade",
    description = "Vulnerabilidade sistêmica a estressores, caracterizada por fraqueza, lentidão e exaustão.",
    commonMeds = listOf(
        "colecalciferol",
        "carbonato_calcio",
        "suplementos_proteicos",
    ),
    commonRisks = listOf(
        Risk.MUSCLE_WASTING,
        Risk.CHRONIC_FATIGUE,
        Risk.LOSS_OF_BALANCE,
        Risk.BONE_FRACTURE,
    ),
)

val HistoricoQuedas = MedicalCondition(
    name = "Histórico de Quedas",
    description = "Síndrome geriátrica multifatorial caracterizada pela perda de equilíbrio e quedas recorrentes. É fortemente agravada pelo uso de medicamentos que causam sedação, tontura ou hipotensão ortostática (FRIDs).",
    commonMeds = listOf(
        "diazepam",
        "clonazepam",
        "midazolam",
        "alprazolam",
        "amitriptilina",
        "furosemida",
        "clortalidona",
        "hidroclorotiazida",
        "levodopa",
        "quetiapina",
        "haloperidol"
    ),
    commonRisks = listOf(
        Risk.LOSS_OF_BALANCE,
        Risk.BONE_FRACTURE,
        Risk.REDUCED_MOBILITY,
        Risk.CNS_DEPRESSION,
        Risk.DELIRIUM
    )
)

val IdadeAvancada = MedicalCondition(
    name = "Idade Avançada",
    description = "Paciente com reserva fisiológica reduzida, especialmente a taxa de filtração glomerular e a eficácia de mecanismos compensatórios cerebrais.",
    commonMeds = listOf(),
    commonRisks = listOf(
        Risk.RENAL_IMPAIRMENT,
        Risk.CNS_DEPRESSION,
        Risk.LOSS_OF_BALANCE
    )
)

val BaixoPeso= MedicalCondition(
    name = "Baixo Peso",
    description = "A perda de peso não intencional em idosos é um marcador de vulnerabilidade, indicando reserva metabólica reduzida, risco aumentado de infecções pós-operatórias e retardo na cicatrização tecidual.",
    commonMeds = listOf(
        "suplementos_proteicos",
        "colecalciferol",
        "multivitaminicos"
    ),
    commonRisks = listOf(
        Risk.MALNUTRITION,
        Risk.MUSCLE_WASTING,
        Risk.ORAL_INFECTION,
        Risk.THERAPEUTIC_FAILURE,
    )
)

val Sobrepeso = MedicalCondition(
    name = "Sobrepeso",
    description = "Presença de IMC elevado em idosos, frequentemente associada a sobrecarga articular, inflamação crônica, apneia do sono e maior risco de doenças metabólicas (Diabetes, Hipertensão).",
    commonMeds = listOf(
        "metformina",
        "empagliflozina",
        "losartana",
        "atorvastatina"
    ),
    commonRisks = listOf(
        Risk.HEART_FAILURE,
        Risk.DIABETIC_COMPLICATION,
        Risk.COPD,
        Risk.REDUCED_MOBILITY,
        Risk.GENERAL_METABOLIC
    )
)

val CreatininaAlta = MedicalCondition(
    name = "Creatinina Sérica Elevada",
    description = "Indica redução da Taxa de Filtração Glomerular (TFG). O idoso apresenta risco aumentado de toxicidade medicamentosa, pois a excreção de fármacos está lentificada.",
    commonMeds = listOf(
        "enalapril",
        "losartana",
        "furosemida",
        "espironolactona"
    ),
    commonRisks = listOf(
        Risk.NEPHROTOXICITY,
        Risk.KIDNEY_FAILURE,
        Risk.RENAL_IMPAIRMENT,
        Risk.THERAPEUTIC_FAILURE
    )
)
val CreatininaBaixa = MedicalCondition(
    name = "Creatinina Sérica Baixa",
    description = "Pode indicar uma massa muscular extremamente reduzida (sarcopenia avançada). A creatinina baixa nestes pacientes mascara uma TFG já reduzida, exigindo cautela extra em doses de medicamentos.",
    commonMeds = listOf(
        "colecalciferol",
        "suplementos_proteicos",
        "carbonato_calcio"
    ),
    commonRisks = listOf(
        Risk.MUSCLE_WASTING,
        Risk.REDUCED_MOBILITY,
        Risk.LOSS_OF_BALANCE,
        Risk.MALNUTRITION
    )
)

val TGOAlta = MedicalCondition(
    name = "TGO Elevada",
    description = "Indica possível dano às células hepáticas (hepatócitos). Exige extrema cautela com medicamentos de metabolização hepática intensa, especialmente AINEs e paracetamol, que podem agravar a lesão.",
    commonMeds = listOf(
        "paracetamol",
        "atorvastatina",
        "cetoconazol",
        "fluconazol"
    ),
    commonRisks = listOf(
        Risk.HEPATOTOXICITY,
        Risk.THERAPEUTIC_FAILURE,
        Risk.GENERAL_GASTROINTESTINAL
    )
)

val TGOBaixa = MedicalCondition(
    name = "TGO Baixa",
    description = "Frequentemente associada a uma massa muscular significativamente reduzida. Em idosos, valores muito baixos de TGO são marcadores clínicos de sarcopenia avançada e maior fragilidade sistêmica.",
    commonMeds = listOf(
        "suplementos_proteicos",
        "colecalciferol",
        "carbonato_calcio"
    ),
    commonRisks = listOf(
        Risk.MUSCLE_WASTING,
        Risk.REDUCED_MOBILITY,
        Risk.LOSS_OF_BALANCE,
        Risk.MALNUTRITION
    )
)
val TGPAlta = MedicalCondition(
    name = "TGP Elevada",
    description = "Indica dano hepatocelular direto. A elevação em idosos sugere toxicidade medicamentosa ou inflamação hepática. É necessário revisar toda a polifarmácia em uso.",
    commonMeds = listOf(
        "paracetamol",
        "atorvastatina",
        "cetoconazol",
        "fluconazol"
    ),
    commonRisks = listOf(
        Risk.HEPATOTOXICITY,
        Risk.THERAPEUTIC_FAILURE,
        Risk.GENERAL_GASTROINTESTINAL
    )
)
val TGPBaixa = MedicalCondition(
    name = "TGP Baixa",
    description = "Valores muito reduzidos de TGP em idosos estão frequentemente correlacionados a uma massa muscular severamente reduzida (sarcopenia). É um marcador de fragilidade sistêmica.",
    commonMeds = listOf(
        "suplementos_proteicos",
        "colecalciferol",
        "carbonato_calcio"
    ),
    commonRisks = listOf(
        Risk.MUSCLE_WASTING,
        Risk.REDUCED_MOBILITY,
        Risk.LOSS_OF_BALANCE,
        Risk.MALNUTRITION
    )
)