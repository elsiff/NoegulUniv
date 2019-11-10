package kr.noegul.android.noeguluniv.course.comparecoins;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.EnumMap;
import java.util.Map;

import kr.noegul.android.noeguluniv.R;
import kr.noegul.android.noeguluniv.course.CourseResult;
import kr.noegul.android.noeguluniv.course.CourseResultActivity;
import kr.noegul.android.noeguluniv.course.CourseTimeLimit;

public class CompareCoinsActivity extends AppCompatActivity {
    private CompareCoins game = new CompareCoins();
    private TextView numSolvedText;
    private Map<CompareCoins.ChoiceLabel, GridView> coinGrids;
    private CourseTimeLimit timeLimit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_coins);

        numSolvedText = findViewById(R.id.num_solved_text);

        coinGrids = new EnumMap<>(CompareCoins.ChoiceLabel.class);
        coinGrids.put(CompareCoins.ChoiceLabel.A, (GridView) findViewById(R.id.choice_a_grid));
        coinGrids.put(CompareCoins.ChoiceLabel.B, (GridView) findViewById(R.id.choice_b_grid));

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
        intent.setClass(CompareCoinsActivity.this, CourseResultActivity.class);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (timeLimit.hasStarted()) {
            timeLimit.stop();
        }
    }

    private void setupQuizLayout() {
        numSolvedText.setText("Solved " + game.getNumSolved() + ", failed " + game.getNumFailed());

        CompareCoins.Quiz quiz = game.getCurrentQuiz();

        for (CompareCoins.ChoiceLabel label : CompareCoins.ChoiceLabel.values()) {
            CompareCoins.Choice choice = quiz.getChoice(label);
            GridView coinGrid = coinGrids.get(label);

            if (coinGrid == null)
                throw new IllegalStateException("Invalid choice label");

            coinGrid.setAdapter(new CoinGridAdapter(this, choice));
        }
    }

    public void onClickChoiceButton(View view) {
        CompareCoins.ChoiceLabel label;
        switch (view.getId()) {
            case R.id.choice_a_button:
                label = CompareCoins.ChoiceLabel.A;
                break;
            case R.id.choice_b_button:
                label = CompareCoins.ChoiceLabel.B;
                break;
            default:
                throw new IllegalStateException("Invalid choice button");
        }

        makeChoice(label);
    }

    private void makeChoice(CompareCoins.ChoiceLabel choiceLabel) {
        CompareCoins.Quiz quiz = game.getCurrentQuiz();

        if (quiz.getAnswer() == choiceLabel) {
            game.solveCurrentQuiz();
        } else {
            game.failCurrentQuiz();
        }
        setupQuizLayout();
    }

    private int imageResourceOf(CompareCoins.Coin coin) {
        switch (coin.getValue()) {
            case 10:
                if (!coin.isFlipped())
                    return R.drawable.coin_10_front;
                else
                    return R.drawable.coin_10_back;
            case 50:
                if (!coin.isFlipped())
                    return R.drawable.coin_50_front;
                else
                    return R.drawable.coin_50_back;
            case 100:
                if (!coin.isFlipped())
                    return R.drawable.coin_100_front;
                else
                    return R.drawable.coin_100_back;
            case 500:
                if (!coin.isFlipped())
                    return R.drawable.coin_500_front;
                else
                    return R.drawable.coin_500_back;
            default:
                throw new IllegalStateException("Invalid coin");
        }
    }

    private class CoinGridAdapter extends BaseAdapter {
        private Context context;
        private CompareCoins.Choice choice;

        public CoinGridAdapter(Context context, CompareCoins.Choice choice) {
            this.context = context;
            this.choice = choice;
        }

        @Override
        public int getCount() {
            return choice.getCoins().size();
        }

        @Override
        public Object getItem(int i) {
            return choice.getCoins().get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            CompareCoins.Coin coin = choice.getCoins().get(i);

            ImageView imageView = new ImageView(context);
            imageView.setImageResource(imageResourceOf(coin));
            imageView.setMaxWidth(150);
            imageView.setMaxHeight(150);
            imageView.setAdjustViewBounds(true);
            imageView.setPadding(1, 1, 1, 1);
            return imageView;
        }
    }
}
