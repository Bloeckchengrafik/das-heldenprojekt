package io.bloeckchengrafik.heldenprojekt.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.bloeckchengrafik.heldenprojekt.Heldenprojekt;
import io.bloeckchengrafik.heldenprojekt.game.*;
import io.bloeckchengrafik.heldenprojekt.utils.CenteredResolution;
import io.bloeckchengrafik.heldenprojekt.utils.MusicBox;
import io.bloeckchengrafik.heldenprojekt.world.EvilCastle;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class FightGUI implements GUI {
    private static final int height = 10, width = 20;
    private final GameGUI gameGUI;
    private final EvilCastle evilCastle;
    private Texture[] uiTextures;
    private final BitmapFont bitmapFont = new BitmapFont();
    private final CenteredResolution scaledResolution = new CenteredResolution((width + 2) * 64, (height + 2) * 64);
    private final SpriteBatch spriteBatch = new SpriteBatch();
    private float scale = 1;
    private final DrawnGoblin[] goblins;
    private final DrawnHero[] heroes;
    private final Kampfregel kampfregel = new Kampfregel();
    private boolean isRunning = false;
    private boolean fightDone = false;
    private double stepTimer = 0;

    public FightGUI(GameGUI gameGUI, EvilCastle evilCastle) {
        this.gameGUI = gameGUI;
        this.evilCastle = evilCastle;

        bitmapFont.getData().scale(2);

        resize(gameGUI.getScale());

        Entity[] evilEntities = evilCastle.getMonsterGruppe().getEntities();

        goblins = new DrawnGoblin[evilEntities.length];
        for (int i = 0; i < evilEntities.length; i++) {
            goblins[i] = new DrawnGoblin((Monster) evilEntities[i]);
        }

        Entity[] heros = Heldenprojekt.getInstance().getSaveFile().getData().getHeldengruppe().getEntities();

        heroes = new DrawnHero[heros.length];
        for (int i = 0; i < heros.length; i++) {
            heroes[i] = new DrawnHero((Held) heros[i]);
        }
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

        Texture[] goblinAttackTextures = new Texture[]{
                Heldenprojekt.getInstance().getAssetManager().get("fight/goblinattack/0.png"),
                Heldenprojekt.getInstance().getAssetManager().get("fight/goblinattack/1.png"),
                Heldenprojekt.getInstance().getAssetManager().get("fight/goblinattack/2.png"),
                Heldenprojekt.getInstance().getAssetManager().get("fight/goblinattack/3.png"),
                Heldenprojekt.getInstance().getAssetManager().get("fight/goblinattack/4.png"),
                Heldenprojekt.getInstance().getAssetManager().get("fight/goblinattack/5.png"),
                Heldenprojekt.getInstance().getAssetManager().get("fight/goblinattack/6.png"),
        };

        Texture[] goblinDeathTextures = new Texture[]{
                Heldenprojekt.getInstance().getAssetManager().get("fight/goblindeath/0.png"),
                Heldenprojekt.getInstance().getAssetManager().get("fight/goblindeath/1.png"),
                Heldenprojekt.getInstance().getAssetManager().get("fight/goblindeath/2.png"),
                Heldenprojekt.getInstance().getAssetManager().get("fight/goblindeath/3.png"),
                Heldenprojekt.getInstance().getAssetManager().get("fight/goblindeath/4.png"),
                Heldenprojekt.getInstance().getAssetManager().get("fight/goblindeath/5.png"),
                Heldenprojekt.getInstance().getAssetManager().get("fight/goblindeath/6.png"),
                Heldenprojekt.getInstance().getAssetManager().get("fight/goblindeath/7.png"),
        };

        Texture[] goblinIdleTextures = new Texture[]{
                Heldenprojekt.getInstance().getAssetManager().get("fight/goblinidle/0.png"),
                Heldenprojekt.getInstance().getAssetManager().get("fight/goblinidle/1.png"),
                Heldenprojekt.getInstance().getAssetManager().get("fight/goblinidle/2.png"),
        };

        Texture[] goblinRunTextures = new Texture[]{
                Heldenprojekt.getInstance().getAssetManager().get("fight/goblinrun/0.png"),
                Heldenprojekt.getInstance().getAssetManager().get("fight/goblinrun/1.png"),
                Heldenprojekt.getInstance().getAssetManager().get("fight/goblinrun/2.png"),
                Heldenprojekt.getInstance().getAssetManager().get("fight/goblinrun/3.png"),
                Heldenprojekt.getInstance().getAssetManager().get("fight/goblinrun/4.png"),
                Heldenprojekt.getInstance().getAssetManager().get("fight/goblinrun/5.png"),
        };

        GoblinAnimationPhase.RUN.setGoblinTextures(goblinRunTextures);
        GoblinAnimationPhase.IDLE.setGoblinTextures(goblinIdleTextures);
        GoblinAnimationPhase.ATTACK.setGoblinTextures(goblinAttackTextures);
        GoblinAnimationPhase.DEATH.setGoblinTextures(goblinDeathTextures);

        Texture[] heroAttackTextures = new Texture[]{
                Heldenprojekt.getInstance().getAssetManager().get("fight/heroattack/0.png"),
                Heldenprojekt.getInstance().getAssetManager().get("fight/heroattack/1.png"),
                Heldenprojekt.getInstance().getAssetManager().get("fight/heroattack/2.png"),
                Heldenprojekt.getInstance().getAssetManager().get("fight/heroattack/3.png"),
        };

        Texture[] heroDeathTextures = new Texture[]{
                Heldenprojekt.getInstance().getAssetManager().get("fight/herodeath/0.png"),
                Heldenprojekt.getInstance().getAssetManager().get("fight/herodeath/1.png"),
                Heldenprojekt.getInstance().getAssetManager().get("fight/herodeath/2.png"),
                Heldenprojekt.getInstance().getAssetManager().get("fight/herodeath/3.png"),
        };

        Texture[] heroidle = new Texture[]{
                Heldenprojekt.getInstance().getAssetManager().get("fight/heroidle/0.png"),
                Heldenprojekt.getInstance().getAssetManager().get("fight/heroidle/1.png"),
                Heldenprojekt.getInstance().getAssetManager().get("fight/heroidle/2.png"),
                Heldenprojekt.getInstance().getAssetManager().get("fight/heroidle/3.png"),
        };

        Texture[] herorun = new Texture[] {
                Heldenprojekt.getInstance().getAssetManager().get("fight/herorun/0.png"),
                Heldenprojekt.getInstance().getAssetManager().get("fight/herorun/1.png"),
                Heldenprojekt.getInstance().getAssetManager().get("fight/herorun/2.png"),
                Heldenprojekt.getInstance().getAssetManager().get("fight/herorun/3.png"),
        };

        HeroAnimationPhase.RUN.setHeroTexture(herorun);
        HeroAnimationPhase.IDLE.setHeroTexture(heroidle);
        HeroAnimationPhase.ATTACK.setHeroTexture(heroAttackTextures);
        HeroAnimationPhase.DEATH.setHeroTexture(heroDeathTextures);

        bitmapFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        resize(gameGUI.getScale());

        gameGUI.getMusicBox().useTrack(MusicBox.Track.BATTLE_THEME);
    }

    @Override
    public void update(double delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            gameGUI.returnToFront();
            gameGUI.getMusicBox().useTrack(MusicBox.Track.MAIN_THEME);
            isRunning = false;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            isRunning = true;
        }

        if (!isRunning) return;

        stepTimer += delta;
        if (stepTimer > 1) {
            stepTimer = 0;

            kampfregel.gruppenKampfSchritt(evilCastle.getMonsterGruppe(), Heldenprojekt.getInstance().getSaveFile().getData().getHeldengruppe());

            if (entitiesAreDead(evilCastle.getMonsterGruppe().getEntities()) || entitiesAreDead(Heldenprojekt.getInstance().getSaveFile().getData().getHeldengruppe().getEntities())) {
                isRunning = false;
                fightDone = true;
            }

            Heldenprojekt.getInstance().getSaveFile().save();
        }
    }

    public boolean entitiesAreDead(Entity[] entities) {
        int lebenspunkte = 0;
        for (Entity entity : entities) {
            lebenspunkte += entity.getLebenspunkte();
        }
        return lebenspunkte <= 0;
    }

    @Override
    public void render() {
        spriteBatch.begin();
        GUI.renderImageBackground(width, height, uiTextures, spriteBatch, scaledResolution, scale);

        // Title
        bitmapFont.draw(spriteBatch, fightDone? "Kampf abgeschlossen!" : "Kampf! (drücke f um zu starten)", scaledResolution.getCenterX()+30, scaledResolution.getCenterY() + scaledResolution.getMemberHeight() - bitmapFont.getData().lineHeight);
        int sidebarX = -250;
        int sidebarY = (int) (21 * 64 * gameGUI.getScale());
        int lineSpace = -gameGUI.getFontSmLineHeight();

        for (Entity entity : evilCastle.getMonsterGruppe().getEntities()) {
            Monster monster = (Monster) entity;
            drawString(monster.getName(), sidebarX, sidebarY + lineSpace);
            lineSpace -= gameGUI.getFontSmLineHeight();
            drawString("HP: " + monster.getLebenspunkte(), sidebarX, sidebarY + lineSpace);
            lineSpace -= gameGUI.getFontSmLineHeight();
            drawString("SP: " + monster.getStaerke(), sidebarX, sidebarY + lineSpace);
            lineSpace -= gameGUI.getFontSmLineHeight();
            drawString("AW: " + monster.getAngriffswert(), sidebarX, sidebarY + lineSpace);
            lineSpace -= gameGUI.getFontSmLineHeight()*2;
        }


        ArrayList<Runnable> renderableMonsters = new ArrayList<>();
        ArrayList<Runnable> renderableHeros = new ArrayList<>();

        AtomicInteger monsterHeight = new AtomicInteger(50);
        AtomicReference<Float> monsterDarkness = new AtomicReference<>(0.2f);

        for (DrawnGoblin goblin : goblins) {
            renderableMonsters.add(() -> {
                if (isRunning) goblin.update(Gdx.graphics.getDeltaTime());
                else goblin.updateSimple(Gdx.graphics.getDeltaTime());

                spriteBatch.setColor(monsterDarkness.get(), monsterDarkness.get(), monsterDarkness.get(), 1);
                goblin.draw(spriteBatch, (int) (scaledResolution.getCenterX()-100*scale), scaledResolution.getCenterY(), (int) (monsterHeight.get() *scale));
                monsterHeight.addAndGet(-50);
                monsterDarkness.updateAndGet(v -> v + 0.2f);
            });
        }

        AtomicInteger heroHeight = new AtomicInteger((int) (-100*scale));
        AtomicReference<Float> heroDarkness = new AtomicReference<>(0.2f);

        for (DrawnHero hero : heroes) {
            renderableHeros.add(() -> {
                if (isRunning) hero.update(Gdx.graphics.getDeltaTime());
                else hero.updateSimple(Gdx.graphics.getDeltaTime());

                spriteBatch.setColor(heroDarkness.get(), heroDarkness.get(), heroDarkness.get(), 1);
                hero.draw(spriteBatch, (int) (scaledResolution.getCenterX()-100*scale), scaledResolution.getCenterY(), (int) (heroHeight.get() *scale));
                heroHeight.addAndGet(-50);
                heroDarkness.updateAndGet(v -> v + 0.2f);
            });
        }

        for (int i = 0; i < Math.max(renderableMonsters.size(), renderableHeros.size()); i++) {
            if (i < renderableMonsters.size()) renderableMonsters.get(i).run();
            if (i < renderableHeros.size()) renderableHeros.get(i).run();
        }

        spriteBatch.end();
    }

    private void drawString(String string, int x, int y) {
        gameGUI.getFontSm().draw(spriteBatch, string, gameGUI.getCenteredResolution().getCenterX() + x, y + gameGUI.getCenteredResolution().getCenterY());
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

        bitmapFont.getData().setScale(scale * 4);
    }

    static class DrawnGoblin {
        private int x, targetX;
        private GoblinAnimationPhase phase = GoblinAnimationPhase.IDLE;
        private int frame = 0;
        private double timer = 0;
        private final Monster monster;

        DrawnGoblin(Monster monster) {
            this.monster = monster;
            x = new Wuerfel(100).wuerfeln();
            targetX = x;
        }

        public void updateSimple(double delta) {
            timer += delta;

            if (this.monster.getLebenspunkte() == 0) {
                phase = GoblinAnimationPhase.DEATH;
                frame = phase.getGoblinTextures().length-1;
                return;
            }

            if (timer > 0.5) {
                timer = 0;

                if (phase != GoblinAnimationPhase.DEATH) frame++;
            }

            if (frame >= phase.getGoblinTextures().length-1) {
                frame = 0;
            }
        }

        public void update(double delta) {
            timer += delta;

            if (timer > 0.5) {
                timer = 0;
                frame++;
            }

            if (phase == GoblinAnimationPhase.ATTACK) {
                if (frame == phase.goblinTextures.length-1) {
                    phase = GoblinAnimationPhase.IDLE;
                    frame = 0;
                }
            }

            if (frame == phase.getGoblinTextures().length-1 && !phase.equals(GoblinAnimationPhase.DEATH)) {
                frame = 0;
            }

            if (x < targetX) {
                x+=1;
            }

            if (x > targetX) {
                x-=1;
            }

            if (Math.abs(x - targetX) < 0.1) {
                x = targetX;
            } else {
                phase = GoblinAnimationPhase.RUN;
            }

            if (x == targetX && phase == GoblinAnimationPhase.RUN) {
                phase = GoblinAnimationPhase.IDLE;
            }

            if (phase == GoblinAnimationPhase.IDLE && new Wuerfel(100).wuerfeln() == 1) {
                phase = GoblinAnimationPhase.ATTACK;
                frame = 0;
            }

            if (monster.getLebenspunkte() <= 0 && phase != GoblinAnimationPhase.DEATH) {
                phase = GoblinAnimationPhase.DEATH;
                frame = 0;
            }

            if (phase == GoblinAnimationPhase.IDLE && new Wuerfel(100).wuerfeln() == 1) {
                phase = GoblinAnimationPhase.RUN;
                frame = 0;
                targetX = new Wuerfel(350).wuerfeln()+50;
            }


        }

        public void draw(SpriteBatch spriteBatch, int x, int y, int height) {
            if (frame >= phase.getGoblinTextures().length-1) {
                if (!phase.equals(GoblinAnimationPhase.DEATH)) {
                    frame = 0;
                } else {
                    frame = phase.getGoblinTextures().length-1;
                }
            }

            spriteBatch.draw(phase.getGoblinTextures()[frame], x + this.x, y+height, 256*2, 256*2);
        }
    }

    class DrawnHero {
        private int x, targetX;
        private HeroAnimationPhase phase = HeroAnimationPhase.IDLE;
        private int frame = 0;
        private double timer = 0;
        private final Held held;

        DrawnHero(Held held) {
            this.held = held;
            x = (int) (new Wuerfel(100).wuerfeln()+500*scale);
            targetX = x;
        }

        public void updateSimple(double delta) {
            timer += delta;

            if (phase != HeroAnimationPhase.DEATH) {
                phase = HeroAnimationPhase.IDLE;
            }

            if (timer > 0.5) {
                timer = 0;
                if (phase != HeroAnimationPhase.DEATH) frame++;
            }

            if (frame >= phase.getHeroTexture().length-1) {
                frame = 0;
            }
        }

        public void update(double delta) {
            timer += delta;

            if (timer > 0.5) {
                timer = 0;
                frame++;
            }

            if (phase == HeroAnimationPhase.ATTACK) {
                if (frame == phase.getHeroTexture().length-1) {
                    phase = HeroAnimationPhase.IDLE;
                    frame = 0;
                }
            }

            if (frame == phase.getHeroTexture().length-1) {
                frame = 0;
            }

            if (x < targetX) {
                x+=1;
            }

            if (x > targetX) {
                x-=1;
            }

            if (Math.abs(x - targetX) < 0.1) {
                x = targetX;
            } else {
                phase = HeroAnimationPhase.RUN;
            }

            if (x == targetX && phase == HeroAnimationPhase.RUN) {
                phase = HeroAnimationPhase.IDLE;
            }

            if (phase == HeroAnimationPhase.IDLE && new Wuerfel(100).wuerfeln() == 1) {
                phase = HeroAnimationPhase.ATTACK;
                frame = 0;
            }

            if (held.getLebenspunkte() <= 0 && phase != HeroAnimationPhase.DEATH) {
                phase = HeroAnimationPhase.DEATH;
                frame = 0;
            }

            if (phase == HeroAnimationPhase.IDLE && new Wuerfel(100).wuerfeln() == 1) {
                phase = HeroAnimationPhase.RUN;
                frame = 0;
                targetX = new Wuerfel(350).wuerfeln()+50;
            }


        }

        public void draw(SpriteBatch spriteBatch, int x, int y, int height) {
            if (frame >= phase.getHeroTexture().length-1) {
                frame = 0;
            }

            float darkness = spriteBatch.getColor().a;

            /* Set transparent if dead */
            if (held.getLebenspunkte() <= 0) {
                spriteBatch.setColor(darkness, darkness, darkness, 0.25f);
            }

            spriteBatch.draw(phase.getHeroTexture()[frame], x + this.x, y+height, 256*3, 256*3);

            /* Reset transparency */
            spriteBatch.setColor(darkness, darkness, darkness, 1f);
        }
    }

    enum GoblinAnimationPhase {
        IDLE,
        ATTACK,
        RUN,
        DEATH;

        private Texture[] goblinTextures;

        public Texture[] getGoblinTextures() {
            return goblinTextures;
        }

        public void setGoblinTextures(Texture[] goblinTextures) {
            this.goblinTextures = goblinTextures;
        }
    }

    enum HeroAnimationPhase {
        IDLE,
        ATTACK,
        RUN,
        DEATH;

        private Texture[] heroTexture;

        public Texture[] getHeroTexture() {
            return heroTexture;
        }

        public void setHeroTexture(Texture[] heroTexture) {
            this.heroTexture = heroTexture;
        }
    }
}
