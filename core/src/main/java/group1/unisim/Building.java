package group1.unisim;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Building {

    public String name;
    public Service[] servicesProvided;
    public int constructionTime;
    public int size;
    public Texture sprite;

    public Building(){

    }

    public void setName(String buildingName){
        name = buildingName;
    }

    public void setCostructionTime(int timeConstruction){
        constructionTime = timeConstruction;
    }
    
    public void setSize(int buildingSize){
        size = buildingSize;
    }

    public void setTexture(Texture buildingTexture){
        sprite = buildingTexture;
    }

    public void render(String name, int constructionTime, int size, Texture sprite, SpriteBatch batch){
        setName(name);
        setCostructionTime(constructionTime);
        setSize(size);
        setTexture(sprite);
        
    }
}
