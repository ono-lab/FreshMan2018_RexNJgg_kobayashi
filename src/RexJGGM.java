import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class RexJGGM {

	public static void main(String[] args)throws IOException{
		int dimension = 20;												//ベクトルの次元数
		double min = -5.0;												//初期化領域の最小値
		double max = +5.0;											//初期化領域の最大値
		int populationSize = 14 * dimension;							//集団サイズ
		int parentsSize = 7*dimension;								//親個体数
		int childrenSize = 15 * dimension;							//子個体生成数
		long maxGeneration = (long)(4* dimension * 1e4);		//打ち切り世代数
		int maxTrials = 3;												//試行回数

		String trialName = "RexJggKTabletP14K15";
		String logFilename = trialName + ".scv";
		int generation;
		double best;

		try {
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(logFilename,true)));
			pw.println("NoOfEvals,"+trialName+"_0,"+trialName+"_1,"+trialName+"_2");

			TRandom random = new TRandom();
			TJGG ga = new TJGG(dimension, populationSize, parentsSize, childrenSize, random);

			for(int trial = 0; trial < maxTrials; trial++) {
				best = Double.MAX_VALUE;	//最良評価値
				TPopulation population = new TPopulation(ga.initialize(min,max)); //集団の初期化

				for(generation = 0; generation < maxGeneration && best > 1e-7; generation++) {
					ga.nextGeneration();	//世代を一つ進める
					//ログを書き込む
					population.copyFrom(ga.getTPopulation());
					population.sortByEvaluationValue();
					best = population.getTIndividual(0).getEvaluationValue();
				}

				System.out.println("TrialNo:" + trial + ", Best:" + best);
			}

			//pw.println(generation+","+trial_1+","+trial_2+","+trial_3);
			pw.close();
		}catch(IOException e) {
			System.out.print(e);
		}
	}
}
