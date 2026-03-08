package exercicio_AcademiaDev;

public enum SubscriptionPlan {
    BASIC(3),
    PREMIUM(Integer.MAX_VALUE);

    private final int maxCourses;

    SubscriptionPlan(int maxCourses){
        this.maxCourses = maxCourses;
    }

    public int getMaxCourses(){
        return maxCourses;
    }
}
