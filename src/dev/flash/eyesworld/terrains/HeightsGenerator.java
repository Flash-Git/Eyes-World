package dev.flash.eyesworld.terrains;

import java.util.Random;

/**
 * Created by Flash on 28/01/2017.
 */

public class HeightsGenerator {
	private static final float AMLPLITUDE = 70f;
	
	private Random random = new Random();
	private int seed;
	
	public HeightsGenerator() {
		this.seed = random.nextInt(1000000000);
	}
	
	public float generateHeight(int x, int z) {
		return 1;
	}
	
	
}
