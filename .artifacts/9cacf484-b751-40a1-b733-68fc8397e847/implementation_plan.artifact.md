# Refatoração do `ExamMenuToolbar.kt`

Este plano detalha as melhorias de organização, redução de redundância e modularização do componente de toolbar de configurações.

## User Review Required

> [!IMPORTANT]
> A refatoração removerá a dependência direta de `BoxScope`. Isso significa que, onde a `ExamMenuToolbar` é chamada, pode ser necessário passar um `Modifier.align(Alignment.BottomEnd)` explicitamente se o pai não for mais um `Box` ou se o comportamento mudar.

## Propostas de Mudanças

### 1. Refatoração de Dados e Enums

#### [MODIFY] [ExamMenuToolbar.kt](file:///home/noctalia/Documentos/Projetos/GeriOdonto/app/src/main/java/com/firmino/geriodonto/ui/widgets/ExamMenuToolbar.kt)
- **`ActiveSettingMenu`**: Adicionar métodos auxiliares para obter o título e o ícone atual baseados no estado.
- **Abstração de Itens**: Criar uma lista interna (ou via classe auxiliar) que mapeia cada `ActiveSettingMenu` para suas ações e ícones.

### 2. Melhoria do Componente UI

#### [MODIFY] [ExamMenuToolbar.kt](file:///home/noctalia/Documentos/Projetos/GeriOdonto/app/src/main/java/com/firmino/geriodonto/ui/widgets/ExamMenuToolbar.kt)
- **`ExamMenuToolbar`**:
    - Remover `BoxScope`.
    - Adicionar `modifier: Modifier = Modifier`.
    - Implementar um `LazyColumn` ou simplesmente um loop (`forEach`) sobre a lista de configurações para renderizar os `SettingIcon`s, eliminando a repetição de código.
    - Simplificar a lógica de `AnimatedVisibility` para os labels.
- **`SettingIcon`**: Pequenos ajustes para garantir que o estado de expansão seja gerenciado de forma fluida.

### 3. Internacionalização (Strings)

#### [MODIFY] [strings.xml](file:///home/noctalia/Documentos/Projetos/GeriOdonto/app/src/main/res/values/strings.xml)
- Adicionar chaves para: "Tema", "Modo OLED", "Modo Escuro", "Palheta", "Configurações", "Continuar Edição".

## Plano de Verificação

### Testes Manuais
1. Verificar se o menu de configurações abre e fecha corretamente.
2. Validar se a seleção de cores, paletas e modos (OLED/Escuro) continua funcionando e refletindo no estado global.
3. Confirmar se os labels flutuantes aparecem na posição correta ao lado dos ícones.
4. Garantir que o botão de "Adicionar/Editar" (FAB) executa o evento correto.

---
**Aguardando aprovação para iniciar a execução.**
