package kr.noegul.android.noeguluniv.dialog;

import android.content.Context;
import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

import kr.noegul.android.noeguluniv.NeogulUnivApp;
import kr.noegul.android.noeguluniv.course.comparecoins.CompareCoinsActivity;
import kr.noegul.android.noeguluniv.course.countblocks.CountBlocksActivity;
import kr.noegul.android.noeguluniv.course.matchthepicture.MatchThePictureActivity;
import kr.noegul.android.noeguluniv.player.PlayerData;

public class Scripts {
    public static final int TUTORIAL = 0;
    public static final int START_COMPARE_COINS = 1;
    public static final int START_MATCH_THE_PICTURE = 2;
    public static final int START_COUNT_BLOCKS = 3;
    public static final int START_REMEMBER_CARDS = 4;
    private static final Map<Integer, Script> scriptMap = new HashMap<>();

    private Scripts() {
    }

    static {
        scriptMap.put(TUTORIAL, new Script.Builder()
                .putSpeech("뇌굴교수", "크흠흠")
                .putSpeech("뇌굴교수", "새내기 여러분\n" +
                        "뇌굴 대학에 오신것을 환영해요")
                .putSpeech("뇌굴교수", "뇌굴 새내기 여러분들은 뇌굴 대학에 입학하기 위해")
                .putSpeech("뇌굴교수", "저어기 뭐냐... 그..\n" +
                        "입학 시험이란 걸 봐야합니다")
                .putSpeech("뇌굴교수", "갑작스런 시험이지만 그래도 저는 여러분들이 잘 풀어줄 것이라 믿습니다")
                .putSpeech("뇌굴교수", "왜냐면 여러분은..\n" +
                        "지금 '개 강'하니깐!")
                .putSpeech("뇌굴교수", "껄-껄 !")
                .putSpeech("뇌굴교수", "그럼 시험을 시작하겠습니다\n" +
                        "제한시간은 강의 당 1분이고 조기 퇴실은 없습니다")
                .putSpeech("뇌굴교수", "성적에는 안 들어가지만 열심히 풀도록 하세요")
                .setOnDialogEndHandler(new Runnable() {
                    @Override
                    public void run() {
                        PlayerData playerData = NeogulUnivApp.getInstance().getPlayerData();
                        playerData.setPlayedTutorial(true);
                    }
                })
                .build());

        scriptMap.put(START_COMPARE_COINS, new Script.Builder()
                .putSpeech("뇌굴교수", "동전 비교는 두 선택지 중 더 큰 금액을 갖는 쪽을 택하는 수업이겠어요.\n" +
                        "제한시간은 1분. 결'코 인'기있는 과목은 아니지만 힘내들. 껄-껄!")
                .setOnDialogEndHandler(new Runnable() {
                    @Override
                    public void run() {
                        Context context = NeogulUnivApp.getContext();
                        Intent intent = new Intent(context, CompareCoinsActivity.class);
                        context.startActivity(intent);
                    }
                })
                .build());

        scriptMap.put(START_MATCH_THE_PICTURE, new Script.Builder()
                .putSpeech("뇌굴교수", "같은 그림 찾기는 여러 개의 과일 그림 중 같은 거 두 개 골라내는 수업이에요.\n" +
                        "제한시간은 1분. 꽤나 헷갈릴 '수 박'에 없을 껄? 껄-껄 !")
                .setOnDialogEndHandler(new Runnable() {
                    @Override
                    public void run() {
                        Context context = NeogulUnivApp.getContext();
                        Intent intent = new Intent(context, MatchThePictureActivity.class);
                        context.startActivity(intent);
                    }
                })
                .build());

        scriptMap.put(START_COUNT_BLOCKS, new Script.Builder()
                .putSpeech("뇌굴교수", "블록 세기 정도는 알지? 나이가 몇인데. 블록이 몇 갠지 세면 돼.\n" +
                        "제한시간은 1분. 그나저나 자꾸 과일만 봤더니, 뭘 안 먹어도 배'블록'.. 껄-껄 !")
                .setOnDialogEndHandler(new Runnable() {
                    @Override
                    public void run() {
                        Context context = NeogulUnivApp.getContext();
                        Intent intent = new Intent(context, CountBlocksActivity.class);
                        context.startActivity(intent);
                    }
                })
                .build());

        scriptMap.put(START_REMEMBER_CARDS, new Script.Builder()
                .putSpeech("뇌굴교수", "이번 수업에선 주어진 과일 카드들을 기억해 맞추기만 하면 됩니다.\n" +
                        "제한시간은 1분. 이'참외' 기억력을 시험해봐요~ 껄-껄 !")
                .setOnDialogEndHandler(new Runnable() {
                    @Override
                    public void run() {
                        Context context = NeogulUnivApp.getContext();
                        Intent intent = new Intent(context, CountBlocksActivity.class);
                        context.startActivity(intent);
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
