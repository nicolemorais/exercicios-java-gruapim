package exercicio_AcademiaDev;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static AcademiaDevPlatform platform = new AcademiaDevPlatform();
    private static final AuthService authService = new AuthService(platform);

    public static void main(String[] args) {
        InitialData.populate(platform);

        System.out.println("=== ACADEMIADEV ===");

        while (true) {
            try {
                if (!authService.isAuthenticated()) {
                    login();
                } else {
                    displayMenu(authService.getCurrentUser());
                }
            } catch (Exception e) {
                System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
            }
        }
    }

    private static void login() {
        System.out.print("\nDigite seu e-mail (ou 'sair'): ");
        String email = scanner.nextLine();

        if (email.equalsIgnoreCase("sair")) {
            System.out.println("Encerrando sistema...");
            System.exit(0);
        }

        authService.login(email);
    }

    private static void displayMenu(User user) {
        Map<Class<? extends User>, Consumer<User>> menus = Map.of(
                Admin.class, u -> menuAdmin((Admin) u),
                Student.class, u -> menuStudent((Student) u));

        menus.getOrDefault(user.getClass(), u -> authService.logout()).accept(user);
    }

    private static void menuAdmin(Admin admin) {
        Map<String, Runnable> acoes = new LinkedHashMap<>();
        acoes.put("1", platform::processNextTicket);
        acoes.put("2", Main::manageCourseStatus);
        acoes.put("3", Main::manageStudentPlan);
        acoes.put("4", platform::listActiveCourses);
        acoes.put("5", Main::openTicket);
        acoes.put("6", authService::logout);

        System.out.println("\n" + "=".repeat(60));
        System.out.println("             PAINEL ADMINISTRATIVO - ACADEMIADEV" + admin.getName().toUpperCase());
        System.out.println("=".repeat(60));

        System.out.println("[1] Atender Tickets de Suporte");
        System.out.println("[2] Gerenciar Status de Cursos");
        System.out.println("[3] Gerenciar Planos de Alunos");
        System.out.println("[4] Consultar Catálogo de Cursos");
        System.out.println("[5] Abrir Ticket de Suporte");
        System.out.println("[6] Sair");
        System.out.println("-".repeat(60));
        executarEscolha(acoes);
    }

    private static void menuStudent(Student student) {
        Map<String, Runnable> acoes = new LinkedHashMap<>();
        acoes.put("1", () -> matricularAluno(student));
        acoes.put("2", Main::checkEnrollments);
        acoes.put("3", Main::updateProgress);
        acoes.put("5", platform::listActiveCourses);
        acoes.put("6", Main::openTicket);
        acoes.put("7", authService::logout);

        System.out.println("\n" + "=".repeat(60));
        System.out.println("             PAINEL DO ALUNO - ACADEMIADEV " + student.getName().toUpperCase());
        System.out.println("=".repeat(60));

        System.out.println("[1] Matricular-se em Curso");
        System.out.println("[2] Consultar Matrículas");
        System.out.println("[3] Atualizar Progresso");
        System.out.println("[4] Cancelar Matrícula");
        System.out.println("[5] Consultar Catálogo de Cursos");
        System.out.println("[6] Abrir Ticket de Suporte");
        System.out.println("[7] Sair");
        System.out.println("-".repeat(60));
        executarEscolha(acoes);
    }

    private static void executarEscolha(Map<String, Runnable> acoes) {
        System.out.print("Escolha: ");
        String opcao = scanner.nextLine();
        Optional.ofNullable(acoes.get(opcao))
                .ifPresentOrElse(Runnable::run, () -> System.out.println("Opção inválida."));
    }

    private static void abrirTicket(Student student) {
        System.out.print("Título: ");
        String title = scanner.nextLine();
        System.out.print("Mensagem: ");
        String message = scanner.nextLine();
        platform.openSupportTicket(student.getEmail(), title, message);
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

    private static void manageCourseStatus() {
        System.out.println("\n>>> GERENCIAR STATUS DE CURSO <<<");
        System.out.print("Digite o título do curso: ");
        String title = scanner.nextLine();

        platform.findCourseByTitle(title).ifPresentOrElse(
                course -> {
                    System.out.println(
                            "Curso encontrado: " + course.getTitle() + " (Status atual: " + course.getStatus() + ")");
                    System.out.print("Alterar para ([1] ATIVO | [2] INATIVO): ");
                    String opt = scanner.nextLine();

                    CourseStatus newStatus = "1".equals(opt) ? CourseStatus.ACTIVE : CourseStatus.INACTIVE;

                    platform.changeStatus(title, newStatus);
                },
                () -> System.out.println("Erro: Curso '" + title + "' não foi encontrado em nossa base."));
    }

    private static void manageStudentPlan() {
        System.out.println("\n--- GERENCIAR PLANO DE ALUNO ---");
        System.out.print("Digite o e-mail do aluno: ");
        String email = scanner.nextLine();

        System.out.print("Alterar o Plano ([1] BASIC | [2] PREMIUM): ");
        String opt = scanner.nextLine();

        SubscriptionPlan plan = "2".equals(opt) ? SubscriptionPlan.PREMIUM : SubscriptionPlan.BASIC;

        platform.changePlan(email, plan);
    }

    private static void openTicket() {
        System.out.println("\n>>> ABERTURA DE TICKET DE SUPORTE <<<");

        String emailSignedIn = authService.getCurrentUser().getEmail();

        System.out.print("Assunto: ");
        String titulo = scanner.nextLine();

        System.out.print("Descrição do problema: ");
        String mensagem = scanner.nextLine();

        platform.openSupportTicket(emailSignedIn, titulo, mensagem);
    }

    private static void enrollStudent() {
        System.out.println("\n>>> MATRÍCULA EM CURSO <<<");

        platform.listActiveCourses();

        System.out.print("Digite o título do curso: ");
        String titleCourse = scanner.nextLine();

        try {
            String emailSignedIn = authService.getCurrentUser().getEmail();
            platform.enrollStudent(emailSignedIn, titleCourse);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }

    }

    private static void updateProgress() {
        System.out.println("\n>>> ATUALIZAR PROGRESSO DO CURSO <<<");

        String emailSignedIn = authService.getCurrentUser().getEmail();
        platform.checkEnrollments(emailSignedIn);

        System.out.print("\nDe qual curso deseja atualizar o progresso? ");
        String titleCourse = scanner.nextLine();

        System.out.print("Novo progresso (0-100): ");
        try {
            int progress = Integer.parseInt(scanner.nextLine());

            platform.updateProgress(emailSignedIn, titleCourse, progress);

        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite um número inteiro válido (0-100)");
        }
    }

    private static void checkEnrollments() {
        String emailSignedIn = authService.getCurrentUser().getEmail();
        platform.checkEnrollments(emailSignedIn);
    }

}
