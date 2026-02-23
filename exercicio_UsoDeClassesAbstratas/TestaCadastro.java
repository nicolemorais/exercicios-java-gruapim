package exercicio_UsoDeClassesAbstratas;

public class TestaCadastro {
    public static void main(String[] args) {
        CadastroPessoas cp = new CadastroPessoas(10);
        Data d1 = new Data(10, 5, 1990);
        Data d2 = new Data(20, 8, 1985);
        Data d3 = new Data(9, 01, 1975);

        Cliente c1 = new Cliente("Juliana Soares", d1, 1001);
        Funcionario f1 = new Funcionario("Ana Souza", d2, 5000.0f);
        Gerente g1 = new Gerente("Roberto Oliveira", d3, 16000.0f, "TI");

        cp.cadastraPessoa(c1);
        cp.cadastraPessoa(f1);
        cp.cadastraPessoa(g1);

        cp.imprimeCadastro();

    }
}
