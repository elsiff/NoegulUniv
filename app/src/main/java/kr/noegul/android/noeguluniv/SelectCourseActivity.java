package kr.noegul.android.noeguluniv;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import kr.noegul.android.noeguluniv.course.Course;
import kr.noegul.android.noeguluniv.course.CourseResult;
import kr.noegul.android.noeguluniv.dialog.DialogActivity;
import kr.noegul.android.noeguluniv.dialog.Scripts;
import kr.noegul.android.noeguluniv.player.PlayerData;

public class SelectCourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_course);
    }

    @Override
    protected void onStart() {
        super.onStart();

        PlayerData playerData = NeogulUnivApp.getInstance().getPlayerData();
        if (!playerData.hasPlayedTutorial()) {
            Intent intent = new Intent(this, DialogActivity.class);
            intent.putExtra("script-num", Scripts.TUTORIAL);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        PlayerData playerData = NeogulUnivApp.getInstance().getPlayerData();

        ImageView compareCoinsCourseGrade = findViewById(R.id.compare_coins_course_grade);
        if (playerData.getResult(Course.COMPARE_COINS) == CourseResult.PASS)
            compareCoinsCourseGrade.setImageResource(R.drawable.pass);
        else
            compareCoinsCourseGrade.setImageResource(R.drawable.npass);

        ImageView matchThePictureCourseGrade = findViewById(R.id.match_the_picture_course_grade);
        if (playerData.getResult(Course.MATCH_THE_PICTURE) == CourseResult.PASS)
            matchThePictureCourseGrade.setImageResource(R.drawable.pass);
        else
            matchThePictureCourseGrade.setImageResource(R.drawable.npass);

        ImageView countBlocksCourseGrade = findViewById(R.id.count_blocks_course_grade);
        if (playerData.getResult(Course.COUNT_BLOCKS) == CourseResult.PASS)
            countBlocksCourseGrade.setImageResource(R.drawable.pass);
        else
            countBlocksCourseGrade.setImageResource(R.drawable.npass);

        ImageView rememberCardsCourseGrade = findViewById(R.id.remember_cards_course_grade);
        if (playerData.getResult(Course.REMEMBER_CARDS) == CourseResult.PASS)
            rememberCardsCourseGrade.setImageResource(R.drawable.pass);
        else
            rememberCardsCourseGrade.setImageResource(R.drawable.npass);

        if (playerData.getResult(Course.COMPARE_COINS) == CourseResult.PASS &&
                playerData.getResult(Course.MATCH_THE_PICTURE) == CourseResult.PASS &&
                playerData.getResult(Course.COUNT_BLOCKS) == CourseResult.PASS &&
                playerData.getResult(Course.REMEMBER_CARDS) == CourseResult.PASS) {
            findViewById(R.id.graduate_exam_button).setVisibility(View.VISIBLE);
        }
    }

    public void onClickCourseButton(View view) {
        int scriptNum;
        switch (view.getId()) {
            case R.id.compare_coins_course_button:
                scriptNum = Scripts.START_COMPARE_COINS;
                break;
            case R.id.match_the_picture_course_button:
                scriptNum = Scripts.START_MATCH_THE_PICTURE;
                break;
            case R.id.count_blocks_course_button:
                scriptNum = Scripts.START_COUNT_BLOCKS;
                break;
            case R.id.remember_cards_course_button:
                scriptNum = Scripts.START_REMEMBER_CARDS;
                break;
            default:
                throw new IllegalStateException("Invalid course button");
        }

        Intent intent = new Intent(this, DialogActivity.class);
        intent.putExtra("script-num", scriptNum);
        startActivity(intent);
    }

    public void onClickGraduateExamButton(View view) {
        Intent intent = new Intent(this, DialogActivity.class);
        intent.putExtra("script-num", Scripts.START_GRADUATE_EXAM);
        startActivity(intent);
    }
}
