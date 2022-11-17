package io.bloeckchengrafik.heldenprojekt.utils;

import com.badlogic.gdx.Gdx;

public class CenteredResolution {
    private final int width;
    private final int height;
    private CenteredResolution container;

    private int memberWidth;
    private int memberHeight;

    private final int memberOffsetX = 0;
    private int memberOffsetY = 0;

    public CenteredResolution(int memberWidth, int memberHeight) {
        this.memberWidth = memberWidth;
        this.memberHeight = memberHeight;
        // Get from LibGDX
        this.width = Gdx.graphics.getWidth();
        this.height = Gdx.graphics.getHeight();
    }

    public void rescaleMember(float prevScale, float newScale) {
        this.memberWidth = (int) (this.memberWidth * (newScale / prevScale));
        this.memberHeight = (int) (this.memberHeight * (newScale / prevScale));
    }

    public int getCenterX() {
        int containerCenterY = 0;

        if (container != null) {
            containerCenterY = container.getCenterY();
        }

        return (width / 2 - memberWidth / 2) + memberOffsetX + containerCenterY;
    }

    public int getCenterY() {
        int containerCenterX = 0;

        if (container != null) {
            containerCenterX = container.getCenterX();
        }
        return (height / 2 - memberHeight / 2) + memberOffsetY + containerCenterX;
    }

    public int getMemberWidth() {
        return this.memberWidth;
    }

    public int getMemberHeight() {
        return this.memberHeight;
    }

    public void setMemberOffsetY(int memberOffsetY) {
        this.memberOffsetY = memberOffsetY;
    }
}
