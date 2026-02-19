import java.util.Scanner;

public class exercicio01{
    public static void main(String[] args) {
        
        Scanner reader = new Scanner(System.in);

        System.out.println("Insira as 3 notas do aluno: ");

        double nota1 = reader.nextDouble();

        double nota2 = reader.nextDouble();
        
        double nota3 = reader.nextDouble();

        double media = (nota1 + nota2 + nota3) / 3;

        System.out.printf("Média final: %.2f%n ", media);

       if (media >= 7) {
        System.out.println("Aprovado!");

       } else if (media >= 5){
        System.out.println("Recuperação.");

       }else {
        System.out.println("Reprovado!");
       }

       reader.close();
    }
}

