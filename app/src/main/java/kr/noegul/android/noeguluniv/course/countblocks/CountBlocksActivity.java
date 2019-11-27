package kr.noegul.android.noeguluniv.course.countblocks;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Set;

import kr.noegul.android.noeguluniv.NeogulUnivApp;
import kr.noegul.android.noeguluniv.R;
import kr.noegul.android.noeguluniv.course.Course;
import kr.noegul.android.noeguluniv.course.CourseResultActivity;
import kr.noegul.android.noeguluniv.course.CourseTimeLimit;
import kr.noegul.android.noeguluniv.course.ScoreLabel;

public class CountBlocksActivity extends AppCompatActivity {
    private static final Handler handler = new Handler();
    private CountBlocks game;
    private TextView numSolvedText;
    private ViewGroup blocksLayout;
    private TextView inputText;
    private Button clearButton;
    private Set<View> blockViews;
    private CourseTimeLimit timeLimit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_blocks);

        numSolvedText = findViewById(R.id.num_solved_text);
        blocksLayout = findViewById(R.id.blocks_layout);
        inputText = findViewById(R.id.input_text);
        clearButton = findViewById(R.id.clear_buton);

        game = new CountBlocks();
        blockViews = new HashSet<>();

        timeLimit = new CourseTimeLimit(this, (ProgressBar) findViewById(R.id.time_limit_progress));
        timeLimit.setFinishHandler(new Runnable() {
            @Override
            public void run() {
                finishWithResultActivity();
            }
        });
        timeLimit.start();

        handler.post(new Runnable() {
            @Override
            public void run() {
                setupQuizLayout();
            }
        });
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

    private void finishWithResultActivity() {
        Intent intent = new Intent();
        intent.setClass(this, CourseResultActivity.class);

        intent.putExtra("course", Course.COUNT_BLOCKS.name());

        intent.putExtra("num-solved", game.getNumSolved());
        intent.putExtra("num-failed", game.getNumFailed());

        ScoreLabel result;
        if (game.getNumSolved() >= 30 && game.getNumFailed() <= 1)
            result = ScoreLabel.A_PLUS;
        else if (game.getNumSolved() >= 26 && game.getNumFailed() <= 2)
            result = ScoreLabel.A;
        else if (game.getNumSolved() >= 22 && game.getNumFailed() <= 4)
            result = ScoreLabel.B_PLUS;
        else if (game.getNumSolved() >= 19 && game.getNumFailed() <= 4)
            result = ScoreLabel.B;
        else if (game.getNumSolved() >= 17 && game.getNumFailed() <= 5)
            result = ScoreLabel.C_PLUS;
        else if (game.getNumSolved() >= 15 && game.getNumFailed() <= 7)
            result = ScoreLabel.C;
        else if (game.getNumSolved() >= 10)
            result = ScoreLabel.D_PLUS;
        else if (game.getNumSolved() >= 7)
            result = ScoreLabel.D;
        else
            result = ScoreLabel.F;
        intent.putExtra("result", result.getMinScore());

        startActivity(intent);
        finish();
    }

    private void setupQuizLayout() {
        numSolvedText.setText("Solved " + game.getNumSolved() + ", failed " + game.getNumFailed());

        CountBlocks.Quiz quiz = game.getCurrentQuiz();

        for (View blockView : blockViews) {
            blocksLayout.removeView(blockView);
        }
        blockViews.clear();

        float originX = blocksLayout.getMeasuredWidth() / 2f;
        float originY = blocksLayout.getMeasuredHeight() / 2f;
        float minX = Float.POSITIVE_INFINITY;
        float maxX = Float.NEGATIVE_INFINITY;
        float minY = Float.POSITIVE_INFINITY;
        float maxY = Float.NEGATIVE_INFINITY;
        for (CountBlocks.Block block : quiz.getBlocks()) {
            ImageView view = new ImageView(this);
            view.setMaxWidth(150);
            view.setMaxHeight(150);
            view.setAdjustViewBounds(true);
            view.setImageResource(R.drawable.block);
            blockViews.add(view);
            blocksLayout.addView(view);

            float offsetX = 84f * (float) (block.getX() - 0.8f * block.getZ() * Math.sin(Math.PI / 4));
            float offsetY = 82f * (float) (block.getY() - 0.5f * block.getZ() * Math.sin(Math.PI / 4));

            if (offsetX < minX)
                minX = offsetX;
            if (offsetX > maxX)
                maxX = offsetX;
            if (offsetY < minY)
                minY = offsetY;
            if (offsetY > maxY)
                maxY = offsetY;

            view.setX(originX + offsetX);
            view.setY(originY - offsetY);
        }

        float centeringOffsetX = -(maxX - (maxX - minX) / 2) - 75;
        float centeringOffsetY = -(maxY - (maxY - minY) / 2) - 75;
        for (View view : blockViews) {
            view.setX(view.getX() + centeringOffsetX);
            view.setY(view.getY() - centeringOffsetY);
        }
    }

    public void onClickNumberButton(View view) {
        Button numberButton = (Button) view;
        String updatedInput = inputText.getText().toString() + numberButton.getText();

        inputText.setText(updatedInput);
        clearButton.setVisibility(View.VISIBLE);

        String answerText = String.valueOf(game.getCurrentQuiz().getAnswer());
        if (updatedInput.length() >= answerText.length()) {
            if (updatedInput.equals(answerText)) {
                game.solveCurrentQuiz();
            } else {
                game.failCurrentQuiz();
            }

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    inputText.setText("");
                }
            }, 500L);
            clearButton.setVisibility(View.INVISIBLE);
            setupQuizLayout();
        }
    }

    public void onClickClearButton(View view) {
        inputText.setText("");
    }
}
