package modelos;

public abstract class ProdutoInvestimento {

    private String nome;
    private Double valorAportado;
    private String descricao;
    private Double rendimentoMensal;

    public ProdutoInvestimento(String nome, double valorAportado, String descricao, Double rendimentoMensal) {
        this.nome = nome;
        this.valorAportado = valorAportado;
        this.descricao = descricao;
        this.rendimentoMensal = rendimentoMensal;
    }

    public Double getValorAportado() {return valorAportado;}

    public void setValorAportado(Double valorAportado) {
        if (valorAportado >= 0) {
            this.valorAportado = valorAportado;
        } else {
            System.out.println("Erro: O valor aportado não pode ser negativo.");
        }
    }

    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}

    public String getDescricao() {return descricao;}    

    public void setDescricao(String descricao) {this.descricao = descricao;}

    public Double getRendimentoMensal() {return rendimentoMensal;}

    public void setRendimentoMensal(Double rendimentoMensal) {this.rendimentoMensal = rendimentoMensal;}

    public abstract double simularRendimento(int dias);

 

}
