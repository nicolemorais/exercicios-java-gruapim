package exercicio_LanchoneteQuaseTresLanches;
import java.time.LocalDate;
import java.util.Scanner;


public class SistemaLanchonete {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        //3 dias de validade a partir da data de criação
        LocalDate dataValidade = LocalDate.now().plusDays(3);

        Pizza p1 = new Pizza(.600, 35.00, dataValidade, "Tomate","Frango com catupiry", "Catupiry");
        Lanche l1 = new Lanche( 0.400, 6.00, dataValidade, "francês", "Calabresa", "Katchup");
        Salgadinho s1 = new Salgadinho(0.80, 2, dataValidade, "Assado","Folhada","Carne");

        Pedido Pedido1 = new Pedido("Marina", 5.00);

        Pedido1.getItensConsumidos().add(p1);
        Pedido1.getItensConsumidos().add(l1);
        Pedido1.getItensConsumidos().add(s1);

        Pedido1.mostrarFatura();

        System.out.println("------------------------");
        double totalDaConta = Pedido1.calcularTotal();
        System.out.print("Valor recebido em dinheiro: R$ ");
        double valorPago = reader.nextDouble();

        double troco = valorPago - totalDaConta;

        if (troco < 0){
            System.out.println("Erro: Faltam R$ " + Math.abs(troco));
        }else{
            System.out.printf("Retorne R$ %.2f de troco.\n", troco);
        }

        reader.close();

    }
}
