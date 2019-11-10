package kr.noegul.android.noeguluniv.player;

import java.util.Map;

import kr.noegul.android.noeguluniv.course.Course;
import kr.noegul.android.noeguluniv.course.CourseResult;
import kr.noegul.android.noeguluniv.exam.GraduateExam;

public class PlayerData {
    private Map<Course, CourseResult> courseResults;
    private double graduateExamScore;
    private boolean playedTutorial;

    public PlayerData(
            Map<Course, CourseResult> courseResults,
            double graduateExamScore,
            boolean playedTutorial
    ) {
        this.courseResults = courseResults;
        this.graduateExamScore = graduateExamScore;
        this.playedTutorial = playedTutorial;
    }

    public CourseResult getResult(Course course) {
        return courseResults.get(course);
    }

    public void setResult(Course course, CourseResult result) {
        courseResults.put(course, result);
    }

    public double getGraduateExamScore() {
        return graduateExamScore;
    }

    public void setGraduateExamScore(double score) {
        if (score < 0 || score > GraduateExam.MAX_SCORE)
            throw new IllegalArgumentException("Score out of range");

        this.graduateExamScore = score;
    }

    public boolean hasPlayedTutorial() {
        return playedTutorial;
    }

    public void setPlayedTutorial(boolean playedTutorial) {
        this.playedTutorial = playedTutorial;
    }
}
