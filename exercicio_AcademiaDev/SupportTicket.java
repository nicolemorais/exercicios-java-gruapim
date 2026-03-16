package exercicio_AcademiaDev;

import java.time.LocalDateTime;

public class SupportTicket {

    @CsvColumn(label = "Solicitante")
    private final User creator;

    @CsvColumn(label = "Assunto")
    private final String title;

    @CsvColumn(label = "Mensagem")
    private final String message;

    @CsvColumn(label = "Data de Abertura")
    private final LocalDateTime createdAt;

    public SupportTicket(User creator, String title, String message) {
       this.creator = creator;
       this.title = title;
       this.message = message;
       this.createdAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format("[%s] Ticket: %s | De: %s", 
                createdAt.toLocalTime(), title, creator.getName());
    }
}
