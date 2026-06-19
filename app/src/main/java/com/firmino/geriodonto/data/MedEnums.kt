package com.firmino.geriodonto.data

import androidx.compose.ui.graphics.Color

enum class InteractionAlertLevel(val symbolName: String, val color: Color, val text: String) {
    HIGH(symbolName = "emergency_home", color = Color(0xFFEF5350), text = "Alto"),
    NORMAL(symbolName = "warning", color = Color(0xFFFFCA28), text = "Normal"),
    LOW(symbolName = "error", color = Color(0xFF26C6DA), text = "Baixo"),
}

enum class RiskCategory(val description: String, val symbolName: String = "") {
    NEUROLOGICAL("Neurológico", "neurology"),
    CARDIOVASCULAR("Cardiovascular", "cardiology"),
    RESPIRATORY("Respiratório", "pulmonology"),
    METABOLIC_ENDOCRINE("Metabólico e Endócrino", "endocrinology"),
    RENAL_UROLOGICAL("Renal e Urológico", "nephrology"),
    GASTROINTESTINAL("Gastrointestinal", "gastroenterology"),
    MUSCULOSKELETAL("Musculoesquelético", "femur_alt"),
    SENSORY("Sensorial", "touch_app"),
    DERMATOLOGICAL("Dermatológico", "dermatology"),
    PSYCHIATRIC("Psiquiátrico e Psicológico", "psychology"),
    INFECTIOUS_HEMATOLOGICAL("Infeccioso, Imunológico e Hematológico", "microbiology"),
    ORAL_AND_SPEECH("Bucal e Fonoaudiológica", "oral_disease"),
    GENERAL("Geral", "stethoscope")
}

enum class Risk(val text: String, val category: RiskCategory) {
    // METABÓLICO E ENDÓCRINO
    HYPOCALCEMIA("Hipocalcemia / Baixo Cálcio", RiskCategory.METABOLIC_ENDOCRINE),
    HYPOKALEMIA("Hipocalemia / Hipopotassemia", RiskCategory.METABOLIC_ENDOCRINE),
    HYPERKALEMIA("Hipercalemia / Hiperpotassemia", RiskCategory.METABOLIC_ENDOCRINE),
    HYPONATREMIA("Hiponatremia / Baixo Sódio", RiskCategory.METABOLIC_ENDOCRINE),
    DEHYDRATION("Desidratação", RiskCategory.METABOLIC_ENDOCRINE),
    DIABETIC_COMPLICATION("Complicações Diabéticas (Ex: Hipoglicemia)", RiskCategory.METABOLIC_ENDOCRINE),
    THYROID_DYSFUNCTION("Disfunção Tireoidiana", RiskCategory.METABOLIC_ENDOCRINE),
    GENERAL_METABOLIC("Outras Complicações Metabólicas", RiskCategory.METABOLIC_ENDOCRINE),

    // SAÚDE BUCAL E FONOAUDIOLÓGICA
    DYSPHAGIA("Disfagia (Dificuldade de Deglutição)", RiskCategory.ORAL_AND_SPEECH),
    CHEWING_IMPAIRMENT("Comprometimento da Mastigação", RiskCategory.ORAL_AND_SPEECH),
    SPEECH_IMPAIRMENT("Dificuldade de Fala", RiskCategory.ORAL_AND_SPEECH),
    XEROSTOMIA_HYPOSALIVATION("Xerostomia / Hipossalivação", RiskCategory.ORAL_AND_SPEECH),
    TOOTH_DECAY("Risco Elevado de Cáries e Perda Dentária", RiskCategory.ORAL_AND_SPEECH),
    ORAL_INFECTION("Infecção Oral (Ex: Candidíase)", RiskCategory.ORAL_AND_SPEECH),
    GINGIVAL_HYPERPLASIA("Hiperplasia Gengival", RiskCategory.ORAL_AND_SPEECH),
    OSTEONECROSIS_OF_JAW("Osteonecrose dos Maxilares", RiskCategory.ORAL_AND_SPEECH),
    TASTE_ALTERATION("Alteração do Paladar (Disgeusia)", RiskCategory.ORAL_AND_SPEECH),
    SIALORRHEA("Sialorreia (Salivação Excessiva)", RiskCategory.ORAL_AND_SPEECH),

