package kr.noegul.android.noeguluniv.exam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import kr.noegul.android.noeguluniv.NeogulUnivApp;
import kr.noegul.android.noeguluniv.R;
import kr.noegul.android.noeguluniv.course.ScoreLabel;
import kr.noegul.android.noeguluniv.dialog.DialogActivity;
import kr.noegul.android.noeguluniv.dialog.Scripts;
import kr.noegul.android.noeguluniv.player.PlayerData;

public class ExamResultActivity extends AppCompatActivity {
    private boolean passed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_result);

        Intent intent = getIntent();

        double compareCoinsScore = intent.getDoubleExtra("compare-coins-score", 0);
        double matchThePictureScore = intent.getDoubleExtra("match-the-picture-score", 0);
        double countBlocksScore = intent.getDoubleExtra("count-blocks-score", 0);
        double rememberCardsScore = intent.getDoubleExtra("remember-cards-score", 0);

        double gpa = (compareCoinsScore + matchThePictureScore + countBlocksScore + rememberCardsScore) / 4;
        if (gpa >= ScoreLabel.B_PLUS.getMinScore()) {
            passed = true;
        }

        TextView compareCoinsGradeText = findViewById(R.id.compare_coins_grade_text);
        compareCoinsGradeText.setText(" " + ScoreLabel.of(compareCoinsScore).getText());

        TextView matchThePictureGradeText = findViewById(R.id.match_the_picture_grade_text);
        matchThePictureGradeText.setText(" " + ScoreLabel.of(matchThePictureScore).getText());

        TextView countBlocksGradeText = findViewById(R.id.count_blocks_grade_text);
        countBlocksGradeText.setText(" " + ScoreLabel.of(countBlocksScore).getText());

        TextView rememberCardsGradeText = findViewById(R.id.remember_cards_grade_text);
        rememberCardsGradeText.setText(" " + ScoreLabel.of(rememberCardsScore).getText());

        TextView graduateResultText = findViewById(R.id.graduate_result_text);
        graduateResultText.setText("평균 평점: " + gpa +" (" + ScoreLabel.of(gpa).getText() + ")");

        Button confirmButton = findViewById(R.id.confirm_button);
        PlayerData playerData = NeogulUnivApp.getInstance().getPlayerData();
        if (!playerData.hasPlayedTutorial()) {
            confirmButton.setText("확인");
        } else {
            if (passed) {
                confirmButton.setText("졸업하기");
            } else {
                confirmButton.setText("재수강하기");
            }
        }
    }

    public void onClickConfirmButton(View view) {
        Intent intent = new Intent(this, DialogActivity.class);
        PlayerData playerData = NeogulUnivApp.getInstance().getPlayerData();

        int scriptNum;
        if (!playerData.hasPlayedTutorial())
            scriptNum = Scripts.FINISH_TUTORIAL;
        else
            scriptNum = (passed ? Scripts.PASS_GRADUATE_EXAM : Scripts.FAIL_GRADUATE_EXAM);
        intent.putExtra("script-num", scriptNum);

        startActivity(intent);
        finish();
    }
}
