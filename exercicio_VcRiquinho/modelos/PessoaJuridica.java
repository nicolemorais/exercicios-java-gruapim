package modelos;
public class PessoaJuridica extends Cliente {
    
    public String cnpj;

    public PessoaJuridica(String nome, String email, String cnpj) {
        super(nome, email);
        this.cnpj = cnpj;
    }

    @Override
    public String getIdentificador() {
        
        return this.cnpj;
    }

    @Override
    public String getTipo() {

        return "PJ";
    }

    @Override
    public double getTaxa() {
        return 0.0015; // 0,15%
    }
    
}
