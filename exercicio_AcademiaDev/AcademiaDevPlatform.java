package exercicio_AcademiaDev;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import exercicio_AcademiaDev.exceptions.EnrollmentException;

public class AcademiaDevPlatform {

    private List<User> users = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();
    private Queue<SupportTicket> supportQueue = new LinkedList<>();

    // --- CADASTROS ---

    public void registerUser(User user) {
        Optional.ofNullable(user)
                .ifPresent(u -> users.add(u));
    }

    public void addCourse(Course course) {
        Optional.ofNullable(course)
                .ifPresent(c -> courses.add(c));
    }

    // --- OPERAÇÕES DO ADMINISTRADOR ---

    public void changeStatus(String titleCourse, CourseStatus newStatus) {
        findCourseByTitle(titleCourse).ifPresentOrElse(
                course -> {
                    course.setStatus(newStatus);
                    System.out.println("Curso: '" + course.getTitle() + "' foi atualizado para: " + newStatus);
                },
                () -> System.out.println("Erro: Curso não encontrado."));
    }

    public void changePlan(String email, SubscriptionPlan newPlan) {
        if (newPlan == null) {
            System.out.println("Erro: Plano de assinatura inválido");
            return;
        }

        findUserByEmail(email)
                .filter(Student.class::isInstance)
                .map(Student.class::cast)
                .ifPresentOrElse(
                        student -> {
                            if (student.getSubscriptionPlan() == newPlan) {
                                System.out.println("O aluno " + student.getName() + " já possui o plano " + newPlan);
                            } else {
                                student.setSubscriptionPlan(newPlan);
                                System.out.println(
                                        "Sucesso: Plano de " + student.getName() + " alterado para " + newPlan);
                            }
                        },
                        () -> System.out.println("Erro: Aluno não encontrado com este e-mail."));
    }

    // ---- ATENDER AO SUPORTE (ADMIN) ----
    public void processNextTicket() {
        Optional.ofNullable(supportQueue.poll())
                .ifPresentOrElse(
                        ticket -> {
                            System.out.println(">>> ATENDENDO TICKET <<<");
                            System.out.println(ticket);
                        }, () -> System.out.println("Nenhum ticket pendente na fila."));
    }

    // --- OPERAÇÕES DO ALUNO ---

    public void enrollStudent(String email, String courseTitle) {
        Student student = findUserByEmail(email)
                .filter(Student.class::isInstance)
                .map(Student.class::cast)
                .orElseThrow(() -> new EnrollmentException("Aluno não encontrado com o e-mail: " + email));

        findCourseByTitle(courseTitle)
                .map(course -> {
                    if (course.getStatus() != CourseStatus.ACTIVE) {
                        throw new EnrollmentException(
                                "Matrícula negada: O curso '" + course.getTitle() + "' está inativo.");
                    }
                    if (!student.canEnroll()) {
                        throw new EnrollmentException("Matrícula negada: Limite de vagas do plano "
                                + student.getSubscriptionPlan() + " atingido.");
                    }
                    return new Enrollment(student, course);
                })
                .ifPresentOrElse(
                        student::addEnrollment, () -> {
                            throw new EnrollmentException("Erro: Curso '" + courseTitle + "' não encontrado.");
                        });

    }

    public void checkEnrollments(String email) {
        findUserByEmail(email)
                .filter(Student.class::isInstance)
                .map(Student.class::cast)
                .ifPresentOrElse(student -> {
                    System.out.println("\n=== MINHAS MATRÍCULAS (" + student.getName() + ") ===");

                    List<Enrollment> enrollments = student.getEnrollments();

                    if (enrollments.isEmpty()) {
                        System.out.println("Você ainda não possui matrículas em cursos.");
                    } else {
                        enrollments.stream()
                                .forEach(e -> System.out.printf("- %s | Progresso: [%d%%] | Status: %s%n",
                                        e.getCourse().getTitle(),
                                        e.getProgress(),
                                        e.getStatus()));
                    }
                }, () -> System.out.println("Erro: Aluno não encontrado."));

    }

