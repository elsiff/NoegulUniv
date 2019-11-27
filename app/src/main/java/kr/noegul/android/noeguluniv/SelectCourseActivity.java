package kr.noegul.android.noeguluniv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import kr.noegul.android.noeguluniv.course.comparecoins.CompareCoinsActivity;
import kr.noegul.android.noeguluniv.course.compareweights.CompareWeightsActivity;
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
            case R.id.compare_weights_course_button:
                activityClass = CompareWeightsActivity.class;
                break;
            default:
                throw new IllegalStateException("Invalid course button");
        }

        Intent intent = new Intent(this, activityClass);
        startActivityForResult(intent,1);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1){
            if(resultCode==-1){
                Toast.makeText(this.getApplicationContext(),"22",Toast.LENGTH_SHORT).show();
                String Course=data.getStringExtra("course");
                String Result=data.getStringExtra("result");
                Toast.makeText(this.getApplicationContext(),Course+" "+Result,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
