import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class HealerGUI implements GUI {
    private static final int height = 10, width = 20;
    private final Healer healer;
    private final GameGUI gameGUI;
    private Texture[] uiTextures;
    private Texture healerImgTexture;
    private Texture bankTexture;
    private Texture bankPersonTexture;
    private final BitmapFont bitmapFont = new BitmapFont();
    private final CenteredResolution scaledResolution = new CenteredResolution((width + 2) * 64, (height + 2) * 64);
    private final SpriteBatch spriteBatch = new SpriteBatch();
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    private double timer = 5;
    private float scale = 1;

    public HealerGUI(Healer healer, GameGUI gameGUI) {
        this.healer = healer;
        this.gameGUI = gameGUI;

        bitmapFont.getData().scale(2);
        bitmapFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        resize(gameGUI.getScale());
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

        resize(gameGUI.getScale());
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
        GUI.renderImageBackground(width, height, uiTextures, spriteBatch, scaledResolution, scale);


        // Title
        bitmapFont.draw(spriteBatch, "Heiler (Druecke e um alle auf die Wartebank zu setzen)", scaledResolution.getCenterX()+30, scaledResolution.getCenterY() + scaledResolution.getMemberHeight() - bitmapFont.getData().lineHeight);

        spriteBatch.draw(healerImgTexture, scaledResolution.getCenterX()-300*scale, scaledResolution.getCenterY(), 1000*scale, 550*scale);

        spriteBatch.draw(bankTexture, scaledResolution.getCenterX()+300*scale, scaledResolution.getCenterY(), 1000*scale, 550*scale);

        int offset = 0;

        for (int i = 0; i < healer.getNum(); i++) {
            spriteBatch.draw(bankPersonTexture, scaledResolution.getCenterX()+400*scale+offset, scaledResolution.getCenterY()+30*scale, 800*scale, 450*scale);
            offset+=200*scale;
        }

        spriteBatch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor( 0, 0.8f, 0, (float) Math.sin((float) timer)/2);
        shapeRenderer.circle(scaledResolution.getCenterX()+300*scale, scaledResolution.getCenterY()+200*scale, ((float) Math.sin((float) timer) + 30)*scale);

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

    @Override
    public void resize(float scale) {
        float oldScale = this.scale;
        this.scale = scale;

        scaledResolution.rescaleMember(oldScale, scale);

        bitmapFont.getData().setScale(scale * 3);
    }
}
