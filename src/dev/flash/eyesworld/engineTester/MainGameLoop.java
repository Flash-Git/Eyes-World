package dev.flash.eyesworld.engineTester;

import dev.flash.eyesworld.entities.*;
import dev.flash.eyesworld.fontMeshCreator.FontType;
import dev.flash.eyesworld.fontMeshCreator.GUIText;
import dev.flash.eyesworld.fontRendering.TextMaster;
import dev.flash.eyesworld.guis.GuiManager;
import dev.flash.eyesworld.guis.GuiRenderer;
import dev.flash.eyesworld.guis.GuiTexture;
import dev.flash.eyesworld.models.RawModel;
import dev.flash.eyesworld.models.TexturedModel;
import dev.flash.eyesworld.normalMappingObjConverter.NormalMappedObjLoader;
import dev.flash.eyesworld.objConverter.ModelData;
import dev.flash.eyesworld.objConverter.OBJFileLoader;
import dev.flash.eyesworld.particles.Particle;
import dev.flash.eyesworld.particles.ParticleMaster;
import dev.flash.eyesworld.particles.ParticleSystem;
import dev.flash.eyesworld.particles.ParticleTexture;
import dev.flash.eyesworld.renderEngine.DisplayManager;
import dev.flash.eyesworld.renderEngine.Loader;
import dev.flash.eyesworld.renderEngine.MasterRenderer;
import dev.flash.eyesworld.terrains.HeightsGenerator;
import dev.flash.eyesworld.terrains.Terrain;
import dev.flash.eyesworld.terrains.TerrainManager;
import dev.flash.eyesworld.textures.ModelTexture;
import dev.flash.eyesworld.textures.TerrainTexture;
import dev.flash.eyesworld.textures.TerrainTexturePack;
import dev.flash.eyesworld.utils.EntitySelector;
import dev.flash.eyesworld.water.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Flash on 29/12/2016.
 */

