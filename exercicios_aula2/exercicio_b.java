import java.util.List;

public class exercicio_b {

    public static void filtrarProdutosPorPreco(List<Produto> listaProdutos){
         System.out.println("\n--- Com filter() e map() ---\n");
        listaProdutos.stream()
                .filter(p -> p.getPreco() > (500))
                .map(p -> p.getPreco())
                .forEach(preco -> System.out.printf("Preço:R$ %.2f%n", preco));
    }
}
