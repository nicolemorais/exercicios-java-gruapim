package exercicio_AcademiaDev;

import java.util.Optional;

public class AuthService {
    private final AcademiaDevPlatform platform;
    private User currentUser;

    public AuthService(AcademiaDevPlatform platform) {
        this.platform = platform;
    }

    public boolean login(String email) {
        return platform.findUserByEmail(email)
                .map(user -> {
                    this.currentUser = user;
                    return true;
                })
                .orElseGet(() -> {
                    this.currentUser = null;
                    System.out.println("Erro: Credenciais inválidas ou usuário não encontrado.");
                    return false;
                });
    }

    public void logout() {
        Optional.ofNullable(this.currentUser)
                .ifPresent(u -> {
                    this.currentUser = null;
                    System.out.println("Sessão encerrada.");
                });
    }

    public boolean isAuthenticated() {
        return currentUser != null;
    }

    public boolean isAdmin() {
        return isAuthenticated() && !(currentUser instanceof Student);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Student getCurrentStudent() {
        return (Student) currentUser;
    }

}
