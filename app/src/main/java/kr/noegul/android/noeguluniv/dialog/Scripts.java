package kr.noegul.android.noeguluniv.dialog;

import java.util.HashMap;
import java.util.Map;

import kr.noegul.android.noeguluniv.NeogulUnivApp;
import kr.noegul.android.noeguluniv.player.PlayerData;

public class Scripts {
    public static final int TUTORIAL = 0;
    private static final Map<Integer, Script> scriptMap = new HashMap<>();

    private Scripts() {
    }

    static {
        scriptMap.put(TUTORIAL, new Script.Builder()
                .putSpeech("뇌굴교수", "안녕")
                .putSpeech("뇌굴교수", "이건 튜토리얼이라고 해")
                .putSpeech("뇌굴교수", "메시지가 길면 어떻게 되나 테스트도 한 번 해보는 거고 김수한무 거북이와 두루미")
                .putSpeech("뇌굴교수", "메시지에\n개행을\n넣으면\n어떻게\n되나도\n보는거야")
                .putSpeech("뇌굴교수", "끝!")
                .setOnDialogEndHandler(new Runnable() {
                    @Override
                    public void run() {
                        PlayerData playerData = NeogulUnivApp.getInstance().getPlayerData();
                        playerData.setPlayedTutorial(true);
                    }
                })
                .build());
    }

    public static Script findScript(int scriptNum) {
        if (!scriptMap.containsKey(scriptNum))
            throw new IllegalStateException("Invalid script number");

        return scriptMap.get(scriptNum);
    }
}
