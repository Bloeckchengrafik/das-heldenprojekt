package io.bloeckchengrafik.heldenprojekt.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.bloeckchengrafik.heldenprojekt.Heldenprojekt;
import io.bloeckchengrafik.heldenprojekt.utils.ScaledResolution;
import io.bloeckchengrafik.heldenprojekt.world.EvilCastle;

public class FightGUI implements GUI {
    private static final int height = 10, width = 20;
    private final GameGUI gameGUI;
    private final EvilCastle evilCastle;
    private final BitmapFont bitmapFont = new BitmapFont();
    private Texture[] uiTextures;
    private final ScaledResolution scaledResolution = new ScaledResolution((width + 2) * 64, (height + 2) * 64);
    private final ScaledResolution inner = new ScaledResolution(0,0);
    private final SpriteBatch spriteBatch = new SpriteBatch();
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    public FightGUI(GameGUI gameGUI, EvilCastle evilCastle) {
        this.gameGUI = gameGUI;
        this.evilCastle = evilCastle;

        inner.setContainer(scaledResolution);
        bitmapFont.getData().scale(2);
    }

    @Override
    public void create() {
        uiTextures = new Texture[]{
                Heldenprojekt.getInstance().getAssetManager().get("ui-0.png"),
                Heldenprojekt.getInstance().getAssetManager().get("ui-1.png"),
                Heldenprojekt.getInstance().getAssetManager().get("ui-2.png"),
                Heldenprojekt.getInstance().getAssetManager().get("ui-3.png"),
                Heldenprojekt.getInstance().getAssetManager().get("ui-4.png"),
                Heldenprojekt.getInstance().getAssetManager().get("ui-5.png"),
                Heldenprojekt.getInstance().getAssetManager().get("ui-6.png"),
                Heldenprojekt.getInstance().getAssetManager().get("ui-7.png"),
                Heldenprojekt.getInstance().getAssetManager().get("ui-8.png"),
        };
    }

    @Override
    public void update(double delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            gameGUI.returnToFront();
        }
    }

    @Override
    public void render() {
        spriteBatch.begin();

        GUI.renderImageBackground(width, height, uiTextures, spriteBatch, scaledResolution);

        // Title
        bitmapFont.draw(spriteBatch, "Fight! (press f)", inner.getCenterX() + 64, scaledResolution.getCenterY() + 64 * (height + 1) - 32);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        bitmapFont.dispose();

        for (Texture uiTexture : uiTextures) {
            uiTexture.dispose();
        }
    }
}
