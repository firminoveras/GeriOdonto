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

data class Med(
    val name: String,
    val principleActive: String = "",
    val medClass: MedClass,
)

val medList = listOf(
    // Diuréticos Tiazídicos
    Med(name = "Hidroclorotiazida", principleActive = "Hidroclorotiazida", medClass = MedClass.MED_CLASS_DIURETICOS_TIAZIDICOS),
    Med(name = "Clertalidona", principleActive = "Clertalidona", medClass = MedClass.MED_CLASS_DIURETICOS_TIAZIDICOS),

    // Inibidores da ECA
    Med(name = "Enalapril", principleActive = "Maleato de Enalapril", medClass = MedClass.MED_CLASS_INIBIDORES_ECA),
    Med(name = "Captopril", principleActive = "Captopril", medClass = MedClass.MED_CLASS_INIBIDORES_ECA),

    // Bloqueadores do receptor de angiotensina (BRA)
    Med(name = "Losartana", principleActive = "Losartana Potássica", medClass = MedClass.MED_CLASS_BRA),
    Med(name = "Valsartana", principleActive = "Valsartana", medClass = MedClass.MED_CLASS_BRA),

    // Bloqueadores de canais de cálcio
    Med(name = "Anlodipino", principleActive = "Besilato de Anlodipino", medClass = MedClass.MED_CLASS_BLOQUEADORES_CALCIO),

    // Betabloqueadores
    Med(name = "Atenolol", principleActive = "Atenolol", medClass = MedClass.MED_CLASS_BETABLOQUEADORES),
    Med(name = "Carvedilol", principleActive = "Carvedilol", medClass = MedClass.MED_CLASS_BETABLOQUEADORES),

    // Diuréticos de Alça e Antagonistas da Aldosterona (Insuficiência Cardíaca)
    Med(name = "Furosemida", principleActive = "Furosemida", medClass = MedClass.MED_CLASS_DIURETICOS_ALCA),
    Med(name = "Espironolactona", principleActive = "Espironolactona", medClass = MedClass.MED_CLASS_ANTAGONISTAS_ALDOSTERONA),

    // Diabetes / Hipoglicemiantes
    Med(name = "Metformina", principleActive = "Cloridrato de Metformina", medClass = MedClass.MED_CLASS_BIGUANIDAS),
    Med(name = "Gliclazida", principleActive = "Gliclazida", medClass = MedClass.MED_CLASS_SULFONILUREIAS),
    Med(name = "Glibenclamida", principleActive = "Glibenclamida", medClass = MedClass.MED_CLASS_SULFONILUREIAS),
    Med(name = "Insulina Glargina", principleActive = "Insulina Glargina", medClass = MedClass.MED_CLASS_INSULINAS),
    Med(name = "Insulina Regular", principleActive = "Insulina Humana Regular", medClass = MedClass.MED_CLASS_INSULINAS),
    Med(name = "Empagliflozina", principleActive = "Empagliflozina", medClass = MedClass.MED_CLASS_INIBIDORES_SGLT2),
    Med(name = "Sitagliptina", principleActive = "Fosfato de Sitagliptina", medClass = MedClass.MED_CLASS_INIBIDORES_DPP4),

    // Antiarrítmicos, Anticoagulantes e Antitrombóticos
    Med(name = "Amiodarona", principleActive = "Cloridrato de Amiodarona", medClass = MedClass.MED_CLASS_ANTIARRITMICOS),
    Med(name = "Varfarina", principleActive = "Varfarina Sódica", medClass = MedClass.MED_CLASS_ANTICOAGULANTES),
    Med(name = "Rivaroxabana", principleActive = "Rivaroxabana", medClass = MedClass.MED_CLASS_ANTICOAGULANTES),
    Med(name = "Ácido Acetilsalicílico", principleActive = "Ácido Acetilsalicílico", medClass = MedClass.MED_CLASS_ANTIAGREGANTES_PLAQUETARIOS),

    // Inibidores de Acetilcolinesterase e NMDA (Cognição / Alzheimer)
    Med(name = "Donepezila", principleActive = "Cloridrato de Donepezila", medClass = MedClass.MED_CLASS_INIBIDORES_ACETILCOLINESTERASE),
    Med(name = "Rivastigmina", principleActive = "Tartarato de Rivastigmina", medClass = MedClass.MED_CLASS_INIBIDORES_ACETILCOLINESTERASE),
    Med(name = "Galantamina", principleActive = "Bromidrato de Galantamina", medClass = MedClass.MED_CLASS_INIBIDORES_ACETILCOLINESTERASE),
    Med(name = "Memantina", principleActive = "Cloridrato de Memantina", medClass = MedClass.MED_CLASS_ANTAGONISTAS_NMDA),

    // Saúde Óssea e Suplementação
    Med(name = "Alendronato", principleActive = "Alendronato de Sódio", medClass = MedClass.MED_CLASS_BIFOSFONATOS),
    Med(name = "Risedronato", principleActive = "Risedronato Sódico", medClass = MedClass.MED_CLASS_BIFOSFONATOS),
    Med(name = "Carbonato de Cálcio", principleActive = "Carbonato de Cálcio", medClass = MedClass.MED_CLASS_SUPLEMENTOS_MINERAIS),
    Med(name = "Colecalciferol", principleActive = "Colecalciferol (Vitamina D3)", medClass = MedClass.MED_CLASS_VITAMINAS),

    // Analgésicos e Anti-inflamatórios (AINEs)
    Med(name = "Paracetamol", principleActive = "Paracetamol", medClass = MedClass.MED_CLASS_ANALGESICOS_AGENTES),
    Med(name = "Dipirona", principleActive = "Dipirona Monoidratada", medClass = MedClass.MED_CLASS_ANALGESICOS_AGENTES),
    Med(name = "Ibuprofeno", principleActive = "Ibuprofeno", medClass = MedClass.MED_CLASS_AINE),
    Med(name = "Diclofenaco", principleActive = "Diclofenaco Sódico/Potássico", medClass = MedClass.MED_CLASS_AINE),
    Med(name = "Tramadol", principleActive = "Cloridrato de Tramadol", medClass = MedClass.MED_CLASS_ANALGESICOS_OPIOIDES),

    // Antidepressivos
    Med(name = "Sertralina", principleActive = "Cloridrato de Sertralina", medClass = MedClass.MED_CLASS_ANTIDEPRESSIVOS),
    Med(name = "Escitalopram", principleActive = "Oxalato de Escitalopram", medClass = MedClass.MED_CLASS_ANTIDEPRESSIVOS),
    Med(name = "Fluoxetina", principleActive = "Cloridrato de Fluoxetina", medClass = MedClass.MED_CLASS_ANTIDEPRESSIVOS),
    Med(name = "Mirtazapina", principleActive = "Mirtazapina", medClass = MedClass.MED_CLASS_ANTIDEPRESSIVOS),

    // Antiparkinsonianos
    Med(name = "Levodopa", principleActive = "Levodopa", medClass = MedClass.MED_CLASS_ANTIPARKINSONIANOS),
    Med(name = "Carbidopa", principleActive = "Carbidopa", medClass = MedClass.MED_CLASS_ANTIPARKINSONIANOS),
    Med(name = "Pramipexol", principleActive = "Dicloridrato de Pramipexol", medClass = MedClass.MED_CLASS_ANTIPARKINSONIANOS),
    Med(name = "Selegilina", principleActive = "Cloridrato de Selegilina", medClass = MedClass.MED_CLASS_ANTIPARKINSONIANOS),

    // Sistema Respiratório
    Med(name = "Salbutamol", principleActive = "Sulfato de Salbutamol", medClass = MedClass.MED_CLASS_BRONCODILATADORES),
    Med(name = "Brometo de Ipratrópio", principleActive = "Brometo de Ipratrópio", medClass = MedClass.MED_CLASS_BRONCODILATADORES),
    Med(name = "Budesonida", principleActive = "Budesonida", medClass = MedClass.MED_CLASS_CORTICOIDES_INALATORIOS),
    Med(name = "Formoterol", principleActive = "Fumarato de Formoterol Dihidratado", medClass = MedClass.MED_CLASS_BRONCODILATADORES),

    // Oncologia / Hormonioterapia
    Med(name = "Bicalutamida", principleActive = "Bicalutamida", medClass = MedClass.MED_CLASS_ANTIANDROGENIOS),
    Med(name = "Leuprorrelina", principleActive = "Acetato de Leuprorrelina", medClass = MedClass.MED_CLASS_ANALOGOS_GNRH),
    Med(name = "Tamoxifeno", principleActive = "Citrato de Tamoxifeno", medClass = MedClass.MED_CLASS_MODULADORES_ESTROGENIO),
    Med(name = "Anastrozol", principleActive = "Anastrozol", medClass = MedClass.MED_CLASS_INIBIDORES_AROMATASE),

    // Hematologia e Renal
    Med(name = "Eritropoetina", principleActive = "Eritropoetina Humana Recombinante", medClass = MedClass.MED_CLASS_ESTIMULANTES_ERITROPOESE),
    Med(name = "Sevelâmer", principleActive = "Cloridrato/Carbonato de Sevelâmer", medClass = MedClass.MED_CLASS_QUELANTES_FOSFATO),

    // Xerostomia / Saúde Bucal
    Med(name = "Pilocarpina", principleActive = "Cloridrato de Pilocarpina", medClass = MedClass.MED_CLASS_ESTIMULANTES_SALIVARES),
    Med(name = "Cevimelina", principleActive = "Cloridrato de Cevimelina", medClass = MedClass.MED_CLASS_ESTIMULANTES_SALIVARES),
    Med(name = "Saliva Artificial", principleActive = "Saliva Artificial", medClass = MedClass.MED_CLASS_PRODUTOS_AUXILIARES_BUCAIS),
    Med(name = "Géis Hidratantes Bucais", principleActive = "Géis Hidratantes Bucais", medClass = MedClass.MED_CLASS_PRODUTOS_AUXILIARES_BUCAIS),
    Med(name = "Substitutos Salivares com Xilitol", principleActive = "Substitutos Salivares com Xilitol", medClass = MedClass.MED_CLASS_PRODUTOS_AUXILIARES_BUCAIS),

    // Suplementos Extras
    Med(name = "Suplementos Proteicos", principleActive = "Proteínas isoladas/concentradas", medClass = MedClass.MED_CLASS_SUPLEMENTOS_ALIMENTARES),
    Med(name = "Aminoácidos Essenciais", principleActive = "Mix de Aminoácidos Essenciais", medClass = MedClass.MED_CLASS_SUPLEMENTOS_ALIMENTARES),
    Med(name = "Testosterona", principleActive = "Testosterona", medClass = MedClass.MED_CLASS_SUPLEMENTOS_ALIMENTARES)
)
