package group1.unisim;


import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.*;

public class SatisfactionBar {

    private float satisfactionScore;
    private float target;
    private float speed = 0.03f;
    private Map<String, Thought> thoughts;

    public SatisfactionBar(){
        resetScore();
        thoughts = new HashMap<>();
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

    public void setThought(String key, Thought thought){
        thoughts.put(key, thought);
        calculateTarget();
    }

    public Thought getThought(String key){
        return thoughts.get(key);
    }

    public void removeThought(String key){
        thoughts.remove(key);
        calculateTarget();
    }

    private void calculateTarget(){
        target = 50;

        for (String key : thoughts.keySet()) {
            target += thoughts.get(key).modification;
        }

        target = max(min(target, 0), 100);
    }
}
