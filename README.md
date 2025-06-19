# Simulador de Arquitetura de Computadores

## Curso
Engenharia de Software

## Componente Curricular
Arquitetura de Computadores

## Professor
Eduardo Henrique Molina da Cruz

## Trabalho do 2º Trimestre
**Instrumento:** Trabalho em grupo (2 alunos)

Descrição do Trabalho:  
A equipe deverá desenvolver um simulador para a arquitetura hipotética vista em sala de aula. O simulador deverá implementar no mínimo as seguintes instruções:  
- add  
- sub  
- mul  
- div  
- cmp_equal  
- cmp_neq  
- load  
- store  
- jump  
- jump_cond  
- mov  
- syscall (usar opcode 63 do tipo R. Serviço 0 (r0 = 0) é para fechar o programa.)

Critérios de Avaliação:  
Equipes que desenvolverem apenas o processador monociclo receberão no máximo conceito **B**. Para conceito **A**, deverá ser implementado, além do processador monociclo, um processador pipeline com os seguintes estágios:  
1. Busca de instrução  
2. Decodificação de instrução  
3. Leitura de operandos, Execução e Write-back  

O pipeline deverá incluir um preditor de desvio dinâmico. Recomenda-se implementar primeiro o modelo monociclo e depois evoluir para pipeline.

Testes:  
A equipe deverá escrever códigos assembly para testar o simulador.

Linguagens e Código Base:  
Pode ser usado C ou Java.  
Código base em C: https://github.com/ehmcruz/arq-sim-c-exercicio  
Código base em Java: https://github.com/ehmcruz/arq-sim-java-exercicio  
Assembler: https://github.com/ehmcruz/arq-sim-assembler

Instruções para Ambiente Windows (MSYS2):  
1. Baixar e instalar MSYS2: https://www.msys2.org/  
2. Abrir MSYS2-UCRT64  
3. Atualizar pacotes:  
4. Instalar GIT:  
5. Instalar compilador:  
6. Instalar make:  
7. Clonar repositório do assembler:  
8. Compilar assembler:  

Critérios para Atribuição dos Conceitos:  
- Adequação à atividade proposta  
- Corretude das atividades propostas  
- Completude das atividades propostas  
- Utilização dos conceitos estudados  
- Domínio do conteúdo  
- Correta utilização das técnicas e ferramentas  
- Qualidade do trabalho  
- Respostas aos questionamentos  

Conceitos:  
- conceito A: Aprendizagem plena, atingindo todos os objetivos  
- conceito B: Aprendizagem parcialmente plena  
- conceito C: Aprendizagem suficiente  
- conceito D: Aprendizagem insuficiente

Expectativas de Aprendizagem:  
- Conjunto de instruções  
- Programação assembly  
- Boas práticas de programação  
- Lógica de programação  
- Chamadas de sistema  
- Processador monociclo  
- Processador pipeline
