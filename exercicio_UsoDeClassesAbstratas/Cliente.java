package exercicio_UsoDeClassesAbstratas;

public class Cliente extends Pessoa {
    private int codigo;

    public Cliente (String nome, Data nascimento, int codigo ){
        super(nome, nascimento);
        this.codigo = codigo;
    }

    public int getCodigo() {return this.codigo;}

    @Override
    public void imprimeDados(){
        System.out.println("--- DADOS DO CLIENTE ---");
        System.out.println("Código: " + getCodigo());
        System.out.println("Nome: " + getNome());
        System.out.println("Data de Nascimento: " + getNascimento());
    }
    
}
