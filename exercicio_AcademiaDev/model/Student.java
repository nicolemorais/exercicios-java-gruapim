package exercicio_AcademiaDev.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import exercicio_AcademiaDev.util.CsvColumn;

public class Student extends User {
    @CsvColumn(label = "Plano de Assinatura")
    private SubscriptionPlan subscriptionPlan;

    private List<Enrollment> enrollments = new ArrayList<>();

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
        return this.enrollments;
    }

    public Optional<Enrollment> findEnrollmentByCourseTitle(String title) {
        return enrollments.stream()
                .filter(e -> e.getCourse().getTitle().equalsIgnoreCase(title))
                .findFirst();
    }

    public int getCourseProgress(String courseTitle) {
        return findEnrollmentByCourseTitle(courseTitle)
                .map(Enrollment::getProgress)
                .orElse(0);
    }

    public boolean isPremium() {
        return this.subscriptionPlan == SubscriptionPlan.PREMIUM;
    }

    @Override
    public String toString() {
        long ativos = enrollments.stream()
                .filter(e -> e.getStatus() == EnrollmentStatus.ACTIVE)
                .count();
        return String.format("%s | Plano: %s | Ativas: %d | Total: %d",
                super.toString(), subscriptionPlan, ativos, enrollments.size());
    }

}
