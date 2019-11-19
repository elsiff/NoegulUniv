package kr.noegul.android.noeguluniv.player;

import kr.noegul.android.noeguluniv.course.Course;
import kr.noegul.android.noeguluniv.course.CourseResult;

public interface PlayerData {
    CourseResult getResult(Course course);

    void setResult(Course course, CourseResult result);

    double getGraduateExamScore();

    void setGraduateExamScore(double score);

    boolean hasPlayedTutorial();

    void setPlayedTutorial(boolean playedTutorial);
}
