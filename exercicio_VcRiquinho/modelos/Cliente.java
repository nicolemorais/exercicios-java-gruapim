package modelos;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public abstract class Cliente {

    private String nome;
    private String email;
    private ArrayList<Conta> contas;

    public Cliente(String nome, String email) {
        this.nome = nome;
        this.email = email;
        this.contas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public boolean setNome(String nome) {
        if (nome != null && !nome.trim().isEmpty()) {
            this.nome = nome;
            return true;
        }
        return false;
    }

    public String getEmail() {
        return email;
    }

    public boolean setEmail(String email) {
        if (email != null && email.contains("@")) {
            this.email = email;
            return true;
        }
        return false;
    }

    public List<Conta> getContas() {

        return Collections.unmodifiableList(this.contas);
    }

    public Boolean adicionarConta(Conta novaConta) {

        if (novaConta != null) {

            return this.contas.add(novaConta);
        }
        return false;
    }

    public boolean removerConta(int indice) {
        Conta conta = this.contas.get(indice);

        if (conta.getSaldo() > 0) {
            System.out.println(
                    ">>> Erro: Não é possível remover uma conta com saldo positivo (R$ " + conta.getSaldo() + ").");
            return false;
        }

        this.contas.remove(indice);
        return true;
    }

    public abstract String getTipo();

    public abstract String getIdentificador();

    public abstract double getTaxa();

}
