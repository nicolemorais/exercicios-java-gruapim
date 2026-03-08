package exercicio_AcademiaDev;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

public class Enrollment {
    private final Student student;
    private final Course course;
    private final LocalDate enrollmentDate;
    private int progress;
    private EnrollmentStatus status;
    private LocalDate completionDate;

    public Enrollment(Student student, Course course) {
        this.student = Objects.requireNonNull(student, "Estudante é obrigatório");
        this.course = Objects.requireNonNull(course, "Curso é obrigatório");
        this.enrollmentDate = LocalDate.now();
        this.status = EnrollmentStatus.IN_PROGRESS;
        this.progress = 0;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public int getProgress() {
        return progress;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public void updateProgress(int newProgress) {
        if (newProgress < 0 || newProgress > 100)
            throw new IllegalArgumentException("Progresso deve estar entre 0 e 100");

        this.progress = newProgress;

        Optional.of(this.progress)
                .filter(p -> p == 100)
                .ifPresent(p -> {
                    this.status = EnrollmentStatus.COMPLETED;
                    this.completionDate = LocalDate.now();
                });
    }

    public void cancel() {

        Optional.of(this.status)
                .filter(s -> s == EnrollmentStatus.IN_PROGRESS)
                .ifPresentOrElse(
                        s -> this.status = EnrollmentStatus.CANCELLED,
                        () -> {
                            throw new IllegalStateException("Status inválido para cancelamento: " + status);
                        });
    }

    public boolean isInProgress() {
        return this.status == EnrollmentStatus.IN_PROGRESS;
    }

    public boolean isCompleted() {
        return this.status == EnrollmentStatus.COMPLETED;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s em %s - Progresso: %d%%",
                status, student.getName(), course.getTitle(), progress);
    }
}
