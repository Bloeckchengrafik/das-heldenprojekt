import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class MusicBox {
    private Track track;
    private Music main1;
    private Music main2;
    private Music battle1;
    private Music battle2;

    private boolean transition = false;
    private float transitionVolume = 0;
    private float transitionVolumeStep = 0.01f;
    private SpriteBatch batch;
    private BitmapFont font;

    public void create() {
        main1 = Gdx.audio.newMusic(Gdx.files.internal("musicOldStories.mp3"));
        main2 = Gdx.audio.newMusic(Gdx.files.internal("musicWelcomeToMyWorld.mp3"));
        battle1 = Gdx.audio.newMusic(Gdx.files.internal("musicSanKen.mp3"));
        battle2 = Gdx.audio.newMusic(Gdx.files.internal("musicNewWorld.mp3"));

        for (FX value : FX.values()) {
            value.load();
        }

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().scale(1.5f);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    public void update() {
        boolean main1Playing = main1.isPlaying();
        boolean main2Playing = main2.isPlaying();
        boolean battle1Playing = battle1.isPlaying();
        boolean battle2Playing = battle2.isPlaying();

        boolean usingTheme1 = new Random().nextBoolean();
        if (transition) {
            transitionVolume += transitionVolumeStep;

            if (transitionVolume < 0) {
                transitionVolume = 0;
                transition = false;

                main1.stop();
                main2.stop();
                battle1.stop();
                battle2.stop();

                usingTheme1 = new Random().nextBoolean();
            }

            main1.setVolume(transitionVolume);
            main2.setVolume(transitionVolume);
            battle1.setVolume(transitionVolume);
            battle2.setVolume(transitionVolume);
            return;
        }

        if ((track == Track.MAIN_THEME && (battle1Playing || battle2Playing)) || (track == Track.BATTLE_THEME && (main1Playing || main2Playing))) {
            transition = true;
            transitionVolume = 1;
            transitionVolumeStep = -0.05f;
            return;
        }

        if (track == Track.MAIN_THEME && !main1Playing && !main2Playing) {
            usingTheme1 = new Random().nextBoolean();
            if (usingTheme1) {
                main1.setVolume(1);
                main1.play();
            } else {
                main2.setVolume(1);
                main2.play();
            }
        } else if (track == Track.BATTLE_THEME && !battle1Playing && !battle2Playing) {
            usingTheme1 = new Random().nextBoolean();
            if (usingTheme1) {
                battle1.setVolume(1);
                battle1.play();
            } else {
                battle2.setVolume(1);
                battle2.play();
            }
        }

        batch.begin();

        String track = "UNKNOWN";

        if (this.main1.isPlaying()) {
            track = "Old Stories by Kevin Penkin";
        } else if (this.main2.isPlaying()) {
            track = "Welcome to my World by Kevin Penkin";
        } else if (this.battle1.isPlaying()) {
            track = "SAN-KEN [The Three SAGES] by Kevin Penkin";
        } else if (this.battle2.isPlaying()) {
            track = "New World by Kevin Penkin";
        }

        font.draw(batch, "Track: " + track, 10, Gdx.graphics.getHeight()-50);

        batch.end();
    }

    public void useTrack(Track track) {
        this.track = track;
    }

    public enum Track {
        MAIN_THEME,
        BATTLE_THEME
    }

    public enum FX {
        FLY("fly.wav"),
        ATTACK_HERO("attack.wav"),
        ATTACK_ENEMY("attack_goblin.wav");

        private final String soundFile;
        private Sound sound;

        FX(String s) {
            this.soundFile = s;
        }

        public void load() {
            sound = Gdx.audio.newSound(Gdx.files.internal(soundFile));
        }

        public void play() {
            sound.play();
        }
    }
}
