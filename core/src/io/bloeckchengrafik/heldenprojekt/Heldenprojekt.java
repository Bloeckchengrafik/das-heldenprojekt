package io.bloeckchengrafik.heldenprojekt;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import io.bloeckchengrafik.heldenprojekt.PlatformProvider;
import io.bloeckchengrafik.heldenprojekt.gui.GUI;
import io.bloeckchengrafik.heldenprojekt.gui.GameGUI;
import io.bloeckchengrafik.heldenprojekt.gui.LoadingScreenGUI;
import io.bloeckchengrafik.heldenprojekt.save.SaveFile;
import io.bloeckchengrafik.heldenprojekt.world.World;
import io.bloeckchengrafik.heldenprojekt.world.WorldLoader;

public class Heldenprojekt extends ApplicationAdapter {
    private static Heldenprojekt instance;
    private AssetManager assetManager;
    private final SaveFile saveFile = new SaveFile();
    private GUI currentGUI;
    private GUI nextGUI;
    private GameGUI ingameGUI;
    private final PlatformProvider platform;

    public Heldenprojekt(PlatformProvider platform) {
        this.platform = platform;
    }

    public static Heldenprojekt getInstance() {
        return Heldenprojekt.instance;
    }


    @Override
    public void create() {
        instance = this;

        assetManager = new AssetManager();
        assetManager.setLoader(World.class, new WorldLoader(new InternalFileHandleResolver()));

        assetManager.load("overworld.wof", World.class);

        assetManager.load("maptiles/forest.png", Texture.class);
        assetManager.load("maptiles/forest_NN.png", Texture.class);
        assetManager.load("maptiles/forest_NO.png", Texture.class);
        assetManager.load("maptiles/forest_NW.png", Texture.class);
        assetManager.load("maptiles/forest_OO.png", Texture.class);
        assetManager.load("maptiles/forest_SO.png", Texture.class);
        assetManager.load("maptiles/forest_SS.png", Texture.class);
        assetManager.load("maptiles/forest_SW.png", Texture.class);
        assetManager.load("maptiles/forest_WW.png", Texture.class);
        assetManager.load("maptiles/grass.png", Texture.class);
        assetManager.load("maptiles/grass_NN.png", Texture.class);
        assetManager.load("maptiles/grass_NO.png", Texture.class);
        assetManager.load("maptiles/grass_NW.png", Texture.class);
        assetManager.load("maptiles/grass_OO.png", Texture.class);
        assetManager.load("maptiles/grass_SO.png", Texture.class);
        assetManager.load("maptiles/grass_SS.png", Texture.class);
        assetManager.load("maptiles/grass_SW.png", Texture.class);
        assetManager.load("maptiles/grass_WW.png", Texture.class);
        assetManager.load("maptiles/sand.png", Texture.class);
        assetManager.load("maptiles/sand_NN.png", Texture.class);
        assetManager.load("maptiles/sand_NO.png", Texture.class);
        assetManager.load("maptiles/sand_NW.png", Texture.class);
        assetManager.load("maptiles/sand_OO.png", Texture.class);
        assetManager.load("maptiles/sand_SO.png", Texture.class);
        assetManager.load("maptiles/sand_SS.png", Texture.class);
        assetManager.load("maptiles/sand_SW.png", Texture.class);
        assetManager.load("maptiles/sand_WW.png", Texture.class);
        assetManager.load("maptiles/mountains.png", Texture.class);
        assetManager.load("maptiles/mountains_NN.png", Texture.class);
        assetManager.load("maptiles/mountains_NO.png", Texture.class);
        assetManager.load("maptiles/mountains_NW.png", Texture.class);
        assetManager.load("maptiles/mountains_OO.png", Texture.class);
        assetManager.load("maptiles/mountains_SO.png", Texture.class);
        assetManager.load("maptiles/mountains_SS.png", Texture.class);
        assetManager.load("maptiles/mountains_SW.png", Texture.class);
        assetManager.load("maptiles/mountains_WW.png", Texture.class);
        assetManager.load("maptiles/water0.png", Texture.class);
        assetManager.load("maptiles/water1.png", Texture.class);
        assetManager.load("maptiles/water2.png", Texture.class);
        assetManager.load("maptiles/water3.png", Texture.class);
        assetManager.load("maptiles/water4.png", Texture.class);
        assetManager.load("maptiles/water5.png", Texture.class);
        assetManager.load("maptiles/water6.png", Texture.class);
        assetManager.load("maptiles/water7.png", Texture.class);
        assetManager.load("maptiles/water8.png", Texture.class);
        assetManager.load("cursor.png", Texture.class);
        assetManager.load("camp.png", Texture.class);
        assetManager.load("Gegnerschloss.png", Texture.class);
        assetManager.load("healer.png", Texture.class);
        assetManager.load("ui-0.png", Texture.class);
        assetManager.load("ui-1.png", Texture.class);
        assetManager.load("ui-2.png", Texture.class);
        assetManager.load("ui-3.png", Texture.class);
        assetManager.load("ui-4.png", Texture.class);
        assetManager.load("ui-5.png", Texture.class);
        assetManager.load("ui-6.png", Texture.class);
        assetManager.load("ui-7.png", Texture.class);
        assetManager.load("ui-8.png", Texture.class);
        assetManager.load("healer_img.png", Texture.class);
        assetManager.load("Bank.png", Texture.class);
        assetManager.load("Bank-Person.png", Texture.class);
        assetManager.load("edgetile-0.png", Texture.class);
        assetManager.load("edgetile-1.png", Texture.class);
        assetManager.load("edgetile-2.png", Texture.class);
        assetManager.load("edgetile-3.png", Texture.class);
        assetManager.load("edgetile-4.png", Texture.class);
        assetManager.load("edgetile-5.png", Texture.class);
        assetManager.load("edgetile-6.png", Texture.class);
        assetManager.load("edgetile-7.png", Texture.class);
        assetManager.load("edgetile-8.png", Texture.class);
        assetManager.load("fight/goblinattack/0.png", Texture.class);
        assetManager.load("fight/goblinattack/1.png", Texture.class);
        assetManager.load("fight/goblinattack/2.png", Texture.class);
        assetManager.load("fight/goblinattack/3.png", Texture.class);
        assetManager.load("fight/goblinattack/4.png", Texture.class);
        assetManager.load("fight/goblinattack/5.png", Texture.class);
        assetManager.load("fight/goblinattack/6.png", Texture.class);
        assetManager.load("fight/goblindeath/0.png", Texture.class);
        assetManager.load("fight/goblindeath/1.png", Texture.class);
        assetManager.load("fight/goblindeath/2.png", Texture.class);
        assetManager.load("fight/goblindeath/3.png", Texture.class);
        assetManager.load("fight/goblindeath/4.png", Texture.class);
        assetManager.load("fight/goblindeath/5.png", Texture.class);
        assetManager.load("fight/goblindeath/6.png", Texture.class);
        assetManager.load("fight/goblindeath/7.png", Texture.class);
        assetManager.load("fight/goblinidle/0.png", Texture.class);
        assetManager.load("fight/goblinidle/1.png", Texture.class);
        assetManager.load("fight/goblinidle/2.png", Texture.class);
        assetManager.load("fight/goblinrun/0.png", Texture.class);
        assetManager.load("fight/goblinrun/1.png", Texture.class);
        assetManager.load("fight/goblinrun/2.png", Texture.class);
        assetManager.load("fight/goblinrun/3.png", Texture.class);
        assetManager.load("fight/goblinrun/4.png", Texture.class);
        assetManager.load("fight/goblinrun/5.png", Texture.class);
        assetManager.load("fight/heroattack/0.png", Texture.class);
        assetManager.load("fight/heroattack/1.png", Texture.class);
        assetManager.load("fight/heroattack/2.png", Texture.class);
        assetManager.load("fight/heroattack/3.png", Texture.class);
        assetManager.load("fight/herodeath/0.png", Texture.class);
        assetManager.load("fight/herodeath/1.png", Texture.class);
        assetManager.load("fight/herodeath/2.png", Texture.class);
        assetManager.load("fight/herodeath/3.png", Texture.class);
        assetManager.load("fight/heroidle/0.png", Texture.class);
        assetManager.load("fight/heroidle/1.png", Texture.class);
        assetManager.load("fight/heroidle/2.png", Texture.class);
        assetManager.load("fight/heroidle/3.png", Texture.class);
        assetManager.load("fight/herorun/0.png", Texture.class);
        assetManager.load("fight/herorun/1.png", Texture.class);
        assetManager.load("fight/herorun/2.png", Texture.class);
        assetManager.load("fight/herorun/3.png", Texture.class);

        nextGUI = new LoadingScreenGUI();
    }


    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1f);

        if (ingameGUI != null) {
            currentGUI.update(Gdx.graphics.getDeltaTime());
            currentGUI.render();
            return;
        }

        if (nextGUI != null) {
            if (currentGUI != null) {
                currentGUI.dispose();
            }

            currentGUI = nextGUI;
            currentGUI.create();
            nextGUI = null;
        }

        currentGUI.update(Gdx.graphics.getDeltaTime());
        currentGUI.render();
    }

    @Override
    public void dispose() {
        if (currentGUI != null) {
            currentGUI.dispose();
        }
    }

    public AssetManager getAssetManager() {
        return this.assetManager;
    }

    public SaveFile getSaveFile() {
        return this.saveFile;
    }

    public PlatformProvider getPlatform() {
        return this.platform;
    }

    public void setNextGUI(GUI nextGUI) {
        this.nextGUI = nextGUI;
    }

    public void gotoGameGUI() {
        this.ingameGUI = new GameGUI();
        this.ingameGUI.create();
    }
}
