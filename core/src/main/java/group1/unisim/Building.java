package group1.unisim;

import com.badlogic.gdx.graphics.Texture;

public class Building {

    private String name;
    private Service[] servicesProvided;
    private int constructionTime;
    private int size;
    private String sprite;

    public Building() {}

    public Building(String buildingName, Service[] services, int timeConstruction, int buildingSize, String buildingTexture){
        name = buildingName;
        servicesProvided = services;
        constructionTime = timeConstruction;
        size = buildingSize;
        sprite = buildingTexture;
    }

    public String getName() {
        return name;
    }

    public Service[] getServicesProvided() {
        return servicesProvided;
    }

    public int getConstructionTime() {
        return constructionTime;
    }

    public int getSize() {
        return size;
    }

    public String getSprite() {
        return sprite;
    }
}
