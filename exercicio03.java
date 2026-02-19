import java.util.Scanner;
import java.util.Random;

public class exercicio03 {
    public static void main(String[] args) {
     
    Scanner reader = new Scanner(System.in);
    Random aleatorizar = new Random();

    int numeroSecreto = aleatorizar.nextInt(100) + 1;

    int palpite = 0;
    int tentativas = 0;

    System.out.println("Tente adivinhar o número de 1 a 100!");

    while (palpite != numeroSecreto) {
        System.out.print("Insira seu palpite: ");
        palpite = reader.nextInt();

        tentativas = tentativas + 1;

        if (palpite == numeroSecreto){
            System.out.println("Parabéns! Você acertou!");
        }else if (palpite > numeroSecreto){
            System.out.println("Muito alto!");
        }else {
            System.out.println("Muito baixo!");
        }
    }
    
    System.out.println("Você conseguiu em " + tentativas + " tentativas.");
}
}


