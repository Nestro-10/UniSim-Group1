package group1.unisim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture toolbar;
    private Texture mapTexture;
    private Texture settingsTexture;
    private Texture buildIconTexture;
    private ShapeRenderer satisfactionBarRect1;
    private ShapeRenderer satisfactionBarRect2;
    private SatisfactionBar satisfactionBar;

    @Override
    public void create() {

        batch = new SpriteBatch();
        toolbar = new Texture("toolbar.png");
        mapTexture = new Texture("mapTexture.png");
        settingsTexture = new Texture("settingsIcon.png");
        buildIconTexture = new Texture("buildIcon.png");
        satisfactionBarRect1 = new ShapeRenderer();
        satisfactionBarRect2 = new ShapeRenderer();
        satisfactionBar = new SatisfactionBar();
        

    }

    @Override
    public void render() {
        
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(mapTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
        batch.draw(toolbar, 0, 720, Gdx.graphics.getWidth(), 80);
        batch.draw(settingsTexture, 0, 720, 70, 70);
        batch.draw(buildIconTexture, 70, 730, 60, 55 );
        
        //Draw satisfactionBar Rectangle
        satisfactionBarRect1.begin(ShapeType.Filled);
        satisfactionBarRect1.setColor(Color.GRAY);
        satisfactionBarRect1.rect(775, 735, 200, 50);

        //Initialise score bar

        satisfactionBarRect2.begin(ShapeType.Filled);
        if (satisfactionBar.getScore() < 31){
            satisfactionBarRect2.setColor(Color.RED);
        }
        else if (satisfactionBar.getScore() > 31 && satisfactionBar.getScore() < 61){
            satisfactionBarRect2.setColor(Color.YELLOW);
        }
        else{
            satisfactionBarRect2.setColor(Color.GREEN);
        }
        satisfactionBarRect2.rect(775, 735, (satisfactionBar.getScore()) * 2, 50);

        
        
        batch.end();
        satisfactionBarRect1.end();
        satisfactionBarRect2.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        mapTexture.dispose();
        toolbar.dispose();
        settingsTexture.dispose();
        buildIconTexture.dispose();
        satisfactionBarRect1.dispose();
        satisfactionBarRect2.dispose();
    }
}
