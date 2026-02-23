package exercicio_UsoDeClassesAbstratas;

public class Funcionario extends Pessoa {
    private Float salario;

    public Funcionario(String nome, Data nascimento, Float salario){
        super(nome, nascimento);
        this.salario = salario;
    }

    public float getSalario() {return this.salario;}

    public float calculaImposto(){

        return getSalario() * 0.03f;

    }

    @Override
    public void imprimeDados(){
        System.out.println("--- DADOS DO FUNCIONÁRIO ---");
        System.out.println("Nome: " + getNome());
        System.out.println("Data de Nascimento: " + getNascimento()); 
        System.out.println("Salário Bruto: " + getSalario());
        System.out.println("Valor do Imposto (3%): " + calculaImposto());
        System.out.println("Salário Líquido: " + (getSalario() - calculaImposto()));
    }
}
