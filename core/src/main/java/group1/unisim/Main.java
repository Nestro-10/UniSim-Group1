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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main extends ApplicationAdapter {
    private Stage stage;
    private SpriteBatch batch;

    private ArrayList<Building> placedBuildings;
    private ArrayList<BuildingSlot> availableSlots;

    // Textures
    private Texture mapTexture, toolbarTexture, settingsTexture, buildIconTexture;
    private Texture accomodationTexture, cafeTexture, libraryTexture;

    // UI Components
    private ImageButton buildButton;
    private boolean areBuildingsVisible = false; // Tracks the visibility of building options
    private Building selectedBuilding;

    // Timer variables
    private float gameTimer = 300;
    private boolean isPaused = true;
    private Label gameTimeText;
    private float updateTimer;
    private final float updateTime = 1 / 30f; // 30 updates/second

    // UI Elements
    private SatisfactionBar satisfactionBar;
    private Skin skin;

    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(stage);

        // Load textures
        mapTexture = new Texture("mapTexture.png");
        toolbarTexture = new Texture("toolbar.png");
        settingsTexture = new Texture("settingsIcon.png");
        buildIconTexture = new Texture("buildIcon.png");
        accomodationTexture = new Texture("accom.png");
        cafeTexture = new Texture("cafe.png");
        libraryTexture = new Texture("library.png");

        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        placedBuildings = new ArrayList<>();
        availableSlots = new ArrayList<>();

        // Setup UI elements
        setupUI();

        // Create building buttons
        createBuildingButtons();

        // Create build button to toggle building options visibility
        setupBuildButton();

        // Create available building slots
        createBuildingSlots();
    }

    private void setupUI() {
        // Timer label
        gameTimeText = new Label("5:00", skin);
        gameTimeText.setPosition(400, 735);
        gameTimeText.setFontScale(4);
        stage.addActor(gameTimeText);

        // Satisfaction bar
        satisfactionBar = new SatisfactionBar(skin, stage);
    }

    private void setupBuildButton() {
        // Initialize build button to toggle visibility of building options
        buildButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(buildIconTexture)));
        buildButton.setPosition(70, 728);
        buildButton.setTransform(true);
        buildButton.setScale(2.5f);

        // Add click listener to toggle building options visibility
        buildButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                toggleBuildingOptions();
            }
        });

        stage.addActor(buildButton);
    }

    private void toggleBuildingOptions() {
        if (areBuildingsVisible) {
            hideBuildingOptions();
        } else {
            showBuildingOptions();
        }
        areBuildingsVisible = !areBuildingsVisible; // Toggle visibility state
    }

    private void showBuildingOptions() {
        for (Building building : placedBuildings) {
            building.addToStage(stage);
        }
    }

    private void hideBuildingOptions() {
        for (Building building : placedBuildings) {
            building.removeFromStage();
        }
    }

    private void createBuildingButtons() {
        // Accommodation Button
        Building accommodationBuilding = new Building("Accommodation", accomodationTexture, 70, 70);
        accommodationBuilding.setPosition(130, 720);
        accommodationBuilding.addClickListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clearAvailableSlots();
                selectedBuilding = accommodationBuilding;
                showAvailableSlots();
            }
        });
        placedBuildings.add(accommodationBuilding);

        // Cafe Button
        Building cafeBuilding = new Building("Cafe", cafeTexture, 35, 35);
        cafeBuilding.setPosition(200, 735);
        cafeBuilding.addClickListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clearAvailableSlots();
                selectedBuilding = cafeBuilding;
                showAvailableSlots();
            }
        });
        placedBuildings.add(cafeBuilding);

        // Library Button
        Building libraryBuilding = new Building("Library", libraryTexture, 70, 70);
        libraryBuilding.setPosition(240, 720);
        libraryBuilding.addClickListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clearAvailableSlots();
                selectedBuilding = libraryBuilding;
                showAvailableSlots();
            }
        });
        placedBuildings.add(libraryBuilding);
    }

    private void createBuildingSlots() {
        int[][] slotPositions = { {100, 200}, {200, 250}, {300, 300}, {400, 350} };
        for (int[] pos : slotPositions) {
            BuildingSlot slot = new BuildingSlot(accomodationTexture, pos[0], pos[1], 70, 70);
            slot.addClickListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (selectedBuilding != null) {
                        placeBuildingInSlot(slot);
                    }
                }
            });
            availableSlots.add(slot);
        }
    }

    private void showAvailableSlots() {
        for (BuildingSlot slot : availableSlots) {
            slot.addToStage(stage);
        }
    }

    private void clearAvailableSlots() {
        for (BuildingSlot slot : availableSlots) {
            slot.removeFromStage();
        }
    }

    private void placeBuildingInSlot(BuildingSlot slot) {
        selectedBuilding.setPosition(slot.getPosition().x, slot.getPosition().y);
        selectedBuilding.addToStage(stage);
        placedBuildings.add(selectedBuilding);
        selectedBuilding = null; // Clear selected building after placing
        clearAvailableSlots();
    }

    private void updateGameTimeText() {
        int seconds = (int) gameTimer;
        gameTimeText.setText(String.format("%d:%02d", (seconds / 60), (seconds % 60)));
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        updateTimer += deltaTime;
        if (!isPaused) {
            gameTimer -= deltaTime;
            updateGameTimeText();
        }

        while (updateTimer > updateTime) { // in case of a long freeze, do multiple updates
            update();
            updateTimer -= updateTime;
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(mapTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(toolbarTexture, 0, 720, Gdx.graphics.getWidth(), 80);
        batch.draw(settingsTexture, 0, 720, 70, 70);

        batch.end();

        stage.act(deltaTime);
        stage.draw();
    }

    private void update() {
        if (!isPaused) {
            satisfactionBar.updateScore();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        skin.dispose();
        mapTexture.dispose();
        toolbarTexture.dispose();
        settingsTexture.dispose();
        buildIconTexture.dispose();
        accomodationTexture.dispose();
        cafeTexture.dispose();
        libraryTexture.dispose();
        for (Building building : placedBuildings) {
            building.dispose();
        }
    }
}
