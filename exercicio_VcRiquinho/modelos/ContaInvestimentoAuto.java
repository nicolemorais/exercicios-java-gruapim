package modelos;
import java.util.List;

import interfaces.InvestimentoRentavel;

import java.util.ArrayList;
import java.util.Collections;

public class ContaInvestimentoAuto extends Conta implements InvestimentoRentavel {

    private ArrayList<ProdutoInvestimento> produtos;

    public ContaInvestimentoAuto(double saldo, Cliente titular, ProdutoInvestimento produtoInicial) {
        super(saldo, titular);
        this.produtos = new ArrayList<>();
        if (produtoInicial != null){
            this.produtos.add(produtoInicial);
        }
        
    }

    public List<ProdutoInvestimento> getProdutos(){
        
        return Collections.unmodifiableList(this.produtos); 
    }

    public boolean adicionarProduto(ProdutoInvestimento novoProduto){
        if (novoProduto != null && !this.produtos.contains(novoProduto)){

            return this.produtos.add(novoProduto);

        }
            return false;
    }
   
    public boolean removerProduto(ProdutoInvestimento produtoParaRemover){
        if (this.produtos.size() > 1){

            return this.produtos.remove(produtoParaRemover);

        }
            return false;
    }

    @Override
    public double simularRendimento(int dias) {
        double total = 0;
        
        for (ProdutoInvestimento produto : produtos){
            total += produto.simularRendimento(dias);
        }

        return total;
    }

    @Override
    public double calcularTaxaServico(double ganhos, int dias) {
        double rendimentoTributavel = 0;

        for (ProdutoInvestimento produto : produtos){
             double rendimentoProduto = produto.simularRendimento(dias);

            // Regra da carência
           if (produto instanceof RendaFixa){
            RendaFixa rf = (RendaFixa) produto;
            if (dias < rf.getCarencia()){
                System.out.println("[AVISO] Produto '" + rf.getNome() + "' em carência (" + rf.getCarencia() + " dias)." + " Rendimento de R$ " + rendimentoProduto + " ignorado na taxa.");

                continue;
            }
           }

           // Se não for renda fixa ou se a carência já passou aplica-se a taxa
           rendimentoTributavel += rendimentoProduto;
        }

        // taxa final (0.1% (pf) ou 0.15% (pj))
        return rendimentoTributavel * getTitular().getTaxa();
    }
}
