package dev.flash.eyesworld.fontRendering;

import dev.flash.eyesworld.fontMeshCreator.FontType;
import dev.flash.eyesworld.fontMeshCreator.GUIText;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.List;
import java.util.Map;

/**
 * Created by Flash on 07/01/2017.
 */

public class FontRenderer {
	
	private FontShader shader;
	
	public FontRenderer() {
		shader = new FontShader();
	}
	
	public void render(Map<FontType, List<GUIText>> texts) {
		prepare();
		for (FontType font : texts.keySet()) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, font.getTextureAtlas());
			for (GUIText text : texts.get(font)) {
				renderText(text);
			}
		}
		endRendering();
	}
	
	public void cleanUp() {
		shader.cleanUp();
	}
	
	private void prepare() {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		shader.start();
	}
	
	private void renderText(GUIText text) {
		//bind VAO
		GL30.glBindVertexArray(text.getMesh());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		shader.loadColour(text.getColour());
		shader.loadTranslation(text.getPosition());
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, text.getVertexCount());
		//unbind VAO
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}
	
	private void endRendering() {
		shader.stop();
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
	}
	
}
