package servicos;
import java.util.ArrayList;
import java.util.List;

import modelos.ProdutoInvestimento;

import java.util.Collections;

public class ProdutoService {

    private ArrayList<ProdutoInvestimento> listaProdutos;

    public ProdutoService() {
         this.listaProdutos = new ArrayList<>();
    }

    public List<ProdutoInvestimento> getListaProdutos(){
        return Collections.unmodifiableList(this.listaProdutos);
    }

    public boolean cadastrar(ProdutoInvestimento produto) {
        if (produto != null && consultar(produto.getNome()) == null){
            return listaProdutos.add(produto);
        }
        return false;
    }

    public ProdutoInvestimento consultar(String nome){
        for (ProdutoInvestimento produto : listaProdutos){
            if (produto.getNome().equalsIgnoreCase(nome)){
                return produto;
            }
        }
        return null;
    }

    public boolean atualizar(String nome, ProdutoInvestimento produtoNovo){        
        for (int i = 0; i < listaProdutos.size(); i++){
            if (listaProdutos.get(i).getNome().equalsIgnoreCase(nome)){
                produtoNovo.setNome(nome);
                listaProdutos.set(i, produtoNovo);
                return true;
            }
        } 
         return false;
    }

    public boolean remover(String nome){
       for (int i = 0; i < listaProdutos.size(); i++){
        if (listaProdutos.get(i).getNome().equalsIgnoreCase(nome)){
            listaProdutos.remove(i);
            return true;
        }
    }
    return false;
    } 
}
