package exercicio_LanchoneteQuaseTresLanches;

import java.util.ArrayList;

public class Pedido {
    private String nomeCliente;
    private double taxaServico;
    private ArrayList<Prato> itensConsumidos;

    public Pedido(String nomeCliente, double taxaServico) {
        this.nomeCliente = nomeCliente;
        this.taxaServico = taxaServico;
        this.itensConsumidos = new ArrayList<>();
    }

    public double calcularTotal() {
        double total = 0;
        for (Prato p : itensConsumidos) {
            total = total + p.getPrecoVenda();
        }
        total = total + taxaServico;
        return total;
    }

    public void mostrarFatura() {
        System.out.println("--- NOTA FISCAL ---");
        System.out.println("Cliente: " + this.nomeCliente);

        for (Prato pratos : itensConsumidos) {
            System.out.println("- " + pratos);
        }

        System.out.println("Taxa de serviço: R$ " + this.taxaServico);
        System.out.println("Total: R$ " + calcularTotal());
    }

    public ArrayList<Prato> getItensConsumidos() {
        return this.itensConsumidos;
    }
}
