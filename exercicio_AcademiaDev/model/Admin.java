package exercicio_AcademiaDev.model;

public class Admin extends User {

    public Admin(String name, String email) {
        super(name, email);
    }

    @Override
    public String toString(){
        return super.toString() + "[Acesso: ADMINISTRADOR]";
    }
}
