import java.util.List;
import java.util.Optional;

public class exercicio_d {
    public static Optional<Produto> buscarProdutoPorNome(List<Produto> listaProdutos, String nome){

        return listaProdutos.stream()
        .filter(p -> p.getNome().equalsIgnoreCase(nome))
        .findFirst();
    }
}
