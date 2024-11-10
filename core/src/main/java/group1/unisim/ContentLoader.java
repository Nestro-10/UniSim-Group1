package group1.unisim;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;

public class ContentLoader {
    public static ContentLoader singleton;
    private HashMap<String, Building> buildings;
    private HashMap<String, Thought> thoughts;

    public ContentLoader() {
        singleton = this;
    }

    public void Load() {
        Json json = new Json();

        try {
            thoughts = json.fromJson(HashMap.class, Thought.class, Gdx.files.internal("thoughts.json"));
        } catch (Exception e) {Gdx.app.error("LoadThoughts", e.getMessage());}

        try {
            buildings = json.fromJson(HashMap.class, Building.class, Gdx.files.internal("buildings.json"));
        } catch (Exception e) {Gdx.app.error("LoadBuildings", e.toString());}
    }

    public Thought getThought(String key) {
        return thoughts.get(key);
    }

    public Building getBuilding(String key) {
        return buildings.get(key);
    }
}
