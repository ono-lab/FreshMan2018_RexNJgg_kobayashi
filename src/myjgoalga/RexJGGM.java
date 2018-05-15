package myjgoalga;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class RexJGGM {

	private static void putLogData(TTable log, String trialName, int trialNo, int index, long noOfEvals, double bestEvalValue) {
		log.putData(index, "NoOfEvals", noOfEvals);
		log.putData(index, trialName + "_" + trialNo, bestEvalValue);
	}

	public static void main(String[] args)throws IOException{
		int Dimension = 2;											//ベクトルの次元数
		int PopulationSize = 14 * Dimension;						//集団サイズ
		int ParentsSize = 5*Dimension;							//親個体数
		int ChildrenSize = 5 * Dimension;						//子個体生成数
		long MaxEvals = (long)(4* Dimension * 1e4);			//打ち切り評価回数
		int MaxTrials = 1;												//試行回数

		//集団の情報を書き込む処理
		PrintWriter pwPopulation = new PrintWriter("PopulationData"+"Po" + PopulationSize/Dimension + "Pa"+ ParentsSize/Dimension+ "K" + ChildrenSize/Dimension + ".csv");
		PrintWriter pwChildren = new PrintWriter("ChildrenData"+"Po" + PopulationSize/Dimension + "Pa" + ParentsSize/Dimension + "K" + ChildrenSize/Dimension + ".csv");
		PrintWriter pwParents = new PrintWriter("ParentsData"+"Po" + PopulationSize/Dimension + "Pa" + ParentsSize/Dimension + "K" + ChildrenSize/Dimension + ".csv"); //parentsはindexで扱う

		//書き込むファイル名
		String trialName = "MyRexJggKTablet" + "Po" + PopulationSize/Dimension +"Pa" + ParentsSize/Dimension +"K" + ChildrenSize/Dimension;
		String logFilename = trialName + ".csv";
		TTable log = new TTable();

		double bestEvalValue = Double.MAX_VALUE;	//集団中の最良個体の評価値
		Random random = new Random(10);

		TJGG ga = new TJGG(Dimension, PopulationSize,  ParentsSize, ChildrenSize, random);

		int logIndex = 0;
		int generation = 0;
		int trial = 0;
		while(trial < MaxTrials) {
			ga.initialize();
			bestEvalValue = ga.getBestEvaluationValue();	//集団中の最良個体の評価値の取得

			logIndex = 0;
			generation = 0;
			while( ChildrenSize*generation < MaxEvals && bestEvalValue > 1e-7) {
				ga.nextGeneration();
				bestEvalValue =ga.getBestEvaluationValue();
				generation++;
				if(generation % 1 == 0) {
					System.out.println("generation:" +generation +" BestEval:" + bestEvalValue);
					putLogData(log, trialName, trial, logIndex, generation, bestEvalValue);
					pwParents.println(ga.getfNoOfSelParents());
					ga.getPopulation().writeTo(pwPopulation);
					ga.getChildren().writeTo(pwChildren);
					logIndex++;
				}
			}
			putLogData(log, trialName, trial, logIndex, generation, bestEvalValue);
			trial++;
		}

		pwParents.close();
		pwChildren.close();
		pwPopulation.close();
		log.writeTo(logFilename);
	}

}
