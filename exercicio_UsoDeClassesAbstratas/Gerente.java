package exercicio_UsoDeClassesAbstratas;

public class Gerente extends Funcionario{
    private String area;

    public Gerente (String nome, Data nascimento, Float salario, String area){
        super(nome, nascimento, salario);
        this.area = area;
    }

     public String getArea() {return this.area;}

   @Override
   public float calculaImposto() {

       return getSalario() * 0.05f;

   }

   @Override
   public void imprimeDados() {
     System.out.println("--- DADOS DO GERENTE ---");
        System.out.println("Nome: " + getNome());
        System.out.println("Nascimento: " + getNascimento());
        System.out.println("Área: " + getArea());
        System.out.println("Salário Bruto: " + getSalario());
        System.out.println("Valor do Imposto (%5): " + calculaImposto());
        System.out.println("Salário Líquido: " + (getSalario() - calculaImposto()));
   }

}
