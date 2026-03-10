package exercicio_AcademiaDev;

import java.util.Objects;

public abstract class User {

    @CsvColumn(label = "Nome Completo")
    private String name;

    @CsvColumn(label = "E-mail Principal")
    private final String email;
 

    public User(String name, String email) {
        this.name = Objects.requireNonNull(name, "Nome é obrigatório.");
        this.email = Objects.requireNonNull(email, "Email é obrigatório.");
    }

    public String getName() {
        return name;
    }

    
    public String getEmail() {
        return email;
    }


}
