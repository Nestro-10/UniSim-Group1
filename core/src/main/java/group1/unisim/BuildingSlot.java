package group1.unisim;

import com.badlogic.gdx.math.Vector2;

public class BuildingSlot {
    public int timeConstructing;
    public boolean isConstructingActive;
    public Vector2 position;
    public int maxSize;

    public void setIsContructing(){
        isConstructingActive = false;
    }

    public boolean isConstructing(){
        return isConstructingActive;
    }

    public void setPosition(){

    }

    public Vector2 getPosition(){
        return position;
    }

    public void setMaxSize(){

    }

    public int getMaxSize(){
        return maxSize;
    }

    public void Update(){

    }

    public void Build(Building building){

    }

    public void Upgrade(){

    }
    
    public void Demolish(){

    }
}

