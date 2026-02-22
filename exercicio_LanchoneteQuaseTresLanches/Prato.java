package exercicio_LanchoneteQuaseTresLanches;
import java.time.LocalDate;

public abstract class Prato {
    private double peso;
    private double precoVenda;
    private LocalDate dataValidade;


    public Prato(double peso, double precoVenda, LocalDate dataValidade) {
        this.peso = peso;
        this.precoVenda = precoVenda;
        this.dataValidade = dataValidade;
    }

    public double getPeso() {return peso;}
    public double getPrecoVenda() {return precoVenda;}
    public LocalDate getDataValidade() {return dataValidade;}

    public abstract Double calcularPreco();

}
