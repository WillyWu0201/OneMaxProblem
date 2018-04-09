package com.onemax;

public class HillClimbing {

	static int runBitCount = 100;
	static int iterate = 0;
	
	public static void startHillClimbing() {
		HillClimbingObject object = initial();
		getHillClimbing(object);
	}
	
	public static void getHillClimbing(HillClimbingObject object) {
		iterate++;
		HillClimbingObject rand = getRandomNeighbor(object);
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
	
	public static HillClimbingObject initial() {
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
		HillClimbingObject object = new HillClimbingObject();
		object.bitCount = bitCount;
		object.bitValue = bitValue;
		return object;
	}
	
	public static HillClimbingObject getRandomNeighbor(HillClimbingObject object) {
		double rand = Math.random() * 100 % 100;
		int index = (int) rand;
		HillClimbingObject newObject = new HillClimbingObject();
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
