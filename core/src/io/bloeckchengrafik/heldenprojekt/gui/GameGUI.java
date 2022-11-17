package io.bloeckchengrafik.heldenprojekt.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.bloeckchengrafik.heldenprojekt.Heldenprojekt;
import io.bloeckchengrafik.heldenprojekt.game.Entity;
import io.bloeckchengrafik.heldenprojekt.game.Held;
import io.bloeckchengrafik.heldenprojekt.save.SaveFile;
import io.bloeckchengrafik.heldenprojekt.utils.CenteredResolution;
import io.bloeckchengrafik.heldenprojekt.utils.MusicBox;
import io.bloeckchengrafik.heldenprojekt.world.EvilCastle;
import io.bloeckchengrafik.heldenprojekt.world.World;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.zip.Deflater;

public class GameGUI implements GUI {
    private final Heldenprojekt heldenprojekt = Heldenprojekt.getInstance();
    private final CenteredResolution scaledResolution = new CenteredResolution(32 * 41, 32 * 41);
    private final BitmapFont font = new BitmapFont();
    private final BitmapFont fontSm = new BitmapFont();

    private static final int TILE_WATER = 0;
    private static final int TILE_MOUNTAIN = 1;
    private static final int TILE_SAND = 2;
    private static final int TILE_GRASS = 4;
    private static final int TILE_FOREST = 5;

    private final int[] tileOrder = new int[]{TILE_WATER, TILE_SAND, TILE_GRASS, TILE_FOREST, TILE_MOUNTAIN};
    private final int[] campBlockedTiles = new int[]{TILE_WATER, TILE_MOUNTAIN};
    private SpriteBatch batch;
    private SpriteBatch shapeBatch;
    private ShapeRenderer shapeRenderer;
    private World world;
    private int x = 35,
            y = 13;
    private int curX = 0,
            curY = 0;

    //region textures
    private Texture forestTexture;
    private Texture grassTexture;
    private Texture sandTexture;
    private Texture mountainTexture;
    private Texture cursorTexture;
    private Texture campTexture;
    private Texture evilCastleTexture;
    private Texture healerTexture;
    private final ArrayList<Texture> rotationAroundMountainTextures = new ArrayList<>();
    private final ArrayList<Texture> rotationAroundForestTextures = new ArrayList<>();
    private final ArrayList<Texture> rotationAroundGrassTextures = new ArrayList<>();
    private final ArrayList<Texture> rotationAroundSandTextures = new ArrayList<>();
    private final Texture[] waterTextures = new Texture[9];
    private Texture[] edgeTextures;
    //endregion

    private HealerGUI healerGUI;

    private SaveFile saveFile;
    private GUI backGUI;
    private float oldScale = 1;
    private float scale = 1;
    private double flash = 0;

    private int fontLineHeight = 0;
    private int fontSmLineHeight = 0;

    private int waterAnimation = 0;
    private double waterAnimationTimer = 0;

    private MusicBox musicBox;

