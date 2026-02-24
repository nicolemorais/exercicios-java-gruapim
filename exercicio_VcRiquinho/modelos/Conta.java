package modelos;

public abstract class Conta {
    private double saldo;
    private Cliente titular;

    public Conta (double saldo, Cliente titular){
        this.saldo = saldo;
        this.titular = titular;
    }

    public double getSaldo() {return saldo;}

    public Cliente getTitular() {return titular;}

    public void depositar(double valor){
        if (valor > 0){
            this.saldo += valor;
        }
    }

    public boolean sacar(double valor){
        if (valor > 0 && valor <= this.saldo){
            this.saldo -= valor;
            return true;

        }else{
            return false;
        }
    }
    
}
