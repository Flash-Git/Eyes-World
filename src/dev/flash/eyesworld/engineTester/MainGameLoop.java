package dev.flash.eyesworld.engineTester;

import dev.flash.eyesworld.entities.Entity;
import dev.flash.eyesworld.models.TexturedModel;
import dev.flash.eyesworld.renderEngine.DisplayManager;
import dev.flash.eyesworld.renderEngine.Loader;
import dev.flash.eyesworld.models.RawModel;
import dev.flash.eyesworld.renderEngine.Renderer;
import dev.flash.eyesworld.shaders.StaticShader;
import dev.flash.eyesworld.textures.ModelTexture;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Flash on 29/12/2016.
 */

public class MainGameLoop {
	
	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		StaticShader shader = new StaticShader();
		
		float[] vertices = {
				-0.5f, 0.5f, 0f,    //V0
				-0.5f, -0.5f, 0f,   //V1
				0.5f, -0.5f, 0f,    //V2
				0.5f, 0.5f, 0f     //V3
		};
		
		int[] indices = {
				0, 1, 3,    //top left v0, v1, v3
				3, 1, 2     //bot right v3, v1, v2
		};
		
		float[] textureCoords = {
				0, 0,   //V0
				0, 1,   //V1
				1, 1,   //V3
				1, 0    //V4
		};
		
		RawModel model = loader.loadToVao(vertices, textureCoords, indices);
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("Flash_Silver_Squared")));
		
		Entity entity = new Entity(staticModel, new Vector3f(0, 0, 0), 0, 0, 0, 1);
		
		while (!Display.isCloseRequested()) {
			renderer.prepare();
			shader.start();
			entity.increasePosition(0, 0, 0);
			entity.increaseRotation(1, 1, 1);
			//render
			renderer.render(entity, shader);
			shader.stop();
			DisplayManager.updateDisplay();
		}
		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
	
}
