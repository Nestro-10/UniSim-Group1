package group1.unisim;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private ContentLoader contentLoader;

    private SpriteBatch batch;

    private Texture toolbar, mapTexture, settingsTexture, buildIconTexture, pauseTexture, playTexture;

    private SatisfactionBar satisfactionBar;
    private float updateTimer;
    private final float updateTime = 1/30f; // 30 updates/second
    private boolean isPaused = true;
    private float gameTimer = 300;

    private Stage stage;

    //UI
    private Stage ui;
    private Label gameTimeText;

    private ImageButton buildButton;
    private ScrollPane buildSelect;
    private ArrayList<BuildingSlot> buildingSlots;
    private Building buildingPreview = null;

    private HashMap<Service, Label> servicesText;

    private ImageButton pauseButton;
    private Image pauseImage;

    @Override
    public void create() {
        contentLoader = new ContentLoader();
        contentLoader.Load();

        Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        batch = new SpriteBatch();

        toolbar = new Texture("toolbar.png");
        mapTexture = new Texture("mapTexture.png");
        settingsTexture = new Texture("settingsIcon.png");
        buildIconTexture = new Texture("buildIcon.png");
        pauseTexture = new Texture("pause.png");
        playTexture = new Texture("play.png");
        ui = new Stage();

        satisfactionBar = new SatisfactionBar(skin, ui);

        gameTimeText = new Label("5:00", skin);
        gameTimeText.setPosition(400, 735);
        gameTimeText.setSize(200, 50);
        gameTimeText.setFontScale(4);
        gameTimeText.setAlignment(1);
        ui.addActor(gameTimeText);

        buildButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(buildIconTexture)));
        buildButton.setPosition(70, 728);
        buildButton.setTransform(true);
        buildButton.setScale(2.5f);

        buildButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buildSelect.setVisible(true);
            }
        });

        int[][] slots = {
            {100, 200, 2}, {150, 550, 2}, {550, 200, 3}, {600, 400, 2}, {200, 200, 1}, {200, 350, 2}, {550, 300, 3}, {600, 500, 2}
        };

        stage = new Stage();

        buildingSlots = new ArrayList<>();

        for (int[] slot : slots) {
            buildingSlots.add(new BuildingSlot(new Vector2(slot[0], slot[1]), slot[2], stage));
        }

        VerticalGroup buttons = new VerticalGroup();

        for (Building building : contentLoader.allBuildings()) {
            ImageButton button = new ImageButton(new TextureRegionDrawable(new TextureRegion(contentLoader.getTexture(building.getSpriteName()))));

            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    preview(building);
                    buildSelect.setVisible(false);
                }
            });

            buttons.addActor(button);
        }

        buttons.setWidth(10);

        buildSelect = new ScrollPane(buttons);
        buildSelect.setPosition(30, 480);
        buildSelect.setHeight(240);
        buildSelect.setVisible(false);

        servicesText = new HashMap<>();

        for (Service service : Service.values()) {
            servicesText.put(service, new Label("0 (0)", skin));
        }

        Table servicesDisplay = new Table(skin);
        servicesDisplay.add(new Label("Accommodation", skin));
        servicesDisplay.add(new Label("Teaching Space", skin));
        servicesDisplay.add(new Label("Self Study", skin));
        servicesDisplay.row();
        servicesDisplay.add(servicesText.get(Service.Accommodation));
        servicesDisplay.add(servicesText.get(Service.TeachingSpace));
        servicesDisplay.add(servicesText.get(Service.SelfStudy));
        servicesDisplay.row();
        servicesDisplay.add(new Label("Food/Drink", skin));
        servicesDisplay.add(new Label("Recreation", skin));
        servicesDisplay.row();
        servicesDisplay.add(servicesText.get(Service.FoodDrink));
        servicesDisplay.add(servicesText.get(Service.Recreation));

        for (Cell cell : servicesDisplay.getCells()) {
            cell.padLeft(10);
            cell.padTop(5);
        }

        servicesDisplay.setPosition(300, 760);

        pauseImage = new Image(new TextureRegionDrawable(new TextureRegion(pauseTexture)));
        pauseImage.setPosition(570, 730);
        pauseImage.setScale(2f);
        pauseButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(pauseTexture)));
        pauseButton.setPosition(570, 730);
        pauseButton.setScale(2f);
        pauseButton.setColor(1, 1, 1, 0);

        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isPaused = !isPaused;
                if (isPaused) pauseImage.setDrawable(new TextureRegionDrawable(new TextureRegion(pauseTexture)));
                else pauseImage.setDrawable(new TextureRegionDrawable(new TextureRegion(playTexture)));
            }
        });

        ui.addActor(servicesDisplay);
        ui.addActor(buildSelect);
        ui.addActor(buildButton);
        ui.addActor(pauseImage);
        ui.addActor(pauseButton);
        Gdx.input.setInputProcessor(new InputMultiplexer(ui, stage));

        satisfactionBar.updateScore();
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT) && buildingPreview != null) {
            stopPreview();
        }

        updateTimer += deltaTime;
        if (!isPaused) {
            gameTimer -= deltaTime;
            updateGameTimeText((int)gameTimer);
        }

        while (updateTimer > updateTime) { // in case of a long freeze, able to do multiple updates
            update();
            updateTimer -= updateTime;
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(mapTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(toolbar, 0, 720, Gdx.graphics.getWidth(), 80);
        batch.draw(settingsTexture, 0, 720, 70, 70);

        batch.end();

        stage.act(deltaTime);
        stage.draw();

        ui.act(deltaTime);
        ui.draw();
    }

    private void update() {
        if (isPaused) return;

        for (BuildingSlot slot : buildingSlots){
            slot.Update();
        }

        updateServiceCounts();

        satisfactionBar.updateScore();
    }

    private void updateGameTimeText(int seconds) {
        gameTimeText.setText(String.format("%d:%02d", (seconds / 60), (seconds % 60)));
    }

    private void updateServiceCounts() {
        HashMap<Service, Integer> services = new HashMap<>();
        HashMap<Service, Integer> servicesUnderConstruction = new HashMap<>();
        for (BuildingSlot slot : buildingSlots){
            if (slot.getBuilding() == null) continue;
            for (Service service : slot.getBuilding().getServicesProvided()) {
                if (slot.isConstructing()) servicesUnderConstruction.merge(service, 1, Integer::sum);
                else services.merge(service, 1, Integer::sum);
            }
        }

        for (Service service : Service.values()) {
            services.putIfAbsent(service, 0);
            servicesUnderConstruction.putIfAbsent(service, 0);
            servicesText.get(service).setText(String.format("%d (%d)", services.get(service), servicesUnderConstruction.get(service)));
        }
    }

    private void preview(Building building) {
        buildingPreview = building;
        for (BuildingSlot slot : buildingSlots) {
            slot.setPreview(building);
        }
    }

    private void stopPreview() {
        buildingPreview = null;
        for (BuildingSlot slot : buildingSlots) {
            slot.clearPreview();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        ui.dispose();
        mapTexture.dispose();
        toolbar.dispose();
        settingsTexture.dispose();
        buildIconTexture.dispose();
        contentLoader.dispose();
    }
}