    // NEUROLÓGICO
    STROKE("Acidente Vascular Cerebral (AVC)", RiskCategory.NEUROLOGICAL),
    SEIZURE("Convulsões / Epilepsia", RiskCategory.NEUROLOGICAL),
    DEMENTIA_ALZHEIMER("Demência / Alzheimer", RiskCategory.NEUROLOGICAL),
    SEROTONIN_SYNDROME("Síndrome Serotoninérgica", RiskCategory.NEUROLOGICAL),
    CNS_DEPRESSION("Depressão do Sistema Nervoso Central", RiskCategory.NEUROLOGICAL),
    LOSS_OF_BALANCE("Perda de Equilíbrio / Risco de Queda", RiskCategory.NEUROLOGICAL),
    GENERAL_NEUROLOGICAL("Outras Complicações Neurológicas", RiskCategory.NEUROLOGICAL),

    // PSIQUIÁTRICO
    DEPRESSION_ANXIETY("Depressão e/ou Ansiedade", RiskCategory.PSYCHIATRIC),
    DELIRIUM("Delirium / Confusão Mental", RiskCategory.PSYCHIATRIC),

    // CARDIOVASCULAR
    HEART_ATTACK("Infarto Agudo do Miocárdio", RiskCategory.CARDIOVASCULAR),
    HEART_FAILURE("Insuficiência Cardíaca", RiskCategory.CARDIOVASCULAR),
    ARRHYTHMIA("Arritmia Cardíaca", RiskCategory.CARDIOVASCULAR),
    THROMBOSIS("Trombose Venosa Profunda (TVP)", RiskCategory.CARDIOVASCULAR),
    HYPERTENSION_CRISIS("Crise Hipertensiva", RiskCategory.CARDIOVASCULAR),
    BLOOD_PRESSURE_ALTERATION("Alteração no Controle da Pressão Arterial", RiskCategory.CARDIOVASCULAR),
    CARDIOVASCULAR_EVENT("Evento Cardiovascular", RiskCategory.CARDIOVASCULAR),

    // RESPIRATÓRIO
    RESPIRATORY_FAILURE("Insuficiência Respiratória", RiskCategory.RESPIRATORY),
    RESPIRATORY_DEPRESSION("Depressão Respiratória", RiskCategory.RESPIRATORY),
    COPD("Doença Pulmonar Obstrutiva Crônica (DPOC)", RiskCategory.RESPIRATORY),
    PULMONARY_EMBOLISM("Embolia Pulmonar", RiskCategory.RESPIRATORY),
    GENERAL_RESPIRATORY("Outros Riscos Respiratórios", RiskCategory.RESPIRATORY),

    // RENAL E UROLÓGICO
    KIDNEY_FAILURE("Insuficiência Renal", RiskCategory.RENAL_UROLOGICAL),
    RENAL_IMPAIRMENT("Piora da Função Renal", RiskCategory.RENAL_UROLOGICAL),
    NEPHROTOXICITY("Nefrotoxicidade", RiskCategory.RENAL_UROLOGICAL),
    URINARY_INFECTION("Infecção do Trato Urinário (ITU)", RiskCategory.RENAL_UROLOGICAL),
    GENERAL_RENAL("Outras Complicações Renais/Urológicas", RiskCategory.RENAL_UROLOGICAL),

