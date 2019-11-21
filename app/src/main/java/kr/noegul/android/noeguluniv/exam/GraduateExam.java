package kr.noegul.android.noeguluniv.exam;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.noegul.android.noeguluniv.course.Course;

public class GraduateExam {
    public static final double MAX_SCORE = 4.5;
    private Map<Course, Double> records;
    private List<Course> courses;
    private int curCourseIndex;

    public GraduateExam() {
        this.records = new HashMap<>();
        this.courses = Arrays.asList(Course.values());
        this.curCourseIndex = 0;

        Collections.shuffle(courses);
    }

    public void putRecord(Course course, double score) {
        records.put(course, score);
    }

    public double getScore(Course course) {
        Double score = records.get(course);
        if (score == null)
            throw new IllegalArgumentException("Couldn't find the record");
        return score;
    }

    public Course getCurrentCourse() {
        return courses.get(curCourseIndex);
    }

    public Course nextCourse() {
        if (isLastCourse())
            throw new IllegalStateException("Next course doesn't exist");

        curCourseIndex++;
        return getCurrentCourse();
    }

    public boolean isLastCourse() {
        return curCourseIndex == courses.size() - 1;
    }
}
