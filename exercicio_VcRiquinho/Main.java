import java.util.List;
import java.util.Scanner;
import modelos.*;
import servicos.*;

public class Main {
    private static ClienteService clienteService = new ClienteService();
    private static ProdutoService produtoService = new ProdutoService();
    private static Scanner leitor = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n========== SISTEMA VC RIQUINHO ==========");
            System.out.println("1 - Gerenciar Clientes");
            System.out.println("2 - Gerenciar Produtos");
            System.out.println("3 - Abrir Conta e Simular Rendimentos");
            System.out.println("0 - Sair");

            opcao = Integer.parseInt(leitor.nextLine());
            switch (opcao) {
                case 1:
                    menuClientes();
                    break;
                case 2:
                    menuProdutos();
                    break;
                case 3:
                    menuContas();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
            }
        }

    }

    // MENU DE CLIENTES
    private static void menuClientes() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- GERENCIAR CLIENTES ---");
            System.out.println("1 - Cadastrar Novo Cliente");
            System.out.println("2 - Consultar Cliente (por CPF/CNPJ)");
            System.out.println("3 - Atualizar Dados (Nome/Email)");
            System.out.println("4 - Remover Cliente");
            System.out.println("0 - Voltar ao Menu Principal");

            opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    consultarCliente();
                    break;
                case 3:
                    atualizarCliente();
                    break;
                case 4:
                    removerCliente();
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void cadastrarCliente() {
        System.out.println("\n[NOVO CADASTRO]");
        System.out.print("Nome: ");
        String nome = leitor.nextLine();
        System.out.print("Email: ");
        String email = leitor.nextLine();
        System.out.print("Documento (CPF ou CNPJ): ");
        String documento = leitor.nextLine();
        System.out.println("Tipo: 1- Pessoa Física | 2- Pessoa Jurídica");
        int tipo = Integer.parseInt(leitor.nextLine());

        Cliente novo;
        if (tipo == 1) {
            novo = new PessoaFisica(nome, email, documento);
        } else {
            novo = new PessoaJuridica(nome, email, documento);
        }

        System.out.print("Saldo inicial para a Conta Corrente: ");
        double saldoInicial = Double.parseDouble(leitor.nextLine());

        ContaCorrente contaInicial = new ContaCorrente(saldoInicial, novo);
        novo.adicionarConta(contaInicial);

        if (clienteService.cadastrar(novo)) {
            System.out.println(">>> Cliente do tipo " + novo.getTipo() + " cadastrado com sucesso!");
        } else {
            System.out.println(">>> Erro: Cliente já existe ou dados inválidos.");
        }
    }

    private static void consultarCliente() {
        System.out.print("\nDigite o CPF/CNPJ para busca: ");
        String documento = leitor.nextLine();
        Cliente cliente = clienteService.consultar(documento);

        if (cliente != null) {
            System.out.println("---------------------------");
            System.out.println("Nome: " + cliente.getNome());
            System.out.println("Email: " + cliente.getEmail());
            System.out.println("Documento: " + cliente.getIdentificador());
            System.out.println("Tipo: " + cliente.getTipo());
            System.out.println("Contas vinculadas: " + cliente.getContas().size());
            System.out.println("---------------------------");
        } else {
            System.out.println(">>> Cliente não encontrado.");
        }
    }

    private static void atualizarCliente() {
        System.out.print("\nDigite o CPF/CNPJ do cliente que deseja alterar: ");
        String documento = leitor.nextLine();

        if (clienteService.consultar(documento) != null) {
            System.out.print("Novo Nome: ");
            String novoNome = leitor.nextLine();
            System.out.print("Novo Email: ");
            String novoEmail = leitor.nextLine();

            if (clienteService.atualizar(documento, novoNome, novoEmail)) {
                System.out.println(">>> Dados atualizados com sucesso!");
            }
        } else {
            System.out.println(">>> Cliente não encontrado.");
        }
    }

    private static void removerCliente() {
        System.out.print("Digite o CPF/CNPJ do cliente que deseja excluir: ");
        String documento = leitor.nextLine();

        if (clienteService.remover(documento)) {
            System.out.println(">>> Cliente removido com sucesso!");
        } else {
            System.out.println(">>> Erro: Não foi possível remover. Verifique se o cliente existe ou se possui saldo.");
        }
    }

    // MENU DE PRODUTOS

    private static void menuProdutos() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- GERENCIAR PRODUTOS ---");
            System.out.println("1 - Cadastrar Renda Fixa");
            System.out.println("2 - Cadastrar Renda Variável");
            System.out.println("3 - Consultar Produto");
            System.out.println("4 - Atualizar Produto");
            System.out.println("5 - Remover Produto");
            System.out.println("0 - Voltar ao Menu Principal");

            opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    cadastrarProduto(true);
                    break;
                case 2:
                    cadastrarProduto(false);
                    break;
                case 3:
                    consultarProduto();
                    break;
                case 4:
                    atualizarProduto();
                    break;
                case 5:
                    removerProduto();
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void cadastrarProduto(boolean isRendaFixa) {
        System.out.println("\n[NOVO PRODUTO]");
        System.out.print("Nome do Investimento: ");
        String nome = leitor.nextLine();
        System.out.print("Valor do Aporte Inicial: ");
        double valor = Double.parseDouble(leitor.nextLine());
        System.out.print("Descrição curta: ");
        String descricao = leitor.nextLine();
        System.out.print("Rendimento Mensal (ex: 0.0193 para 1,93%): ");
        double rendimento = Double.parseDouble(leitor.nextLine());

        ProdutoInvestimento novo;
        if (isRendaFixa) {
            System.out.print("Dias de Carência: ");
            int carencia = Integer.parseInt(leitor.nextLine());
            novo = new RendaFixa(nome, valor, descricao, carencia, rendimento);
        } else {
            novo = new RendaVariavel(nome, valor, descricao, rendimento);
        }

        if (produtoService.cadastrar(novo)) {
            System.out.println(">>> Produto '" + nome + "' adicionado ao catálogo!");
        } else {
            System.out.println(">>> Erro: Produto já cadastrado.");
        }
    }

    private static void consultarProduto() {
        System.out.print("\nNome do produto para busca: ");
        String nome = leitor.nextLine();
        ProdutoInvestimento produto = produtoService.consultar(nome); //

        if (produto != null) {
            System.out.println("--- Dados do Investimento ---");
            System.out.println("Nome: " + produto.getNome());
            System.out.println("Valor: R$ " + String.format("%.2f", produto.getValorAportado()));
            System.out.println("Rendimento: " + (produto.getRendimentoMensal() * 100) + "% ao mês");

            if (produto instanceof RendaFixa) {
                System.out.println("Carência: " + ((RendaFixa) produto).getCarencia() + " dias"); //
            }
        } else {
            System.out.println(">>> Produto não localizado no catálogo.");
        }
    }

    private static void atualizarProduto() {
        System.out.print("\nDigite o nome do produto que deseja atualizar: ");
        String nome = leitor.nextLine();

        ProdutoInvestimento produto = produtoService.consultar(nome);

        if (produto != null) {
            System.out.println("Produto encontrado: " + produto.getNome());
            System.out.print("Novo Valor Aportado: ");
            double novoValor = Double.parseDouble(leitor.nextLine());
            System.out.print("Novo Rendimento Mensal (ex: 0.02): ");
            double novoRendimento = Double.parseDouble(leitor.nextLine());

            produto.setValorAportado(novoValor);
            produto.setRendimentoMensal(novoRendimento);

            if (produto instanceof RendaFixa) {
                System.out.print("Novos Dias de Carência: ");
                int novaCarencia = Integer.parseInt(leitor.nextLine());
                ((RendaFixa) produto).setCarencia(novaCarencia);
            }

            if (produtoService.atualizar(nome, produto)) {
                System.out.println(">>> Produto atualizado com sucesso!");
            }
        } else {
            System.out.println(">>> Produto não encontrado no catálogo.");
        }
    }

    private static void removerProduto() {
        System.out.print("\nNome do produto a remover: ");
        String nome = leitor.nextLine();
        if (produtoService.remover(nome)) {
            System.out.println(">>> Produto removido com sucesso.");
        } else {
            System.out.println(">>> Produto não encontrado.");
        }
    }

    // MENU CONTAS
    private static void menuContas() {
        System.out.print("\nDigite o CPF/CNPJ do cliente para gerenciar: ");
        String documento = leitor.nextLine();
        Cliente cliente = clienteService.consultar(documento);

        if (cliente == null) {
            System.out.println(">>> Erro: Cliente não encontrado.");
            return;
        }

        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- CONTAS DE: " + cliente.getNome().toUpperCase() + " ---");
            System.out.println("1 - Abrir Conta Corrente");
            System.out.println("2 - Abrir Conta CDI");
            System.out.println("3 - Abrir Conta Investimento Auto");
            System.out.println("4 - Realizar Simulação de Rendimentos");
            System.out.println("5 - Remover Conta");
            System.out.println("0 - Voltar ao Menu Principal");

            opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    abrirCorrente(cliente);
                    break;
                case 2:
                    abrirCdi(cliente);
                    break;
                case 3:
                    abrirInvestimentoAuto(cliente);
                    break;
                case 4:
                    simularRendimento(cliente);
                    break;
                case 5:
                    removerConta(cliente);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void abrirCorrente(Cliente cliente) {
        System.out.print("Saldo Inicial: ");
        double saldo = Double.parseDouble(leitor.nextLine());
        cliente.adicionarConta(new ContaCorrente(saldo, cliente));
        System.out.println(">>> Conta Corrente aberta!");
    }

    private static void abrirCdi(Cliente cliente) {
        System.out.print("Saldo Inicial para CDI: ");
        double saldo = Double.parseDouble(leitor.nextLine());
        cliente.adicionarConta(new ContaCdi(saldo, cliente));
        System.out.println(">>> Conta CDI aberta!");
    }

    private static void abrirInvestimentoAuto(Cliente cliente) {
        System.out.println("\n[VINCULAR PRODUTO]");
        System.out.print("Nome do Produto: ");
        String nomeProduto = leitor.nextLine();
        ProdutoInvestimento produto = produtoService.consultar(nomeProduto);

        if (produto != null) {
            ContaInvestimentoAuto novaAuto = new ContaInvestimentoAuto(0, cliente, produto);
            cliente.adicionarConta(novaAuto);
            System.out.println(">>> Conta Investimento Automático vinculada ao produto '" + produto.getNome() + "'!");
        } else {
            System.out.println(">>> Erro: Produto não existe.");
        }
    }

    private static void simularRendimento(Cliente cliente) {
        System.out.println("\n--- SIMULAÇÃO DE RENDIMENTOS ---");
        System.out.print("Informe o período da simulação (em dias): ");

        try {
            int dias = Integer.parseInt(leitor.nextLine());

            double lucroTotal = clienteService.simularRendimentos(cliente, dias);

            System.out.println("\n========================================");
            System.out.println("   RESULTADO DA SIMULAÇÃO - VC RIQUINHO");
            System.out.println("========================================");
            System.out.println("Cliente: " + cliente.getNome() + " (" + cliente.getTipo() + ")");
            System.out.println("Período: " + dias + " dias");
            System.out.println("Lucro Líquido Estimado: R$ " + String.format("%.2f", lucroTotal));
            System.out.println("========================================\n");

        } catch (NumberFormatException error) {
            System.out.println(">>> Erro: Por favor, insira um número inteiro para os dias.");
        }
    }

    private static void removerConta(Cliente cliente) {
        System.out.println("\n--- CONTAS DE " + cliente.getNome() + " ---");
        List<Conta> listaContas = cliente.getContas();

        for (int i = 0; i < listaContas.size(); i++) {
            System.out.println("[" + i + "] " + listaContas.get(i).getClass().getSimpleName());
        }

        System.out.print("Escolha o índice para remover: ");
        int indice = Integer.parseInt(leitor.nextLine());

        if (listaContas.size() > 1) {
            if (indice >= 0 && indice < listaContas.size()) {
                cliente.removerConta(indice); // Chama o método da classe Cliente
                System.out.println(">>> Conta removida com sucesso!");
            } else {
                System.out.println(">>> Erro: Índice inválido.");
            }
        } else {
            System.out.println(">>> Erro: O cliente deve possuir pelo menos uma conta cadastrada.");
        }
    }

    private static int lerOpcao() {
        try {
            return Integer.parseInt(leitor.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}