    // GASTROINTESTINAL
    GASTROINTESTINAL_BLEEDING("Hemorragia Digestiva", RiskCategory.GASTROINTESTINAL),
    PEPTIC_ULCER("Úlcera Gástrica/Péptica", RiskCategory.GASTROINTESTINAL),
    HEPATOTOXICITY("Hepatotoxicidade / Lesão Hepática", RiskCategory.GASTROINTESTINAL),
    GENERAL_GASTROINTESTINAL("Outras Complicações Gastrointestinais", RiskCategory.GASTROINTESTINAL),

    // MUSCULOESQUELÉTICO
    MUSCLE_TOXICITY("Toxicidade Muscular / Miopatia", RiskCategory.MUSCULOSKELETAL),
    BONE_FRACTURE("Fratura Óssea", RiskCategory.MUSCULOSKELETAL),
    REDUCED_MOBILITY("Redução de Movimento", RiskCategory.MUSCULOSKELETAL),
    MUSCLE_WASTING("Perda de Massa Muscular (Sarcopenia)", RiskCategory.MUSCULOSKELETAL),

    // SENSORIAL
    VISION_LOSS("Complicações Visuais / Cegueira", RiskCategory.SENSORY),
    OTOTOXICITY("Ototoxicidade / Perda Auditiva", RiskCategory.SENSORY),
    PERIPHERAL_NEUROPATHY("Perda de Sensibilidade (Neuropatia)", RiskCategory.SENSORY),

    // DERMATOLÓGICO
    PRESSURE_ULCER("Lesão por Pressão (Escara)", RiskCategory.DERMATOLOGICAL),
    GENERAL_DERMATOLOGICAL("Outras Complicações Dermatológicas", RiskCategory.DERMATOLOGICAL),

    // INFECCIOSO E HEMATOLÓGICO
    SEPSIS("Sepse / Infecção Generalizada", RiskCategory.INFECTIOUS_HEMATOLOGICAL),
    HOSPITAL_INFECTION("Risco de Infecção Hospitalar", RiskCategory.INFECTIOUS_HEMATOLOGICAL),
    HEMATOLOGICAL_TOXICITY("Toxicidade Hematológica / Agranulocitose", RiskCategory.INFECTIOUS_HEMATOLOGICAL),

    // GERAL
    CHRONIC_PAIN("Dor Aguda/Crônica", RiskCategory.GENERAL),
    CHRONIC_FATIGUE("Fadiga Crônica", RiskCategory.GENERAL),
    BLEEDING_RISK("Risco de Hemorragia / Sangramento", RiskCategory.GENERAL),
    MALNUTRITION("Desnutrição", RiskCategory.GENERAL),
    ALTERED_PHARMACOKINETICS("Alteração na Absorção ou Eficácia", RiskCategory.GENERAL),
    THERAPEUTIC_FAILURE("Falha Terapêutica / Redução de Eficácia", RiskCategory.GENERAL)
}

