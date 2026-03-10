package exercicio_AcademiaDev;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Student extends User {
    @CsvColumn(label = "Plano de Assinatura")
    private SubscriptionPlan subscriptionPlan;

    private final List<Enrollment> enrollments;

    public Student(String name, String email, SubscriptionPlan subscriptionPlan) {
        super(name, email);
        this.subscriptionPlan = Objects.requireNonNull(subscriptionPlan, "Plano de assinatura é obrigatório");
        this.enrollments = new ArrayList<>();
    }

    public SubscriptionPlan getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public void setSubscriptionPlan(SubscriptionPlan subscriptionPlan) {
    this.subscriptionPlan = subscriptionPlan;
}

    public boolean canEnroll() {
        long inProgressCount = enrollments.stream()
                .filter(Enrollment::isInProgress)
                .count();

        return inProgressCount < subscriptionPlan.getMaxCourses();
    }

    public void addEnrollment(Enrollment enrollment){
        Optional.of(enrollment)
        .filter(e -> canEnroll())
        .ifPresentOrElse(
            this.enrollments::add,() -> {throw new IllegalStateException("Limite do plano " + subscriptionPlan + " atingido!");}
        );
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
