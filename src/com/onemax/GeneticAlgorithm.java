package com.onemax;

import java.util.Random;

enum GeneticSelection {
    WHEEL, 
    TOURNAMENT
}

public class GeneticAlgorithm extends BaseAlgorithm {
	
	// 物品的價值 
	private static int[] objectValue = { 220, 208, 198, 192, 180, 180, 165, 162, 160, 158,  
            				155, 130, 125, 122, 120, 118, 115, 110, 105, 101,  
            				100, 100, 98, 96, 95, 90, 88, 82, 80, 77,  
            				75, 73, 72, 70, 69, 66, 65, 63, 60, 58,  
            				56, 50, 30, 20, 15, 10, 8, 5, 3, 1 }; 
	// 物品重量
    private static int[] objectWeight = { 80, 82, 85, 70, 72, 70, 66, 50, 55, 25,  
            					50, 55, 40, 48, 50, 32, 22, 60, 30, 32,  
            					40, 38, 35, 32, 25, 28, 30, 22, 50, 30,  
            					45, 30, 60, 50, 20, 65, 20, 25, 30, 10,  
            					20, 25, 15, 10, 10, 10, 4, 4, 2, 1};	
    // 背包容量
    private static int bagCapacity = 1000;
    // 初始群體（父代） 
    private static int[][] parentPopulation;
    // 新的群體（子代） 
    private static int[][] childPopulation;
    // 群體規模
    private static int scale = 4;
    // 最大演化次數
    private static int maxGeneration = 4;
    // 群體的適應度
    private static int[] fitness;
    // 群體的總適應度
    private static int sumFitness;
    // 基因演算法選擇的方式
    private static GeneticSelection selection;
    // 群體中每個個體的機率(輪盤法)
    private static float[] wheelProbability;
    // 交配機率
    private static double corssoverRate = 0.8;
    // 突變機率
    private static double mutaionRate = 0.9;
    // 染色體長度
    private static int chromosomeLength = 50; //runBitCount;
    // 隨機
    private static Random random;
   
    
//    1. 適應度(fitness) -> 看看會不會過重，只保留不會超重的，這邊就會知道總重和價值
//    2. selection -> 用 wheel selection 和 tournament selection去算（所以最後可以畫出兩條線）
//    3. crossover
//    4. mutation
    
    public static void init() {
    	// 初始化參數
    	initialParameter();
    	// 初始化群體
    	initialPopulation();
    	System.out.println("====start=====");
		for (int k = 0; k < scale; k++) {
			for (int i = 0; i < chromosomeLength; i++) {
				System.out.print(parentPopulation[k][i] + ",");
			}
			System.out.println();
			System.out.println("---" + fitness[k] + " " + wheelProbability[k]);
		}
    	while(iterate < maxGeneration) {
    		iterate++;
    		// 計算適應度
        	calculateFitness();
        	// 開始演化
        	startEvaluate();
    	}
    	System.out.println("====end=====");
		for (int k = 0; k < scale; k++) {
			for (int i = 0; i < chromosomeLength; i++) {
				System.out.print(parentPopulation[k][i] + ",");
			}
			System.out.println();
			System.out.println("---" + fitness[k] + " " + wheelProbability[k]);
		}
    }
    
    private static void initialParameter() {
    	parentPopulation = new int[scale][chromosomeLength];  
    	childPopulation = new int[scale][chromosomeLength]; 
    	fitness = new int[scale];
    	wheelProbability = new float[scale];
    	selection = GeneticSelection.WHEEL;
    	random = new Random(System.currentTimeMillis());
    }
    
    private static void initialPopulation() {
    	for (int i = 0; i < scale; i++) {
    		for(int j = 0; j < chromosomeLength; j++) {
    			parentPopulation[i][j] = random.nextInt(2);
    		}
    	}
    }
    
    private static void calculateFitness() {
    	for(int i = 0; i < scale; i++) {
    		int value = 0;
        	int weight = 0;
        	for(int j = 0; j < chromosomeLength; j++) {
        		if (parentPopulation[i][j] == 1) {
        			value += objectValue[j];
        			weight += objectWeight[j];
        		}
        	}
    		fitness[i] = (weight > bagCapacity) ? 0 : value;
//    		System.out.println("fitness = " + fitness[i]);
    	}
    }
    
