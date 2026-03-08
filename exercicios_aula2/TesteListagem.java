import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TesteListagem {
    public static void main(String[] args) {
        List<Produto> listaProdutos = new ArrayList<>();

        listaProdutos.add(new Produto("Galaxy S25 Ultra", "Eletrônicos", 7650.00));
        listaProdutos.add(new Produto("Smart TV 40\" AOC Roku", "Eletrônicos", 1043.10));
        listaProdutos.add(new Produto("Galaxy Buds 2 Pro", "Eletrônicos", 700.00));

        listaProdutos.add(new Produto("A Cabeça do Santo", "Livros", 42.29));
        listaProdutos.add(new Produto("Vidas Secas", "Livros", 34.90));
        listaProdutos.add(new Produto("A Amiga Genial", "Livros", 79.90));

        listaProdutos.add(new Produto("Ipad mini A17 Pro", "Eletrônicos", 7499.00));
        listaProdutos.add(new Produto("A Morte de Ivan Ilitch", "Livros", 59.90));

        // Estrutura "forEach" e "If" vs .steam() e .filter()
        exercicio_a.filtrarProdutosPorCategoriaEletronico(listaProdutos);

        // .filter() e .map()
        exercicio_b.filtrarProdutosPorPreco(listaProdutos);

        // .filter(), mapToDouble e sum()
        exercicio_c.calcularValorEstoqueLivro(listaProdutos);

        // exericio_e
        System.out.println("\n--- Com Optional, .filter(), .findFirst(), .ifPresent() e .orElseThrow() ---\n");

        System.out.println("CENÁRIO 1: Produto que existe\n");
        Optional<Produto> produtoExistente = exercicio_d.buscarProdutoPorNome(listaProdutos, "Galaxy S25 Ultra");
        produtoExistente.ifPresent(p -> System.out.println(p));

        try {
            System.out.println("\nCENÁRIO 2: Produto que não existe\n");
            Optional<Produto> produtoInexistente = exercicio_d.buscarProdutoPorNome(listaProdutos,
                    "O Mestre e Margarida");

            Produto p = produtoInexistente.orElseThrow(() -> new RuntimeException("Produto não encontrado!"));
            System.out.println(p);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        exercicio_f.listarProdutosPorNomes(listaProdutos);

    }
}
