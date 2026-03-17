package exercicio_AcademiaDev;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

import exercicio_AcademiaDev.data.InitialData;
import exercicio_AcademiaDev.model.Admin;
import exercicio_AcademiaDev.model.Student;
import exercicio_AcademiaDev.model.User;
import exercicio_AcademiaDev.service.AcademiaDevPlatform;
import exercicio_AcademiaDev.service.AuthService;
import exercicio_AcademiaDev.ui.ConsoleUI;

public class Main {
    private static final AcademiaDevPlatform platform = new AcademiaDevPlatform();
    private static final AuthService authService = new AuthService(platform);
    private static final ConsoleUI ui = new ConsoleUI(platform, authService);

    public static void main(String[] args) {
        ui.header("DADOS INICIAIS");
        InitialData.populate(platform);

        ui.header("BEM-VINDO AO ACADEMIADEV");

        while (true) {
            try {
                if (!authService.isAuthenticated()) {
                    ui.login();
                } else {
                    displayMenu(authService.getCurrentUser());
                }
            } catch (Exception e) {
                System.out.println("Ocorreu um problema: " + e.getMessage());
            }
        }
    }

    private static void displayMenu(User user) {
        Map<Class<? extends User>, Consumer<User>> menus = Map.of(
                Admin.class, u -> menuAdmin((Admin) u),
                Student.class, u -> menuStudent((Student) u));

        menus.getOrDefault(user.getClass(), u -> authService.logout()).accept(user);
    }

    private static void menuAdmin(Admin admin) {
        Map<String, Runnable> actions = new LinkedHashMap<>();
        actions.put("1", platform::processNextTicket);
        actions.put("2", ui::manageCourseStatus);
        actions.put("3", ui::manageStudentPlan);
        actions.put("4", ui::viewCatalog);
        actions.put("5", ui::openTicket);
        actions.put("6", authService::logout);

        ui.showMenuAdmin(admin, actions);

    }

    private static void menuStudent(Student student) {
        Map<String, Runnable> actions = new LinkedHashMap<>();
        actions.put("1", ui::enrollStudent);
        actions.put("2", ui::checkEnrollments);
        actions.put("3", ui::updateProgress);
        actions.put("4", ui::unsubscribe);
        actions.put("5", ui::viewCatalog);
        actions.put("6", ui::openTicket);
        actions.put("7", authService::logout);

        ui.showMenuStudent(student, actions);
    }

}
