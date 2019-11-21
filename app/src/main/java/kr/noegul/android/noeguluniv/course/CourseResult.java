package kr.noegul.android.noeguluniv.course;

public enum CourseResult {
    PASS("Pass"),
    NON_PASS("Non-Pass");

    private String title;

    CourseResult(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static CourseResult fromScore(double score) {
        CourseResult result;
        if (score >= ScoreLabel.C.getMinScore())
            result = PASS;
        else
            result = NON_PASS;
        return result;
    }
}
