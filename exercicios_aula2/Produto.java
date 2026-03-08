public class Produto {

    private String nome, categoria;
    private double preco;

    public Produto(String nome, String categoria, double preco) {
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }
    
    public String getCategoria() {
        return this.categoria;
    }

    public double getPreco() {
        return this.preco;
    }
    
    @Override
    public String toString() {
        return String.format("Categoria: %-15s| Produto: %-25s | Preço:R$ %7.2f",
                categoria, nome, preco);
    }
}
