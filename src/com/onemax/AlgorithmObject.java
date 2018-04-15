package com.onemax;

public class AlgorithmObject {
	int bitCount = 0;
	String bitValue = "";
	
	public static AlgorithmObject getRandomNeighbor(AlgorithmObject object) {
		double rand = Math.random() * 100 % 100;
		int index = (int) rand;
		AlgorithmObject newObject = new AlgorithmObject();
		newObject.bitCount = object.bitCount;
		newObject.bitValue = object.bitValue;
		if(newObject.bitValue.charAt(index) == '1') {
			StringBuilder string = new StringBuilder(newObject.bitValue);
			string.setCharAt(index, '0');
			newObject.bitCount -= 1;
			newObject.bitValue = string.toString();
		} else {
			StringBuilder string = new StringBuilder(newObject.bitValue);
			string.setCharAt(index, '1');
			newObject.bitCount += 1;
			newObject.bitValue = string.toString();
		}
		return newObject;
	}
}
