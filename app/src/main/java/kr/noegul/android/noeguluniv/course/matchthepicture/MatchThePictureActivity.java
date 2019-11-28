package kr.noegul.android.noeguluniv.course.matchthepicture;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import kr.noegul.android.noeguluniv.NeogulUnivApp;
import kr.noegul.android.noeguluniv.R;
import kr.noegul.android.noeguluniv.course.Course;
import kr.noegul.android.noeguluniv.course.CourseResultActivity;
import kr.noegul.android.noeguluniv.course.CourseTimeLimit;
import kr.noegul.android.noeguluniv.course.ScoreLabel;

public class MatchThePictureActivity extends AppCompatActivity {
    private static final Handler handler = new Handler();
    private TextView numSolvedText;
    private MatchPictures game = new MatchPictures();
    private CourseTimeLimit timeLimit;
    private MatchPictures.Quiz quiz;
    private List<MatchPictures.Picture> pictures;
    private GridView gv;
    List<ImageButton> clickedButtons = new ArrayList<>();
    private boolean locked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_the_picture);

        numSolvedText = findViewById(R.id.num_solved_text);
        gv = findViewById(R.id.gridView);

        timeLimit = new CourseTimeLimit(this, (ProgressBar) findViewById(R.id.time_limit_progress));
        timeLimit.setFinishHandler(new Runnable() {
            @Override
            public void run() {
                finishWithResultActivity();
            }
        });
        timeLimit.start();

        setupQuizLayout();
    }

    private void finishWithResultActivity() {
        Intent intent = new Intent();
        intent.setClass(MatchThePictureActivity.this, CourseResultActivity.class);

        intent.putExtra("course", Course.MATCH_THE_PICTURE.name());

        intent.putExtra("num-solved", game.getNumSolved());
        intent.putExtra("num-failed", game.getNumFailed());

        int gradePoint = game.getNumSolved() * 100 / (game.getNumFailed() + game.getNumSolved());
        ScoreLabel score;
        if (gradePoint >= 95 && game.getNumSolved() >= 25)
            score = ScoreLabel.A_PLUS;
        else if (gradePoint >= 90 && game.getNumSolved() >= 25)
            score = ScoreLabel.A;
        else if (gradePoint >= 85 && game.getNumSolved() >= 20)
            score = ScoreLabel.B_PLUS;
        else if (gradePoint >= 80 && game.getNumSolved() >= 20)
            score = ScoreLabel.B;
        else if (gradePoint >= 75 && game.getNumSolved() >= 15)
            score = ScoreLabel.C_PLUS;
        else if (gradePoint >= 70 && game.getNumSolved() >= 25)
            score = ScoreLabel.C;
        else if (gradePoint >= 65)
            score = ScoreLabel.D_PLUS;
        else if (gradePoint >= 60)
            score = ScoreLabel.D;
        else
            score = ScoreLabel.F;

        intent.putExtra("result", score.getMinScore());

        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (timeLimit.hasStarted()) {
            timeLimit.stop();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        NeogulUnivApp game = NeogulUnivApp.getInstance();
        if (game.hasOngoingExam()) {
            game.stopGraduateExam();
        }
    }

    private void setupQuizLayout() {
        numSolvedText.setText("Solved " + game.getNumSolved() + ", failed " + game.getNumFailed());

        quiz = game.getCurrentQuiz();
        pictures = quiz.getPictures();
        clickedButtons.clear();

        gv.setAdapter(new PictureGridAdapter(this, pictures));

    }

    private void makeChoice(List<ImageButton> clickedButton) {
        MatchPictures.Picture picture1 =
                (MatchPictures.Picture) clickedButton.get(0).getTag();
        MatchPictures.Picture picture2 =
                (MatchPictures.Picture) clickedButton.get(1).getTag();

        if (quiz.getAnswer().equals(picture1.getValue()) &&
                (quiz.getAnswer().equals(picture2.getValue()))) {
            game.solveCurrentQuiz();
        } else {
            game.failCurrentQuiz();
        }

        locked = true;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                locked = false;
                setupQuizLayout();
            }
        }, 300L);
    }

    private int imageResourceOf(MatchPictures.Picture picture) {
        switch (picture.getValue()) {
            case "apple":
                return R.drawable.fruit_0;
            case "banana":
                return R.drawable.fruit_1;
            case "cherry":
                return R.drawable.fruit_7;
            case "orange":
                return R.drawable.fruit_2;
            case "peach":
                return R.drawable.fruit_4;
            case "strawberry":
                return R.drawable.fruit_5;
            case "watermelon":
                return R.drawable.fruit_6;
            case "grape":
                return R.drawable.fruit_8;
            case "kiwi":
                return R.drawable.fruit_9;
            case "lemon":
                return R.drawable.fruit_10;
            case "orientallemon":
                return R.drawable.fruit_11;
            default:
                throw new IllegalStateException("Invalid picture");
        }
    }

    private class PictureGridAdapter extends BaseAdapter {
        private Context context;
        private List<MatchPictures.Picture> pictures;

        public PictureGridAdapter(Context context, List<MatchPictures.Picture> pictures) {
            this.context = context;
            this.pictures = pictures;
        }

        @Override
        public int getCount() {
            return pictures.size();
        }

        @Override
        public Object getItem(int i) {
            return pictures.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;//?
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            MatchPictures.Picture picture = pictures.get(i);


            ImageButton imageButton = new ImageButton(context);
            imageButton.setImageResource(imageResourceOf(picture));
            imageButton.setBackgroundResource(R.color.colorLightGray);
            imageButton.setMaxHeight(150);
            imageButton.setAdjustViewBounds(true);
            imageButton.setPadding(1, 1, 1, 1);
            imageButton.setTag(picture);

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // 안 잠겨져있으면 실행
                    if (!locked) {
                        ImageButton button = (ImageButton) view;

                        if (clickedButtons.contains(button)) {
                            clickedButtons.remove(button);

                            button.setBackgroundResource(R.color.colorLightGray);
                        } else {
                            clickedButtons.add(button);
                            button.setBackgroundResource(R.color.colorGray);
                        }

                        if (clickedButtons.size() == 2) makeChoice(clickedButtons);
                    }
                }
            });
            return imageButton;
        }
    }
}
