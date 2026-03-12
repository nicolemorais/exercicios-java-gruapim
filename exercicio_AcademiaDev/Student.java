package exercicio_AcademiaDev;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import exercicio_AcademiaDev.exceptions.EnrollmentException;

public class Student extends User {
    @CsvColumn(label = "Plano de Assinatura")
    private SubscriptionPlan subscriptionPlan;

    private final List<Enrollment> enrollments;

    public Student(String name, String email, SubscriptionPlan subscriptionPlan) {
        super(name, email);
        this.subscriptionPlan = subscriptionPlan;
        this.enrollments = new ArrayList<>();
    }

    public SubscriptionPlan getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public void setSubscriptionPlan(SubscriptionPlan subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }

    public boolean canEnroll() {
        long activeCount = enrollments.stream()
                .filter(e -> e.getStatus() == EnrollmentStatus.ACTIVE)
                .count();

        return activeCount < subscriptionPlan.getMaxCourses();
    }

    public void addEnrollment(Enrollment enrollment) {
        if (enrollment != null) {
            this.enrollments.add(enrollment);
        }
    }

    public List<Enrollment> getEnrollments() {
        return Collections.unmodifiableList(enrollments);
    }

    public Optional<Enrollment> findEnrollmentByCourseTitle(String title) {
        return enrollments.stream()
                .filter(e -> e.getCourse().getTitle().equalsIgnoreCase(title))
                .findFirst();
    }

    @Override
    public String toString() {
        return String.format("%s | Plano: %s | Matrículas: %d",
                super.toString(), subscriptionPlan, enrollments.size());
    }

}
