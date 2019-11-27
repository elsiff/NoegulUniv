package kr.noegul.android.noeguluniv.course;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import kr.noegul.android.noeguluniv.NeogulUnivApp;
import kr.noegul.android.noeguluniv.R;
import kr.noegul.android.noeguluniv.dialog.DialogActivity;
import kr.noegul.android.noeguluniv.dialog.Scripts;
import kr.noegul.android.noeguluniv.exam.ExamResultActivity;
import kr.noegul.android.noeguluniv.exam.GraduateExam;
import kr.noegul.android.noeguluniv.player.PlayerData;

public class CourseResultActivity extends AppCompatActivity {
    private double score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_result);

        Intent intent = getIntent();
        int numSolved = intent.getIntExtra("num-solved", 0);
        int numFailed = intent.getIntExtra("num-failed", 0);
        this.score = intent.getDoubleExtra("result", 0);
        Course course = Course.valueOf(intent.getStringExtra("course"));

        PlayerData playerData = NeogulUnivApp.getInstance().getPlayerData();
        if (playerData.getResult(course) == CourseResult.NON_PASS &&
                score >= ScoreLabel.C.getMinScore()) {
            playerData.setResult(course, CourseResult.PASS);
        }

        TextView resultText = findViewById(R.id.result_text);
        resultText.setText("맞은 개수: " + numSolved + "\n" +
                "틀린 개수: " + numFailed + "\n" +
                "최종 성적: " + String.format("%.1f", score) +" (" + ScoreLabel.of(score).getText()
                + ", " + CourseResult.fromScore(score).getTitle() + ")");
    }

    public void onClickConfirmButton(View view) {
        NeogulUnivApp game = NeogulUnivApp.getInstance();
        if (game.hasOngoingExam()) {
            GraduateExam exam = game.getOngoingExam();
            exam.putRecord(exam.getCurrentCourse(), score);

            if (exam.isLastCourse()) {
                Intent intent = new Intent(this, ExamResultActivity.class);
                intent.putExtra("compare-coins-score", exam.getScore(Course.COMPARE_COINS));
                intent.putExtra("match-the-picture-score", exam.getScore(Course.MATCH_THE_PICTURE));
                intent.putExtra("count-blocks-score", exam.getScore(Course.COUNT_BLOCKS));
                intent.putExtra("remember-cards-score", exam.getScore(Course.REMEMBER_CARDS));
                startActivity(intent);
                game.stopGraduateExam();
            } else {
                Course course = exam.nextCourse();
                startCourseActivity(course);
            }
        }

        this.finish();
    }

    private void startCourseActivity(Course course) {
        int scriptNum;
        switch (course) {
            case COMPARE_COINS:
                scriptNum = Scripts.START_COMPARE_COINS;
                break;
            case MATCH_THE_PICTURE:
                scriptNum = Scripts.START_MATCH_THE_PICTURE;
                break;
            case COUNT_BLOCKS:
                scriptNum = Scripts.START_COUNT_BLOCKS;
                break;
            case REMEMBER_CARDS:
                scriptNum = Scripts.START_REMEMBER_CARDS;
                break;
            default:
                throw new IllegalArgumentException("Invalid course");
        }

        Intent intent = new Intent(this, DialogActivity.class);
        intent.putExtra("script-num", scriptNum);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        NeogulUnivApp game = NeogulUnivApp.getInstance();
        if (game.hasOngoingExam()) {
            game.stopGraduateExam();
        }
    }
}
