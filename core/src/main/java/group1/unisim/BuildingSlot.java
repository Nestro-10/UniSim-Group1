package group1.unisim;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class BuildingSlot {
    private Vector2 position;
    private ImageButton slotButton;
    private Texture texture;

    public BuildingSlot(Texture slotTexture, float x, float y, float width, float height) {
        this.position = new Vector2(x, y); // Store the position
        this.texture = slotTexture;

        // Initialize the slot as an ImageButton with translucent color
        slotButton = new ImageButton(new TextureRegionDrawable(slotTexture));
        slotButton.setSize(width, height);
        slotButton.setPosition(x, y);
        slotButton.setColor(0, 0, 1, 0.4f); // Translucent blue color for slot
    }

    // Method to add slot button to stage
    public void addToStage(Stage stage) {
        stage.addActor(slotButton);
    }

    // Method to remove slot button from stage
    public void removeFromStage() {
        slotButton.remove();
    }

    public Vector2 getPosition() {
        return position;
    }

    public void addClickListener(ClickListener listener) {
        slotButton.addListener(listener);
    }

    public void dispose() {
        texture.dispose();
    }
}
