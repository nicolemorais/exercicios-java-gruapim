import java.util.List;

public class exercicio_c {

    public static void calcularValorEstoqueLivro(List<Produto> listaProdutos) {

        System.out.println("\n--- Com .filter(), .mapToDouble e sum() ---\n");
        Double totalLivros = listaProdutos.stream()
                .filter(p -> p.getCategoria().equalsIgnoreCase("Livros"))
                .mapToDouble(p -> p.getPreco())
                .sum();

        System.out.printf("Preço Total da Categoria \"Livros\": R$ %.2f%n", totalLivros);

    }
}
