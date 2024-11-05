package group1.unisim;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.*;

public class SatisfactionBar {

    private float satisfactionScore;
    private float target;
    private float speed = 0.03f;
    private Map<String, Thought> thoughts;
    private ShapeRenderer background;
    private ShapeRenderer fill;

    public SatisfactionBar(){
        background = new ShapeRenderer();
        fill = new ShapeRenderer();
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

    public void render(){
        //Draw satisfactionBar Rectangle
        background.begin(ShapeRenderer.ShapeType.Filled);
        background.setColor(Color.GRAY);
        background.rect(775, 735, 200, 50);

        //Initialise score bar

        fill.begin(ShapeRenderer.ShapeType.Filled);
        if (satisfactionScore < 31){
            fill.setColor(Color.RED);
        }
        else if (satisfactionScore < 61){
            fill.setColor(Color.YELLOW);
        }
        else{
            fill.setColor(Color.GREEN);
        }
        fill.rect(775, 735, (satisfactionScore) * 2, 50);

        background.end();
        fill.end();
    }

    public void dispose(){
        background.dispose();
        fill.dispose();
    }
}
