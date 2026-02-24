package modelos;
public class PessoaFisica extends Cliente {

    private String cpf;

    public PessoaFisica(String nome, String email, String cpf){
        super(nome, email);
        this.cpf = cpf;
    }

   @Override
   public String getIdentificador() {

       return this.cpf;
   }

   @Override
   public String getTipo() {
       return "PF";
   }

    @Override
    public double getTaxa() {
        return 0.001; // 0,1%
}

}
