package dev.flash.eyesworld.engineTester;

import dev.flash.eyesworld.entities.Camera;
import dev.flash.eyesworld.entities.Entity;
import dev.flash.eyesworld.models.TexturedModel;
import dev.flash.eyesworld.renderEngine.DisplayManager;
import dev.flash.eyesworld.renderEngine.Loader;
import dev.flash.eyesworld.models.RawModel;
import dev.flash.eyesworld.renderEngine.OBJLoader;
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
		
		
		
		RawModel model = OBJLoader.loadObjModel("stall", loader);
		
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("stallTexture")));
		
		Entity entity = new Entity(staticModel, new Vector3f(0, 0, -50), 0, 0, 0, 1);
		
		Camera camera = new Camera();
		
		while (!Display.isCloseRequested()) {
			entity.increasePosition(0, 0,0);
			entity.increaseRotation(0, 0.15f, 0);
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
