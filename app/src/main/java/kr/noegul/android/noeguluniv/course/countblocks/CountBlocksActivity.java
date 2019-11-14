package kr.noegul.android.noeguluniv.course.countblocks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

import kr.noegul.android.noeguluniv.R;
import kr.noegul.android.noeguluniv.course.CourseResult;
import kr.noegul.android.noeguluniv.course.CourseResultActivity;
import kr.noegul.android.noeguluniv.course.CourseTimeLimit;

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

    private void finishWithResultActivity() {
        Intent intent = new Intent();
        intent.setClass(this, CourseResultActivity.class);

        intent.putExtra("num-solved", game.getNumSolved());
        intent.putExtra("num-failed", game.getNumFailed());

        CourseResult result;
        if (game.getNumFailed() <= 3 && game.getNumSolved() >= 25)
            result = CourseResult.PASS;
        else
            result = CourseResult.NON_PASS;
        intent.putExtra("result", result);

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
