package com.onemax;

public class SimulateAneal extends BaseAlgorithm {

	static double temperature = 100.0;       // 初始温度
    static double coolingRate = 0.99;        // 下降速率
    static double absolutetemperature = 0.00;// 凝固温度
    
    public static void startSimulateAneal() {
    	AlgorithmObject object = initial();
		getSimulateAneal(object);
	}
	
	private static void getSimulateAneal(AlgorithmObject object) {
		iterate++;
		AlgorithmObject rand = AlgorithmObject.getRandomNeighbor(object);
		if(rand.bitCount > object.bitCount) {
			object.bitCount = rand.bitCount;
			object.bitValue = rand.bitValue;
		} else {
			int detal = rand.bitCount - object.bitCount;
			if( Math.exp(detal / temperature) > Math.random() ) {
				object.bitCount = rand.bitCount;
				object.bitValue = rand.bitValue;
			}
			temperature *= coolingRate;
		}
		String string = "" + object.bitCount;
		System.out.println(string);
		if(object.bitCount != runBitCount) {
			getSimulateAneal(object);
		}
	}
}
