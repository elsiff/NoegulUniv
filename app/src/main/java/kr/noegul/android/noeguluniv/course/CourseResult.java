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
}