    @Override
    public void create() {
        world = heldenprojekt.getAssetManager().get("overworld.wof");

        //region textures
        forestTexture = heldenprojekt.getAssetManager().get("maptiles/forest.png");
        Texture forest_NN_Texture = heldenprojekt.getAssetManager().get("maptiles/forest_NN.png");
        Texture forest_NO_Texture = heldenprojekt.getAssetManager().get("maptiles/forest_NO.png");
        Texture forest_NW_Texture = heldenprojekt.getAssetManager().get("maptiles/forest_NW.png");
        Texture forest_OO_Texture = heldenprojekt.getAssetManager().get("maptiles/forest_OO.png");
        Texture forest_SO_Texture = heldenprojekt.getAssetManager().get("maptiles/forest_SO.png");
        Texture forest_SS_Texture = heldenprojekt.getAssetManager().get("maptiles/forest_SS.png");
        Texture forest_SW_Texture = heldenprojekt.getAssetManager().get("maptiles/forest_SW.png");
        Texture forest_WW_Texture = heldenprojekt.getAssetManager().get("maptiles/forest_WW.png");
        grassTexture = heldenprojekt.getAssetManager().get("maptiles/grass.png");
        Texture grass_NN_Texture = heldenprojekt.getAssetManager().get("maptiles/grass_NN.png");
        Texture grass_NO_Texture = heldenprojekt.getAssetManager().get("maptiles/grass_NO.png");
        Texture grass_NW_Texture = heldenprojekt.getAssetManager().get("maptiles/grass_NW.png");
        Texture grass_OO_Texture = heldenprojekt.getAssetManager().get("maptiles/grass_OO.png");
        Texture grass_SO_Texture = heldenprojekt.getAssetManager().get("maptiles/grass_SO.png");
        Texture grass_SS_Texture = heldenprojekt.getAssetManager().get("maptiles/grass_SS.png");
        Texture grass_SW_Texture = heldenprojekt.getAssetManager().get("maptiles/grass_SW.png");
        Texture grass_WW_Texture = heldenprojekt.getAssetManager().get("maptiles/grass_WW.png");
        sandTexture = heldenprojekt.getAssetManager().get("maptiles/sand.png");
        Texture sand_NN_Texture = heldenprojekt.getAssetManager().get("maptiles/sand_NN.png");
        Texture sand_NO_Texture = heldenprojekt.getAssetManager().get("maptiles/sand_NO.png");
        Texture sand_NW_Texture = heldenprojekt.getAssetManager().get("maptiles/sand_NW.png");
        Texture sand_OO_Texture = heldenprojekt.getAssetManager().get("maptiles/sand_OO.png");
        Texture sand_SO_Texture = heldenprojekt.getAssetManager().get("maptiles/sand_SO.png");
        Texture sand_SS_Texture = heldenprojekt.getAssetManager().get("maptiles/sand_SS.png");
        Texture sand_SW_Texture = heldenprojekt.getAssetManager().get("maptiles/sand_SW.png");
        Texture sand_WW_Texture = heldenprojekt.getAssetManager().get("maptiles/sand_WW.png");
        mountainTexture = heldenprojekt.getAssetManager().get("maptiles/mountains.png");
        Texture mountain_NN_Texture = heldenprojekt.getAssetManager().get("maptiles/mountains_NN.png");
        Texture mountain_NO_Texture = heldenprojekt.getAssetManager().get("maptiles/mountains_NO.png");
        Texture mountain_NW_Texture = heldenprojekt.getAssetManager().get("maptiles/mountains_NW.png");
        Texture mountain_OO_Texture = heldenprojekt.getAssetManager().get("maptiles/mountains_OO.png");
        Texture mountain_SO_Texture = heldenprojekt.getAssetManager().get("maptiles/mountains_SO.png");
        Texture mountain_SS_Texture = heldenprojekt.getAssetManager().get("maptiles/mountains_SS.png");
        Texture mountain_SW_Texture = heldenprojekt.getAssetManager().get("maptiles/mountains_SW.png");
        Texture mountain_WW_Texture = heldenprojekt.getAssetManager().get("maptiles/mountains_WW.png");
        Texture water0Texture = heldenprojekt.getAssetManager().get("maptiles/water0.png");
        Texture water1Texture = heldenprojekt.getAssetManager().get("maptiles/water1.png");
        Texture water2Texture = heldenprojekt.getAssetManager().get("maptiles/water2.png");
        Texture water3Texture = heldenprojekt.getAssetManager().get("maptiles/water3.png");
        Texture water4Texture = heldenprojekt.getAssetManager().get("maptiles/water4.png");
        Texture water5Texture = heldenprojekt.getAssetManager().get("maptiles/water5.png");
        Texture water6Texture = heldenprojekt.getAssetManager().get("maptiles/water6.png");
        Texture water7Texture = heldenprojekt.getAssetManager().get("maptiles/water7.png");
        Texture water8Texture = heldenprojekt.getAssetManager().get("maptiles/water8.png");

        cursorTexture = heldenprojekt.getAssetManager().get("cursor.png");
        campTexture = heldenprojekt.getAssetManager().get("camp.png");
        evilCastleTexture = heldenprojekt.getAssetManager().get("Gegnerschloss.png");
        healerTexture = heldenprojekt.getAssetManager().get("healer.png");

        rotationAroundMountainTextures.add(mountain_NN_Texture);
        rotationAroundMountainTextures.add(mountain_NO_Texture);
        rotationAroundMountainTextures.add(mountain_NW_Texture);
        rotationAroundMountainTextures.add(mountain_OO_Texture);
        rotationAroundMountainTextures.add(mountain_SO_Texture);
        rotationAroundMountainTextures.add(mountain_SS_Texture);
        rotationAroundMountainTextures.add(mountain_SW_Texture);
        rotationAroundMountainTextures.add(mountain_WW_Texture);

        rotationAroundGrassTextures.add(grass_NN_Texture);
        rotationAroundGrassTextures.add(grass_NO_Texture);
        rotationAroundGrassTextures.add(grass_NW_Texture);
        rotationAroundGrassTextures.add(grass_OO_Texture);
        rotationAroundGrassTextures.add(grass_SO_Texture);
        rotationAroundGrassTextures.add(grass_SS_Texture);
        rotationAroundGrassTextures.add(grass_SW_Texture);
        rotationAroundGrassTextures.add(grass_WW_Texture);

        rotationAroundSandTextures.add(sand_NN_Texture);
        rotationAroundSandTextures.add(sand_NO_Texture);
        rotationAroundSandTextures.add(sand_NW_Texture);
        rotationAroundSandTextures.add(sand_OO_Texture);
        rotationAroundSandTextures.add(sand_SO_Texture);
        rotationAroundSandTextures.add(sand_SS_Texture);
        rotationAroundSandTextures.add(sand_SW_Texture);
        rotationAroundSandTextures.add(sand_WW_Texture);

        rotationAroundForestTextures.add(forest_NN_Texture);
        rotationAroundForestTextures.add(forest_NO_Texture);
        rotationAroundForestTextures.add(forest_NW_Texture);
        rotationAroundForestTextures.add(forest_OO_Texture);
        rotationAroundForestTextures.add(forest_SO_Texture);
        rotationAroundForestTextures.add(forest_SS_Texture);
        rotationAroundForestTextures.add(forest_SW_Texture);
        rotationAroundForestTextures.add(forest_WW_Texture);

        waterTextures[0] = water0Texture;
        waterTextures[1] = water1Texture;
        waterTextures[2] = water2Texture;
        waterTextures[3] = water3Texture;
        waterTextures[4] = water4Texture;
        waterTextures[5] = water5Texture;
        waterTextures[6] = water6Texture;
        waterTextures[7] = water7Texture;
        waterTextures[8] = water8Texture;

        edgeTextures = new Texture[] {
                heldenprojekt.getAssetManager().get("edgetile-0.png"),
                heldenprojekt.getAssetManager().get("edgetile-1.png"),
                heldenprojekt.getAssetManager().get("edgetile-2.png"),
                heldenprojekt.getAssetManager().get("edgetile-3.png"),
                heldenprojekt.getAssetManager().get("edgetile-4.png"),
                heldenprojekt.getAssetManager().get("edgetile-5.png"),
                heldenprojekt.getAssetManager().get("edgetile-6.png"),
                heldenprojekt.getAssetManager().get("edgetile-7.png"),
                heldenprojekt.getAssetManager().get("edgetile-8.png")
        };
        //endregion

        batch = new SpriteBatch();

        font.setColor(Color.WHITE);
        font.getData().scale(3f);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        fontSm.setColor(Color.WHITE);
        fontSm.getData().scale(2f);
        fontSm.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        saveFile = Heldenprojekt.getInstance().getSaveFile();

        resize(saveFile.getData().getScale());

        healerGUI = new HealerGUI(world.getHealer(), this);
        healerGUI.create();

        shapeRenderer = new ShapeRenderer();
        shapeBatch = new SpriteBatch();

        musicBox = new MusicBox();
        musicBox.create();
        musicBox.useTrack(MusicBox.Track.MAIN_THEME);
    }

