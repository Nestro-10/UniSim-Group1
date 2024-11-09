package group1.unisim;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main extends ApplicationAdapter {
    private Stage stage;
    private SpriteBatch batch;

    private Texture toolbar, mapTexture, settingsTexture, buildIconTexture;
    private Texture accomodationTexture, cafe, library;

    private ImageButton buildButton;

    private ImageButton accomodationButton;
    private ImageButton cafeButton;
    private ImageButton libraryButton;

    private boolean showAccomodationSlots = false;
    private boolean showCafeSlots = false;
    private boolean showLibrarySlots = false;

    private SatisfactionBar satisfactionBar;
    private float updateTimer;
    private final float updateTime = 1 / 30f; // 30 updates per second

    private ArrayList<ImageButton> placedBuildings;
    private ArrayList<ImageButton> availableSlots;

    @Override
    public void create() {
        batch = new SpriteBatch();

        toolbar = new Texture("toolbar.png");
        mapTexture = new Texture("mapTexture.png");
        settingsTexture = new Texture("settingsIcon.png");
        buildIconTexture = new Texture("buildIcon.png");
        accomodationTexture = new Texture("accom.png");
        cafe = new Texture("cafe.png");
        library = new Texture("library.png");

        buildButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(buildIconTexture)));
        buildButton.setPosition(70, 728);
        buildButton.setTransform(true);
        buildButton.setScale(2.5f);

        buildButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (showAccomodationSlots || showCafeSlots || showLibrarySlots) {
                    hideAllSlots();  
                    clearAvailableSlots(); 
                } else {
                    toggleBuildingButtons();  
                }
            }
        });

        stage = new Stage(new ScreenViewport());
        stage.addActor(buildButton);
        Gdx.input.setInputProcessor(stage);

        accomodationButton = createBuildingButton(accomodationTexture, 130, 720, 70, 70, "accomodation");
        cafeButton = createBuildingButton(cafe, 200, 735, 35, 35, "cafe");
        libraryButton = createBuildingButton(library, 240, 720, 70, 70, "library");

        satisfactionBar = new SatisfactionBar();
        placedBuildings = new ArrayList<>();
        availableSlots = new ArrayList<>();
    }

    private void toggleBuildingButtons() {
        if (!stage.getActors().contains(accomodationButton, true)) {
            stage.addActor(accomodationButton);
            stage.addActor(cafeButton);
            stage.addActor(libraryButton);
        } else {
            accomodationButton.remove();
            cafeButton.remove();
            libraryButton.remove();
        }
    }

    private ImageButton createBuildingButton(Texture texture, float x, float y, float width, float height, String buildingType) {
        ImageButton button = new ImageButton(new TextureRegionDrawable(new TextureRegion(texture)));
        button.setPosition(x, y);
        button.setSize(width, height); 

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clearAvailableSlots(); 
                toggleBuildingSlots(buildingType);
            }
        });

        return button;
    }

    private void toggleBuildingSlots(String buildingType) {
        hideAllSlots(); 

        switch (buildingType) {
            case "accomodation":
                showAccomodationSlots = true;
                createAvailableSlots(accomodationTexture, 70, 70, "accomodation");
                break;
            case "cafe":
                showCafeSlots = true;
                createAvailableSlots(cafe, 35, 35, "cafe");
                break;
            case "library":
                showLibrarySlots = true;
                createAvailableSlots(library, 70, 70, "library");
                break;
        }
    }

    private void hideAllSlots() {
        showAccomodationSlots = false;
        showCafeSlots = false;
        showLibrarySlots = false;
    }

    private void clearAvailableSlots() {
        for (ImageButton slotButton : availableSlots) {
            slotButton.remove();
        }
        availableSlots.clear();
    }

    private void createAvailableSlots(Texture texture, float width, float height, String buildingType) {
        int[][] slotPositions = {
            {100, 200}, {200, 250}, {550, 200}, {600, 400}

        };

        for (int[] pos : slotPositions) {
            ImageButton slotButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(texture)));
            slotButton.setPosition(pos[0], pos[1]);
            slotButton.setSize(width, height);
            slotButton.setColor(0, 0, 1, 0.4f);  

            slotButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    placeBuilding(texture, pos[0], pos[1], width, height, buildingType);
                    slotButton.remove();  
                }
            });

            stage.addActor(slotButton);
            availableSlots.add(slotButton); 
        }
    }

    private void placeBuilding(Texture texture, float x, float y, float width, float height, String buildingType) {
        ImageButton buildingButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(texture)));
        buildingButton.setPosition(x, y);
        buildingButton.setSize(width, height);

        stage.addActor(buildingButton);
        placedBuildings.add(buildingButton);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        
        batch.draw(mapTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(toolbar, 0, 720, Gdx.graphics.getWidth(), 80);
        batch.draw(settingsTexture, 0, 720, 70, 70);

        batch.end();

        float deltaTime = Gdx.graphics.getDeltaTime();
        updateTimer += deltaTime;
        while (updateTimer > updateTime) {
            update();
            updateTimer -= updateTime;
        }

        satisfactionBar.render();

        stage.act(deltaTime);
        stage.draw();
    }

    private void update() {
        satisfactionBar.updateScore();
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        mapTexture.dispose();
        toolbar.dispose();
        accomodationTexture.dispose();
        settingsTexture.dispose();
        buildIconTexture.dispose();
        cafe.dispose();
        library.dispose();
        satisfactionBar.dispose();
    }
}
