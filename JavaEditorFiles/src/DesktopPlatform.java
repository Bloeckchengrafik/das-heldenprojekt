import com.badlogic.gdx.Gdx;
import org.lwjgl.opengl.GL11;

public class DesktopPlatform implements PlatformProvider {
    @Override
    public void enableBlend() {
        Gdx.gl.glEnable(GL11.GL_BLEND);
        Gdx.gl.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void disableBlend() {
        Gdx.gl.glDisable(GL11.GL_BLEND);
    }
}
