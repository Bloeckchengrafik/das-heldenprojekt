package io.bloeckchengrafik.heldenprojekt.world;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

public class WorldLoader extends AsynchronousAssetLoader<World, AssetLoaderParameters<World>> {
    public WorldLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, AssetLoaderParameters<World> parameter) {
    }

    @Override
    public World loadSync(AssetManager manager, String fileName, FileHandle file, AssetLoaderParameters<World> parameter) {
        return new World(file.readString());
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, AssetLoaderParameters<World> parameter) {
        return Array.with();
    }
}
