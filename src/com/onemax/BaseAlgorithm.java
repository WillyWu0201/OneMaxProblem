package com.onemax;

public class BaseAlgorithm {

	static int runBitCount = 100;
	static int iterate = 0;
	
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
