package kr.noegul.android.noeguluniv.dialog;

import android.app.Activity;
import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

import kr.noegul.android.noeguluniv.NeogulUnivApp;
import kr.noegul.android.noeguluniv.course.Course;
import kr.noegul.android.noeguluniv.course.comparecoins.CompareCoinsActivity;
import kr.noegul.android.noeguluniv.course.countblocks.CountBlocksActivity;
import kr.noegul.android.noeguluniv.course.matchthepicture.MatchThePictureActivity;
import kr.noegul.android.noeguluniv.course.remembercards.RememberCardsActivity;
import kr.noegul.android.noeguluniv.player.PlayerData;

public class Scripts {
    public static final int TUTORIAL = 0;
    public static final int FINISH_TUTORIAL = 1;
    public static final int START_COMPARE_COINS = 2;
    public static final int START_MATCH_THE_PICTURE = 3;
    public static final int START_COUNT_BLOCKS = 4;
    public static final int START_REMEMBER_CARDS = 5;
    public static final int START_GRADUATE_EXAM = 6;
    public static final int FAIL_GRADUATE_EXAM = 7;
    public static final int PASS_GRADUATE_EXAM = 8;
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
                .putSpeech("뇌굴교수", "껄-껄")
                .putSpeech("뇌굴교수", "그럼 시험을 시작하겠습니다\n" +
                        "제한시간은 강의 당 1분이고 조기 퇴실은 없습니다")
                .putSpeech("뇌굴교수", "성적에는 안 들어가지만 열심히 풀도록 하세요")
                .setOnDialogEndListener(new OnDialogEndListener() {
                    @Override
                    public void onDialogEnd(Activity activity) {
                        NeogulUnivApp game = NeogulUnivApp.getInstance();
                        game.startGraduateExam();

                        Intent intent = new Intent(activity, DialogActivity.class);

                        int scriptNum = getCourseScriptNum(game.getOngoingExam().getCurrentCourse());
                        intent.putExtra("script-num", scriptNum);
                        activity.startActivity(intent);
                    }
                })
                .build());

        scriptMap.put(FINISH_TUTORIAL, new Script.Builder()
                .putSpeech("뇌굴교수", "뭐 대충 어땠나요들")
                .putSpeech("뇌굴교수", "어려웠다면 정상입니다")
                .putSpeech("뇌굴교수", "방금 본 시험이 여러분들이 앞으로 공부하게 될 과목들이에요")
                .putSpeech("뇌굴교수", "이제 각 강의를 C+ 이상 성적으로 마치는게 여러분의 목표입니다")
                .putSpeech("뇌굴교수", "그 다음은 마지막 관문인 졸업 시험이 남아있지요")
                .putSpeech("뇌굴교수", "입학 시험과 똑같은 형식인데, B+ 이상만 나오면 졸업입니다")
                .putSpeech("뇌굴교수", "물론 쉽진 않겠지만.")
                .putSpeech("뇌굴교수", "잘못하면 학교에서 몇십 년 동안 썩을 수도 있을거에요")
                .putSpeech("뇌굴교수", "껄-껄")
                .putSpeech("뇌굴교수", "그럼 다들 행운을 빌어요. 이만")
                .setOnDialogEndListener(new OnDialogEndListener() {
                    @Override
                    public void onDialogEnd(Activity activity) {
                        PlayerData playerData = NeogulUnivApp.getInstance().getPlayerData();
                        playerData.setPlayedTutorial(true);
                    }
                })
                .build());

        scriptMap.put(START_COMPARE_COINS, new Script.Builder()
                .putSpeech("뇌굴교수", "이번 '동전 비교'는 두 선택지 중 더 큰 금액을 갖는 쪽을 택하는 수업이겠어요.")
                .putSpeech("뇌굴교수", "제한시간은 1분.\n결'코 인'기있는 과목은 아니지만 힘내들. 껄-껄")
                .setOnDialogEndListener(new OnDialogEndListener() {
                    @Override
                    public void onDialogEnd(Activity activity) {
                        Intent intent = new Intent(activity, CompareCoinsActivity.class);
                        activity.startActivity(intent);
                    }
                })
                .build());

        scriptMap.put(START_MATCH_THE_PICTURE, new Script.Builder()
                .putSpeech("뇌굴교수", "이번 '같은 그림 찾기'는 여러 개의 과일 그림 중 같은 거 두 개 골라내는 수업이에요.")
                .putSpeech("뇌굴교수", "제한시간은 1분.\n꽤나 헷갈릴 '수 박'에 없을 껄? 껄-껄")
                .setOnDialogEndListener(new OnDialogEndListener() {
                    @Override
                    public void onDialogEnd(Activity activity) {
                        Intent intent = new Intent(activity, MatchThePictureActivity.class);
                        activity.startActivity(intent);
                    }
                })
                .build());

        scriptMap.put(START_COUNT_BLOCKS, new Script.Builder()
                .putSpeech("뇌굴교수", "이번 수업은 '블록 세기'. 단순히 블록이 몇 갠지 세면 됩니다.")
                .putSpeech("뇌굴교수", "제한시간은 1분.\n그나저나 자꾸 과일만 봤더니, 뭘 안 먹어도 배'블록'.. 껄-껄")
                .setOnDialogEndListener(new OnDialogEndListener() {
                    @Override
                    public void onDialogEnd(Activity activity) {
                        Intent intent = new Intent(activity, CountBlocksActivity.class);
                        activity.startActivity(intent);
                    }
                })
                .build());

        scriptMap.put(START_REMEMBER_CARDS, new Script.Builder()
                .putSpeech("뇌굴교수", "이번 '카드 기억' 수업에선 주어진 과일 카드들을 기억해 맞추기만 하면 되겠어요.")
                .putSpeech("뇌굴교수", "제한시간은 1분.\n이'참외' 기억력을 시험해봐요~ 껄-껄")
                .setOnDialogEndListener(new OnDialogEndListener() {
                    @Override
                    public void onDialogEnd(Activity activity) {
                        Intent intent = new Intent(activity, RememberCardsActivity.class);
                        activity.startActivity(intent);
                    }
                })
                .build());

        scriptMap.put(START_GRADUATE_EXAM, new Script.Builder()
                .putSpeech("뇌굴교수", "음, 뭐. 졸업을 하고싶다고?")
                .putSpeech("뇌굴교수", "어디보자..\n용캐 요건은 다 맞췄네")
                .putSpeech("뇌굴교수", "졸업 시험만 잘 보면 졸업 할 수 있겠네.\n준비는 됐지?")
                .putSpeech("뇌굴교수", "지금까지 치른 네 가지 시험을 무작위 순서로 보게 될거야")
                .putSpeech("뇌굴교수", "평균이 B+ 이상만 나온다면 합격")
                .putSpeech("뇌굴교수", "물론 보통은 그러질 못 해서 '그 강'을 건너게 되지..")
                .putSpeech("뇌굴교수", "'재수강'말이야.")
                .putSpeech("뇌굴교수", "껄-껄")
                .putSpeech("뇌굴교수", "그럼 시험 시작")
                .setOnDialogEndListener(new OnDialogEndListener() {
                    @Override
                    public void onDialogEnd(Activity activity) {
                        NeogulUnivApp game = NeogulUnivApp.getInstance();
                        game.startGraduateExam();

                        Intent intent = new Intent(activity, DialogActivity.class);

                        int scriptNum = getCourseScriptNum(game.getOngoingExam().getCurrentCourse());
                        intent.putExtra("script-num", scriptNum);
                        activity.startActivity(intent);
                    }
                })
                .build());

        scriptMap.put(FAIL_GRADUATE_EXAM, new Script.Builder()
                .putSpeech("뇌굴교수", "하하하")
                .putSpeech("뇌굴교수", "축하해. 미달 성적이야")
                .putSpeech("뇌굴교수", "ㅋㅋㅋㅋ~")
                .putSpeech("뇌굴교수", "그래도 조금만 노력하면 될 성적이구만")
                .putSpeech("뇌굴교수", "물론 긴장 풀진 말고.\n" +
                        "학교를 몇십 년 동안 다니고 있는 학생들이 꽤 많다고")
                .putSpeech("뇌굴교수", "자네도 그리 되기 싫으면 열심히 재수강하도록 해")
                .putSpeech("뇌굴교수", "수고")
                .setOnDialogEndListener(new OnDialogEndListener() {
                    @Override
                    public void onDialogEnd(Activity activity) {
                        /* Do nothing */
                    }
                })
                .build());

        scriptMap.put(PASS_GRADUATE_EXAM, new Script.Builder()
                .putSpeech("뇌굴교수", "뭐...")
                .putSpeech("뇌굴교수", "봐줄만한 성적이구만")
                .putSpeech("뇌굴교수", "옛다 졸업장")
                .putSpeech("뇌굴교수", "짧은 시간이었지만 이렇게 보내게 되니 눈물이 또 나려고 하네")
                .putSpeech("뇌굴교수", "롬곡 롬곡...")
                .putSpeech("뇌굴교수", "껄-껄")
                .putSpeech("뇌굴교수", "뭐. 이제 가봐")
                .putSpeech("", "플레이 해주셔서 감사합니다!")
                .setOnDialogEndListener(new OnDialogEndListener() {
                    @Override
                    public void onDialogEnd(Activity activity) {
                        /* Do nothing */
                    }
                })
                .build());
    }

    public static Script findScript(int scriptNum) {
        if (!scriptMap.containsKey(scriptNum))
            throw new IllegalStateException("Invalid script number");

        return scriptMap.get(scriptNum);
    }

    public static int getCourseScriptNum(Course course) {
        switch (course) {
            case COMPARE_COINS:
                return START_COMPARE_COINS;
            case MATCH_THE_PICTURE:
                return START_MATCH_THE_PICTURE;
            case COUNT_BLOCKS:
                return START_COUNT_BLOCKS;
            case REMEMBER_CARDS:
                return START_REMEMBER_CARDS;
            default:
                throw new IllegalArgumentException("Invalid course");
        }
    }
}
