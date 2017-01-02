package dev.flash.eyesworld.engineTester;

import dev.flash.eyesworld.entities.Camera;
import dev.flash.eyesworld.entities.Entity;
import dev.flash.eyesworld.entities.Light;
import dev.flash.eyesworld.entities.Player;
import dev.flash.eyesworld.models.TexturedModel;
import dev.flash.eyesworld.objConverter.ModelData;
import dev.flash.eyesworld.objConverter.OBJFileLoader;
import dev.flash.eyesworld.renderEngine.*;
import dev.flash.eyesworld.models.RawModel;
import dev.flash.eyesworld.shaders.StaticShader;
import dev.flash.eyesworld.terrains.Terrain;
import dev.flash.eyesworld.terrains.TerrainTexture;
import dev.flash.eyesworld.terrains.TerrainTexturePack;
import dev.flash.eyesworld.textures.ModelTexture;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Flash on 29/12/2016.
 */

public class MainGameLoop {
	
	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		
		//Terrain Texture Stuff
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		
		Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "heightMap");
		
		//Dragon
		ModelData dragonData = OBJFileLoader.loadOBJ("dragon");
		RawModel dragonModel = loader.loadToVAO(
				dragonData.getVertices(), dragonData.getTextureCoords(), dragonData.getNormals(), dragonData.getIndices());
		TexturedModel staticDragonModel = new TexturedModel(dragonModel, new ModelTexture(loader.loadTexture("white")));
		ModelTexture dragonTexture = staticDragonModel.getTexture();
		dragonTexture.setShineDamper(10);
		dragonTexture.setReflectivity(1);
		Entity dragonEntity = new Entity(staticDragonModel, new Vector3f(0, 0, -25), 0, 0, 0, 1);
		
		//Player
		Player player = new Player(staticDragonModel, new Vector3f(100, 0, -5), 0, 270, 0, 1);
		
		
		//Grass
		ModelData treeData = OBJFileLoader.loadOBJ("lowPolyTree");
		RawModel treeModel = loader.loadToVAO(treeData.getVertices(), treeData.getTextureCoords(), treeData.getNormals(), treeData.getIndices());
		TexturedModel staticTreeModel = new TexturedModel(treeModel, new ModelTexture(loader.loadTexture("lowPolyTree")));
		ModelTexture treeTexture = staticTreeModel.getTexture();
		
		treeTexture.setShineDamper(10);
		treeTexture.setReflectivity(0.25f);
		//staticGrassModel.getTexture().setTransparency(true);
		//staticGrassModel.getTexture().setFakeLighting(true);
		List<Entity> trees = new ArrayList<Entity>();
		Random random = new Random();
		for (int i = 0; i < 90; i++) {
			float x = random.nextFloat() * 800;
			float z = random.nextFloat() * -800;
			float y = terrain.getHeightOfTerrain(x, z);
			
			trees.add(new Entity(staticTreeModel, new Vector3f(x, y, z), 0, random.nextFloat() * 180, 0, 1));
		}
		
		Light light = new Light(new Vector3f(0, 500, -20), new Vector3f(1, 1, 1));
		
		Camera camera = new Camera(player);
		
		MasterRenderer renderer = new MasterRenderer();
		float x = 0;
		while (!Display.isCloseRequested()) {
			x += 0.1f;
			dragonEntity.increasePosition(0, (float) (Math.sin(x)), 0);
			dragonEntity.increaseRotation(0, 0.15f, 0);
			
			player.move(terrain);
			camera.move();
			renderer.processEntity(player);
			renderer.processEntity(dragonEntity);
			for (Entity tree : trees)
				renderer.processEntity(tree);
			renderer.processTerrain(terrain);
			
			renderer.render(light, camera);
			
			DisplayManager.updateDisplay();
		}
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}
