package exercicio_AcademiaDev.data;

import java.util.List;

import exercicio_AcademiaDev.model.Admin;
import exercicio_AcademiaDev.model.Course;
import exercicio_AcademiaDev.model.DifficultyLevel;
import exercicio_AcademiaDev.model.Enrollment;
import exercicio_AcademiaDev.model.Student;
import exercicio_AcademiaDev.model.SubscriptionPlan;
import exercicio_AcademiaDev.service.AcademiaDevPlatform;

public class InitialData {

        public static void populate(AcademiaDevPlatform platform) {

                // Registro de Cursos
                List<Course> initialCourses = List.of(
                                new Course("Java Moderno", "Aprenda Lambdas e Streams", "Hélio Matos", 40,
                                                DifficultyLevel.BEGINNER),
                                new Course("Spring Boot Expert", "Microserviços do zero", "Bruna Silveira", 60,
                                                DifficultyLevel.ADVANCED),
                                new Course("SQL para Devs", "Bancos de dados relacionais", "Carlos Olimpico", 20,
                                                DifficultyLevel.INTERMEDIATE),
                                new Course("React Essentials", "Ecossistema React e Hooks", "Aline Ferreira", 45,
                                                DifficultyLevel.BEGINNER),
                                new Course("Design Patterns", "Padrões de projeto e Clean Code", "Sérgio Antunes", 35,
                                                DifficultyLevel.ADVANCED));

                initialCourses.forEach(platform::addCourse);

                // Registro de Estudantes
                List<Student> initialStudent = List.of(
                                new Student("Allan Silva", "allan@email.com", SubscriptionPlan.BASIC),
                                new Student("Maria Lima", "maria@email.com", SubscriptionPlan.PREMIUM),
                                new Student("Carolina Lima", "carolina@email.com", SubscriptionPlan.PREMIUM));

                initialStudent.forEach(platform::registerUser);

                // Registro de Administradores
                List<Admin> initialAdmins = List.of(
                                new Admin("Sérgio Antunes", "sergio@academiadev.com"),
                                new Admin("Ana Luxemburgo", "ana@academiadev.com"));
                initialAdmins.forEach(platform::registerUser);

                // Registro de Suporte (Tickets)
                List<String[]> initialTickets = List.of(
                                new String[] { "maria@email.com", "Financeiro",
                                                "Meu plano Premium não ativou as aulas avançadas." },
                                new String[] { "allan@email.com", "Sugestão",
                                                "Poderiam adicionar um curso de Python?" },
                                new String[] { "sergio@academiadev.com", "Problemas de acesso",
                                                "Não estou conseguindo gerenciar os cursos." });

                initialTickets.forEach(ticket -> platform.openSupportTicket(ticket[0], ticket[1], ticket[2]));

                // Registro de matrículas iniciais (Allan - 2 cursos)
                platform.findUserByEmail("allan@email.com")
                                .filter(Student.class::isInstance)
                                .map(Student.class::cast)
                                .ifPresent(allan -> {
                                        List.of("Java Moderno", "SQL para Devs").forEach(titulo -> platform
                                                        .findCourseByTitle(titulo)
                                                        .ifPresent(c -> allan.addEnrollment(new Enrollment(allan, c))));
                                });

                // Registro de matrículas iniciais (Maria - 4 cursos)
                platform.findUserByEmail("maria@email.com")
                                .filter(Student.class::isInstance)
                                .map(Student.class::cast)
                                .ifPresent(maria -> {
                                        List<String> cursosMaria = List.of(
                                                        "Java Moderno",
                                                        "Spring Boot Expert",
                                                        "SQL para Devs",
                                                        "React Essentials");

                                        cursosMaria.forEach(titulo -> platform.findCourseByTitle(titulo).ifPresent(
                                                        curso -> maria.addEnrollment(new Enrollment(maria, curso))));
                                });
        }

}
