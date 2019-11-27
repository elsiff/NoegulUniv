package kr.noegul.android.noeguluniv.course.remembercards;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import kr.noegul.android.noeguluniv.NeogulUnivApp;
import kr.noegul.android.noeguluniv.R;
import kr.noegul.android.noeguluniv.course.Course;
import kr.noegul.android.noeguluniv.course.CourseResult;
import kr.noegul.android.noeguluniv.course.CourseResultActivity;
import kr.noegul.android.noeguluniv.course.CourseTimeLimit;
import kr.noegul.android.noeguluniv.course.comparecoins.CompareCoins;
import kr.noegul.android.noeguluniv.course.comparecoins.CompareCoinsActivity;

public class RememberCardsActivity extends AppCompatActivity {
    private RememberCards game=new RememberCards();
    private TextView txtscore;
    private ImageView[][] cards=new ImageView[2][4];
    private boolean[][] selected=new boolean[2][4];
    private boolean[][] visited=new boolean[2][4];
    private Button[] results=new Button[4];
    private CourseTimeLimit timeLimit;
    private Button btnStart;
    private Random random=new Random();
    private Button btnstart;
    private int resultAns,ans_x, ans_y,vFruit;

    public abstract class ChoiceClickListener implements  View.OnClickListener{
        protected int idx;
        public ChoiceClickListener(int idx){
            this.idx=idx;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remember_cards);
        txtscore=(TextView)findViewById(R.id.txt_remember_solved);
        for(int i=0;i<2;i++){
            for(int j=0;j<4;j++){
                String id="th"+String.valueOf(i)+"td"+String.valueOf(j);
                int ID=getResources().getIdentifier(id,"id", getPackageName());
                cards[i][j]=(ImageView)findViewById(ID);
                cards[i][j].setVisibility(View.INVISIBLE);
                visited[i][j]=false;
                selected[i][j]=false;

            }
        }

        for(int i=0;i<4;i++){
            String id="result"+String.valueOf(i);
            int ID=getResources().getIdentifier(id,"id", getPackageName());
           results[i]=(Button) findViewById(ID);
           results[i].setOnClickListener(new ChoiceClickListener(i) {
               @Override
               public void onClick(View view) {
                   checkAnswer(idx);
               }
           });
        }

        btnStart=(Button)findViewById(R.id.btn_start);

        timeLimit = new CourseTimeLimit(this, (ProgressBar) findViewById(R.id.time_limit_progress_rc));
        timeLimit.setFinishHandler(new Runnable() {
            @Override
            public void run() {
                finishWithResultActivity();
            }
        });
        timeLimit.start();

        setQuizLayout();

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
        intent.setClass(RememberCardsActivity.this, CourseResultActivity.class);

        intent.putExtra("course", Course.REMEMBER_CARDS.name());

        intent.putExtra("num-solved", game.getNumSolved());
        intent.putExtra("num-failed", game.getNumFailed());

        intent.putExtra("result", 4.5); //TODO 평점 계산하는 코드 필요

        startActivity(intent);
        finish();
    }

    //점수에 따른 카드개수 123-2, 456-3, 789-4, 101112-5 13이상-6
    private void setQuizLayout(){

        if(game.getNumSolved()<13)
            game.setCardNum(game.getNumSolved()/3+2);
        else
            game.setCardNum(6);

        if(game.getCardNum()==2){
            selected[0][1]=true;
            selected[0][2]=true;
        }
        else if(game.getCardNum()==3){
            selected[0][1]=true;
            selected[0][2]=true;
            selected[1][1]=true;
        }
        else if(game.getCardNum()==4){
            selected[0][1]=true;
            selected[0][2]=true;
            selected[1][1]=true;
            selected[1][2]=true;
        }
        else if(game.getCardNum()==5){
            selected[0][1]=true;
            selected[0][2]=true;
            selected[1][1]=true;
            selected[1][2]=true;
            int x=random.nextInt(2);
            int y=random.nextInt(2);
            if(y==1)
                selected[x][3]=true;
            else
                selected[x][0]=true;
        }
        else{
            selected[0][1]=true;
            selected[0][2]=true;
            selected[0][3]=true;
            selected[1][1]=true;
            selected[1][2]=true;
            selected[1][3]=true;
        }
        txtscore.setText("Solved " + game.getNumSolved() + ", failed " + game.getNumFailed());
        choiceFruit();

        //정답 선택시 점수 올리기
    }
    //개수에 맞는 과일 랜덤 선택->개수 따른 카드 위치에 과일 res 배치
    private void choiceFruit(){
        int ans=random.nextInt(game.getCardNum());
        resultAns=random.nextInt(4);
        int idx=0;
        //기억해야하는 카드 생성
        for(int i=0;i<game.getCardNum();i++){
            for(int j=0;j<2;j++){
                for(int k=0;k<4;k++){
                  if(visited[j][k]==false&&selected[j][k]==true) {
                       //정답인 경우
                        if(idx==ans){
                            int fnumber=random.nextInt(12);
                            vFruit=fnumber;
                            String id = "fruit_" + String.valueOf(fnumber);
                            int ID = getResources().getIdentifier(id, "drawable", getPackageName());
                            game.setAnsResource(ID);
                            cards[j][k].setImageResource(ID);
                            cards[j][k].setVisibility(View.VISIBLE);
                            game.setAnsID(cards[j][k].getId());
                            ans_x=k;
                            ans_y=j;
                            results[resultAns].setBackgroundResource(ID);
                            results[resultAns].setVisibility(View.INVISIBLE);
                            visited[j][k] = true;
                        }
                        //정답 아닌 경우
                        else{
                            String id = "fruit_" + String.valueOf(random.nextInt(12));
                            int ID = getResources().getIdentifier(id, "drawable", getPackageName());
                            cards[j][k].setVisibility(View.VISIBLE);
                            cards[j][k].setImageResource(ID);
                            visited[j][k] = true;

                        }
                        idx++;
                    }
                }
            }
        }
        //선택지 생성
        for(int i=0;i<4;i++){
            if(i!=resultAns)
            {
                int chFruit=random.nextInt(12);
                while(chFruit==vFruit){
                    chFruit=random.nextInt(12);
                }
                String id = "fruit_" + String.valueOf(chFruit);
                int ID = getResources().getIdentifier(id, "drawable", getPackageName());
                results[i].setBackgroundResource(ID);
                results[i].setVisibility(View.INVISIBLE);
            }
        }
    }
    //시작 버튼을 누르면 랜덤하게 과일 하나 물음표 정답 카드가 있는 선택지 보이기
    public void startGame(View v){
        cards[ans_y][ans_x].setImageResource(R.drawable.blank);
        for(int i=0;i<4;i++){
                results[i].setVisibility(View.VISIBLE);
        }
    }

    //정답시 점수 올리고 reset
    public void checkAnswer(int id){
        //Toast.makeText(getApplicationContext(), resultAns+","+id,Toast.LENGTH_SHORT).show();
        if(resultAns==id)
            game.solveCurrentQuiz();
        else
            game.failCurrentQuiz();

        for(int i=0;i<2;i++){
            for(int j=0;j<4;j++){
                cards[i][j].setVisibility(View.INVISIBLE);
                visited[i][j]=false;
                selected[i][j]=false;

            }
        }
        for(int i=0;i<4;i++){
            results[i].setVisibility(View.INVISIBLE);
        }
        setQuizLayout();
    }

}
