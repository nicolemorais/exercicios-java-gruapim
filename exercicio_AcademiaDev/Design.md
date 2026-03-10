# Documentação de Design: Plataforma de Cursos Online AcademiaDev

## 1. Visão Geral

Este projeto consiste em um protótipo funcional para uma plataforma de ensino a distância (EAD). O sistema gerencia o ciclo de vida de matrículas, cursos e suporte técnico, integrando diferentes níveis de acesso para administradores e alunos.

![Diagrama de Classes](/exercicio_AcademiaDev/Diagrama_de_Classes_AcademiaDev.png "Diagrama de Classes - Plataforma de Cursos Online AcademiaDev")

## 2. Tecnologias e Requisitos de Implementação

- Utilização do Java Collections para simulação de base de dados.
- Implementação de Stream API, Expressões Lambda e Method References.
- Estruturas de Dados:
  - **Map:** Utilizado para garantir a unicidade e busca de alta performance ($O(1)$) de usuários por e-mail e cursos por título.
  - **Queue (LinkedList):** Implementação de fila com comportamento FIFO para o atendimento de tickets de suporte.
  - **Set:** Utilizado nos relatórios para filtragem evitando duplicidade de dados.
- **Reflection API:** Motor de exportação dinâmica de dados para formato CSV baseado em metadados das classes.

## 3. Justificativa de Design e Decisões de Arquitetura

**3.1. Arquitetura de Busca e Performance:** A escolha da interface `Map` em detrimento de `List` para o armazenamento principal de usuários e cursos fundamenta-se na eficiência algorítmica. Enquanto uma lista exigiria uma busca linear $O(n)$, o mapeamento por chaves (e-mail/título) permite o acesso direto, garantindo que o tempo de resposta da plataforma permaneça constante independentemente do volume de dados carregados.

**3.2. Gerenciamento de Estado e Encapsulamento:** A lógica de progresso e validação de percentual (0 a 100) foi centralizada na classe `Enrollment`. Esta decisão de design respeita o princípio de "Expert Information", onde a classe que detém o atributo é a responsável por sua integridade. Da mesma forma, a classe `Student` gerencia sua própria lista de matrículas, permitindo que a regra de negócio de limite de plano (ex: máximo 3 cursos para Plano Basic) seja validada no momento da inserção.

**3.3. Processamento Declarativo com Stream API:** Para a geração de relatórios e análises complexas, optou-se pelo uso de Streams em vez de loops iterativos tradicionais. Isso permitiu a implementação de requisitos como "instrutores únicos de cursos ativos" e "média geral de progresso" de forma mais concisa e menos propensa a erros de lógica, aproveitando as otimizações internas da JVM para processamento de coleções.

**3.4. Flexibilidade via Reflection:** A funcionalidade de exportação CSV foi projetada utilizando reflexão computacional. Isso permite que a classe `AcademiaDevPlatform` permaneça agnóstica em relação aos campos específicos das entidades, permitindo que o administrador escolha colunas dinamicamente. Esta abordagem reduz drasticamente a necessidade de manutenção de código caso novos atributos sejam adicionados às classes `Student` ou `Course`.

## 4. Funcionalidades de Interface (CLI)

- **Operações de Administrador:** 
  - **Gestão de cursos:** impactando a disponibilidade de matrículas.

  - **Gestão de Alunos:** Alteração de planos de assinatura e monitoramento de progresso.

  - **Fila de Suporte:** Atendimento sequencial de chamados técnicos.

  - **Análise de Dados:** Relatórios de agrupamento por plano e identificação de alunos destaque.

- **Operações de Aluno**
  - **Matrícula:** Sistema de inscrição condicionado ao status do curso e limites do plano vigente.
  - **Acompanhamento:** Consulta de cursos matriculados e atualização de percentual de conclusão.
  - **Cancelamento:** Remoção de matrículas com liberação automática de cota de plano.
