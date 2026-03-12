package exercicio_AcademiaDev;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import exercicio_AcademiaDev.exceptions.EnrollmentException;

public class AcademiaDevPlatform {

    private final Map<String, User> users = new HashMap<>();
    private final Map<String, Course> courses = new HashMap<>();
    private final Queue<SupportTicket> supportQueue = new LinkedList<>();

    public List<Course> getCourses() {
        return new ArrayList<>(courses.values());
    }

    // --- CADASTROS ---

    public void registerUser(User user) {
        Optional.ofNullable(user)
                .ifPresent(u -> users.put(u.getEmail().toLowerCase(), u));
    }

    public void addCourse(Course course) {
        Optional.ofNullable(course)
                .ifPresent(c -> courses.put(c.getTitle().toLowerCase(), c));
    }

    // --- Operações de adminitrador ---

    public void changeStatus(String titulo) {
        findCourseByTitle(titulo).ifPresentOrElse(
                course -> {
                    course.setActive(!course.isActive());
                    System.out.println("Curso atualizado para: " + course.getStatus());
                },
                () -> System.out.println("Erro: Curso não encontrado."));
    }

    public void changePlan(String email, SubscriptionPlan novoPlano) {
        findUserByEmail(email)
                .filter(Student.class::isInstance)
                .map(Student.class::cast)
                .ifPresentOrElse(
                        student -> {
                            student.setSubscriptionPlan(novoPlano);
                            System.out.println("Plano de " + student.getName() + " alterado para " + novoPlano);
                        },
                        () -> System.out.println("Erro: Aluno não encontrado com este e-mail."));
    }

    public void updateProgress(Student student, String courseTitle, int newProgress) {
        student.getEnrollments().stream()
                .filter(e -> e.getCourse().getTitle().equalsIgnoreCase(courseTitle))
                .findFirst()
                .ifPresentOrElse(
                        enrollment -> {
                            try {
                                enrollment.updateProgress(newProgress);
                                System.out.println("Progresso atualizado para " + newProgress + "%");
                            } catch (IllegalArgumentException ex) {
                                System.out.println("Erro: " + ex.getMessage());
                            }
                        },
                        () -> System.out.println("Erro: Você não está matriculado neste curso."));
    }

    // Geral

    public List<Course> getCatalogoAtivo() {
        return courses.values().stream()
                .filter(Course::isActive)
                .toList();
    }

    // Aluno

        public void enrollStudent(String email, String courseTitle) {
        Student student = findUserByEmail(email)
            .filter(u -> u instanceof Student)
            .map(u -> (Student) u)
            .orElseThrow(() -> new EnrollmentException("Aluno não encontrado com o e-mail: " + email));

        findCourseByTitle(courseTitle)
        .map(course -> {
            if (course.getStatus() != CourseStatus.ACTIVE){
                throw new EnrollmentException("Matrícula negada: O curso '\" + course.getTitle() + \"' está inativo.");
            }
            if (!student.canEnroll()){
                throw new EnrollmentException("Matrícula negada: Limite de vagas do plano " + student.getSubscriptionPlan() + " atingido.");
            }
            return new Enrollment(student, course);
        })
        .ifPresentOrElse(
            student::addEnrollment, () -> {
                throw new EnrollmentException("Erro: Curso '\" + courseTitle + \"' não encontrado.\");");
            }
        );
        
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
        return Optional.ofNullable(email)
                .map(String::toLowerCase)
                .map(users::get);
    }

    public Optional<Course> findCourseByTitle(String title) {
        return Optional.ofNullable(title)
                .map(String::toLowerCase)
                .map(courses::get);
    }

    // --- SUPORTE --

    public void openTicket(User user, String title, String message) {
        supportQueue.add(new SupportTicket(user, title, message));
    }

    public void processNextTicket() {
        Optional.ofNullable(supportQueue.poll())
                .ifPresentOrElse(
                        ticket -> {
                            System.out.println("=== ATENDIMENTO EM ANDAMENTO ===");
                            System.out.println(ticket);
                            System.out.println("Status: CONCLUÍDO");
                        },

                        () -> System.out.println("Nenhum ticket pendente."));
    }

    // --- RELATÓRIOS E ANÁLISES ---

    public List<Student> getAllStudents() {
        return users.values().stream()
                .filter(Student.class::isInstance)
                .map(Student.class::cast)
                .toList();
    }

    public Set<String> getUniqueActiveInstructors() {
        return courses.values().stream()
                .filter(Course::isActive)
                .map(Course::getInstructorName)
                .collect(Collectors.toSet());
    }

    public Map<SubscriptionPlan, List<Student>> groupStudentsByPlan() {
        return getAllStudents().stream()
                .collect(Collectors.groupingBy(Student::getSubscriptionPlan));
    }

    public double calculateGlobalAverageProgress() {
        return getAllStudents().stream()
                .flatMap(s -> s.getEnrollments().stream())
                .mapToInt(Enrollment::getProgress)
                .average()
                .orElse(0.0);
    }

    public Optional<Student> getTopEnrolledStudent() {
        return getAllStudents().stream()
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

    public void imprimirRelatorios() {
        System.out.println("\n======= RELATÓRIOS DA PLATAFORMA =======");
        System.out.printf("Média de Progresso Global: %.2f%%\n", calculateGlobalAverageProgress());
        System.out.println("Instrutores Ativos: " + getUniqueActiveInstructors());

        System.out.println("\n--- Alunos por Plano ---");
        groupStudentsByPlan().forEach((plano, lista) -> System.out.println(plano + ": " + lista.size() + " alunos"));

        getTopEnrolledStudent().ifPresentOrElse(
                s -> System.out.println("\nAluno com mais matrículas: " + s.getName()),
                () -> System.out.println("\nNenhum aluno matriculado ainda."));
        System.out.println("========================================\n");
    }
}