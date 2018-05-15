package myjgoalga;
import java.util.Random;

public class TRandom{ //Randomのラッパークラス
	private Random random;
	private int seed = 10;

	public TRandom() {
		random = new Random(seed);
	}

	public double nextInt(int bound) {
		return random.nextInt(bound);
	}

	public double nextDouble() {
		return random.nextDouble();
	}

	public double nextGaussian() {
		return random.nextGaussian();
	}


}
