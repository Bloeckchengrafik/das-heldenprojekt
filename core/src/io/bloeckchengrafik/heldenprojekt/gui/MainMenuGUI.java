package io.bloeckchengrafik.heldenprojekt.gui;

import com.badlogic.gdx.assets.AssetManager;
import io.bloeckchengrafik.heldenprojekt.Heldenprojekt;

public class MainMenuGUI implements GUI{
    @Override
    public void create() {
    }

    @Override
    public void update(double delta) {
        Heldenprojekt.getInstance().setNextGUI(new GameGUI());
    }

    @Override
    public void render() {
    }

    @Override
    public void dispose() {
    }
}
