package myjgoalga;

import java.util.Random;

public class TJGG {
	/** 次元 */
	private int fDimension;
	/** 集団サイズ */
	private  int fPopulationSize;
	/** 親個体のサイズ*/
	private int fParentsSize;
	/** 子個体のサイズ*/
	private int fChildrenSize;
	/** 集団 */
	private TPopulation fPopulation;
	/** 親個体 */
	private TPopulation fParents;
	/** 子個体 */
	private TPopulation fChildren;
	/** 子個体生成器 */
	private TRex fRex;
	/** 乱数生成器 */
	private Random fRandom;
	/** 選択された親個体の番号 */
	private int[] fNoOfSelParents;

	/**
	 * コンストラクタ
	 */
	public TJGG(int Dimension, int PopulationSize,  int ParentsSize, int ChildrenSize, Random random) {
		fDimension = Dimension;
		fPopulationSize = PopulationSize;
		fParentsSize = ParentsSize;
		fChildrenSize = ChildrenSize;
		fRandom = random;
		fRex = new TRex(random);
		fNoOfSelParents = new int[fParentsSize];
	}

	public void initialize() {
		//集団の初期化
		//fPopulationについての初期化
		fPopulation = new TPopulation();
		fPopulation.setPopulationSize(fPopulationSize);
		for(int i = 0; i < fPopulationSize; i++) {
			fPopulation.getTIndividual(i).setEvaluationValue(0.0);
			fPopulation.getTIndividual(i).getTVector().setDimension(fDimension);
		}

		//fParentsについての初期化
		fParents = new TPopulation();
		fParents.setPopulationSize(fParentsSize);
		for(int i = 0; i < fParentsSize; i++) {
			fParents.getTIndividual(i).setEvaluationValue(0.0);
			fParents.getTIndividual(i).getTVector().setDimension(fDimension);
		}

		//fChildrenについての初期化
		fChildren = new TPopulation();
		fChildren.setPopulationSize(fChildrenSize);
		for(int i = 0; i < fChildrenSize; i++) {
			fChildren.getTIndividual(i).setEvaluationValue(0.0);
			fChildren.getTIndividual(i).getTVector().setDimension(fDimension);
		}

		//個体の生成
		generateIndividuals();

		//評価値の設定
		evalAllIndividuals(fPopulation);
	}

	/**
	 * 一応集団の評価値と個体のベクトルの数値を0にする
	 * @param population
	 */
	public void clearPopulation(TPopulation population) {
		int PopulationSize = population.getPopulationSize();

		for(int i = 0; i < PopulationSize; i++) {
			population.getTIndividual(i).setEvaluationValue(0.0);
			for(int j = 0; j < fDimension; j++) {
				population.getTIndividual(i).getTVector().setElement(j, 0.0);
			}
		}
	}

	/**
	 * [-5,5]の一様乱数に従いランダムにfPopulaitonSize個の個体を生成
	 */
	private void generateIndividuals() {
		double min = -5.0;
		double max = 5.0;

		for(int i = 0; i < fPopulationSize; i++) {
			for(int j = 0; j < fDimension; j++) {
				fPopulation.getTIndividual(i).getTVector().setElement(j, fRandom.nextDouble() * (max - min) + min);
			}
		}
	}

	/**
	 * 集団中の個体すべてを評価する
	 * @param population
	 */
	private void evalAllIndividuals(TPopulation population) {
		int PopulationSize = population.getPopulationSize();	//引数の集団サイズ

		for(int i = 0; i < PopulationSize; i++) {
			population.getTIndividual(i).setEvaluationValue(TKtablet.ktablet(population.getTIndividual(i).getTVector()));
		}
	}

	/**
	 * 交叉に参加する親を選ぶ
	 */
	private void selectParents() {
		for(int i = 0; i < fParentsSize; i++) {
			//親個体が元の集団のどの場所に位置するか
			fNoOfSelParents[i] = fRandom.nextInt(fPopulationSize);
			//親個体の選択
			fParents.getTIndividual(i).copyFrom(fPopulation.getTIndividual(fNoOfSelParents[i]));
		}
	}

	/**
	 * 世代交代を行う
	 */
	private void generationalchange() {
		for(int i = 0; i < fParentsSize; i++) {
			fPopulation.getTIndividual(fNoOfSelParents[i]).copyFrom(fChildren.getTIndividual(i));
		}
	}

	/**
	 * 世代を一つ進める
	 */
	public void nextGeneration() {
		clearPopulation(fChildren);
		clearPopulation(fParents);

		//複製に用いる親個体の選択
		selectParents();

		//子個体の生成
		fRex.makeOffSpring(fParents, fChildren);

		//子個体の評価
		evalAllIndividuals(fChildren);

		//子個体のソート(バブルソート)
		fChildren.sortByEvaluationValue();

		//世代交代
		generationalchange();

	}

	/**
	 * 集団中の最良個体を返す
	 * @return 集団中の最良個体
	 */
	public TIndividual getBestIndividual() {
		fPopulation.sortByEvaluationValue();
		return fPopulation.getTIndividual(0);
	}

	/**
	 * 集団中の最良個体の評価値を返す
	 * @return 集団中の最良個体の評価値
	 */
	public double getBestEvaluationValue() {
		return getBestIndividual().getEvaluationValue();
	}

	public TPopulation getPopulation() {
		return fPopulation;
	}

	public TPopulation getParents() {
		return fParents;
	}

	public TPopulation getChildren() {
		return fChildren;
	}

	public String getfNoOfSelParents() {
		StringBuilder str = new StringBuilder();
		for(int i = 0; i < fNoOfSelParents.length - 1; i++) {
			str.append(fNoOfSelParents[i] + ",");
		}
		str.append(fNoOfSelParents[fNoOfSelParents.length-1]);
		return str.toString();
	}
}
