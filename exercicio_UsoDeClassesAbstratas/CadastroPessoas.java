package exercicio_UsoDeClassesAbstratas;

public class CadastroPessoas {
    private int qtdAtual;
    private Pessoa[] pessoas;

    public CadastroPessoas(int tamanhoMax) {
        this.pessoas = new Pessoa[tamanhoMax];
        this.qtdAtual = 0;
    }

    public void cadastraPessoa(Pessoa pess){
        if (qtdAtual < pessoas.length){
            pessoas[qtdAtual] = pess;
            qtdAtual++;

        }else{
            System.out.println("Erro: Cadastro lotado!");
        }
    }

    public void imprimeCadastro(){
        System.out.println("\n----- LISTA DE CADASTRO -----\n");
        for (int i = 0; i < qtdAtual; i++) {
            
            pessoas[i].imprimeDados();
            System.out.println("---------------------");
        }
    }

}
