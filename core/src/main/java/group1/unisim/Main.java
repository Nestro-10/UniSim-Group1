package group1.unisim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import org.snakeyaml.engine.v2.api.*;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture toolbar;
    private Texture mapTexture;
    private Texture settingsTexture;
    private Texture buildIconTexture;


    @Override
    public void create() {

        batch = new SpriteBatch();
        toolbar = new Texture("toolbar.png");
        mapTexture = new Texture("mapTexture.png");
        settingsTexture = new Texture("settingsIcon.png");
        buildIconTexture = new Texture("buildIcon.png");
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(mapTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
        batch.draw(toolbar, 0, 1420, Gdx.graphics.getWidth(), 80);
        batch.draw(settingsTexture, 0, 1425, 70, 70);
        batch.draw(buildIconTexture, 70, 1430, 60, 55 );
        batch.end();
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
