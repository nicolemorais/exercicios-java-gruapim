package exercicio_AcademiaDev;

import java.util.*;

public class AcademiaDevPlatform {

    // 1 Dicionário de Usuários
    private Map<String, User> users = new HashMap<>();

    // 2 Dicionário de Cursos
    private Map<String, Course> courses = new HashMap<>();

    // 3 Tickets
    private Queue<SupportTicket> supportQueue = new LinkedList<>();

    // cria ticket
    public void openTicket(User user, String title, String message) {

        SupportTicket novoTicket = new SupportTicket(title, message);
        supportQueue.add(novoTicket);

        System.out.println("Ticket criado com sucesso para o utilizador: " + user.getName());
    }
    //pega o proximo ticket
    public SupportTicket processNextTicket() {
        return supportQueue.poll();
    }

    //add curso
    public void addCourse(Course novoCurso) {
        String titulo = novoCurso.getTitle();

         if (courses.containsKey(titulo)) {
            System.out.println("Erro: Já existe um curso cadastrado com o título '" + titulo + "'.");
            return;
        }

        courses.put(titulo, novoCurso);
        System.out.println("Sucesso: Curso '" + titulo + "' adicionado ao catálogo!");
    }
    // muda status do curso
    public void changeCourseStatus(String title, CourseStatus newStatus) {
        Course course = courses.get(title);
        if (course != null) {
            course.setStatus(newStatus);
            System.out.println("Status do curso '" + title + "' alterado para " + newStatus);
        } else {
            System.out.println("Erro: Curso '" + title + "' não encontrado.");
        }
    }

    // Consulta o catalogo apenas ativos
    public void listActiveCourses() {
        System.out.println("\n- Catálogo de Cursos Ativos -");


        courses.values().stream()
                .filter(Course::isActive)
                .forEach(course -> System.out.println(course.toString()));
    }

 }