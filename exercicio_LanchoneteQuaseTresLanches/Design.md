# Documentação de Design: Sistema Lanchonete Quase Três Lanches

## 1. Visão Geral

Este sistema é um protótipo de cardápio virtual e gestão de pedidos desenvolvido em Java. O foco está na organização de diferentes categorias de alimentos e na automação do fechamento de contas para a lanchonete.

![Diagrama de Classes](/exercicio_LanchoneteQuaseTresLanches/Diagrama_de_Classes.png "Diagrama de Classes - Lanchonete Quase Três Lanches")

## 2. Arquitetura e Escolhas de Design

O sistema foi construído utilizando os quatro pilares da POO.

### Abstração e Herança
- **Classe `Prato`:** É uma **classe abstrata**, atuando como classe "pai". Nela foram definidos os atributos comuns a qualquer item vendido: `precoVenda`, `peso` e `dataValidade`. A escolha dessa classe para ser **abstrata** foi devido ao fato de não fazer sentido vender um "prato" genérico, mas sim um produto específico.
- **Subclasses (`Pizza`, `Lanche`, `Salgadinho`):** Estas classes estendem `Prato` e adicionam detalhes específicos exigidos pelo levantamento de requisitos, como tipos de borda para pizzas ou massas para salgadinhos.

### Encapsulamento
- Todos os atributos sensíveis (preços e dados do cliente) foram definidos como `private`. Para o acesso e a modificação desses dados ocorram exclusivamente através de métodos _Getters_ e _Setters_, garantindo que o estado do objeto não seja corrompido.

### Polimorfismo
- A classe `Pedido` funciona como o "agregador". Ela utiliza um `ArraList<Prato>`, essa lista armazena simultaneamente objetos de diferentes tipos, isso significa que um único pedido pode conter várias pizzas e lanches misturados. O _ArrayList_ torna o acesso por índice uma operação extremamente rápida (O(1)). Sendo ótimo para leitura e acesso rápido por posição.
- O método `calcularPreco()` é definido na classe "pai" e sobrescrito nas "filhas" permitindo que cada item saiba calcular seu próprio valor final.

## 3. Funcionamento do Sistema
O fluxo de execução segue da seguinte forma.
1. **Instanciação de Itens:** O sistema instancia itens específicos (ex: uma `Pizza` de calabresa ou um `Lanche` de mortadela). A data de validade é gerada automaticamente utilizando `LocalDate.now()` e somado mais 3 dias. Garantindo que o produto esteja sempre com a validade atualizada no momento da inserção, atendendo ao requisito de controle de qualidade.
2. **Gestão do Pedido:** Um objeto da classe `Pedido` é criado, recebendo o nome do cliente e a taxa de serviço. Os itens são adicionados à lista interna do pedido atráves do método `getItensConsumidos().add()`.
3. **Processamento da Nota Fiscal:** Ao chamar `mostrarFatura()`, o sistema percorre a lista de itens somando os preços e exibindo um resumo detalhado para o vendedor.
4. **Cálculo de Troco:** O sistema solicita o valor pago em dinheiro. utilizando o método `calcularTotal()`, o sitema subtrai o valor da conta do valor recebido e exibe o troco exato ao vendedor.

[def]: exercicio_LanchoneteQuaseTresLanches\Diagrama_de_Classes.png "Lanchonete Quase Três Lanches"