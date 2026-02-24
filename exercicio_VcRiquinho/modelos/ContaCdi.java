package modelos;

import interfaces.InvestimentoRentavel;

public class ContaCdi extends Conta implements InvestimentoRentavel{

    // Valor da taxa de serviço 0,07% 
    private static final double TAXA_SERVICO_CDI = 0.0007;

    // Projeção CDI Mensal de 1,93% (0.0193)
    private static final double TAXA_CDI_MENSAL = 0.0193;

    public ContaCdi(double saldo, Cliente titular){
        super(saldo, titular);
    }

    @Override
    public double simularRendimento(int dias) {

        double taxaDiaria = TAXA_CDI_MENSAL / 30;
        return getSaldo() * taxaDiaria * dias;
    }

    @Override
    public double calcularTaxaServico(double ganhos, int dias) {        
        
        return ganhos * TAXA_SERVICO_CDI;
    }
}
