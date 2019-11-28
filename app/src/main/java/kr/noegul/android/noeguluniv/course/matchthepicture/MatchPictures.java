package kr.noegul.android.noeguluniv.course.matchthepicture;

import android.graphics.Picture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MatchPictures {
    private static final String[] PICTURES = {"apple", "banana","cherry","orange","peach","strawberry","watermelon","grape","kiwi","lemon","orientallemon"};
    private static final Random random = new Random();
    private Quiz currentQuiz;
    private int numSolved;
    private int numFailed;

    public MatchPictures() {
        currentQuiz = generateQuiz(numSolved);
        numSolved = 0;
        numFailed = 0;
    }

    public void solveCurrentQuiz() {
        numSolved++;
        currentQuiz = generateQuiz(numSolved);
    }

    public void failCurrentQuiz() {
        numFailed++;
        currentQuiz = generateQuiz(numSolved);
    }

    public Quiz getCurrentQuiz() {
        return currentQuiz;
    }

    public int getNumSolved() {
        return numSolved;
    }

    public int getNumFailed() {
        return numFailed;
    }

    private Quiz generateQuiz(int numSolved) {
        List<String> pictures_name_list = new ArrayList<>();

        int numPictures=8;
        if(numSolved>10) numPictures=10;

        int numAnswerPicture=random.nextInt(8);//답 하나 설정
        String answerPictureValue=PICTURES[numAnswerPicture];

        pictures_name_list.add(answerPictureValue);
        pictures_name_list.add(answerPictureValue);//2개만 정답 쌍으로 들어감

        for (int i = 0; i < numPictures-2; i++) {
            String pictureValue= PICTURES[random.nextInt(PICTURES.length)];//하나를 뽑기
            while(pictures_name_list.contains(pictureValue))//정답과 같거나 이미 뽑은 거면 다시 뽑기
                pictureValue= PICTURES[random.nextInt(PICTURES.length)];
            pictures_name_list.add(pictureValue);
        }

        List<Picture> pictures=new ArrayList<>();
        for(int i=0;i<pictures_name_list.size();i++){
            Picture picture=new Picture(pictures_name_list.get(i));
            pictures.add(picture);
        }
        Collections.shuffle(pictures);//순서 섞어서 보여주기 위함

        return new Quiz(pictures,answerPictureValue);
    }

    public class Quiz {
        private List<Picture> pictures;
        private final String answerPictureValue;

        public Quiz(List<Picture> pictures, String answerPictureValue) {
            this.pictures = pictures;
            this.answerPictureValue=answerPictureValue;
        }

        public List<Picture> getPictures() {
            return pictures;
        }

        public String getAnswer() {
            return answerPictureValue;
        }
    }

    public class Picture {
        private final String value;

        public Picture(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
