# Processsador_Trabalho
Curso: Engenharia de Software 
Componente Curricular: Arquitetura de Computadores 
Professor: Eduardo Henrique Molina da Cruz 
Trabalho do 2º trimestre 
Instrumento: trabalho em grupo (2 alunos) 
Descrição: 
A equipe deverá desenvolver um simulador para a arquitetura hipotética vista em sala 
de aula. Deverá ter no mínimo as seguintes instruções: 
● add 
● sub 
● mul 
● div 
● cmp_equal 
● cmp_neq 
● load 
● store 
● jump 
● jump_cond 
● mov 
● syscall - Usar opcode 63 do tipo R. Serviço 0 (r0 = 0) é para fechar o programa. 
Equipes que desenvolverem um processador monociclo receberão no máximo conceito 
B. 
Implementar o mais próximo possível da operação real do processador, de forma 
funcional (não no nível de circuito), separando as diferentes etapas de processamento. 
Para poder atingir conceito A, deverá, além do processador monociclo, implementar 
também um processador pipeline, com os seguintes estágios: 
1. Busca de instrução 
2. Decodificação de instrução 
3. Leitura de operandos, Execução e Write-back 
O pipeline deverá também ter um preditor de desvio dinâmico. 
Sugestão: implementar primeiro o modelo monociclo, e depois evoluir para pipeline. 
A equipe deverá também escrever códigos assembly para testar o simulador. 
Deverá utilizar a linguagem de programação C ou Java. 
Apesar da avaliação ser em equipe, o professor irá verificar toda aula quais alunos estão 
de fato desenvolvendo a avaliação. Alunos que não usarem o tempo em sala de aula para 
desenvolver a avaliação terão redução de seus conceitos individualmente, independente do 
conceito da equipe. 
Um código base (em C) pode ser baixado no seguinte endereço: 
https://github.com/ehmcruz/arq-sim-c-exercicio 
Um código base (em Java) pode ser baixado no seguinte endereço: 
https://github.com/ehmcruz/arq-sim-java-exercicio 
Um assembler pode ser baixado no seguinte endereço: 
https://github.com/ehmcruz/arq-sim-assembler 
Instruções - Windows - MSYS2 
Baixar MSYS2 no site https://www.msys2.org/ 
Instalar MSYS2. 
Abrir MSYS2-UCRT64 
Atualizar pacotes com o seguinte comando: 
pacman -Syu 
Instalar GIT 
pacman -S git 
Instalar compilador 
pacman -S mingw-w64-ucrt-x86_64-gcc 
Instalar make 
pacman -S make 
Clonar repositório do assembler 
git clone https://github.com/ehmcruz/arq-sim-assembler.git 
Compilar o assembler 
cd arq-sim-assembler 
make 
Instruções Linux 
Usar Linux, preferencialmente Ubuntu. 
Sugiro virtualizar o Linux usando o Virtualbox. 
Pode usar o WSL no Windows também. 
Instalar GIT 
sudo apt install git 
Instalar compilador 
sudo apt install build-essential 
sudo apt install gcc g++ 
Clonar repositório do assembler 
git clone https://github.com/ehmcruz/arq-sim-assembler.git 
Compilar assembler: 
1. Entrar na pasta do assembler 
2. make 
Instruções gerais 
Gerar o binário dos códigos assembly: 
1. Colocar o código-fonte na mesma pasta do assembler 
2. ./pasm arquivo.asm arquivo.bin 
Para compilar o código C, basta usar o comando make na pasta do simulador: 
1. make 
Executar um código no simulador (em C): 
1. ./arq-sim arquivo.bin 
Critério base para atribuição dos conceitos: 
● Adequação à atividade proposta; 
● Corretude das atividades propostas; 
● Completude das atividades propostas; 
● Utilização dos conceitos estudados; 
● Domínio do conteúdo; 
● Correta utilização das técnicas e ferramentas; 
● Qualidade do trabalho; 
● Respostas aos questionamentos. 
Observados os critérios, a atribuição de conceitos se dará da seguinte forma: 
● conceito A - quando a aprendizagem do estudante for PLENA e atingir os 
objetivos, conforme critérios propostos no plano de ensino; 
● conceito B - quando a aprendizagem do estudante for PARCIALMENTE 
PLENA  e atingir os objetivos, conforme critérios propostos no plano de ensino; 
● conceito C - quando a aprendizagem do estudante for SUFICIENTE e atingir os 
objetivos, conforme critérios propostos no plano de ensino; 
● conceito D - quando a aprendizagem do estudante for INSUFICIENTE e não 
atingir os objetivos, conforme critérios propostos no plano de ensino. 
Expectativas de aprendizagem: 
● Conjunto de instruções 
● Programação assembly 
● Boas práticas de programação 
● Lógica de programação 
● Chamadas de sistema 
● Processador monociclo 
● Processador pipeline 
