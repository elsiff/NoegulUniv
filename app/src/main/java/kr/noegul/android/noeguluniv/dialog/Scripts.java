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
                .putSpeech("뇌굴교수", "크흠흠")
                .putSpeech("뇌굴교수", "새내기 여러분\n뇌굴 대학에 오신것을 환영해요")
                .putSpeech("뇌굴교수", "뇌굴 새내기 여러분들은 뇌굴 대학에 입학하기 위해")
                .putSpeech("뇌굴교수", "저어기 뭐냐... 그..\n입학 시험이란 걸 봐야합니다")
                .putSpeech("뇌굴교수", "갑작스런 시험이지만 그래도 저는 여러분들이 잘 풀어줄 것이라 믿습니다")
                .putSpeech("뇌굴교수", "왜냐면 여러분은..\n지금 '개 강'하니깐!")
                .putSpeech("뇌굴교수", "ㅋㅋㅋ")
                .putSpeech("뇌굴교수", "그럼 시험을 시작하겠습니다\n제한시간은 강의 당 1분이고 조기 퇴실은 없습니다")
                .putSpeech("뇌굴교수", "성적에는 안 들어가지만 열심히 풀도록 하세요")
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
