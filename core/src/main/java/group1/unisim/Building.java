package group1.unisim;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Building {
    private String name;
    private Texture sprite;
    private ImageButton buildingButton;

    public Building(String name, Texture texture, float width, float height) {
        this.name = name;
        this.sprite = texture;

        // Initialize the building as an ImageButton
        buildingButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(texture)));
        buildingButton.setSize(width, height);
    }

    public void setPosition(float x, float y) {
        buildingButton.setPosition(x, y);
    }

    public void addClickListener(ClickListener listener) {
        buildingButton.addListener(listener);
    }

    public void addToStage(Stage stage) {
        stage.addActor(buildingButton);
    }

    public void removeFromStage() {
        buildingButton.remove();
    }

    public void dispose() {
        sprite.dispose();
    }
}
