package modelos;

public class RendaFixa extends ProdutoInvestimento{
    private int carencia;
    
    public RendaFixa(String nome, double valorAportado, String descricao, int carencia, double rendimentoMensal) {
        super(nome, valorAportado, descricao, rendimentoMensal);
        this.carencia = carencia;
    }

    public int getCarencia() {return carencia;}

    public void setCarencia(int carencia) {this.carencia = carencia;}

    @Override
    public double simularRendimento(int dias) {
        
        return getValorAportado() * (getRendimentoMensal() / 30) * dias;
    }
    
    
}