public class MainGameLoop {
	private static TerrainManager terrainManager = new TerrainManager();
	private static EntityManager entityManager = new EntityManager();
	private static WaterManager waterManager = new WaterManager();
	private static GuiManager guiManager = new GuiManager();
	private static Camera camera;
	
	
	public static void main(String[] args) {
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		TextMaster.init(loader);
		MasterRenderer renderer = new MasterRenderer(loader);
		ParticleMaster.init(loader, renderer.getProjectionMatrix());
		
		
		WaterFrameBuffers buffers = new WaterFrameBuffers();
		WaterShader waterShader = new WaterShader();
		WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), buffers);
		
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		
		//createWaters();
		createTerrains(loader);
		createEntities(loader);
		createGUIs(loader);
		camera = new Camera(entityManager.getPlayer());
		
		EntitySelector picker = new EntitySelector(camera, renderer.getProjectionMatrix(), terrainManager);
		
		ParticleTexture particleTexture = new ParticleTexture(loader.loadTexture("fire"), 8);
		
		ParticleSystem particleSystem = new ParticleSystem(particleTexture, 150, 3f, 0.005f, 1f, 3f);
		particleSystem.setDirection(new Vector3f(0, 1, 0), 0.2f);
		particleSystem.setScaleError(0.3f);
		particleSystem.setSpeedError(0.4f);
		particleSystem.setLifeError(0.7f);
		Random random = new Random();
		
		Mouse.setGrabbed(true);
		while (!Display.isCloseRequested()) {
			Display.setTitle(Float.toString(1000 / DisplayManager.getFrameTimeMillis()));
			
			////UPDATE////
			entityManager.getPlayer().move(terrainManager.getTerrain(entityManager.getPlayer().getPosition().x, entityManager.getPlayer().getPosition().z));
			camera.move();
			picker.update();
			
			particleSystem.generateParticles(new Vector3f(entityManager.getPlayer().getPosition().x + random.nextFloat() * 2 - 1, entityManager.getPlayer().getPosition().y + 10 + random.nextFloat(), entityManager.getPlayer().getPosition().z + random.nextFloat() * 2 - 1));
			
			ParticleMaster.update(camera);
			
			grabEntities(picker);
			makeThemBounce();
			
			////RENDER////
			renderWater(buffers, renderer);
			renderer.renderScene(entityManager.getEntities(), entityManager.getNormalMappedEntities(), terrainManager.getTerrains(), entityManager.getLights(), camera, new Vector4f(0, -1, 0, 100000));//hacky and gross cos drivers ignore command
			waterRenderer.render(waterManager.getWaters(), camera, entityManager.getSun());
			
			ParticleMaster.renderParticles(camera);
			
			guiRenderer.render(guiManager.getGuis());
			//TextMaster.render();
			
			//Push to Screen
			DisplayManager.updateDisplay();
		}
		ParticleMaster.cleanUp();
		TextMaster.cleanUp();
		buffers.cleanUp();
		guiRenderer.cleanUp();
		renderer.cleanUp();//water renderer not need to clean up?
		waterShader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
	
	private static void grabEntities(EntitySelector picker) {
		try {
			entityManager.getSelectedEntity().moveTowards(picker.getCurrentTerrainPoint());
		} catch (Exception e) {
			if (Mouse.isButtonDown(0)) {
				for (Entity entity : entityManager.getEntities()) {
					try {
						if (Math.floor(picker.getCurrentTerrainPoint().x / 10) == Math.floor(entity.getPosition().x / 10) && Math.floor(picker.getCurrentTerrainPoint().z / 10) == Math.floor(entity.getPosition().z / 10))
							entityManager.setSelectedEntity(entity);
					} catch (Exception e1) {
						
					}
				}
			}
		}
		try {
			entityManager.getSelectedEntity().moveTowards(picker.getCurrentTerrainPoint());
		} catch (Exception e) {
			if (Mouse.isButtonDown(0)) {
				for (Entity entity : entityManager.getNormalMappedEntities()) {
					try {
						if (Math.floor(picker.getCurrentTerrainPoint().x / 10) == Math.floor(entity.getPosition().x / 10) && Math.floor(picker.getCurrentTerrainPoint().z / 10) == Math.floor(entity.getPosition().z / 10))
							entityManager.setSelectedEntity(entity);
					} catch (Exception e1) {
						
					}
				}
			}
		}
		if (Mouse.isButtonDown(1)) {
			try {
				entityManager.getSelectedEntity().setPosition(new Vector3f(entityManager.getSelectedEntity().getPosition().x, entityManager.getSelectedEntity().getPosition().y + 0, entityManager.getSelectedEntity().getPosition().z));
				entityManager.setSelectedEntity(null);
			} catch (Exception e) {
				
			}
		}
	}
	
	private static void renderWater(WaterFrameBuffers buffers, MasterRenderer renderer) {
		GL11.glEnable(GL30.GL_CLIP_DISTANCE0);//allows clipping/culling on one side of a plane TODO verify 2:30 3rd water opengl thinmatrix
		
		buffers.bindReflectionFrameBuffer();
		float distance = 2 * (camera.getPosition().y - waterManager.getWaters().get(0).getHeight());//only works on one water height
		camera.getPosition().y -= distance;
		camera.invertPitch();
		
		renderer.renderScene(entityManager.getEntities(), entityManager.getNormalMappedEntities(), terrainManager.getTerrains(), entityManager.getLights(), camera, new Vector4f(0, 1, 0, -waterManager.getWaters().get(0).getHeight() + 1f));//little offset reduces edge water glitch
		
		camera.getPosition().y += distance;
		camera.invertPitch();
		
		buffers.bindRefractionFrameBuffer();
		renderer.renderScene(entityManager.getEntities(), entityManager.getNormalMappedEntities(), terrainManager.getTerrains(), entityManager.getLights(), camera, new Vector4f(0, -1, 0, waterManager.getWaters().get(0).getHeight() + 1f));//little offset reduces edge water glitch
		
		GL11.glDisable(GL30.GL_CLIP_DISTANCE0);//Some drivers ignore this command
		buffers.unbindCurrentFrameBuffer();
	}
	
	private static void createEntities(Loader loader) {
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
		Player player = new Player(staticDragonModel, new Vector3f(500, 100, 500), 0, 270, 0, 1);
		
		//Lamps
		ModelData lampData = OBJFileLoader.loadOBJ("lamp");
		RawModel lampModel = loader.loadToVAO(
				lampData.getVertices(), lampData.getTextureCoords(), lampData.getNormals(), lampData.getIndices());
		TexturedModel staticLampModel = new TexturedModel(lampModel, new ModelTexture(loader.loadTexture("lamp")));
		//staticLampModel.getTexture().setTransparency(true);
		staticLampModel.getTexture().setFakeLighting(true);
		
		//Fern
		ModelData fernData = OBJFileLoader.loadOBJ("fern");
		
		RawModel fernModel = loader.loadToVAO(
				fernData.getVertices(), fernData.getTextureCoords(), fernData.getNormals(), fernData.getIndices());
		ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fern"));
		fernTextureAtlas.setNumberOfRows(2);
		
		TexturedModel staticFernModel = new TexturedModel(fernModel, fernTextureAtlas);
		staticFernModel.getTexture().setTransparency(true);
		//staticFernModel.getTexture().setFakeLighting(true);
		
		List<Entity> ferns = new ArrayList<>();
		Random random = new Random();
		for (int i = 0; i < 1200; i++) {
			float x = random.nextFloat() * 16000 - 8000;
			float z = random.nextFloat() * -16000 + 8000;
			float y = terrainManager.getTerrain(x, z).getHeightOfTerrain(x, z);
			
			ferns.add(new Entity(staticFernModel, random.nextInt(4), new Vector3f(x, y, z), 0, random.nextFloat() * 180, 0, 1));
		}
		
		//Grass
		ModelData treeData = OBJFileLoader.loadOBJ("lowPolyTree");
		RawModel treeModel = loader.loadToVAO(treeData.getVertices(), treeData.getTextureCoords(), treeData.getNormals(), treeData.getIndices());
		TexturedModel staticTreeModel = new TexturedModel(treeModel, new ModelTexture(loader.loadTexture("lowPolyTree")));
		ModelTexture treeTexture = staticTreeModel.getTexture();
		
		treeTexture.setShineDamper(10);
		treeTexture.setReflectivity(0.25f);
		
		List<Entity> trees = new ArrayList<>();
		for (int i = 0; i < 1600; i++) {
			float x = random.nextFloat() * 16000 - 8000;
			float z = random.nextFloat() * -16000 + 8000;
			float y = terrainManager.getTerrain(x, z).getHeightOfTerrain(x, z);
			
			trees.add(new Entity(staticTreeModel, new Vector3f(x, y, z), 0, random.nextFloat() * 180, 0, 1));
		}
		
		List<Light> lights = new ArrayList<>();
		
		Light sun = new Light(new Vector3f(0, 5000, -2000), new Vector3f(0.8f, 0.8f, 0.8f));
		
		List<LightEntity> lamps = new ArrayList<>();
		
		for (int i = 0; i < 5; i++) {
			float x = random.nextFloat() * 16000 - 8000;
			float z = random.nextFloat() * -16000 + 8000;
			float y = terrainManager.getTerrain(x, z).getHeightOfTerrain(x, z);
			
			lamps.add(new LightEntity(staticLampModel, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, 1,
					new Light(new Vector3f(x, y + 14, z),
							new Vector3f(random.nextFloat() * 1.5f - 0.5f, random.nextFloat() * 1.5f - 0.5f, random.nextFloat() * 1.5f - 0.5f), new Vector3f(0.55f, 0.00035f, 0.00015f))));
			lights.add(lamps.get(i).getLight());
		}
		lights.add(sun);
		entityManager.setSun(sun);
		
		TexturedModel barrelModel = new TexturedModel(NormalMappedObjLoader.loadOBJ("barrel", loader), new ModelTexture(loader.loadTexture("barrel")));
		barrelModel.getTexture().setReflectivity(0.5f);
		barrelModel.getTexture().setShineDamper(10);
		barrelModel.getTexture().setNormalMap(loader.loadTexture("barrelNormal"));
		
		List<Entity> barrels = new ArrayList<>();
		for (int i = 0; i < 500; i++) {
			float x = random.nextFloat() * 16000 - 8000;
			float z = random.nextFloat() * -10600 + 8000;
			float y = terrainManager.getTerrain(x, z).getHeightOfTerrain(x, z) + 20;
			
			barrels.add(new Entity(barrelModel, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, random.nextFloat() + 1));
		}
		
		List<Entity> entities = new ArrayList<>();
		List<Entity> normalMappedEntities = new ArrayList<>();
		
		entities.addAll(trees);
		entities.addAll(lamps);
		entities.addAll(ferns);
		entities.add(player);
		entities.add(dragonEntity);
		normalMappedEntities.addAll(barrels);
		
		entityManager.addEntities(entities);
		entityManager.addNormalMappedEntities(normalMappedEntities);
		entityManager.setPlayer(player);
		entityManager.addLights(lights);
	}
	
	private static void createTerrains(Loader loader) {
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		
		Random random = new Random();
		int seed = random.nextInt(1000000000);
		List<Terrain> terrains = new ArrayList<>();
		List<WaterTile> waters = new ArrayList<>();
		
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				terrains.add(new Terrain(i - 10, j - 10, loader, texturePack, blendMap, seed));
				waters.add(new WaterTile(i * 512 - 10 * 512 + 256, j * 512 - 10 * 512 + 256, -15f));
			}
		}
		
		waterManager.addWaters(waters);
		terrainManager.addTerrains(terrains);
		terrainManager.setDummyTerrain(new Terrain(0, 0, loader, texturePack, blendMap, seed));
	}
	
	private static void makeThemBounce() {
		Random random = new Random();
		for (Entity entity : entityManager.getEntities()) {
			entity.i += random.nextFloat() / 50;
			if (entity.equals(entityManager.getPlayer()) || entity.equals(entityManager.getSelectedEntity()))
				continue;
			entity.increasePosition(0, (float) Math.sin(entity.i / 2) * 2.5f - 0.5f, 0);
			entity.increaseRotation(0, 0.4f, 0);
			entity.setPosition(new Vector3f(entity.getPosition().x, Math.max(terrainManager.getTerrain(entity.getPosition().x, entity.getPosition().z).getHeightOfTerrain(entity.getPosition().x, entity.getPosition().z), entity.getPosition().y), entity.getPosition().z));
			
		}
		for (Entity entity : entityManager.getNormalMappedEntities()) {
			entity.i += random.nextFloat() / 50;
			if (entity.equals(entityManager.getPlayer()))
				continue;
			entity.increasePosition(0, (float) Math.sin(entity.i / 2) * 2.5f - entity.getScale() / 4, 0);
			entity.increaseRotation(0, -0.2f, 0);
			entity.setPosition(new Vector3f(entity.getPosition().x, Math.max(terrainManager.getTerrain(entity.getPosition().x, entity.getPosition().z).getHeightOfTerrain(entity.getPosition().x, entity.getPosition().z) - entity.getScale() * 4 + 17, entity.getPosition().y), entity.getPosition().z));
		}
	}
	
	private static void createGUIs(Loader loader) {
		FontType font = new FontType(loader.loadTexture("Candara", 0), new File("res/Candara.fnt"));
		GUIText text = new GUIText("TEST TEXT THAT SHOULD ALSO WRAP AROUND IF IT IS LONG ENOUGH", 3, font, new Vector2f(0.25f, 0.15f), 0.5f, true);
		text.setColour(0.1f, 0.1f, 0.1f);
		
		List<GuiTexture> guis = new ArrayList<>();
		GuiTexture flashIcon = new GuiTexture(loader.loadTexture("Flash_Silver_Squared"), new Vector2f(0.9f, -0.85f), new Vector2f(0.1f, 0.15f));
		guis.add(flashIcon);
		guiManager.addGuis(guis);
	}
}
