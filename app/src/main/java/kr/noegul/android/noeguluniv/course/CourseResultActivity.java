package kr.noegul.android.noeguluniv.course;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import kr.noegul.android.noeguluniv.R;

public class CourseResultActivity extends AppCompatActivity {
    private Intent intent;
    private CourseResult result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_result);

        intent = getIntent();
        int numSolved = intent.getIntExtra("num-solved", 0);
        int numFailed = intent.getIntExtra("num-failed", 0);
        result = (CourseResult) intent.getSerializableExtra("result");

        TextView resultText = findViewById(R.id.result_text);
        resultText.setText("맞은 개수: " + numSolved + "\n" +
                "틀린 개수: " + numFailed + "\n" +
                "최종 성적: " + result.getTitle());
    }

    public void onClickConfirmButton(View view) {
        intent =new Intent();
        intent.putExtra("course","4");
        intent.putExtra("result",result);
        setResult(RESULT_OK,intent);
        finish();
    }
}
