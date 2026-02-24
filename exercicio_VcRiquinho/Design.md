# Documentação de Design: VcRiquinho

## 1. Visão Geral
O sistema foi projetado para atender à startup vcRiquinho, cujo modelo de negócio foca na gestão e alocação estratégica de recursos. A rentabilidade da empresa provém diretamente de taxas de serviço aplicadas sobre os rendimentos dos clientes, o que exigiu uma arquitetura que priorizasse o cálculo preciso e a rastreabilidade financeira.

![Diagrama de Classes](/exercicio_VcRiquinho/Diagrama_de_Classes_vcRiquinho.png "Diagrama de Classes - Vc Riquinho")

## 2. Escolhas de Design e POO
### 2.1. Interfaces
A interface `InvestimentoRentavel` é o pilar central do faturamento da startup.
- Justificativa: Ela isola a lógica de cálculo de rendimento e a aplicação da taxa de serviço. Isso permite que o `ClienteService` processe o lucro da empresa de forma polimórfica, tratando diferentes produtos (Renda Fixa, Variável ou CDI) sob um mesmo contrato financeiro.
- Escalabilidade: Novos modelos de investimento podem ser acoplados ao sistema apenas assinando a interface.

2.2. Hierarquia de Classes e Especialização
- Abstração: A classe `Conta` fornece a base comum para `saldo` e `titularidade`, enquanto subclasses como `ContaCdi` e `ContaInvestimentoAuto` especializam o comportamento de alocação
- Agregação de Produtos: A `ContaInvestimentoAuto` utiliza uma lista de `ProdutoInvestimento`, permitindo a alocação estratégica mencionada no modelo de negócio, onde diferentes ativos com diferentes carências convivem em uma única carteira.

## 3. Regras de Negócio e Integridade de Dados
### 3.1. Segurança Financeira e Remoção
Travas de segurança foram implementadas para proteger tanto o cliente quanto a startup:

- Trava de Saldo: Nenhuma conta ou cadastro de cliente pode ser excluído se houver saldo positivo Isso garante que a startup não perca a base de cálculo de suas taxas e que o cliente não tenha fundos "esquecidos" no encerramento.
- Multiplicidade Obrigatória: O sistema impede que um cliente fique sem contas vinculadas (regra 1..* no UML), garantindo a continuidade da prestação de serviço.
  
### 3.2. Auditoria e Carência
- Validação de Prazo: O design inclui verificações de carência para produtos de Renda Fixa. Se o período de simulação for menor que a carência, o sistema alerta o usuário e ajusta a cobrança de taxas, refletindo a transparência exigida no mercado financeiro.

## 4. Arquitetura de Software
### 4.1. Separação de Responsabilidades
- `ClienteService`: Atua como a "fachada" de regras de negócio, gerenciando o ciclo de vida dos clientes e as simulações globais, sem se preocupar com a interface de usuário.

- Camada de Modelo: Contém a inteligência dos dados. Por exemplo, a classe `Cliente` é responsável por gerenciar suas próprias contas.
