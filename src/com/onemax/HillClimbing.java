package com.onemax;

public class HillClimbing extends BaseAlgorithm {
	
	public static void startHillClimbing() {
		AlgorithmObject object = initial();
		getHillClimbing(object);
	}
	
	public static void getHillClimbing(AlgorithmObject object) {
		iterate++;
		AlgorithmObject rand = AlgorithmObject.getRandomNeighbor(object);
		if(rand.bitCount > object.bitCount) {
			object.bitCount = rand.bitCount;
			object.bitValue = rand.bitValue;
		}
		String string = "" + object.bitCount;
		System.out.println(string);
		if(object.bitCount != runBitCount) {
			getHillClimbing(object);
		}
	}
}
