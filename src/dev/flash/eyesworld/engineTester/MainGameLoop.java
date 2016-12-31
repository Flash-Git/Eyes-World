package dev.flash.eyesworld.engineTester;

import dev.flash.eyesworld.entities.Camera;
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
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);
		
		float[] vertices = {
				-0.5f,0.5f,-0.5f,
				-0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,-0.5f,
				0.5f,0.5f,-0.5f,
				
				-0.5f,0.5f,0.5f,
				-0.5f,-0.5f,0.5f,
				0.5f,-0.5f,0.5f,
				0.5f,0.5f,0.5f,
				
				0.5f,0.5f,-0.5f,
				0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,0.5f,
				0.5f,0.5f,0.5f,
				
				-0.5f,0.5f,-0.5f,
				-0.5f,-0.5f,-0.5f,
				-0.5f,-0.5f,0.5f,
				-0.5f,0.5f,0.5f,
				
				-0.5f,0.5f,0.5f,
				-0.5f,0.5f,-0.5f,
				0.5f,0.5f,-0.5f,
				0.5f,0.5f,0.5f,
				
				-0.5f,-0.5f,0.5f,
				-0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,0.5f
		};
		
		float[] textureCoords = {
				
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0
		};
		
		int[] indices = {
				0,1,3,
				3,1,2,
				4,5,7,
				7,5,6,
				8,9,11,
				11,9,10,
				12,13,15,
				15,13,14,
				16,17,19,
				19,17,18,
				20,21,23,
				23,21,22
		};
		
		RawModel model = loader.loadToVao(vertices, textureCoords, indices);
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("Flash_Silver_Squared")));
		
		Entity entity = new Entity(staticModel, new Vector3f(0, 0, -1), 0, 0, 0, 1);
		
		Camera camera = new Camera();
		
		while (!Display.isCloseRequested()) {
			entity.increasePosition(0, 0,0);
			entity.increaseRotation(0.1f, 0.1f, 0);
			camera.move();
			
			renderer.prepare();
			shader.start();
			shader.loadViewMatrix(camera);
			renderer.render(entity, shader);
			shader.stop();
			DisplayManager.updateDisplay();
		}
		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
	
}
