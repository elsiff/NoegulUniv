package kr.noegul.android.noeguluniv.course.remembercards;

import java.util.Random;

public class RememberCards {
    private static final Random random = new Random();
    private int numSolved;
    private int numFailed;
    private int cardNum;
    private int ansResource;
    private int ansID;

    public int getAnsID() {
        return ansID;
    }

    public void setAnsID(int ansID) {
        this.ansID = ansID;
    }

    public RememberCards() {
        numSolved = 0;
        numFailed = 0;
    }


    public int getCardNum(){
        return cardNum;
    }
    public void setCardNum(int cd){
        cardNum=cd;
    }
    public void setAnsResource(int res){
        ansResource=res;
    }
    public int getAnsResource(){return ansResource;}
    public void solveCurrentQuiz() {
        numSolved++;
    }

    public void failCurrentQuiz() {
        numFailed++;
    }
    public int getNumSolved() {
        return numSolved;
    }

    public int getNumFailed() {
        return numFailed;
    }
}
