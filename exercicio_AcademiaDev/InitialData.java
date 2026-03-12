package exercicio_AcademiaDev;

import java.util.List;

public class InitialData {

    public static void populate(AcademiaDevPlatform platform) {
        List.of(
                new Admin("Sérgio Antunes", "sergio@academiadev.com"),
                new Admin("Ana Luxemburgo", "ana@academiadev.com")).forEach(platform::registerUser);

        List<Course> initialCourses = List.of(
                new Course("Java Moderno", "Aprenda Lambdas e Streams", "Hélio Matos", 40, DifficultyLevel.BEGINNER),
                new Course("Spring Boot Expert", "Microserviços do zero", "Bruna Silveira", 60,
                        DifficultyLevel.ADVANCED),
                new Course("SQL para Devs", "Bancos de dados relacionais", "Carlos Olimpico", 20,
                        DifficultyLevel.INTERMEDIATE));

        initialCourses.forEach(platform::addCourse);

        Student allan = new Student("Allan Silva", "allan@email.com", SubscriptionPlan.BASIC);
        Student carolina = new Student("Carolina Lima", "carolina@email.com", SubscriptionPlan.PREMIUM);

        List.of(allan, carolina).forEach(student -> {
            platform.registerUser(student);
        });

        platform.openTicket(allan, "Dúvida Técnica", "Não entendi o conceito de Optional na aula 02.");
        platform.openTicket(carolina, "Financeiro", "Meu plano Premium não ativou as aulas avançadas.");
        platform.openTicket(allan, "Sugestão", "Poderiam adicionar um curso de Python?");

        System.out.println("\n>>> Sistema populado com sucesso! <<<\n");
    }
}
