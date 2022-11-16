package io.bloeckchengrafik.heldenprojekt.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.bloeckchengrafik.heldenprojekt.Heldenprojekt;
import io.bloeckchengrafik.heldenprojekt.game.Entity;
import io.bloeckchengrafik.heldenprojekt.game.Held;
import io.bloeckchengrafik.heldenprojekt.utils.ScaledResolution;
import io.bloeckchengrafik.heldenprojekt.world.Healer;

public class HealerGUI implements GUI {
    private static final int height = 10, width = 20;
    private final Healer healer;
    private final GameGUI gameGUI;
    private Texture[] uiTextures;
    private Texture healerImgTexture;
    private Texture bankTexture;
    private Texture bankPersonTexture;
    private final BitmapFont bitmapFont = new BitmapFont();
    private final ScaledResolution scaledResolution = new ScaledResolution((width + 2) * 64, (height + 2) * 64);
    private final ScaledResolution inner = new ScaledResolution(0,0);
    private final SpriteBatch spriteBatch = new SpriteBatch();
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    private double timer = 5;

    public HealerGUI(Healer healer, GameGUI gameGUI) {
        this.healer = healer;
        this.gameGUI = gameGUI;

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

        healerImgTexture = Heldenprojekt.getInstance().getAssetManager().get("healer_img.png");
        bankTexture = Heldenprojekt.getInstance().getAssetManager().get("Bank.png");
        bankPersonTexture = Heldenprojekt.getInstance().getAssetManager().get("Bank-Person.png");
    }

    @Override
    public void update(double delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            gameGUI.returnToFront();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            for (Entity entity : Heldenprojekt.getInstance().getSaveFile().getData().getHeldengruppe().getEntities()) {
                Held held = (Held) entity;

                if (healer.getNum() >= 4) {
                    break;
                }

                healer.enqueue(held);
            }
        }

        timer -= delta;

        if (timer <= 0) {
            timer = 5;
            try {
                healer.heal();
                Heldenprojekt.getInstance().getSaveFile().save();
            } catch (RuntimeException ignored) { /* Die Queue ist leer */ }
        }
    }

    @Override
    public void render() {
        spriteBatch.begin();
        GUI.renderImageBackground(width, height, uiTextures, spriteBatch, scaledResolution);


        // Title
        bitmapFont.draw(spriteBatch, "Healer (e to enqueue all)", inner.getCenterX() + 64, scaledResolution.getCenterY() + 64 * (height + 1) - 32);

        spriteBatch.draw(healerImgTexture, scaledResolution.getCenterX()-300, scaledResolution.getCenterY(), 1000, 550);

        spriteBatch.draw(bankTexture, scaledResolution.getCenterX()+300, scaledResolution.getCenterY(), 1000, 550);

        int offset = 0;

        for (int i = 0; i < healer.getNum(); i++) {
            spriteBatch.draw(bankPersonTexture, scaledResolution.getCenterX()+400+offset, scaledResolution.getCenterY()+30, 800, 450);
            offset+=200;
        }

        spriteBatch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor( 0, 0.8f, 0, (float) Math.sin((float) timer)/2);
        shapeRenderer.circle(scaledResolution.getCenterX()+300, scaledResolution.getCenterY()+200, (float) Math.sin((float) timer) + 30);

        shapeRenderer.end();
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