    private boolean near(int x, int y, int tx, int ty) {
        int dx = Math.abs(x - tx);
        int dy = Math.abs(y - ty);

        return dx <= 1 && dy <= 1;
    }

    @Override
    public void update(double delta) {
        musicBox.update();

        if (Gdx.input.isKeyJustPressed(Input.Keys.U)) {
            resize(scale + 0.1f);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.J)) {
            if (scale > 0.2f) {
                resize(scale - 0.1f);
            }
        }

        if (backGUI != null) {
            backGUI.update(delta);
            return;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) y++;
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) x--;
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) x++;
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) y--;
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();

        curX = x + 10;
        curY = y + 10;

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            int currentTile = resolveTile(world.getTiles(), curX, curY);

            if (campBlockedTiles[0] == currentTile || campBlockedTiles[1] == currentTile) {
                return;
            }

            for (EvilCastle evilCastle : world.getEvilCastles()) {
                if (curX == evilCastle.getX() && curY == evilCastle.getY()) {
                    return;
                }
            }

            if (curX == world.getHealer().getX() && curY == world.getHealer().getY()) {
                return;
            }

            saveFile.getData().setCampX(curX);
            saveFile.getData().setCampY(curY);
            saveFile.save();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            /* Action! */

            int campX = saveFile.getData().getCampX();
            int campY = saveFile.getData().getCampY();

            if (near(campX, campY, world.getHealer().getX(), world.getHealer().getY())) {
                backGUI = healerGUI;
            }

            for (EvilCastle evilCastle : world.getEvilCastles()) {
                if (near(campX, campY, evilCastle.getX(), evilCastle.getY())) {
                    backGUI = new FightGUI(this, evilCastle);
                    backGUI.create();
                    break;
                }
            }
        }
    }

    private int resolveTile(int[][] tiles, int x, int y) {
        if (x >= tiles.length || x < 0 ||
                y >= tiles[0].length || y < 0) {
            return 0;
        }

        return tiles[x][y];
    }

    private int ordinal(int[] iterable, int value) {
        for (int i = 0; i < iterable.length; i++) {
            if (iterable[i] == value) {
                return i;
            }
        }
        return -1;
    }

    private ArrayList<Texture> getRotationAroundTextures(int id) {
        switch (id) {
            case 1:
                return rotationAroundMountainTextures;
            case 2:
                return rotationAroundSandTextures;
            case 4:
                return rotationAroundGrassTextures;
            case 5:
                return rotationAroundForestTextures;
        }

        return rotationAroundMountainTextures;
    }

    @Override
    public void render() {
        waterAnimationTimer += Gdx.graphics.getDeltaTime();
        if (waterAnimationTimer > 0.5d) {
            waterAnimationTimer -= 0.5d;
            waterAnimation++;

            if (waterAnimation > 8) {
                waterAnimation = 0;
            }
        }

        batch.begin();


        int[][] tiles = world.getTiles();

        for (int x = 0; x < 21; x++) {
            for (int y = 0; y < 21; y++) {
                int tile = resolveTile(tiles, this.x + x, this.y + y);

                int waterTileNo = (locationHashCode(this.x + x, this.y + y) + waterAnimation) % 9;
                Texture texture = waterTextures[waterTileNo];

                switch (tile) {
                    case 1:
                        texture = mountainTexture;
                        break;
                    case 2:
                        texture = sandTexture;
                        break;
                    case 4:
                        texture = grassTexture;
                        break;
                    case 5:
                        texture = forestTexture;
                        break;
                }

                batch.draw(texture, x * 64 * scale + scaledResolution.getCenterX(), y * 64 * scale + scaledResolution.getCenterY(), 64 * scale, 64 * scale);

                // Check if it has a higher-order tile next to it
                int northTile = resolveTile(tiles, this.x + x, this.y + y + 1);
                int westTile = resolveTile(tiles, this.x + x - 1, this.y + y);
                int eastTile = resolveTile(tiles, this.x + x + 1, this.y + y);
                int southTile = resolveTile(tiles, this.x + x, this.y + y - 1);

                int[][] tileOrdinalPairs = new int[][]{
                        {northTile, 5},
                        {westTile, 3},
                        {eastTile, 7},
                        {southTile, 0}
                };

                for (int[] tileOrdinalPair : tileOrdinalPairs) {
                    int adjacentTile = tileOrdinalPair[0];
                    int id = tileOrdinalPair[1];

                    if (ordinal(tileOrder, tile) < ordinal(tileOrder, adjacentTile)) {
                        batch.draw(getRotationAroundTextures(adjacentTile).get(id), x * 64 * scale + scaledResolution.getCenterX(), y * 64 * scale + scaledResolution.getCenterY(), 64 * scale, 64 * scale);
                    }
                }

                if (this.x + x == heldenprojekt.getSaveFile().getData().getCampX() && this.y + y == heldenprojekt.getSaveFile().getData().getCampY()) {
                    batch.draw(campTexture, x * 64 * scale + scaledResolution.getCenterX(), y * 64 * scale + scaledResolution.getCenterY(), 64 * scale, 64 * scale);
                }


                for (EvilCastle evilCastle : world.getEvilCastles()) {
                    if (this.x + x == evilCastle.getX() && this.y + y == evilCastle.getY()) {
                        batch.draw(evilCastleTexture, x * 64 * scale + scaledResolution.getCenterX(), y * 64 * scale + scaledResolution.getCenterY(), 64 * scale, 64 * scale);
                    }
                }

                if (this.x + x == world.getHealer().getX() && this.y + y == world.getHealer().getY()) {
                    batch.draw(healerTexture, x * 64 * scale + scaledResolution.getCenterX(), y * 64 * scale + scaledResolution.getCenterY(), 64 * scale, 64 * scale);
                }

                boolean isUpperEdge = y == 0;
                boolean isLowerEdge = y == 20;
                boolean isLeftEdge = x == 0;
                boolean isRightEdge = x == 20;
                int edge = 4;
                if (isUpperEdge) {
                    edge = 7;
                } else if (isLowerEdge) {
                    edge = 1;
                } else if (isLeftEdge) {
                    edge = 3;
                } else if (isRightEdge) {
                    edge = 5;
                }

                if (isUpperEdge && isLeftEdge) {
                    edge = 6;
                }

                if (isUpperEdge && isRightEdge) {
                    edge = 8;
                }

                if (isLowerEdge && isLeftEdge) {
                    edge = 0;
                }

                if (isLowerEdge && isRightEdge) {
                    edge = 2;
                }

                batch.draw(edgeTextures[edge], x * 64 * scale + scaledResolution.getCenterX(), y * 64 * scale + scaledResolution.getCenterY(), 64 * scale, 64 * scale);
            }
        }

        // Draw cursor at center (Cursor is 9x9 tiles)
        int x = 10, y = 10;
        batch.draw(cursorTexture, x * 64 * scale + scaledResolution.getCenterX() - 64 * scale, y * 64 * scale + scaledResolution.getCenterY() - 64 * scale, 64 * scale * 3, 64 * scale * 3);

        int tile = resolveTile(tiles, this.x + x, this.y + y);

        switch (tile) {
            case 1:
                drawString("Gebirge", 0, -10);
                break;
            case 2:
                drawString("Strand", 0, -10);
                break;
            case 4:
                drawString("Grasland", 0, -10);
                break;
            case 5:
                drawString("Wald", 0, -10);
                break;
            case 0:
                drawString("Ozean", 0, -10);
                break;
        }

        if (this.y > 22) {
            drawString("Feindliches Gebiet", 0, -10-fontLineHeight);
        } else {
            drawString("eigenes Gebiet", 0, -10-fontLineHeight);
        }

        drawString("Koordinaten: " + this.curX + ", " + this.curY, 0, -10-(fontLineHeight*2));

        int sidebarX = (int) (21 * 64 * scale + 10);
        int sidebarY = (int) (21 * 64 * scale);
        int lineSpace = -fontSmLineHeight;

        for (Entity entity : saveFile.getData().getHeldengruppe().getEntities()) {
            Held held = (Held) entity;
            drawString(held.getName(), sidebarX, sidebarY + lineSpace, fontSm);
            lineSpace -= fontSmLineHeight;
            drawString("HP: " + held.getLebenspunkte(), sidebarX, sidebarY + lineSpace, fontSm);
            lineSpace -= fontSmLineHeight;
            drawString("SP: " + held.getStaerke(), sidebarX, sidebarY + lineSpace, fontSm);
            lineSpace -= fontSmLineHeight;
            drawString("AW: " + held.getAngriffswert(), sidebarX, sidebarY + lineSpace, fontSm);
            lineSpace -= fontSmLineHeight;
            drawString("Waffe: " + held.getWaffe().getName(), sidebarX, sidebarY + lineSpace, fontSm);
            lineSpace -= fontSmLineHeight;
            drawString("Material: " + held.getWaffe().getMaterial().getName(), sidebarX, sidebarY + lineSpace, fontSm);
            lineSpace -= fontSmLineHeight;
            drawString("WB: " + held.getWaffe().getBonus(), sidebarX, sidebarY + lineSpace, fontSm);
            lineSpace -= fontSmLineHeight*2;
        }

        batch.end();

        if (backGUI != null) {
            backGUI.render();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.F2)) {
            Pixmap pixmap = Pixmap.createFromFrameBuffer(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
            ByteBuffer pixels = pixmap.getPixels();

            int size = Gdx.graphics.getBackBufferWidth() * Gdx.graphics.getBackBufferHeight() * 4;
            for (int i = 3; i < size; i += 4) {
                pixels.put(i, (byte) 255);
            }

            PixmapIO.writePNG(Gdx.files.absolute(Heldenprojekt.getInstance().getSaveFile().getDataDir() + File.separatorChar + "Screenshot-" + System.currentTimeMillis() + ".png"), pixmap, Deflater.DEFAULT_COMPRESSION, true);
            pixmap.dispose();

            flash = 0.5f;
        }

        if (flash > 0) {
            flash -= Gdx.graphics.getDeltaTime();

            Heldenprojekt.getInstance().getPlatform().enableBlend();
            shapeBatch.begin();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1f, 1f, 1f, 0.1f);
            shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            shapeRenderer.end();
            shapeBatch.end();
            Heldenprojekt.getInstance().getPlatform().disableBlend();


            if (flash < 0) {
                flash = 0;
            }
        }
}

    public void returnToFront() {
        backGUI = null;
    }

    private void drawString(String string, int x, int y) {
        drawString(string, x, y, font);
    }

    private void drawString(String string, int x, int y, BitmapFont font) {
        font.draw(batch, string, scaledResolution.getCenterX() + x, y + scaledResolution.getCenterY());
    }

    private int locationHashCode(int x, int y) {
        return System.identityHashCode(x) ^ System.identityHashCode(y);
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();

        forestTexture.dispose();
        grassTexture.dispose();
        sandTexture.dispose();
        mountainTexture.dispose();
        cursorTexture.dispose();
        campTexture.dispose();
        evilCastleTexture.dispose();
        healerTexture.dispose();

        for (Texture tex : rotationAroundForestTextures) {
            tex.dispose();
        }
        for (Texture tex : rotationAroundGrassTextures) {
            tex.dispose();
        }
        for (Texture tex : rotationAroundMountainTextures) {
            tex.dispose();
        }
        for (Texture tex : rotationAroundSandTextures) {
            tex.dispose();
        }
        for (Texture tex : waterTextures) {
            tex.dispose();
        }
        for (Texture tex : edgeTextures) {
            tex.dispose();
        }

        shapeRenderer.dispose();
        shapeBatch.dispose();
    }

    @Override
    public void resize(float scale) {
        oldScale = this.scale;
        this.scale = scale;

        scaledResolution.rescaleMember(oldScale, scale);

        font.getData().setScale(scale*3f);
        fontSm.getData().setScale(scale*2f);

        fontLineHeight = (int) (font.getLineHeight() + 5);
        fontSmLineHeight = (int) (fontSm.getLineHeight() + 5);

        if (healerGUI != null) {
            healerGUI.resize(scale);
        }

        saveFile.getData().setScale(scale);
        saveFile.save();
    }

    public float getOldScale() {
        return this.oldScale;
    }

    public float getScale() {
        return this.scale;
    }

    public MusicBox getMusicBox() {
        return musicBox;
    }
}
