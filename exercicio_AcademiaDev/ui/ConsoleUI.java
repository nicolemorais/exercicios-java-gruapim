package exercicio_AcademiaDev.ui;

import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import exercicio_AcademiaDev.exceptions.EnrollmentException;
import exercicio_AcademiaDev.model.Admin;
import exercicio_AcademiaDev.model.CourseStatus;
import exercicio_AcademiaDev.model.Student;
import exercicio_AcademiaDev.model.SubscriptionPlan;
import exercicio_AcademiaDev.service.AcademiaDevPlatform;
import exercicio_AcademiaDev.service.AuthService;

public class ConsoleUI {
    private final Scanner scanner = new Scanner(System.in);
    private final AcademiaDevPlatform platform;
    private final AuthService authService; // 1. Adicione este campo

    public ConsoleUI(AcademiaDevPlatform platform, AuthService authService) {
        this.platform = platform;
        this.authService = authService;
    }

    // --- FORMATAÇÃO VISUAL ---
    public void header(String... lines) {
        int width = 60;
        System.out.println("=".repeat(width));

        for (String line : lines) {

            int leftovers = width - line.length();
            int leftMargin = leftovers / 2;

            System.out.println(" ".repeat(Math.max(0, leftMargin)) + line);
        }
        System.out.println("=".repeat(width));
    }

    public void notification(String message) {
        System.out.println("\n" + "-".repeat(60));
        System.out.println("NOTIFICAÇÃO: " + message);
        System.out.println("-".repeat(60));
    }

    // --- MÉTODOS DE INTERAÇÃO ---

    // ---- AÇÕES DE TODOS OS USUÁRIOS ---
    public void login() {
        System.out.print("\nDigite seu e-mail (ou 'sair'): ");
        String email = scanner.nextLine();

        if (email.equalsIgnoreCase("sair")) {
            System.out.println("Encerrando sistema...");
            System.exit(0);
        }

        authService.login(email);
    }

    private void runSelection(Map<String, Runnable> actions) {
        String opt = scanner.nextLine();
        Optional.ofNullable(actions.get(opt))
                .ifPresentOrElse(Runnable::run, () -> System.out.println("Opção inválida."));
    }

    public void openTicket() {
        System.out.println("\n>>> ABERTURA DE TICKET DE SUPORTE <<<");

        String emailSignedIn = authService.getCurrentUser().getEmail();

        System.out.print("Assunto: ");
        String titulo = scanner.nextLine();

        System.out.print("Descrição do problema: ");
        String mensagem = scanner.nextLine();

        platform.openSupportTicket(emailSignedIn, titulo, mensagem);
    }

    public void viewCatalog() {
        header("CATÁLOGO DE CURSOS - ACADEMIADEV");

        platform.listActiveCourses();

        System.out.println("\nPressione ENTER para voltar ao menu...");
        scanner.nextLine();
    }

    // ---- AÇÕES DO ADMINISTRADOR ----

    public void showMenuAdmin(Admin admin, Map<String, Runnable> actions) {
        header("PAINEL ADMINISTRATIVO - ACADEMIADEV", "BEM VINDO - " + admin.getName().toUpperCase());

        System.out.println("[1] Atender Tickets de Suporte");
        System.out.println("[2] Gerenciar Status de Cursos");
        System.out.println("[3] Gerenciar Planos de Alunos");
        System.out.println("[4] Consultar Catálogo de Cursos");
        System.out.println("[5] Abrir Ticket de Suporte");
        System.out.println("[6] Sair");
        System.out.println("-".repeat(60));
        runSelection(actions);
    }

    public void manageCourseStatus() {
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

    public void manageStudentPlan() {
        System.out.println("\n>>> GERENCIAR PLANO DE ALUNO <<<");
        System.out.print("Digite o e-mail do aluno: ");
        String email = scanner.nextLine();

        System.out.print("Alterar o Plano ([1] BASIC | [2] PREMIUM): ");
        String opt = scanner.nextLine();

        SubscriptionPlan plan = "2".equals(opt) ? SubscriptionPlan.PREMIUM : SubscriptionPlan.BASIC;

        platform.changePlan(email, plan);
    }

    // ---- AÇÕES DO ALUNO ----
    public void showMenuStudent(Student student, Map<String, Runnable> actions) {
        header("PORTAL DO ALUNO - ACADEMIADEV",
                "OLÁ, " + student.getName().toUpperCase(),
                "PLANO: " + student.getSubscriptionPlan());

        System.out.println("[1] Matricular-se em Curso");
        System.out.println("[2] Consultar Matrículas");
        System.out.println("[3] Atualizar Progresso");
        System.out.println("[4] Cancelar Matrícula");
        System.out.println("[5] Consultar Catálogo de Cursos");
        System.out.println("[6] Abrir Ticket de Suporte");
        System.out.println("[7] Sair");
        System.out.println("-".repeat(60));
        runSelection(actions);
    }

    public void enrollStudent() {
        System.out.println("\n>>> MATRÍCULAR-SE EM UM CURSO <<<");

        platform.listActiveCourses();

        System.out.print("Título do curso para matrícula: ");
        String titleCourse = scanner.nextLine();

        try {
        Student currentStudent = (Student) authService.getCurrentUser();
        
        platform.enrollStudent(currentStudent, titleCourse);

        notification("Matrícula realizada com sucesso em: " + titleCourse.toUpperCase());
        
    } catch (EnrollmentException e) {
        notification("Erro: " + e.getMessage());
    }

    }

    public void updateProgress() {
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

    public void unsubscribe() {
        System.out.println("\n>>> CANCELAR MATRÍCULA <<<");

        String email = authService.getCurrentUser().getEmail();
        System.out.print("Qual curso deseja cancelar? ");
        String titleCourse = scanner.nextLine();

        platform.unsubscribe(email, titleCourse);
    }

    public void checkEnrollments() {
        System.out.println("\n>>> CONSULTAR MATRÍCULAS <<<");

        String emailSignedIn = authService.getCurrentUser().getEmail();
        platform.checkEnrollments(emailSignedIn);
    }

}
