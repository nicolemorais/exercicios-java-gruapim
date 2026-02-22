package exercicio_LanchoneteQuaseTresLanches;

import java.time.LocalDate;

public class Pizza extends Prato {
    private String molho;
    private String recheio;
    private String borda;

    public Pizza(double peso, double precoVenda, LocalDate dataValidade, String molho, String recheio, String borda) {
        super(peso, precoVenda, dataValidade);
        this.molho = molho;
        this.recheio = recheio;
        this.borda = borda;
    }

    @Override
    public Double calcularPreco() {
        
        return getPrecoVenda();
    }

    @Override
    public String toString() {
        return "Pizza: " + "Recheio de " + recheio + " (Borda: " + borda + ") - R$ " + getPrecoVenda();
    }
}
