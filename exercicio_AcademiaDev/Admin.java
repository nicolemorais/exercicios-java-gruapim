package exercicio_AcademiaDev;

import java.util.Optional;
import java.util.function.Consumer;

public class Admin extends User {

    public Admin(String name, String email) {
        super(name, email);
    }

    public void manageCourses(Consumer<Course> action, Course course) {
        Optional.ofNullable(action)
                .ifPresent(a -> a.accept(course));
        System.out.println("Admin " + getName() + "executou alteração no curso: " + course.getTitle());
    }

    public void manageUsers(Consumer<User> action, User user) {
        Optional.ofNullable(action)
                .ifPresent(a -> a.accept(user));
        System.out.println("Admin " + getName() + "gerenciou o perfil de: " + user.getName());
    }

    @Override
    public String toString(){
        return super.toString() + "[Acesso: ADMINISTRADOR]";
    }
}
