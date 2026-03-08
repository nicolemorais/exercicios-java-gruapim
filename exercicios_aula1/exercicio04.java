public class exercicio04 {
    public static void main(String[] args) {
        
        int [] lista = {25, 55, 3, 20, 9, 22, 97, 1, 30, 60, 62, 27, 83};
        int soma = 0;

        for (int numero:lista){
            if (numero % 2 != 0){
              soma += numero;
            }
        }

        System.out.println("A soma dos ímpares é: " + soma);
    }
}
