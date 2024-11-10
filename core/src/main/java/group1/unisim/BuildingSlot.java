package group1.unisim;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class BuildingSlot {
    private int timeConstructing;
    private boolean isConstructingActive;
    private Vector2 position;
    private int maxSize;
    private Building building;
    private Image sprite;
    private Building previewing;
    private ImageButton button;

    public BuildingSlot(Vector2 pos, int maxSiz, Stage stage) {
        position = pos;
        maxSize = maxSiz;
        sprite = new Image(new TextureRegionDrawable(new TextureRegion(ContentLoader.singleton.getTexture("accom.png"))));
        sprite.setDrawable(null); // Image needs to be instantiated with a texture or setting it later won't work
        sprite.setScale(2f);
        sprite.setPosition(position.x, position.y);
        button = new ImageButton(new TextureRegionDrawable(new TextureRegion(ContentLoader.singleton.getTexture("accom.png"))));
        button.setColor(1, 1, 1, 0);
        button.setScale(2f);
        button.setPosition(position.x, position.y);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (previewing != null) {
                    Build(previewing);
                }
            }
        });
        stage.addActor(sprite);
        stage.addActor(button);
    }

    public boolean isConstructing(){
        return isConstructingActive;
    }

    public Vector2 getPosition(){
        return position;
    }

    public int getMaxSize(){
        return maxSize;
    }

    public Building getBuilding(){
        return building;
    }

    public void Update(){
        if (timeConstructing > 0){
            timeConstructing--;
        } else if (timeConstructing == 0) {
            isConstructingActive = false;
        }
    }

    public void Build(Building _building){
        clearPreview();
        timeConstructing = _building.getConstructionTime();
        building = _building;
        sprite.setDrawable(new TextureRegionDrawable(new TextureRegion(ContentLoader.singleton.getTexture(building.getSpriteName()))));
        isConstructingActive = true;
    }

    public void Upgrade(){

    }

    public void Demolish(){

    }

    public void setPreview(Building preview) {
        clearPreview();
        if (building != null) return;
        if (preview.getSize() > maxSize) return;

        sprite.setColor(1, 1, 1, 0.6f);
        sprite.setDrawable(new TextureRegionDrawable(new TextureRegion(ContentLoader.singleton.getTexture(preview.getSpriteName()))));
        previewing = preview;
    }

    public void clearPreview() {
        if (previewing == null) return;

        sprite.setColor(1, 1, 1, 1);
        sprite.setDrawable(null);
        previewing = null;
    }
}

