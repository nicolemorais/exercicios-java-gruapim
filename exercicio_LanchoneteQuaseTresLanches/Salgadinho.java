package exercicio_LanchoneteQuaseTresLanches;

import java.time.LocalDate;

public class Salgadinho extends Prato {
    private String tipo;
    private String massa;
    private String recheio;

    public Salgadinho(double peso, double precoVenda, LocalDate dataValidade, String tipo, String massa,
            String recheio) {
        super(peso, precoVenda, dataValidade);
        this.tipo = tipo;
        this.massa = massa;
        this.recheio = recheio;

    }

    @Override
    public Double calcularPreco() {
        
        return getPrecoVenda();
    }

    @Override
    public String toString() {
        return "Salgadinho: " + "Recheio de " + recheio + " (" + tipo + " com massa " + massa + ") - R$ " + getPrecoVenda();
    }
}
