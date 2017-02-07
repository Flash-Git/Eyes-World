package dev.flash.eyesworld.water;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Flash on 11/01/2017.
 */

public class WaterManager {
	private List<WaterTile> waters = new ArrayList<>();
	
	public WaterManager() {}
	
	public void addWater(WaterTile water) {
		waters.add(water);
	}
	
	public void removeWater(WaterTile water) {
		waters.remove(water);
	}
	
	public void addWaters(List<WaterTile> waters) {
		this.waters.addAll(waters);
	}
	
	public void removeWaters(List<WaterTile> waters) {
		for (WaterTile water : waters) {
			for (int i = 0; i < this.waters.size(); i++) {
				if (water.equals(this.waters.get(i))) {
					this.waters.remove(i);
				}
			}
		}
	}
	
	//G and S
	
	public List<WaterTile> getWaters() {
		return waters;
	}
	
	public void setWaters(List<WaterTile> waters) {
		this.waters = waters;
	}
	
}