    private static void startEvaluate() {
    	switch(selection) {
		case WHEEL:
			wheelSelection();
		case TOURNAMENT:
			tournamentSelection();
		}
		calculateCrossover();
		calculateMutation();
		chagePopulation();
    }
    
    private static void wheelSelection() {
    	// calculate rate
    	calculateWheelProbability();
    	// selection
    	selection();
    }
    
    private static void calculateWheelProbability() {
    	int[] tempP = new int[scale];
    	double sum = 0;
    	sumFitness = 0;
    	for (int i = 0; i < scale; i++) {
    		tempP[i] = fitness[i];
    		sumFitness += tempP[i];
		}
    	sum = sumFitness;
    	for (int i = 0; i < scale; i++) {
    		if (i == 0) {
    			wheelProbability[i] = (float) (tempP[0] / sum);
    		} else {
    			wheelProbability[i] = (float) (tempP[i] / sum + wheelProbability[i - 1]);
    		}
//    		System.out.println("wheelProbability = " + wheelProbability[i]);
		}
    }
    
    private static void tournamentSelection() {
    	
    }
    
    private static void selection() {
    	switch(selection) {
			case WHEEL:
				selectPopulation();
			case TOURNAMENT:
				break;
    	}
    }
    
    private static void selectPopulation() {
    	float rand;
		int index;
		for (int i = 0; i < scale; i++) {
			rand = (float) (random.nextFloat());
			for (index = 0; index < scale; index++) {
				if (rand <= wheelProbability[index]) {
					break;
				}
			}
			copyChromosome(i, index);
		}
    }
    
    private static void copyChromosome(int newIndex, int oldIndex) {
    	for (int i = 0; i < chromosomeLength; i++) {
//    		System.out.println("newIndex = " + newIndex + ", oldIndex = " + oldIndex + ", i = " + i);
			childPopulation[newIndex][i] = parentPopulation[oldIndex][i];
		}
    }
    
    private static void calculateCrossover() {
    	float rand;
    	// 交叉方式交配(1,3)(2,4)
    	for (int i = 0; i < scale; i += 2) {
    		rand = random.nextFloat();
    		if (rand < corssoverRate) {
    			crossover(i, i + 1);
    		}
    	}
    }
    
    private static void crossover(int k1, int k2) {
    	int i, j, flag;
    	int rand1, rand2, temp;
    	rand1 = random.nextInt(chromosomeLength);
    	rand2 = random.nextInt(chromosomeLength);
		while (rand1 == rand2) {
			rand2 = random.nextInt(chromosomeLength);
		}
		// 確保rand1 < rand2
		if (rand1 > rand2) {
			temp = rand1;
			rand1 = rand2;
			rand2 = temp;
		}
		flag = rand2 - rand1 + 1; // 把染色體切成兩份的index
		for (i = 0, j = rand1; i < flag; i++, j++) {
			temp = childPopulation[k1][j];
			childPopulation[k1][j] = childPopulation[k2][j];
			childPopulation[k2][j] = temp;
		}
    }
    
    private static void calculateMutation() {
    	float rand;
    	// 交叉方式交配(1,3)(2,4)
    	for (int i = 0; i < scale; i += 2) {
    		rand = random.nextFloat();
    		if (rand < mutaionRate) {
    			mutation(i);
    		}
    	}
    }
    
    private static void mutation(int k1) {
    	int rand = random.nextInt(chromosomeLength);
    	childPopulation[k1][rand] ^= childPopulation[k1][rand];
    }
    
    private static void chagePopulation() {
    	// 將新群體複製到就群體中，繼續下一次的演化
    	for (int i = 0; i < scale; i++) {
    		for (int j = 0; j < chromosomeLength; j++) {
    			parentPopulation[i][j] = childPopulation[i][j];
    		}
    	}
    	
    	System.out.println("=========");
		for (int k = 0; k < scale; k++) {
			for (int i = 0; i < chromosomeLength; i++) {
				System.out.print(parentPopulation[k][i] + ",");
			}
			System.out.println();
			System.out.println("---" + fitness[k] + " " + wheelProbability[k]);
		}
    }
}
