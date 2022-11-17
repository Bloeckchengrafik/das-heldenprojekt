package io.bloeckchengrafik.heldenprojekt.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.bloeckchengrafik.heldenprojekt.utils.ScaledResolution;

public interface GUI {
    void create();
    void update(double delta);
    void render();
    void dispose();
    void resize(float scale);

    static void renderImageBackground(int width, int height, Texture[] uiTextures, SpriteBatch spriteBatch, ScaledResolution scaledResolution, float scale) {
        int toRender = 0;

        // UI
        for (int row = 0; row < (width + 2); row++) {
            for (int col = 0; col < (height + 2); col++) {
                toRender = 4;

                boolean isLeftBound = row == 0,
                        isLowerBound = col == 0,
                        isRightBound = row == (width + 1),
                        isUpperBound = col == (height + 1);

                if (isLeftBound) {
                    toRender = 3;
                } else if (isRightBound) {
                    toRender = 5;
                } else if (isUpperBound) {
                    toRender = 1;
                } else if (isLowerBound) {
                    toRender = 7;
                }

                if (isLeftBound && isUpperBound) {
                    toRender = 0;
                }
                if (isRightBound && isUpperBound) {
                    toRender = 2;
                }
                if (isLeftBound && isLowerBound) {
                    toRender = 6;
                }
                if (isRightBound && isLowerBound) {
                    toRender = 8;
                }

                spriteBatch.draw(uiTextures[toRender], scaledResolution.getCenterX() + row * 64*scale, scaledResolution.getCenterY() + col * 64*scale, 64*scale, 64*scale);
            }
        }
    }
}
