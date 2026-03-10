package exercicio_AcademiaDev;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class SupportTicket {

    private static int contadorId = 1;

    @CsvColumn(label = "ID do Ticket")
    private final int id;

    @CsvColumn(label = "Solicitante")
    private String userName;

    private User user;

    @CsvColumn(label = "Assunto")
    private final String title;

    @CsvColumn(label = "Mensagem")
    private final String message;

    @CsvColumn(label = "Data de Abertura")
    private final LocalDateTime createdAt;

    public SupportTicket(User user, String title, String message) {
        this.id = contadorId++;
        this.user = Objects.requireNonNull(user, "Usuário solicitante é obrigatório.");
        this.userName = user.getName();
        this.title = Objects.requireNonNull(title, "Título não pode ser vazio.");
        this.message = Objects.requireNonNull(message, "Mensagem não pode ser vazia.");
        this.createdAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getFormattedDate() {
        return createdAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    @Override
    public String toString() {
        return String.format("Ticket #%d [%s] - De: %s | Assunto: %s",
                id, getFormattedDate(), userName, title);
    }
}
