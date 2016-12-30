package dev.flash.eyesworld.textures;

/**
 * Created by Flash on 30/12/2016.
 */
public class ModelTexture {

    public int textureID;

    public ModelTexture(int id){
        this.textureID = id;
    }

    public int getID(){
        return this.textureID;//this or no this better?
    }
}
