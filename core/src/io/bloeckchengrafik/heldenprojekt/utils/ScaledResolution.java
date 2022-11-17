package io.bloeckchengrafik.heldenprojekt.utils;

import com.badlogic.gdx.Gdx;

public class ScaledResolution {
    private int width;
    private int height;
    private ScaledResolution container;

    private int memberWidth;
    private int memberHeight;

    private int memberOffsetX = 0;
    private int memberOffsetY = 0;

    public ScaledResolution(int memberWidth, int memberHeight) {
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
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

    public ScaledResolution getContainer() {
        return this.container;
    }

    public int getMemberWidth() {
        return this.memberWidth;
    }

    public int getMemberHeight() {
        return this.memberHeight;
    }

    public int getMemberOffsetX() {
        return this.memberOffsetX;
    }

    public int getMemberOffsetY() {
        return this.memberOffsetY;
    }

    public void setContainer(ScaledResolution container) {
        width = container.getMemberWidth();
        height = container.getMemberHeight();
        this.container = container;
    }

    public void setMemberWidth(int memberWidth) {
        this.memberWidth = memberWidth;
    }

    public void setMemberHeight(int memberHeight) {
        this.memberHeight = memberHeight;
    }

    public void setMemberOffsetX(int memberOffsetX) {
        this.memberOffsetX = memberOffsetX;
    }

    public void setMemberOffsetY(int memberOffsetY) {
        this.memberOffsetY = memberOffsetY;
    }
}
