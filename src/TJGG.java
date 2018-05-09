import java.util.Random;

public class TJGG {
	/** ベクトルの次元数 */
	private int fDimension;
	/** 集団 */
	private TPopulation fPopulation;
	/** 集団サイズ */
	private int fPopulationSize;
	/** 親個体集合 */
	private TPopulation fParents;
	/** 親個体数 */
	private int fParentsSize;
	/** 子個体集合 */
	private TPopulation fChildren;
	/** 生成する子個体数 */
	private int fChildrenSize;
	/** 乱数生成器 */
	private TRandom fRandom;
	/** 子個体生成器 */
	private TRex fRex;

	private int seed = 10;

	public TJGG(int dimension , int poulationSize,int ParentsSize, int childrenSize, TRandom random) {
		fDimension = dimension;
		fPopulationSize = poulationSize;
		fChildrenSize = childrenSize;
		fRandom = random;
		fParentsSize = ParentsSize;
	}

	/**
	 * 初期集団の生成
	 * @return 生成された初期集団
	 */
	public TPopulation initialize(double min, double max) {
		Random random = new Random(seed);
		fRex = new TRex(fDimension, fParentsSize, fRandom, TRex.ProbDist.GAUSSIAN);

		//TPopulationの宣言
		fPopulation 	= new TPopulation(fPopulationSize);
		fParents 		= new TPopulation(fParentsSize);
		fChildren 		= new TPopulation(fChildrenSize);

		//ベクトルに次元をセット
		for(int i = 0; i < fPopulationSize; i++) {
			fPopulation.getTIndividual(i).getTVector().setDimension(fDimension);

			for(int j = 0; j < fDimension; j++) {
				fPopulation.getTIndividual(i).getTVector().setElement(j, random.nextDouble()*(max - min) + min);
			}
		}

		for(int i = 0; i < fParentsSize; i++) {
			fParents.getTIndividual(i).getTVector().setDimension(fDimension);
		}

		for(int i = 0; i < fChildrenSize; i++) {
			fChildren.getTIndividual(i).getTVector().setDimension(fDimension);
		}

		clear(fParents);
		clear(fChildren);

		return fPopulation;
	}

	public void selectParents() {
		int index;
		Random rand = new Random(seed);
		for(int i = 0; i < fParentsSize; i++) {
			index = rand.nextInt(fPopulationSize);
			fParents.getTIndividual(i).copyFrom(fPopulation.getTIndividual(index));
			fPopulation.getTIndividual(index).setEvaluationValue(Double.MAX_VALUE);
		}
	}

	public void setEvaluationValue(TPopulation pop) {
		for(int i = 0; i < pop.getPopulationSize(); i++) {
			pop.getTIndividual(i).setEvaluationValue(TKtablet.ktablet(pop.getTIndividual(i).getTVector()));
		}
	}

	/**
	 * 子個体を昇順にソートして先頭(上位)個体を集団に戻す
	 */
	public void selectPopulation() {
		fPopulation.sortByEvaluationValue();//後ろにいる個体が淘汰される個体(親個体)
		fChildren.sortByEvaluationValue();
		for(int i = 0; i < fParentsSize; i++) {
			fPopulation.getTIndividual(fPopulationSize-1-i).copyFrom(fChildren.getTIndividual(i));
		}
	}

	public void nextGeneration() {
		clear(fParents);
		clear(fChildren);
		setEvaluationValue(fPopulation);
		selectParents();									//親個体の選択
		fRex.makeOffspring(fParents, fChildren);		//子個体の生成(同じ個体しか生成されない)
		System.out.println(fChildren.toString());
		setEvaluationValue(fChildren);					//子個体の評価
		selectPopulation();								//親個体を取り除き上位の子個体を追加
	}

	public TPopulation makeOffspring() {
		clear(fParents);
		clear(fChildren);
		fRex.makeOffspring(fParents, fChildren);
		return fChildren;
	}



	/**
	 * 集団のクリア
	 */
	public void clear(TPopulation pop) {
		for(int i = 0; i < pop.getPopulationSize(); i++) {
			for(int j = 0; j < fDimension; j++) {
				pop.getTIndividual(i).getTVector().setElement(j, 0.0);
			}
		}
	}

	public TPopulation getTPopulation() {
		return fPopulation;
	}
}
