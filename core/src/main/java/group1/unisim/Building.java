package group1.unisim;

import com.badlogic.gdx.graphics.Texture;

public class Building {

    public String name;
    public Service[] servicesProvided;
    public int constructionTime;
    public int size;
    public Texture sprite;

    public void setName(String buildingName){
        name = buildingName;
    }
    
    public void setSize(int buildingSize){
        size = buildingSize;
    }

    public void setTexture(Texture buildingTexture){
        sprite = buildingTexture;
    }

    public void render(){
        
    }
}
