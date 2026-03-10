package exercicio_AcademiaDev;
import java.time.LocalDateTime;

public class SupportTicket {

    private static int contadorId = 1;

    private int id;
    private String title;
    private String message;
    private LocalDateTime createdAt;

    public SupportTicket(String title, String message) {
        this.title = title;
        this.message = message;

        this.id = contadorId++;

        this.createdAt = LocalDateTime.now();
    }

    public int getId() {
        return this.id;
    }
    public String getTitle() {
        return this.title;
    }
    public String getMessage() {
        return this.message;
    }
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

}
