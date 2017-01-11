package dev.flash.eyesworld.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Flash on 11/01/2017.
 */

public class EntityManager {
	private List<Entity> entities = new ArrayList<>();
	private List<Entity> normalMappedEntities = new ArrayList<>();
	private Entity player;
	
	
	public EntityManager() {
	}
	
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
	
	//Getters n Setters
	
	public Entity getPlayer() {
		return player;
	}
	
	public void setPlayer(Entity player) {
		this.player = player;
	}
	
}
