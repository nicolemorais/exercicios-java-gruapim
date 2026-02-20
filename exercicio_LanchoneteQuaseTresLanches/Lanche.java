package exercicio_LanchoneteQuaseTresLanches;

import java.time.LocalDate;

public class Lanche extends Prato {
    private String paoTipo;
    private String recheio;
    private String molho;

    public Lanche(double peso, double precoVenda, LocalDate dataValidade, String paoTipo, String recheio, String molho) {
        super(peso, precoVenda, dataValidade);
        this.paoTipo = paoTipo;
        this.recheio = recheio;
        this.molho = molho;
    }

    @Override
    public void calcularPreco() {
        // O preço de venda já é o valor total.
        // Segundo os requisitos não há acréscimos.
    }

    @Override
    public String toString() {
        return "Lanche: " + "Recheio de " + recheio + " no Pão " + paoTipo + " com molho " + molho + " - R$ " + getPrecoVenda();
    }
}
