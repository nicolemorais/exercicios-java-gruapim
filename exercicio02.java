import java.util.Scanner;

public class exercicio02 {
    public static void main(String[] args) {
        
        Scanner reader = new Scanner(System.in);

        System.out.println("Insira um número inteiro para exibir sua tabuada: ");

        int numeroInt = reader.nextInt();

        System.out.println("*** TABUADA DO " + numeroInt + " *** ");


        for (int i = 1; i <= 10; i++) {
            
            int tabuada = numeroInt * i;

            System.out.println(numeroInt + " X " + i + " = " + tabuada);
            
        }

        reader.close();

    }
}

