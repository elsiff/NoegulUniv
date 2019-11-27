package kr.noegul.android.noeguluniv.player;

import android.content.SharedPreferences;

import kr.noegul.android.noeguluniv.course.Course;
import kr.noegul.android.noeguluniv.course.CourseResult;
import kr.noegul.android.noeguluniv.exam.GraduateExam;

public class SharedPrefPlayerData implements PlayerData {
    private SharedPreferences pref;

    public SharedPrefPlayerData(SharedPreferences pref) {
        this.pref = pref;
    }

    @Override
    public CourseResult getResult(Course course) {
        String str = pref.getString("course-result." + course.name(), "");
        return (str.isEmpty() ? CourseResult.NON_PASS : CourseResult.valueOf(str));
    }

    @Override
    public void setResult(Course course, CourseResult result) {
        pref.edit()
                .putString("course-result." + course.name(), result.name())
                .apply();
    }

    @Override
    public double getGraduateExamScore() {
        return pref.getFloat("graduate-exam-score", 0);
    }

    @Override
    public void setGraduateExamScore(double score) {
        if (score < 0 || score > GraduateExam.MAX_SCORE)
            throw new IllegalArgumentException("Score out of range");

        pref.edit()
                .putFloat("graduate-exam-score", (float) score)
                .apply();
    }

    @Override
    public boolean hasPlayedTutorial() {
        return pref.getBoolean("played-tutorial", false);
    }

    @Override
    public void setPlayedTutorial(boolean playedTutorial) {
        pref.edit()
                .putBoolean("played-tutorial", playedTutorial)
                .apply();
    }
}