enum class MedClass(val text: String) {
    GLICOSIDEOS_CARDIACOS(text = "Glicosídeos Cardíacos / Digitálicos"),
    ANTIACIDOS(text = "Antiácidos"),
    SUBSTANCIAS_QUIMICAS(text = "Substâncias Químicas"),
    RELAXANTES_MUSCULARES(text = "Relaxantes Musculares"),
    ANTAGONISTAS_H2(text = "Antagonistas do Receptor H2"),
    ANTIGOTOSOS(text = "Antigotosos / Uricosúricos"),
    IMUNOMODULADORES_ANTIMALARICOS(text = "Imunomoduladores / Antimaláricos"),
    IBP(text = "Inibidores da Bomba de Prótons (IBP)"),
    ANTIPSICOTICOS(text = "Antipsicóticos"),
    BLOQUEADORES_CANAL_CALCIO(text = "Bloqueadores dos Canais de Cálcio"),
    DIURETICOS_POUPADORES_POTASSIO(text = "Diuréticos Poupadores de Potássio"),
    HIPOLIPEMIANTES(text = "Hipolipemiantes / Resinas"),
    QUIMIOTERAPICOS_IMUNOSSUPRESSORES(text = "Quimioterápicos / Imunossupressores"),
    ANTICOLINERGICOS(text = "Anticolinérgicos / Antiespasmódicos Urinários"),
    ANTIFUNGICOS(text = "Antifúngicos"),
    IECA(text = "Inibidor da Enzima Conversora de Angiotensina (IECA)"),
    CORTICOIDES(text = "Corticosteroides Sistêmicos"),
    ANTIEPILEPTICOS(text = "Antiepilépticos"),
    PROCINETICOS(text = "Pró-cinéticos"),
    ANTIBIOTICOS_TUBERCULOSTATICOS(text = "Antibióticos / Tuberculostáticos"),
    ESTABILIZADORES_HUMOR(text = "Estabilizadores de Humor"),
    ISRS(text = "Inibidores Seletivos da Recaptação de Serotonina (ISRS)"),
    IRSN(text = "Inibidores da Recaptação de Serotonina e Norepinefrina (IRSN)"),
    BENZODIAZEPINICOS(text = "Benzodiazepínicos"),
    ANTIEMETICOS(text = "Antieméticos"),
    DIURETICOS_TIAZIDICOS(text = "Diuréticos Tiazídicos"),
    BRA(text = "Bloqueadores do Receptor de Angiotensina (BRA)"),
    BETABLOQUEADORES(text = "Betabloqueadores"),
    DIURETICOS_ALCA(text = "Diuréticos de Alça"),
    ANTAGONISTAS_ALDOSTERONA(text = "Antagonistas da Aldosterona"),
    BIGUANIDAS(text = "Biguanidas"),
    SULFONILUREIAS(text = "Sulfonilureias"),
    INSULINAS(text = "Insulinas"),
    INIBIDORES_SGLT2(text = "Inibidores de SGLT2"),
    INIBIDORES_DPP4(text = "Inibidores de DPP-4"),
    ANTIARRITMICOS(text = "Antiarrítmicos"),
    ANTICOAGULANTES(text = "Anticoagulantes"),
    ANTIAGREGANTES_PLAQUETARIOS(text = "Antiagregantes Plaquetários"),
    INIBIDORES_ACETILCOLINESTERASE(text = "Inibidores da Acetilcolinesterase"),
    ANTAGONISTAS_NMDA(text = "Antagonistas do Receptor NMDA"),
    ANTIDEPRESSIVOS(text = "Antidepressivos"),
    ANTIPARKINSONIANOS(text = "Antiparkinsonianos"),
    BIFOSFONATOS(text = "Bifosfonatos"),
    SUPLEMENTOS_MINERAIS(text = "Suplementos Minerais"),
    VITAMINAS(text = "Vitaminas"),
    ANALGESICOS_AGENTES(text = "Analgésicos Comuns"),
    AINE(text = "Anti-inflamatórios Não Esteroides (AINEs)"),
    ANALGESICOS_OPIOIDES(text = "Analgésicos Opioides"),
    BRONCODILATADORES(text = "Broncodilatadores"),
    CORTICOIDES_INALATORIOS(text = "Corticoides Inalatórios"),
    ANTIANDROGENIOS(text = "Antiandrogênios (Próstata)"),
    ANALOGOS_GNRH(text = "Análogos de GnRH (Próstata)"),
    MODULADORES_ESTROGENIO(text = "Moduladores Seletivos do Receptor de Estrogênio (Mama)"),
    INIBIDORES_AROMATASE(text = "Inibidores da Aromatase (Mama)"),
    ESTIMULANTES_ERITROPOESE(text = "Agentes Estimuladores da Eritropoese"),
    QUELANTES_FOSFATO(text = "Quelantes de Fosfato"),
    ESTIMULANTES_SALIVARES(text = "Estimulantes Salivares"),
    PRODUTOS_AUXILIARES_BUCAIS(text = "Produtos Auxiliares e Substitutos Salivares"),
    SUPLEMENTOS_ALIMENTARES(text = "Suplementos e Aminoácidos")
}
