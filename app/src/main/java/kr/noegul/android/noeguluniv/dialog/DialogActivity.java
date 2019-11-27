package kr.noegul.android.noeguluniv.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import kr.noegul.android.noeguluniv.R;

public class DialogActivity extends AppCompatActivity {
    private Dialog dialog;
    private TextView speakerText;
    private TextView messageText;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        int scriptNum = getIntent().getIntExtra("script-num", -1);

        Script script = Scripts.findScript(scriptNum);
        this.dialog = new Dialog(script);

        this.speakerText = findViewById(R.id.speaker_text);
        this.messageText = findViewById(R.id.message_text);
        this.nextButton = findViewById(R.id.next_button);

        setupLayout(dialog.getCurrentSpeech(), dialog);
    }

    public void onClickNextButton(View view) {
        if (dialog.hasEnded()) {
            dialog.getScript().getOnDialogEndListener().onDialogEnd(this);
            finish();
        } else {
            Script.Speech speech = dialog.nextSpeech();
            setupLayout(speech, dialog);
        }
    }

    private void setupLayout(Script.Speech speech, Dialog dialog) {
        if (dialog.hasEnded()) {
            nextButton.setText("확인");
        } else {
            nextButton.setText("다음");
        }

        speakerText.setText(speech.getSpeaker());
        messageText.setText(speech.getMessage());
    }
}
