package io.bloeckchengrafik.heldenprojekt.gui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.bloeckchengrafik.heldenprojekt.Heldenprojekt;
import io.bloeckchengrafik.heldenprojekt.utils.CenteredResolution;

public class LoadingScreenGUI implements GUI {
    private float opacity = 0f;
    private final AssetManager assetManager;
    private final ShapeRenderer shapeRenderer;
    private final Heldenprojekt heldenprojekt = Heldenprojekt.getInstance();

    private SpriteBatch batch;
    private Texture fuzzyLogo;
    private Texture heldenprojektLogo;
    private CenteredResolution imgResolution;
    private CenteredResolution loadingBarResolution;

    private int loadingBarWidth = 0;
    private boolean fadeOut = false;

    public LoadingScreenGUI() {
        this.assetManager = Heldenprojekt.getInstance().getAssetManager();
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        fuzzyLogo = new Texture("fuzzy-logo.png");
        heldenprojektLogo = new Texture("heldenprojekt-logo@0.5.png");

        imgResolution = new CenteredResolution(fuzzyLogo.getWidth()+heldenprojektLogo.getWidth()+20, fuzzyLogo.getHeight());
        loadingBarResolution = new CenteredResolution(400, 20);
        loadingBarResolution.setMemberOffsetY(-350);
    }

    @Override
    public void update(double deltaTime) {
        loadingBarWidth = (int) Math.min(getLoaderPos(deltaTime)*400, 400);

        if (assetManager.update() && loadingBarWidth == 400) {
            fadeOut = true;
        }

        if (!fadeOut) {
            opacity = Math.min(1.0f, opacity + (float) deltaTime);
            return;
        }
        opacity = Math.max(0.0f, opacity - (float) deltaTime);

        if (opacity == 0.0f) {
            assetManager.finishLoading();
            heldenprojekt.setNextGUI(new GameGUI());
        }
    }

    @Override
    public void render() {
        batch.begin();

        // Set Opacity
        batch.setColor(1*opacity, 1*opacity, 1*opacity, opacity);
        batch.draw(fuzzyLogo, imgResolution.getCenterX(), imgResolution.getCenterY());
        batch.draw(heldenprojektLogo, imgResolution.getCenterX()+fuzzyLogo.getWidth()+20, imgResolution.getCenterY());

        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Outline
        shapeRenderer.setColor(0.5f*opacity, 0.5f*opacity, 0.5f*opacity, opacity);
        shapeRenderer.rect(loadingBarResolution.getCenterX(), loadingBarResolution.getCenterY(), loadingBarResolution.getMemberWidth(), loadingBarResolution.getMemberHeight());

        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Draw loading bar
        shapeRenderer.setColor(1, 1, 1, opacity);
        shapeRenderer.rect(loadingBarResolution.getCenterX(), loadingBarResolution.getCenterY(), loadingBarWidth, loadingBarResolution.getMemberHeight());

        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        fuzzyLogo.dispose();
        shapeRenderer.dispose();
    }

    @Override
    public void resize(float scale) {

    }

    private float getLoaderPos(double deltaTime) {
        return (assetManager.getProgress());
    }
}
