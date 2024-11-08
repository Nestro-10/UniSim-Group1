package group1.unisim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture toolbar;
    private Texture mapTexture;
    private Texture settingsTexture;
    private Texture buildIconTexture;
    private SatisfactionBar satisfactionBar;
    private float updateTimer;
    private final float updateTime = 1/30f; // 30 updates/second

    //UI
    private Stage stage;

    @Override
    public void create() {
        Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        batch = new SpriteBatch();
        toolbar = new Texture("toolbar.png");
        mapTexture = new Texture("mapTexture.png");
        settingsTexture = new Texture("settingsIcon.png");
        buildIconTexture = new Texture("buildIcon.png");
        stage = new Stage();

        satisfactionBar = new SatisfactionBar(skin);
        satisfactionBar.setPosition(775, 735);
        stage.addActor(satisfactionBar);
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        updateTimer += deltaTime;

        while (updateTimer > updateTime) { // in case of a long freeze, able to do multiple updates
            update();
            updateTimer -= updateTime;
        }

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(mapTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
        batch.draw(toolbar, 0, 720, Gdx.graphics.getWidth(), 80);
        batch.draw(settingsTexture, 0, 720, 70, 70);
        batch.draw(buildIconTexture, 70, 730, 60, 55 );

        //satisfactionBar.render();

        stage.draw();

        batch.end();
    }

    private void update() {
        satisfactionBar.updateScore();
    }

    @Override
    public void dispose() {
        batch.dispose();
        mapTexture.dispose();
        toolbar.dispose();
        settingsTexture.dispose();
        buildIconTexture.dispose();
    }
}
