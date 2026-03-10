package exercicio_AcademiaDev;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static AcademiaDevPlatform platform = new AcademiaDevPlatform();
    private static User currentUser = null;

    public static void main(String[] args) {
        InitialData.populate(platform);
        System.out.println("=== ACADEMIADEV ===");

        while (true) {
            Optional.ofNullable(currentUser)
                    .ifPresentOrElse(
                            Main::exibirMenu,
                            Main::realizarLogin);
        }
    }

    private static void realizarLogin() {
        System.out.print("\nDigite seu e-mail (ou 'sair'): ");
        String email = scanner.nextLine();

        if (email.equalsIgnoreCase("sair"))
            System.exit(0);

        platform.findUserByEmail(email).ifPresentOrElse(
                user -> currentUser = user,
                () -> System.out.println("Usuário não encontrado."));
    }

    private static void exibirMenu(User user) {
        Map<Class<? extends User>, Consumer<User>> menus = Map.of(
                Admin.class, u -> menuAdmin(),
                Student.class, u -> menuStudent((Student) u));

        Optional.ofNullable(menus.get(user.getClass()))
                .ifPresentOrElse(
                        acao -> acao.accept(user),
                        () -> System.out.println("Usuário desconhecido."));
    }

    private static void menuAdmin() {
        Map<String, Runnable> acoes = new LinkedHashMap<>();
        acoes.put("1", platform::processNextTicket); // Method Reference
        acoes.put("2", () -> platform.imprimirRelatorios());
        acoes.put("3", Main::executarExportacao);
        acoes.put("4", () -> currentUser = null);

        System.out.println("\n[1] Atender Ticket | [2] Relatórios | [3] Exportar | [4] Sair");
        executarEscolha(acoes);
    }

    private static void menuStudent(Student s) {
        Map<String, Runnable> acoes = new LinkedHashMap<>();
        acoes.put("1", () -> matricularAluno(s));
        acoes.put("2", () -> s.getEnrollments().forEach(System.out::println));
        acoes.put("3", () -> abrirTicket(s));
        acoes.put("4", () -> currentUser = null);

        System.out.println("\n[1] Matricular | [2] Ver Matrículas | [3] Suporte | [4] Sair");
        executarEscolha(acoes);
    }

    private static void executarEscolha(Map<String, Runnable> acoes) {
        System.out.print("Escolha: ");
        String opcao = scanner.nextLine();
        Optional.ofNullable(acoes.get(opcao))
                .ifPresentOrElse(Runnable::run, () -> System.out.println("Opção inválida."));
    }

    private static void abrirTicket(Student s) {
        System.out.print("Título: ");
        String t = scanner.nextLine();
        System.out.print("Msg: ");
        String m = scanner.nextLine();
        platform.openTicket(s, t, m);
    }

    private static void executarExportacao() {
        List<String> colunas = List.of("name", "email", "subscriptionPlan");
        System.out.println(platform.exportToCSV(platform.getAllStudents(), colunas));
    }

    private static void matricularAluno(Student s) {
        System.out.print("Digite o título do curso: ");
        String titulo = scanner.nextLine();

        platform.findCourseByTitle(titulo).ifPresentOrElse(
                course -> {
                    try {
                        Enrollment e = new Enrollment(s, course);
                        s.addEnrollment(e);
                        System.out.println("Matrícula realizada!");
                    } catch (Exception ex) {
                        System.out.println("Erro: " + ex.getMessage());
                    }
                },
                () -> System.out.println("Curso não encontrado ou inativo."));
    }
}
