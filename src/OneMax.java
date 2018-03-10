
public class OneMax {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int pow = 30;
		long value = 1<<pow;
		long maxBitCount = 0;
		long maxValue = 0;
		
		Time t = new Time();
		for(int i = 0; i < value; i++) {
			int count = Long.bitCount(i);
			if (count > maxBitCount) {
				maxBitCount = count;
				maxValue = value;
			}
		}
		
		System.out.println("spend time = " + t.end() + "ms");
		System.out.println("maxValue = " + maxValue + ", maxBitCount = " + maxBitCount);
	}
}
