package modelos;

public class RendaVariavel extends ProdutoInvestimento {

    public RendaVariavel(String nome, double valorAportado, String descricao, double rendimentoMensal){
        super(nome, valorAportado, descricao, rendimentoMensal);
    }

    @Override
    public double simularRendimento(int dias){
        
        return getValorAportado() * (getRendimentoMensal()/ 30) * dias;
    }

}
