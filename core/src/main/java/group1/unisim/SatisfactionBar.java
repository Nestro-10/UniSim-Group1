package group1.unisim;


import static java.lang.Math.abs;

public class SatisfactionBar {

    private float satisfactionScore;
    private float target;
    private float speed = 0.03f;

    public SatisfactionBar(){
        resetScore();
    }

    private void resetScore(){
        satisfactionScore = 50;
    }

    public float getScore(){
        return satisfactionScore;
    }

    public void updateScore(){
        float difference = target - satisfactionScore;
        if (abs(difference) < speed) satisfactionScore = target;
        else {
            float direction = (difference < 0) ? -1 : 1;
            satisfactionScore += speed * direction;
        }
    }
}
