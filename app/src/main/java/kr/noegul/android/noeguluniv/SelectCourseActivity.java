package kr.noegul.android.noeguluniv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import kr.noegul.android.noeguluniv.course.Course;
import kr.noegul.android.noeguluniv.course.CourseResult;
import kr.noegul.android.noeguluniv.course.comparecoins.CompareCoinsActivity;
import kr.noegul.android.noeguluniv.course.countblocks.CountBlocksActivity;
import kr.noegul.android.noeguluniv.course.matchthepicture.MatchThePictureActivity;
import kr.noegul.android.noeguluniv.course.remembercards.RememberCardsActivity;
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
    }

    public void onClickCourseButton(View view) {
        Class<? extends Activity> activityClass;

        switch (view.getId()) {
            case R.id.compare_coins_course_button:
                activityClass = CompareCoinsActivity.class;
                break;
            case R.id.match_the_picture_course_button:
                activityClass = MatchThePictureActivity.class;
                break;
            case R.id.count_blocks_course_button:
                activityClass = CountBlocksActivity.class;
                break;
            case R.id.remember_cards_course_button:
                activityClass = RememberCardsActivity.class;
                break;
            default:
                throw new IllegalStateException("Invalid course button");
        }

        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
}
