import java.util.List;
import java.util.stream.Collectors;

public class exercicio_f {
    public static void listarProdutosPorNomes(List<Produto> listaProdutos) {
        List<String> nomes = listaProdutos.stream()
                .map(p -> p.getNome())
                .collect(Collectors.toList());

        System.out.println("\n--- Com expressão lambda ---\n");
        System.out.println(nomes);

        
        List<String> nomes2 = listaProdutos.stream()
                .map(Produto::getNome)
                .collect(Collectors.toList());

        System.out.println("\n--- Com Method Reference (Produto::getNome) ---\n");
        System.out.println(nomes2);

    }
}
