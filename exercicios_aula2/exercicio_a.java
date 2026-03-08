import java.util.List;
import java.util.function.Consumer;

public class exercicio_a {

    public static void filtrarProdutosPorCategoriaEletronico(List<Produto> listaProdutos) {
        System.out.println("--- Com Estrutura Tradicional \"ForEach\" e \"If\" ---\n");
        listaProdutos.forEach(new Consumer<Produto>() {
            @Override
            public void accept(Produto p) {
                if (p.getCategoria().equalsIgnoreCase("Eletrônicos")) {
                    System.out.println(p);
                }
            }
        });

        System.out.println("\n--- Com .stream() e .filter()---\n");
        listaProdutos.stream()
                .filter(p -> p.getCategoria().equalsIgnoreCase("Eletrônicos"))
                .forEach(p -> System.out.println(p));
    }
}
