package servicos;

import java.util.ArrayList;
import java.util.List;

import interfaces.InvestimentoRentavel;
import modelos.Cliente;
import modelos.Conta;

import java.util.Collections;

public class ClienteService {

    private ArrayList<Cliente> listaClientes;

    public ClienteService() {
        this.listaClientes = new ArrayList<>();
    }

    public List<Cliente> getListaClientes() {
        return Collections.unmodifiableList(this.listaClientes);
    }

    public boolean cadastrar(Cliente cliente) {
        if (cliente != null && consultar(cliente.getIdentificador()) == null) {
            return listaClientes.add(cliente);
        }
        return false;
    }

    public Cliente consultar(String id) {
        for (Cliente cliente : listaClientes) {

            if (cliente.getIdentificador().equals(id))
                return cliente;
        }
        return null;
    }

    public boolean atualizar(String id, String novoNome, String novoEmail) {
        Cliente cliente = consultar(id);

        if (cliente != null) {
            cliente.setNome(novoNome);
            cliente.setEmail(novoEmail);
            return true;

        }
        return false;
    }

    public boolean remover(String id) {
    Cliente cliente = consultar(id);

    if (cliente != null) {
        double saldoTotal = 0;
        for (Conta conta : cliente.getContas()) {
            saldoTotal += conta.getSaldo();
        }

        if (saldoTotal > 0) {
            return false;
        }

        return listaClientes.remove(cliente);
    }
    
    return false;
}
    public double simularRendimentos(Cliente cliente, int dias) {
        double lucroLiquidoTotal = 0;

        if (cliente != null && cliente.getContas() != null) {
            for (Conta conta : cliente.getContas()) {

                if (conta instanceof InvestimentoRentavel) {
                    InvestimentoRentavel investimento = (InvestimentoRentavel) conta;

                    double rendimentoBruto = investimento.simularRendimento(dias);
                    double taxaServico = investimento.calcularTaxaServico(rendimentoBruto, dias);

                    lucroLiquidoTotal += (rendimentoBruto - taxaServico);
                }
            }
        }
        return lucroLiquidoTotal;
    }
}
