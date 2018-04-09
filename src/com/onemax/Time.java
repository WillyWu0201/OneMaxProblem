package com.onemax;

public class Time {

	long start = System.currentTimeMillis();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public long end() {
		long end = System.currentTimeMillis();
		return end - start;
	}

}