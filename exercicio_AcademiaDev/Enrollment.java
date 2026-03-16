package exercicio_AcademiaDev;

import java.time.LocalDate;

import exercicio_AcademiaDev.exceptions.EnrollmentException;

public class Enrollment {
    private final Student student;
    private final Course course;
    private final LocalDate enrollmentDate;
    private int progress;
    private EnrollmentStatus status;
    private LocalDate completionDate;

    public Enrollment(Student student, Course course) {
        if (course.getStatus() != CourseStatus.ACTIVE) {
            throw new EnrollmentException("Não é possível matricular: o curso '" + course.getTitle() + "' não está ativo.");
        }

        this.student = student;
        this.course = course;
        this.enrollmentDate = LocalDate.now();
        this.progress = 0;
        this.status = EnrollmentStatus.ACTIVE;
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
        if (newProgress < 0 || newProgress > 100) {
            throw new IllegalArgumentException("O progresso deve estar entre 0 e 100%.");
        }

        if (newProgress < this.progress) {
            throw new IllegalArgumentException("O progresso não pode ser inferior ao atual (" + this.progress + "%).");
        }

        this.progress = newProgress;

        if (this.progress == 100 && this.status != EnrollmentStatus.COMPLETED) {
            this.status = EnrollmentStatus.COMPLETED;
            this.completionDate = LocalDate.now();
        }
    }

    public void unsubscribe(){
        if (this.status == EnrollmentStatus.COMPLETED){
            throw new IllegalStateException("Não é possível cancelar um curso já concluído.");
        }
        this.status = EnrollmentStatus.CANCELLED;
    }

    public boolean isActive() {
        return this.status == EnrollmentStatus.ACTIVE;
    }

    public boolean isCompleted() {
        return this.status == EnrollmentStatus.COMPLETED;
    }
}
