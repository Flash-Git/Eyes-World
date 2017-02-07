package dev.flash.eyesworld.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Flash on 11/01/2017.
 */

public class EntityManager {
	private List<Entity> entities = new ArrayList<>();
	private List<Entity> normalMappedEntities = new ArrayList<>();
	private List<Light> lights = new ArrayList<>();
	private Player player;
	private Light sun;
	private Entity selectedEntity;
	
	public EntityManager() {}
	
	public void addEntity(Entity entity) {
		entities.add(entity);
	}
	
	public void removeEntity(Entity entity) {
		entities.remove(entity);
	}
	
	public void addEntities(List<Entity> entities) {
		this.entities.addAll(entities);
	}
	
	public void removeEntities(List<Entity> entities) {
		for (Entity entity : entities) {
			for (int i = 0; i < this.entities.size(); i++) {
				if (entity.equals(this.entities.get(i))) {
					this.entities.remove(i);
				}
			}
		}
	}
	
	//normalMapped
	public void addNormalMappedEntity(Entity entity) {
		normalMappedEntities.add(entity);
	}
	
	public void removeNormalMappedEntity(Entity entity) {
		normalMappedEntities.remove(entity);
	}
	
	public void addNormalMappedEntities(List<Entity> entities) {
		this.normalMappedEntities.addAll(entities);
	}
	
	public void removeNormalMappedEntities(List<Entity> entities) {
		for (Entity entity : entities) {
			for (int i = 0; i < this.normalMappedEntities.size(); i++) {
				if (entity.equals(this.normalMappedEntities.get(i))) {
					this.normalMappedEntities.remove(i);
				}
			}
		}
	}
	
	//Lights
	public void addLight(Light light) {
		lights.add(light);
	}
	
	public void removeLight(Light light) {
		lights.remove(light);
	}
	
	public void addLights(List<Light> lights) {
		this.lights.addAll(lights);
	}
	
	public void removeLights(List<Light> lights) {
		for (Light light : lights) {
			for (int i = 0; i < this.lights.size(); i++) {
				if (light.equals(this.lights.get(i))) {
					this.lights.remove(i);
				}
			}
		}
	}
	
	//Getters n Setters
	
	public Entity getSelectedEntity() {
		return selectedEntity;
	}
	
	public void setSelectedEntity(Entity selectedEntity) {
		this.selectedEntity = selectedEntity;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public Light getSun() {
		return sun;
	}
	
	public void setSun(Light sun) {
		this.sun = sun;
	}
	
	public List<Entity> getEntities() {
		return entities;
	}
	
	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}
	
	public List<Entity> getNormalMappedEntities() {
		return normalMappedEntities;
	}
	
	public void setNormalMappedEntities(List<Entity> normalMappedEntities) {
		this.normalMappedEntities = normalMappedEntities;
	}
	
	public List<Light> getLights() {
		return lights;
	}
	
	public void setLights(List<Light> lights) {
		this.lights = lights;
	}
	
}
