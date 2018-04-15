package com.onemax;

public class HillClimbing {

	static int runBitCount = 100;
	static int iterate = 0;
	
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
	
	public static AlgorithmObject initial() {
		int bitCount = 0;
		String bitValue = "";
		for(int i = 0; i < runBitCount; i++) {
			double random = (int) (Math.random() * 100) % 2;
			if(random == 1) {
				bitCount++;
				bitValue += "1";
			} else {
				bitValue += "0";
			}
		}
		AlgorithmObject object = new AlgorithmObject();
		object.bitCount = bitCount;
		object.bitValue = bitValue;
		return object;
	}
}
