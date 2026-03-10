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

 }