    public void updateProgress(String email, String courseTitle, int newProgress) {
        findUserByEmail(email)
        .filter(Student.class::isInstance)
        .map(Student.class::cast)
        .ifPresentOrElse(student -> {
            student.getEnrollments().stream()
                .filter(e -> e.getCourse().getTitle().equalsIgnoreCase(courseTitle))
                .findFirst()
                .ifPresentOrElse(
                    enrollment -> enrollment.updateProgress(newProgress),
                    () -> System.out.println("Erro: Aluno não matriculado neste curso.")
                );
        }, () -> System.out.println("Erro: Aluno não encontrado."));
    }

    public void unsubscribe(Student student, String courseTitle) {
        Optional.of(student.getEnrollments().removeIf(e -> e.getCourse().getTitle().equalsIgnoreCase(courseTitle)))
                .filter(removed -> removed) // Só segue se for true
                .ifPresentOrElse(
                        r -> System.out.println(
                                "Matrícula encerrada. Vaga liberada no plano " + student.getSubscriptionPlan()),
                        () -> System.out.println("Erro: Você não possui matrícula ativa no curso: " + courseTitle));
    }

    // --- BUSCAS ---

    public Optional<User> findUserByEmail(String email) {
        return users.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public Optional<Course> findCourseByTitle(String title) {
        return courses.stream()
                .filter(c -> c.getTitle().equalsIgnoreCase(title))
                .findFirst();

    }

    public double getAverageProgress() {
        return users.stream()
                .filter(u -> u instanceof Student)
                .map(u -> (Student) u)
                .flatMap(s -> s.getEnrollments().stream())
                .mapToInt(Enrollment::getProgress)
                .average()
                .orElse(0.0);
    }

    // --- OPERAÇÕES GERAIS (QUALQUER USUÁRIO) ---

    public void listActiveCourses() {
        System.out.println("\n--- CURSOS DISPONÍVEIS ---");
        long count = courses.stream()
                .filter(c -> c.getStatus() == CourseStatus.ACTIVE)
                .peek(c -> System.out.printf("- %-20s | Carga Horária: %dh%n",
                        c.getTitle(), c.getDurationInHours()))
                .count();

        if (count == 0) {
            System.out.println("No momento, não há cursos ativos.");
        }
    }

    public void openSupportTicket(String userEmail, String title, String message) {
        findUserByEmail(userEmail)
                .map(user -> new SupportTicket(user, title, message))
                .ifPresentOrElse(
                        ticket -> {
                            supportQueue.add(ticket);
                            System.out.println("Ticket registrado com sucesso para: " + userEmail);
                        },
                        () -> System.out.println("Erro: Usuário não cadastrado para abertura de ticket."));
    }

    // --- RELATÓRIOS E ANÁLISES ---

    public List<Student> getAllStudents() {
        return users.stream()
                .filter(Student.class::isInstance)
                .map(Student.class::cast)
                .toList();
    }

    public Set<String> getUniqueActiveInstructors() {
        return courses.stream()
                .filter(Course::isActive)
                .map(Course::getInstructorName)
                .collect(Collectors.toSet());
    }

    public Map<SubscriptionPlan, List<Student>> groupStudentsByPlan() {
        return getAllStudents().stream()
                .collect(Collectors.groupingBy(Student::getSubscriptionPlan));
    }

    public Optional<Student> findTopStudent() {
        return users.stream()
                .filter(u -> u instanceof Student)
                .map(u -> (Student) u)
                .max(Comparator.comparingInt(s -> s.getEnrollments().size()));
    }

    // --- EXPORTAÇÃO ---
    public String exportToCSV(List<?> data, List<String> columns) {
        return Optional.ofNullable(data)
                .filter(list -> !list.isEmpty())
                .map(list -> {
                    String header = String.join(",", columns);
                    String rows = list.stream()
                            .map(obj -> columns.stream()
                                    .map(col -> getFieldValue(obj, col))
                                    .collect(Collectors.joining(",")))
                            .collect(Collectors.joining("\n"));
                    return header + "\n" + rows;
                }).orElse("Lista vazia.");
    }

    private String getFieldValue(Object obj, String fieldName) {
        try {
            Field field = getFieldRecursively(obj.getClass(), fieldName);
            field.setAccessible(true);
            return String.valueOf(field.get(obj)).replace(",", ";");
        } catch (Exception e) {
            return "N/A";
        }
    }

    private Field getFieldRecursively(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        while (clazz != null) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchFieldException(fieldName);
    }
}