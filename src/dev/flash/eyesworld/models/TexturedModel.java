package dev.flash.eyesworld.models;

import dev.flash.eyesworld.textures.ModelTexture;

/**
 * Created by Flash on 30/12/2016.
 */
public class TexturedModel {

    private RawModel model;
    private ModelTexture texture;

    public TexturedModel(RawModel model, ModelTexture texture){
        this.model = model;
        this.texture = texture;
    }

    public RawModel getModel() {
        return model;
    }

    public ModelTexture getTexture() {
        return texture;
    }
}
