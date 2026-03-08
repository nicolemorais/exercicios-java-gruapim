package exercicio_AcademiaDev;

import java.util.Objects;

public class Course {
    private final String title;
    private String description;
    private String instructorName;
    private int durationInHours;
    private DifficultyLevel level;
    private CourseStatus status;

    public Course(String title, String description, String instructorName, int durationInHours, DifficultyLevel level) {
        this.title = Objects.requireNonNull(title, "Título é obrigatório");
        this.description = Objects.requireNonNull(description, "Descrição é obrigatória");
        this.instructorName = Objects.requireNonNull(instructorName, "Nome do instrutor é obrigatório");
        this.level = Objects.requireNonNull(level, "Nível de dificuldade é obrigatório");
        this.durationInHours = durationInHours;
        this.status = CourseStatus.ACTIVE;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public int getDurationInHours() {
        return durationInHours;
    }

    public DifficultyLevel getLevel() {
        return level;
    }

    public CourseStatus getStatus() {
        return status;
    }

    public boolean isActive() {
        return this.status == CourseStatus.ACTIVE;
    }

    public void setStatus(CourseStatus status) {
        this.status = Objects.requireNonNull(status);
    }

    @Override
    public String toString() {
        return String.format("Curso: %s [%s] | Instrutor: %s | Duração: %dh",
                title, level, instructorName, durationInHours);
    }

}
