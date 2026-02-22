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
    public Double calcularPreco() {

        return getPrecoVenda();
    }

    @Override
    public String toString() {
        return "Lanche: " + "Recheio de " + recheio + " (Pão " + paoTipo + " com molho " + molho + " - Validade: " + getDataValidade() + ")" + " - R$ " + getPrecoVenda();
    }
}
