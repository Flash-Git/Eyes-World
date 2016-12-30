package dev.flash.eyesworld.renderEngine;

import dev.flash.eyesworld.models.RawModel;
import dev.flash.eyesworld.models.TexturedModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * Created by Flash on 29/12/2016.
 */
public class Renderer {
    public void prepare(){
        GL11.glClearColor(1, 0,0,1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
    }

    public void render(TexturedModel texturedModel){
        RawModel model = texturedModel.getModel();
        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getID());
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }
}
