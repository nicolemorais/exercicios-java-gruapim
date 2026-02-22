package exercicio_LanchoneteQuaseTresLanches;

import java.time.LocalDate;
import java.util.Scanner;

public class SistemaLanchonete {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        // 3 dias de validade a partir da data de criação
        LocalDate dataValidade = LocalDate.now().plusDays(3);

        Pizza p1 = new Pizza(.800, 45.00, dataValidade, "Tomate", "Frango com catupiry", "Catupiry");
        Lanche l1 = new Lanche(0.400, 12.00, dataValidade, "francês", "Calabresa", "Katchup");
        Salgadinho s1 = new Salgadinho(0.80, 4.50, dataValidade, "Assado", "Folhada", "Carne");

        Pedido Pedido1 = new Pedido("Marina", 5.00);

        Pedido1.getItensConsumidos().add(p1);
        Pedido1.getItensConsumidos().add(l1);
        Pedido1.getItensConsumidos().add(s1);

        Pedido1.mostrarFatura();

        System.out.println("------------------------");
        double totalDaConta = Pedido1.calcularTotal();
        double valorPago = 0;

        while (valorPago < totalDaConta) {
            double falta = totalDaConta - valorPago;
            System.out.printf("Faltam R$ %.2f. Digite o valor entregue: R$ ", falta);

            double valorFaltante = reader.nextDouble();

            if (valorFaltante > 0) {
                valorPago += valorFaltante;
            } else {
                System.out.println("Por favor, insira um valor positivo.");
            }
        }
        double troco = valorPago - totalDaConta;

        System.out.println("\n------------------------");
        System.out.printf("Total pago: R$ %.2f\n", valorPago);

        if (troco > 0) {
            System.out.printf("Troco a devolver: R$ %.2f\n", troco);
        }else{
            System.out.println("Pagamento exato. Não há troco.");
        }

    }
}
