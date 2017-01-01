package dev.flash.eyesworld.engineTester;

import dev.flash.eyesworld.entities.Camera;
import dev.flash.eyesworld.entities.Entity;
import dev.flash.eyesworld.entities.Light;
import dev.flash.eyesworld.models.TexturedModel;
import dev.flash.eyesworld.renderEngine.*;
import dev.flash.eyesworld.models.RawModel;
import dev.flash.eyesworld.shaders.StaticShader;
import dev.flash.eyesworld.terrains.Terrain;
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
		
		
		RawModel model = OBJLoader.loadObjModel("dragon", loader);
		
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("grass")));
		
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(1);
		
		Entity entity = new Entity(staticModel, new Vector3f(0, 0, -25), 0, 0, 0, 1);

		Terrain terrain = new Terrain(-1, -1, loader, new ModelTexture(loader.loadTexture("grass")));
		Terrain terrain2 = new Terrain(0, -1, loader, new ModelTexture(loader.loadTexture("grass")));
		
		Light light = new Light(new Vector3f(0, 2000, 20), new Vector3f(1, 1, 1));
		
		
		Camera camera = new Camera();
		
		MasterRenderer renderer = new MasterRenderer();
		
		while (!Display.isCloseRequested()) {
			entity.increasePosition(0, 0, 0);
			entity.increaseRotation(0, 0.15f, 0);
			camera.move();
			
			renderer.processEntity(entity);
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			
			renderer.render(light, camera);
			
			DisplayManager.updateDisplay();
		}
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}
