package com.firmino.geriodonto.data

import androidx.compose.ui.graphics.Color

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
    INFECTIOUS_HEMATOLOGICAL("Infeccioso, Imunológico e Hematológico"),
    ORAL_AND_SPEECH("Saúde Bucal e Fonoaudiológica"),
    GENERAL("Geral e Sintomas")
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
    LOSS_OF_BALANCE("Perda de Equilíbrio / Risco de Queda", RiskCategory.NEUROLOGICAL), // Ajustado para neurológico
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
    RENAL_IMPAIRMENT("Piora da Função Renal", RiskCategory.RENAL_UROLOGICAL), // Removido "Desidratação" do texto
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
    OTOTOXICITY("Ototoxicidade / Perda Auditiva", RiskCategory.SENSORY), // Ajustado a categoria e unificado com Hearing Loss
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

enum class InteractionAlertLevel(symbolName: String, color: Color){
    HIGH("brightness_alert", Color(0xFFEF5350)),
    NORMAL("warning", Color(0xFFFFCA28)),
    LOW("error", Color(0xFF26C6DA)),
}

enum class MedClass(val text: String, val short_text: String = "") {
    MED_CLASS_GLICOSIDEOS_CARDIACOS(text = "Glicosídeos Cardíacos / Digitálicos"),
    MED_CLASS_ANTIACIDOS(text = "Antiácidos"),
    MED_CLASS_SUBSTANCIAS_QUIMICAS(text = "Substâncias Químicas"),
    MED_CLASS_RELAXANTES_MUSCULARES(text = "Relaxantes Musculares"),
    MED_CLASS_ANTAGONISTAS_H2(text = "Antagonistas do Receptor H2"),
    MED_CLASS_ANTIGOTOSOS(text = "Antigotosos / Uricosúricos"),
    MED_CLASS_IMUNOMODULADORES_ANTIMALARICOS(text = "Imunomoduladores / Antimaláricos"),
    MED_CLASS_IBP(text = "Inibidores da Bomba de Prótons (IBP)"),
    MED_CLASS_ANTIPSICOTICOS(text = "Antipsicóticos"),
    MED_CLASS_BLOQUEADORES_CANAL_CALCIO(text = "Bloqueadores dos Canais de Cálcio"),
    MED_CLASS_DIURETICOS_POUPADORES_POTASSIO(text = "Diuréticos Poupadores de Potássio"),
    MED_CLASS_HIPOLIPEMIANTES(text = "Hipolipemiantes / Resinas"),
    MED_CLASS_QUIMIOTERAPICOS_IMUNOSSUPRESSORES(text = "Quimioterápicos / Imunossupressores"),
    MED_CLASS_ANTICOLINERGICOS(text = "Anticolinérgicos / Antiespasmódicos Urinários"),
    MED_CLASS_ANTIFUNGICOS(text = "Antifúngicos"),
    MED_CLASS_IECA(text = "Inibidor da Enzima Conversora de Angiotensina (IECA)"),
    MED_CLASS_CORTICOIDES(text = "Corticosteroides Sistêmicos"),
    MED_CLASS_ANTIEPILEPTICOS(text = "Antiepilépticos"),
    MED_CLASS_PROCINETICOS(text = "Pró-cinéticos"),
    MED_CLASS_ANTIBIOTICOS_TUBERCULOSTATICOS(text = "Antibióticos / Tuberculostáticos"),
    MED_CLASS_ESTABILIZADORES_HUMOR(text = "Estabilizadores de Humor"),
    MED_CLASS_ISRS(text = "Inibidores Seletivos da Recaptação de Serotonina (ISRS)"),
    MED_CLASS_IRSN(text = "Inibidores da Recaptação de Serotonina e Norepinefrina (IRSN)"),
    MED_CLASS_BENZODIAZEPINICOS(text = "Benzodiazepínicos"),
    MED_CLASS_ANTIEMETICOS(text = "Antieméticos"),
    MED_CLASS_DIURETICOS_TIAZIDICOS(text = "Diuréticos Tiazídicos"),
    MED_CLASS_BRA(text = "Bloqueadores do Receptor de Angiotensina (BRA)"),
    MED_CLASS_BETABLOQUEADORES(text = "Betabloqueadores"),
    MED_CLASS_DIURETICOS_ALCA(text = "Diuréticos de Alça"),
    MED_CLASS_ANTAGONISTAS_ALDOSTERONA(text = "Antagonistas da Aldosterona"),
    MED_CLASS_BIGUANIDAS(text = "Biguanidas"),
    MED_CLASS_SULFONILUREIAS(text = "Sulfonilureias"),
    MED_CLASS_INSULINAS(text = "Insulinas"),
    MED_CLASS_INIBIDORES_SGLT2(text = "Inibidores de SGLT2"),
    MED_CLASS_INIBIDORES_DPP4(text = "Inibidores de DPP-4"),
    MED_CLASS_ANTIARRITMICOS(text = "Antiarrítmicos"),
    MED_CLASS_ANTICOAGULANTES(text = "Anticoagulantes"),
    MED_CLASS_ANTIAGREGANTES_PLAQUETARIOS(text = "Antiagregantes Plaquetários"),
    MED_CLASS_INIBIDORES_ACETILCOLINESTERASE(text = "Inibidores da Acetilcolinesterase"),
    MED_CLASS_ANTAGONISTAS_NMDA(text = "Antagonistas do Receptor NMDA"),
    MED_CLASS_ANTIDEPRESSIVOS(text = "Antidepressivos"),
    MED_CLASS_ANTIPARKINSONIANOS(text = "Antiparkinsonianos"),
    MED_CLASS_BIFOSFONATOS(text = "Bifosfonatos"),
    MED_CLASS_SUPLEMENTOS_MINERAIS(text = "Suplementos Minerais"),
    MED_CLASS_VITAMINAS(text = "Vitaminas"),
    MED_CLASS_ANALGESICOS_AGENTES(text = "Analgésicos Comuns"),
    MED_CLASS_AINE(text = "Anti-inflamatórios Não Esteroides (AINEs)"),
    MED_CLASS_ANALGESICOS_OPIOIDES(text = "Analgésicos Opioides"),
    MED_CLASS_BRONCODILATADORES(text = "Broncodilatadores"),
    MED_CLASS_CORTICOIDES_INALATORIOS(text = "Corticoides Inalatórios"),
    MED_CLASS_ANTIANDROGENIOS(text = "Antiandrogênios (Próstata)"),
    MED_CLASS_ANALOGOS_GNRH(text = "Análogos de GnRH (Próstata)"),
    MED_CLASS_MODULADORES_ESTROGENIO(text = "Moduladores Seletivos do Receptor de Estrogênio (Mama)"),
    MED_CLASS_INIBIDORES_AROMATASE(text = "Inibidores da Aromatase (Mama)"),
    MED_CLASS_ESTIMULANTES_ERITROPOESE(text = "Agentes Estimuladores da Eritropoese"),
    MED_CLASS_QUELANTES_FOSFATO(text = "Quelantes de Fosfato"),
    MED_CLASS_ESTIMULANTES_SALIVARES(text = "Estimulantes Salivares"),
    MED_CLASS_PRODUTOS_AUXILIARES_BUCAIS(text = "Produtos Auxiliares e Substitutos Salivares"),
    MED_CLASS_SUPLEMENTOS_ALIMENTARES(text = "Suplementos e Aminoácidos")
}
