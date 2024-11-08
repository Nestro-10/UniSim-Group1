package group1.unisim;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.*;

public class SatisfactionBar extends ProgressBar {

    private float satisfactionScore;
    private float target;
    private final float baseValue = 50;
    private final float speed = 0.03f;
    private final Map<String, Thought> thoughts;

    public SatisfactionBar(Skin skin){
        super(0.0f, 100.0f, 0.1f, false, skin);
        setSize(200, 50);
        resetScore();
        thoughts = new HashMap<>();
    }

    private void resetScore(){
        satisfactionScore = baseValue;
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
        setValue(satisfactionScore);
        if (satisfactionScore < 31){
            setColor(Color.RED);
        }
        else if (satisfactionScore < 61){
            setColor(Color.YELLOW);
        }
        else{
            setColor(Color.GREEN);
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
        target = baseValue;

        for (String key : thoughts.keySet()) {
            target += thoughts.get(key).modification;
        }

        target = max(min(target, 0), 100);
    }
}
