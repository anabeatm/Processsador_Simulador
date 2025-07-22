# 💻 Simulador de Arquitetura de Computadores

## 🎓 Curso: **Engenharia de Software**


## 📚 Componente Curricular
**Arquitetura de Computadores**

## 👨‍🏫 Professor
**Eduardo Henrique Molina da Cruz**

---

## 📌 Trabalho do 2º Trimestre

- **Instrumento:** Trabalho em grupo (2 alunos)

### 📝 Descrição do Trabalho

A equipe deverá desenvolver um **simulador para a arquitetura hipotética** vista em sala de aula.  
O simulador deve implementar no mínimo as seguintes instruções:

- `add`
- `sub`
- `mul`
- `div`
- `cmp_equal`
- `cmp_neq`
- `load`
- `store`
- `jump`
- `jump_cond`
- `mov`
- `syscall`  
  ➤ *Usar opcode `63` do tipo R. Serviço `0` (r0 = 0) é para fechar o programa.*

---

## ✅ Critérios de Avaliação

- Equipes que desenvolverem **apenas o processador monociclo**:  
  ➤ Nota máxima: **Conceito B**

- Para obter **Conceito A**, é necessário implementar também um **processador pipeline**, com os seguintes estágios:

  1. Busca de instrução (IF)
  2. Decodificação de instrução (ID)
  3. Leitura de operandos, Execução e Write-back (EX/WB)

- O pipeline deverá incluir um **preditor de desvio dinâmico**.

📌 *Recomenda-se implementar primeiro o modelo monociclo e, depois, evoluir para o pipeline.*

---

## 🧪 Testes

A equipe deverá escrever **códigos em assembly** para testar o simulador.

---

## 💻 Linguagens e Código Base

Pode ser usado **C ou Java**.

- Código base em C: [https://github.com/ehmcruz/arq-sim-c-exercicio](https://github.com/ehmcruz/arq-sim-c-exercicio)
- Código base em Java: [https://github.com/ehmcruz/arq-sim-java-exercicio](https://github.com/ehmcruz/arq-sim-java-exercicio)
- Assembler: [https://github.com/ehmcruz/arq-sim-assembler](https://github.com/ehmcruz/arq-sim-assembler)

---

## 🪟 Instruções para Ambiente Windows (MSYS2)

1. Baixe e instale o MSYS2:  
   👉 [https://www.msys2.org/](https://www.msys2.org/)

2. Abra o terminal **MSYS2-UCRT64**

3. Atualize os pacotes:
   ```bash
   pacman -Syu
   ```

4. Instale o **GIT**:
   ```bash
   pacman -S git
   ```

5. Instale o **compilador**:
   ```bash
   pacman -S gcc
   ```

6. Instale o **make**:
   ```bash
   pacman -S make
   ```

7. Clone o repositório do assembler:
   ```bash
   git clone https://github.com/ehmcruz/arq-sim-assembler
   ```

8. Compile o assembler:
   ```bash
   cd arq-sim-assembler
   make
   ```

---

## 📊 Critérios para Atribuição dos Conceitos

- ✅ Adequação à atividade proposta
- ✅ Corretude das atividades propostas
- ✅ Completude das atividades propostas
- ✅ Utilização dos conceitos estudados
- ✅ Domínio do conteúdo
- ✅ Correta utilização das técnicas e ferramentas
- ✅ Qualidade do trabalho
- ✅ Respostas aos questionamentos

---

## 🏅 Conceitos

- **Conceito A**: Aprendizagem plena, atingindo todos os objetivos
- **Conceito B**: Aprendizagem parcialmente plena
- **Conceito C**: Aprendizagem suficiente
- **Conceito D**: Aprendizagem insuficiente

---

## 🎯 Expectativas de Aprendizagem

- Entendimento do **conjunto de instruções**
- Programação em **Assembly**
- Aplicação de **boas práticas de programação**
- Desenvolvimento de **lógica de programação**
- Implementação de **chamadas de sistema**
- Construção de **processador monociclo**
- Construção de **processador pipeline**
```
